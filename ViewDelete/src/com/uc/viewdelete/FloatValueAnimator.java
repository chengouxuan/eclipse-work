/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uc.viewdelete;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

public class FloatValueAnimator implements Cloneable {
	
	private static final long DEFAULT_FRAME_DELAY = 10;
	
	/**
	 * Messages sent to timing handler: START is sent when an animation first begins, FRAME is sent
	 * by the handler to itself to process the next animation frame
	 */
	static final int ANIMATION_START = 0;
	static final int ANIMATION_FRAME = 1;
	
	/**
	 * Values used with internal variable mPlayingState to indicate the current state of an
	 * animation.
	 */
	static final int STOPPED = 0; // Not yet playing
	static final int RUNNING = 1; // Playing normally
	static final int SEEKED = 2; // Seeked to some time value
	
	// The first time that the animation's animateFrame() method is called. This time is used to
	// determine elapsed time (and therefore the elapsed fraction) in subsequent calls
	// to animateFrame()
	long mStartTime;
	
	/**
	 * Set when setCurrentPlayTime() is called. If negative, animation is not currently seeked to a
	 * value.
	 */
	long mSeekTime = -1;
	
	// TODO: We access the following ThreadLocal variables often, some of them on every update.
	// If ThreadLocal access is significantly expensive, we may want to put all of these
	// fields into a structure sot hat we just access ThreadLocal once to get the reference
	// to that structure, then access the structure directly for each field.
	
	// The static sAnimationHandler processes the internal timing loop on which all animations
	// are based
	private static ThreadLocal<AnimationHandler> sAnimationHandler = new ThreadLocal<AnimationHandler>();
	
	// The per-thread list of all active animations
	private static final ThreadLocal<ArrayList<FloatValueAnimator>> sAnimations = new ThreadLocal<ArrayList<FloatValueAnimator>>() {
		@Override
		protected ArrayList<FloatValueAnimator> initialValue() {
			return new ArrayList<FloatValueAnimator>();
		}
	};
	
	// The per-thread set of animations to be started on the next animation frame
	private static final ThreadLocal<ArrayList<FloatValueAnimator>> sPendingAnimations = new ThreadLocal<ArrayList<FloatValueAnimator>>() {
		@Override
		protected ArrayList<FloatValueAnimator> initialValue() {
			return new ArrayList<FloatValueAnimator>();
		}
	};
	
	/**
	 * Internal per-thread collections used to avoid set collisions as animations start and end
	 * while being processed.
	 */
	private static final ThreadLocal<ArrayList<FloatValueAnimator>> sDelayedAnims = new ThreadLocal<ArrayList<FloatValueAnimator>>() {
		@Override
		protected ArrayList<FloatValueAnimator> initialValue() {
			return new ArrayList<FloatValueAnimator>();
		}
	};
	
	private static final ThreadLocal<ArrayList<FloatValueAnimator>> sEndingAnims = new ThreadLocal<ArrayList<FloatValueAnimator>>() {
		@Override
		protected ArrayList<FloatValueAnimator> initialValue() {
			return new ArrayList<FloatValueAnimator>();
		}
	};
	
	private static final ThreadLocal<ArrayList<FloatValueAnimator>> sReadyAnims = new ThreadLocal<ArrayList<FloatValueAnimator>>() {
		@Override
		protected ArrayList<FloatValueAnimator> initialValue() {
			return new ArrayList<FloatValueAnimator>();
		}
	};
	
	/**
	 * The set of listeners to be sent events through the life of an animation.
	 */
	ArrayList<AnimatorListener> mListeners = null;
	
	// The time interpolator to be used if none is set on the animation
	private static final LinearInterpolator sDefaultInterpolator = new LinearInterpolator();
	
	/**
	 * Used to indicate whether the animation is currently playing in reverse. This causes the
	 * elapsed fraction to be inverted to calculate the appropriate values.
	 */
	private boolean mPlayingBackwards = false;
	
	/**
	 * This variable tracks the current iteration that is playing. When mCurrentIteration exceeds
	 * the repeatCount (if repeatCount!=INFINITE), the animation ends
	 */
	private int mCurrentIteration = 0;
	
	/**
	 * Tracks current elapsed/eased fraction, for querying in getAnimatedFraction().
	 */
	private float mCurrentFraction = 0f;
	
	/**
	 * Tracks whether a startDelay'd animation has begun playing through the startDelay.
	 */
	private boolean mStartedDelay = false;
	
	/**
	 * Tracks the time at which the animation began playing through its startDelay. This is
	 * different from the mStartTime variable, which is used to track when the animation became
	 * active (which is when the startDelay expired and the animation was added to the active
	 * animations list).
	 */
	private long mDelayStartTime;
	
	/**
	 * Flag that represents the current state of the animation. Used to figure out when to start an
	 * animation (if state == STOPPED). Also used to end an animation that has been cancel()'d or
	 * end()'d since the last animation frame. Possible values are STOPPED, RUNNING, SEEKED.
	 */
	int mPlayingState = STOPPED;
	
	/**
	 * Additional playing state to indicate whether an animator has been start()'d. There is some
	 * lag between a call to start() and the first animation frame. We should still note that the
	 * animation has been started, even if it's first animation frame has not yet happened, and
	 * reflect that state in isRunning(). Note that delayed animations are different: they are not
	 * started until their first animation frame, which occurs after their delay elapses.
	 */
	private boolean mRunning = false;
	
	/**
	 * Additional playing state to indicate whether an animator has been start()'d, whether or not
	 * there is a nonzero startDelay.
	 */
	private boolean mStarted = false;
	
	/**
	 * Flag that denotes whether the animation is set up and ready to go. Used to set up animation
	 * that has not yet been started.
	 */
	boolean mInitialized = false;
	
	private float[] mValues;
	
	private float[] mCurrentValue = new float[1];
	
	// How long the animation should last in ms
	private long mDuration = 300;
	
	// The amount of time in ms to delay starting the animation after start() is called
	private long mStartDelay = 0;
	
	// The number of milliseconds between animation frames
	private static long sFrameDelay = DEFAULT_FRAME_DELAY;
	
	// The number of times the animation will repeat. The default is 0, which means the animation
	// will play only once
	private int mRepeatCount = 0;
	
	/**
	 * The type of repetition that will occur when repeatMode is nonzero. RESTART means the
	 * animation will start from the beginning on every new cycle. REVERSE means the animation will
	 * reverse directions on each iteration.
	 */
	private int mRepeatMode = RESTART;
	
	/**
	 * The time interpolator to be used. The elapsed fraction of the animation will be passed
	 * through this interpolator to calculate the interpolated fraction, which is then used to
	 * calculate the animated values.
	 */
	private Interpolator mInterpolator = sDefaultInterpolator;
	
	/**
	 * The set of listeners to be sent events through the life of an animation.
	 */
	private ArrayList<AnimatorUpdateListener> mUpdateListeners = null;
	
	/**
	 * When the animation reaches the end and <code>repeatCount</code> is INFINITE or a positive
	 * value, the animation restarts from the beginning.
	 */
	public static final int RESTART = 1;
	/**
	 * When the animation reaches the end and <code>repeatCount</code> is INFINITE or a positive
	 * value, the animation reverses direction on every iteration.
	 */
	public static final int REVERSE = 2;
	/**
	 * This value used used with the {@link #setRepeatCount(int)} property to repeat the animation
	 * indefinitely.
	 */
	public static final int INFINITE = -1;
	
	public FloatValueAnimator() {
	}
	
	public static FloatValueAnimator ofFloat(float... values) {
		FloatValueAnimator anim = new FloatValueAnimator();
		anim.setValues(values);
		return anim;
	}
	
	public void setValues(float... values) {
		if (values == null || values.length == 0) {
			throw new IllegalArgumentException("value cannot be null");
		}
		
		mValues = new float[values.length];
		mCurrentValue = new float[(values.length + 1) / 2];
		for (int i = 0; i < values.length; i++) {
			mValues[i] = values[i];
		}
		mInitialized = false;
	}
	
	private void initAnimation() {
		if (!mInitialized) {
			mInitialized = true;
		}
	}
	
	/**
	 * Sets the length of the animation. The default duration is 300 milliseconds.
	 * 
	 * @param duration
	 *            The length of the animation, in milliseconds. This value cannot be negative.
	 * @return ValueAnimator The object called with setDuration(). This return value makes it easier
	 *         to compose statements together that construct and then set the duration, as in
	 *         <code>ValueAnimator.ofInt(0, 10).setDuration(500).start()</code>.
	 */
	public FloatValueAnimator setDuration(long duration) {
		if (duration < 0) {
			throw new IllegalArgumentException("Animators cannot have negative duration: "
					+ duration);
		}
		mDuration = duration;
		return this;
	}
	
	/**
	 * Gets the length of the animation. The default duration is 300 milliseconds.
	 * 
	 * @return The length of the animation, in milliseconds.
	 */
	public long getDuration() {
		return mDuration;
	}
	
	/**
	 * Sets the position of the animation to the specified point in time. This time should be
	 * between 0 and the total duration of the animation, including any repetition. If the animation
	 * has not yet been started, then it will not advance forward after it is set to this time; it
	 * will simply set the time to this value and perform any appropriate actions based on that
	 * time. If the animation is already running, then setCurrentPlayTime() will set the current
	 * playing time to this value and continue playing from that point.
	 * 
	 * @param playTime
	 *            The time, in milliseconds, to which the animation is advanced or rewound.
	 */
	public void setCurrentPlayTime(long playTime) {
		initAnimation();
		long currentTime = AnimationUtils.currentAnimationTimeMillis();
		if (mPlayingState != RUNNING) {
			mSeekTime = playTime;
			mPlayingState = SEEKED;
		}
		mStartTime = currentTime - playTime;
		animationFrame(currentTime);
	}
	
	/**
	 * Gets the current position of the animation in time, which is equal to the current time minus
	 * the time that the animation started. An animation that is not yet started will return a value
	 * of zero.
	 * 
	 * @return The current position in time of the animation.
	 */
	public long getCurrentPlayTime() {
		if (!mInitialized || mPlayingState == STOPPED) {
			return 0;
		}
		return AnimationUtils.currentAnimationTimeMillis() - mStartTime;
	}
	
	/**
	 * This custom, static handler handles the timing pulse that is shared by all active animations.
	 * This approach ensures that the setting of animation values will happen on the UI thread and
	 * that all animations will share the same times for calculating their values, which makes
	 * synchronizing animations possible.
	 * 
	 */
	private static class AnimationHandler extends Handler {
		/**
		 * There are only two messages that we care about: ANIMATION_START and ANIMATION_FRAME. The
		 * START message is sent when an animation's start() method is called. It cannot start
		 * synchronously when start() is called because the call may be on the wrong thread, and it
		 * would also not be synchronized with other animations because it would not start on a
		 * common timing pulse. So each animation sends a START message to the handler, which causes
		 * the handler to place the animation on the active animations queue and start processing
		 * frames for that animation. The FRAME message is the one that is sent over and over while
		 * there are any active animations to process.
		 */
		@Override
		public void handleMessage(Message msg) {
			boolean callAgain = true;
			ArrayList<FloatValueAnimator> animations = sAnimations.get();
			ArrayList<FloatValueAnimator> delayedAnims = sDelayedAnims.get();
			switch (msg.what) {
			// TODO: should we avoid sending frame message when starting if we
			// were already running?
				case ANIMATION_START:
					ArrayList<FloatValueAnimator> pendingAnimations = sPendingAnimations.get();
					if (animations.size() > 0 || delayedAnims.size() > 0) {
						callAgain = false;
					}
					// pendingAnims holds any animations that have requested to be started
					// We're going to clear sPendingAnimations, but starting animation may
					// cause more to be added to the pending list (for example, if one animation
					// starting triggers another starting). So we loop until sPendingAnimations
					// is empty.
					while (pendingAnimations.size() > 0) {
						ArrayList<FloatValueAnimator> pendingCopy = (ArrayList<FloatValueAnimator>) pendingAnimations
								.clone();
						pendingAnimations.clear();
						int count = pendingCopy.size();
						for (int i = 0; i < count; ++i) {
							FloatValueAnimator anim = pendingCopy.get(i);
							// If the animation has a startDelay, place it on the delayed list
							if (anim.mStartDelay == 0) {
								anim.startAnimation();
							} else {
								delayedAnims.add(anim);
							}
						}
					}
					// fall through to process first frame of new animations
				case ANIMATION_FRAME:
					// currentTime holds the common time for all animations processed
					// during this frame
					long currentTime = AnimationUtils.currentAnimationTimeMillis();
					ArrayList<FloatValueAnimator> readyAnims = sReadyAnims.get();
					ArrayList<FloatValueAnimator> endingAnims = sEndingAnims.get();
					
					// First, process animations currently sitting on the delayed queue, adding
					// them to the active animations if they are ready
					int numDelayedAnims = delayedAnims.size();
					for (int i = 0; i < numDelayedAnims; ++i) {
						FloatValueAnimator anim = delayedAnims.get(i);
						if (anim.delayedAnimationFrame(currentTime)) {
							readyAnims.add(anim);
						}
					}
					int numReadyAnims = readyAnims.size();
					if (numReadyAnims > 0) {
						for (int i = 0; i < numReadyAnims; ++i) {
							FloatValueAnimator anim = readyAnims.get(i);
							anim.startAnimation();
							anim.mRunning = true;
							delayedAnims.remove(anim);
						}
						readyAnims.clear();
					}
					
					// Now process all active animations. The return value from animationFrame()
					// tells the handler whether it should now be ended
					int numAnims = animations.size();
					int i = 0;
					while (i < numAnims) {
						FloatValueAnimator anim = animations.get(i);
						if (anim.animationFrame(currentTime)) {
							endingAnims.add(anim);
						}
						if (animations.size() == numAnims) {
							++i;
						} else {
							// An animation might be canceled or ended by client code
							// during the animation frame. Check to see if this happened by
							// seeing whether the current index is the same as it was before
							// calling animationFrame(). Another approach would be to copy
							// animations to a temporary list and process that list instead,
							// but that entails garbage and processing overhead that would
							// be nice to avoid.
							--numAnims;
							endingAnims.remove(anim);
						}
					}
					if (endingAnims.size() > 0) {
						for (i = 0; i < endingAnims.size(); ++i) {
							endingAnims.get(i).endAnimation();
						}
						endingAnims.clear();
					}
					
					// If there are still active or delayed animations, call the handler again
					// after the frameDelay
					if (callAgain && (!animations.isEmpty() || !delayedAnims.isEmpty())) {
						sendEmptyMessageDelayed(ANIMATION_FRAME, Math.max(0, sFrameDelay
								- (AnimationUtils.currentAnimationTimeMillis() - currentTime)));
					}
					break;
			}
		}
	}
	
	/**
	 * The amount of time, in milliseconds, to delay starting the animation after {@link #start()}
	 * is called.
	 * 
	 * @return the number of milliseconds to delay running the animation
	 */
	public long getStartDelay() {
		return mStartDelay;
	}
	
	/**
	 * The amount of time, in milliseconds, to delay starting the animation after {@link #start()}
	 * is called.
	 * 
	 * @param startDelay
	 *            The amount of the delay, in milliseconds
	 */
	public void setStartDelay(long startDelay) {
		this.mStartDelay = startDelay;
	}
	
	/**
	 * The amount of time, in milliseconds, between each frame of the animation. This is a requested
	 * time that the animation will attempt to honor, but the actual delay between frames may be
	 * different, depending on system load and capabilities. This is a static function because the
	 * same delay will be applied to all animations, since they are all run off of a single timing
	 * loop.
	 * 
	 * @return the requested time between frames, in milliseconds
	 */
	public static long getFrameDelay() {
		return sFrameDelay;
	}
	
	/**
	 * The amount of time, in milliseconds, between each frame of the animation. This is a requested
	 * time that the animation will attempt to honor, but the actual delay between frames may be
	 * different, depending on system load and capabilities. This is a static function because the
	 * same delay will be applied to all animations, since they are all run off of a single timing
	 * loop.
	 * 
	 * @param frameDelay
	 *            the requested time between frames, in milliseconds
	 */
	public static void setFrameDelay(long frameDelay) {
		sFrameDelay = frameDelay;
	}
	
	/**
	 * Sets how many times the animation should be repeated. If the repeat count is 0, the animation
	 * is never repeated. If the repeat count is greater than 0 or {@link #INFINITE}, the repeat
	 * mode will be taken into account. The repeat count is 0 by default.
	 * 
	 * @param value
	 *            the number of times the animation should be repeated
	 */
	public void setRepeatCount(int value) {
		mRepeatCount = value;
	}
	
	/**
	 * Defines how many times the animation should repeat. The default value is 0.
	 * 
	 * @return the number of times the animation should repeat, or {@link #INFINITE}
	 */
	public int getRepeatCount() {
		return mRepeatCount;
	}
	
	/**
	 * Defines what this animation should do when it reaches the end. This setting is applied only
	 * when the repeat count is either greater than 0 or {@link #INFINITE}. Defaults to
	 * {@link #RESTART}.
	 * 
	 * @param value
	 *            {@link #RESTART} or {@link #REVERSE}
	 */
	public void setRepeatMode(int value) {
		mRepeatMode = value;
	}
	
	/**
	 * Defines what this animation should do when it reaches the end.
	 * 
	 * @return either one of {@link #REVERSE} or {@link #RESTART}
	 */
	public int getRepeatMode() {
		return mRepeatMode;
	}
	
	/**
	 * Adds a listener to the set of listeners that are sent update events through the life of an
	 * animation. This method is called on all listeners for every frame of the animation, after the
	 * values for the animation have been calculated.
	 * 
	 * @param listener
	 *            the listener to be added to the current set of listeners for this animation.
	 */
	public void addUpdateListener(AnimatorUpdateListener listener) {
		if (mUpdateListeners == null) {
			mUpdateListeners = new ArrayList<AnimatorUpdateListener>();
		}
		mUpdateListeners.add(listener);
	}
	
	/**
	 * Removes all listeners from the set listening to frame updates for this animation.
	 */
	public void removeAllUpdateListeners() {
		if (mUpdateListeners == null) {
			return;
		}
		mUpdateListeners.clear();
		mUpdateListeners = null;
	}
	
	/**
	 * Removes a listener from the set listening to frame updates for this animation.
	 * 
	 * @param listener
	 *            the listener to be removed from the current set of update listeners for this
	 *            animation.
	 */
	public void removeUpdateListener(AnimatorUpdateListener listener) {
		if (mUpdateListeners == null) {
			return;
		}
		mUpdateListeners.remove(listener);
		if (mUpdateListeners.size() == 0) {
			mUpdateListeners = null;
		}
	}
	
	/**
	 * The time interpolator used in calculating the elapsed fraction of this animation. The
	 * interpolator determines whether the animation runs with linear or non-linear motion, such as
	 * acceleration and deceleration. The default value is
	 * {@link android.view.animation.AccelerateDecelerateInterpolator}
	 * 
	 * @param value
	 *            the interpolator to be used by this animation. A value of <code>null</code> will
	 *            result in linear interpolation.
	 */
	public void setInterpolator(Interpolator value) {
		if (value != null) {
			mInterpolator = value;
		} else {
			mInterpolator = new LinearInterpolator();
		}
	}
	
	/**
	 * Returns the timing interpolator that this ValueAnimator uses.
	 * 
	 * @return The timing interpolator for this ValueAnimator.
	 */
	public Interpolator getInterpolator() {
		return mInterpolator;
	}
	
	/**
	 * Start the animation playing. This version of start() takes a boolean flag that indicates
	 * whether the animation should play in reverse. The flag is usually false, but may be set to
	 * true if called from the reverse() method.
	 * 
	 * <p>
	 * The animation started by calling this method will be run on the thread that called this
	 * method. This thread should have a Looper on it (a runtime exception will be thrown if this is
	 * not the case). Also, if the animation will animate properties of objects in the view
	 * hierarchy, then the calling thread should be the UI thread for that view hierarchy.
	 * </p>
	 * 
	 * @param playBackwards
	 *            Whether the ValueAnimator should start playing in reverse.
	 */
	private void start(boolean playBackwards) {
		if (Looper.myLooper() == null) {
			throw new AndroidRuntimeException("Animators may only be run on Looper threads");
		}
		mPlayingBackwards = playBackwards;
		mCurrentIteration = 0;
		mPlayingState = STOPPED;
		mStarted = true;
		mStartedDelay = false;
		sPendingAnimations.get().add(this);
		if (mStartDelay == 0) {
			// This sets the initial value of the animation, prior to actually starting it running
			// setCurrentPlayTime(getCurrentPlayTime());
			mPlayingState = STOPPED;
			mRunning = true;
			
			if (mListeners != null) {
				ArrayList<AnimatorListener> tmpListeners = (ArrayList<AnimatorListener>) mListeners
						.clone();
				int numListeners = tmpListeners.size();
				for (int i = 0; i < numListeners; ++i) {
					tmpListeners.get(i).onAnimationStart(this);
				}
			}
		}
		AnimationHandler animationHandler = sAnimationHandler.get();
		if (animationHandler == null) {
			animationHandler = new AnimationHandler();
			sAnimationHandler.set(animationHandler);
		}
		animationHandler.sendEmptyMessage(ANIMATION_START);
	}
	
	public void start() {
		start(false);
	}
	
	public void cancel() {
		// Only cancel if the animation is actually running or has been started and is about
		// to run
		if (mPlayingState != STOPPED || sPendingAnimations.get().contains(this)
				|| sDelayedAnims.get().contains(this)) {
			// Only notify listeners if the animator has actually started
			if (mRunning && mListeners != null) {
				ArrayList<AnimatorListener> tmpListeners = (ArrayList<AnimatorListener>) mListeners
						.clone();
				for (AnimatorListener listener : tmpListeners) {
					listener.onAnimationCancel(this);
				}
			}
			endAnimation();
		}
	}
	
	public void end() {
		if (!sAnimations.get().contains(this) && !sPendingAnimations.get().contains(this)) {
			// Special case if the animation has not yet started; get it ready for ending
			mStartedDelay = false;
			startAnimation();
		} else if (!mInitialized) {
			initAnimation();
		}
		// The final value set on the target varies, depending on whether the animation
		// was supposed to repeat an odd number of times
		if (mRepeatCount > 0 && (mRepeatCount & 0x01) == 1) {
			animateValue(0f);
		} else {
			animateValue(1f);
		}
		endAnimation();
	}
	
	public boolean isRunning() {
		return (mPlayingState == RUNNING || mRunning);
	}
	
	public boolean isStarted() {
		return mStarted;
	}
	
	/**
	 * Plays the ValueAnimator in reverse. If the animation is already running, it will stop itself
	 * and play backwards from the point reached when reverse was called. If the animation is not
	 * currently running, then it will start from the end and play backwards. This behavior is only
	 * set for the current animation; future playing of the animation will use the default behavior
	 * of playing forward.
	 */
	public void reverse() {
		mPlayingBackwards = !mPlayingBackwards;
		if (mPlayingState == RUNNING) {
			long currentTime = AnimationUtils.currentAnimationTimeMillis();
			long currentPlayTime = currentTime - mStartTime;
			long timeLeft = mDuration - currentPlayTime;
			mStartTime = currentTime - timeLeft;
		} else {
			start(true);
		}
	}
	
	/**
	 * Called internally to end an animation by removing it from the animations list. Must be called
	 * on the UI thread.
	 */
	private void endAnimation() {
		sAnimations.get().remove(this);
		sPendingAnimations.get().remove(this);
		sDelayedAnims.get().remove(this);
		mPlayingState = STOPPED;
		if (mRunning && mListeners != null) {
			ArrayList<AnimatorListener> tmpListeners = (ArrayList<AnimatorListener>) mListeners
					.clone();
			int numListeners = tmpListeners.size();
			for (int i = 0; i < numListeners; ++i) {
				tmpListeners.get(i).onAnimationEnd(this);
			}
		}
		mRunning = false;
		mStarted = false;
	}
	
	/**
	 * Called internally to start an animation by adding it to the active animations list. Must be
	 * called on the UI thread.
	 */
	private void startAnimation() {
		initAnimation();
		sAnimations.get().add(this);
		if (mStartDelay > 0 && mListeners != null) {
			// Listeners were already notified in start() if startDelay is 0; this is
			// just for delayed animations
			ArrayList<AnimatorListener> tmpListeners = (ArrayList<AnimatorListener>) mListeners
					.clone();
			int numListeners = tmpListeners.size();
			for (int i = 0; i < numListeners; ++i) {
				tmpListeners.get(i).onAnimationStart(this);
			}
		}
	}
	
	/**
	 * Internal function called to process an animation frame on an animation that is currently
	 * sleeping through its <code>startDelay</code> phase. The return value indicates whether it
	 * should be woken up and put on the active animations queue.
	 * 
	 * @param currentTime
	 *            The current animation time, used to calculate whether the animation has exceeded
	 *            its <code>startDelay</code> and should be started.
	 * @return True if the animation's <code>startDelay</code> has been exceeded and the animation
	 *         should be added to the set of active animations.
	 */
	private boolean delayedAnimationFrame(long currentTime) {
		if (!mStartedDelay) {
			mStartedDelay = true;
			mDelayStartTime = currentTime;
		} else {
			long deltaTime = currentTime - mDelayStartTime;
			if (deltaTime > mStartDelay) {
				// startDelay ended - start the anim and record the
				// mStartTime appropriately
				mStartTime = currentTime - (deltaTime - mStartDelay);
				mPlayingState = RUNNING;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This internal function processes a single animation frame for a given animation. The
	 * currentTime parameter is the timing pulse sent by the handler, used to calculate the elapsed
	 * duration, and therefore the elapsed fraction, of the animation. The return value indicates
	 * whether the animation should be ended (which happens when the elapsed time of the animation
	 * exceeds the animation's duration, including the repeatCount).
	 * 
	 * @param currentTime
	 *            The current time, as tracked by the static timing handler
	 * @return true if the animation's duration, including any repetitions due to
	 *         <code>repeatCount</code> has been exceeded and the animation should be ended.
	 */
	boolean animationFrame(long currentTime) {
		boolean done = false;
		
		if (mPlayingState == STOPPED) {
			mPlayingState = RUNNING;
			if (mSeekTime < 0) {
				mStartTime = currentTime;
			} else {
				mStartTime = currentTime - mSeekTime;
				// Now that we're playing, reset the seek time
				mSeekTime = -1;
			}
		}
		switch (mPlayingState) {
			case RUNNING:
			case SEEKED:
				float fraction = mDuration > 0 ? (float) (currentTime - mStartTime) / mDuration
						: 1f;
				if (fraction >= 1f) {
					if (mCurrentIteration < mRepeatCount || mRepeatCount == INFINITE) {
						// Time to repeat
						if (mListeners != null) {
							int numListeners = mListeners.size();
							for (int i = 0; i < numListeners; ++i) {
								mListeners.get(i).onAnimationRepeat(this);
							}
						}
						if (mRepeatMode == REVERSE) {
							mPlayingBackwards = mPlayingBackwards ? false : true;
						}
						mCurrentIteration += (int) fraction;
						fraction = fraction % 1f;
						mStartTime += mDuration;
					} else {
						done = true;
						fraction = Math.min(fraction, 1.0f);
					}
				}
				if (mPlayingBackwards) {
					fraction = 1f - fraction;
				}
				animateValue(fraction);
				break;
		}
		
		return done;
	}
	
	/**
	 * Returns the current animation fraction, which is the elapsed/interpolated fraction used in
	 * the most recent frame update on the animation.
	 * 
	 * @return Elapsed/interpolated fraction of the animation.
	 */
	public float getAnimatedFraction() {
		return mCurrentFraction;
	}
	
	/**
	 * get the value during the animation. Notice : the return array size is calculated as follow:
	 * <p>
	 * (input number +1) / 2
	 * 
	 * @return
	 */
	public float[] getCurrentValue() {
		return mCurrentValue;
	}
	
	/**
	 * This method is called with the elapsed fraction of the animation during every animation
	 * frame. This function turns the elapsed fraction into an interpolated fraction and then into
	 * an animated value (from the evaluator. The function is called mostly during animation
	 * updates, but it is also called when the <code>end()</code> function is called, to set the
	 * final value on the property.
	 * 
	 * <p>
	 * Overrides of this method must call the superclass to perform the calculation of the animated
	 * value.
	 * </p>
	 * 
	 * @param fraction
	 *            The elapsed fraction of the animation.
	 */
	void animateValue(float fraction) {
		fraction = mInterpolator.getInterpolation(fraction);
		mCurrentFraction = fraction;
		int currentIndex = 0;
		for (int i = 0; i < mValues.length; i++) {
			float from = mValues[i];
			float to = 0;
			if (i + 1 < mValues.length) {
				to = mValues[++i];
			}
			mCurrentValue[currentIndex++] = from + (to - from) * fraction;
		}
		
		if (mUpdateListeners != null) {
			int numListeners = mUpdateListeners.size();
			for (int i = 0; i < numListeners; ++i) {
				mUpdateListeners.get(i).onAnimationUpdate(this);
			}
		}
	}
	
	@Override
	public FloatValueAnimator clone() {
		try {
			final FloatValueAnimator anim = (FloatValueAnimator) super.clone();
			if (mListeners != null) {
				ArrayList<AnimatorListener> oldListeners = mListeners;
				anim.mListeners = new ArrayList<AnimatorListener>();
				int numListeners = oldListeners.size();
				for (int i = 0; i < numListeners; ++i) {
					anim.mListeners.add(oldListeners.get(i));
				}
			}
			if (mUpdateListeners != null) {
				ArrayList<AnimatorUpdateListener> oldListeners = mUpdateListeners;
				anim.mUpdateListeners = new ArrayList<AnimatorUpdateListener>();
				int numListeners = oldListeners.size();
				for (int i = 0; i < numListeners; ++i) {
					anim.mUpdateListeners.add(oldListeners.get(i));
				}
			}
			anim.mSeekTime = -1;
			anim.mPlayingBackwards = false;
			anim.mCurrentIteration = 0;
			anim.mInitialized = false;
			anim.mPlayingState = STOPPED;
			anim.mStartedDelay = false;
			return anim;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
		
	}
	
	/**
	 * Implementors of this interface can add themselves as update listeners to an
	 * <code>ValueAnimator</code> instance to receive callbacks on every animation frame, after the
	 * current frame's values have been calculated for that <code>ValueAnimator</code>.
	 */
	public static interface AnimatorUpdateListener {
		/**
		 * <p>
		 * Notifies the occurrence of another frame of the animation.
		 * </p>
		 * 
		 * @param animation
		 *            The animation which was repeated.
		 */
		void onAnimationUpdate(FloatValueAnimator animation);
		
	}
	
	/**
	 * Return the number of animations currently running.
	 * 
	 * Used by StrictMode internally to annotate violations. Only called on the main thread.
	 * 
	 * @hide
	 */
	public static int getCurrentAnimationsCount() {
		return sAnimations.get().size();
	}
	
	/**
	 * Clear all animations on this thread, without canceling or ending them. This should be used
	 * with caution.
	 * 
	 * @hide
	 */
	public static void clearAllAnimations() {
		sAnimations.get().clear();
		sPendingAnimations.get().clear();
		sDelayedAnims.get().clear();
	}
	
	/**
	 * Adds a listener to the set of listeners that are sent events through the life of an
	 * animation, such as start, repeat, and end.
	 * 
	 * @param listener
	 *            the listener to be added to the current set of listeners for this animation.
	 */
	public void addListener(AnimatorListener listener) {
		if (mListeners == null) {
			mListeners = new ArrayList<AnimatorListener>();
		}
		mListeners.add(listener);
	}
	
	/**
	 * Removes a listener from the set listening to this animation.
	 * 
	 * @param listener
	 *            the listener to be removed from the current set of listeners for this animation.
	 */
	public void removeListener(AnimatorListener listener) {
		if (mListeners == null) {
			return;
		}
		mListeners.remove(listener);
		if (mListeners.size() == 0) {
			mListeners = null;
		}
	}
	
	/**
	 * Gets the set of {@link android.animation.Animator.AnimatorListener} objects that are
	 * currently listening for events on this <code>Animator</code> object.
	 * 
	 * @return ArrayList<AnimatorListener> The set of listeners.
	 */
	public ArrayList<AnimatorListener> getListeners() {
		return mListeners;
	}
	
	/**
	 * Removes all listeners from this object. This is equivalent to calling
	 * <code>getListeners()</code> followed by calling <code>clear()</code> on the returned list of
	 * listeners.
	 */
	public void removeAllListeners() {
		if (mListeners != null) {
			mListeners.clear();
			mListeners = null;
		}
	}
	
	@Override
	public String toString() {
		String returnVal = "ValueAnimator@" + Integer.toHexString(hashCode());
		return returnVal;
	}
	
	/**
	 * <p>
	 * An animation listener receives notifications from an animation. Notifications indicate
	 * animation related events, such as the end or the repetition of the animation.
	 * </p>
	 */
	public static interface AnimatorListener {
		/**
		 * <p>
		 * Notifies the start of the animation.
		 * </p>
		 * 
		 * @param animation
		 *            The started animation.
		 */
		void onAnimationStart(FloatValueAnimator animation);
		
		/**
		 * <p>
		 * Notifies the end of the animation. This callback is not invoked for animations with
		 * repeat count set to INFINITE.
		 * </p>
		 * 
		 * @param animation
		 *            The animation which reached its end.
		 */
		void onAnimationEnd(FloatValueAnimator animation);
		
		/**
		 * <p>
		 * Notifies the cancellation of the animation. This callback is not invoked for animations
		 * with repeat count set to INFINITE.
		 * </p>
		 * 
		 * @param animation
		 *            The animation which was canceled.
		 */
		void onAnimationCancel(FloatValueAnimator animation);
		
		/**
		 * <p>
		 * Notifies the repetition of the animation.
		 * </p>
		 * 
		 * @param animation
		 *            The animation which was repeated.
		 */
		void onAnimationRepeat(FloatValueAnimator animation);
	}
}