package com.uc.viewdelete;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import com.uc.viewdelete.FloatValueAnimator.AnimatorListener;
import com.uc.viewdelete.FloatValueAnimator.AnimatorUpdateListener;

/**
 * <p>
 * Title: ucweb
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2010-2013
 * </p>
 * 
 * <p>
 * Company: ucweb.com
 * </p>
 * 
 * @author linhz@ucweb.com
 * @version 1.0
 **/
public class DisappearView extends View {
	
	private static final String TAG = "DisappearView";
	
	private static final boolean DEBUG = true;
	private static final int DEFAULT_MESH_COUNT = 10;
	
	private static final float SHRINK_FIRST_START_FACTOR = 0.955F;
	private static final float SHRINK_FIRST_END_FACTOR = 0.985F;
	private static final float SHRINK_SECOND_START_FACTOR = SHRINK_FIRST_END_FACTOR + 0.0001f;
	private static final float SHRINK_SECOND_END_FACTOR = 0.9967F;
	private static final float SHRINK_THIRD_START_FACTOR = SHRINK_SECOND_END_FACTOR + 0.00001f;
	private static final float SHRINK_THIRD_END_FACTOR = 0.9998F;
	
	private static final int SHRINK_FIRST_DURATION = 100;
	private static final int SHRINK_SECOND_DURATION = 200;
	private static final int SHRINK_THIRD_DURATION = 400;
	
	private final ArrayList<FloatValueAnimator> mAnimators = new ArrayList<FloatValueAnimator>(3);
	
	private Bitmap mBitmap;
	private int mBitmapWidth;
	private int mBitmapHeight;
	
	private int mWidthNumber = DEFAULT_MESH_COUNT;
	private int mHeightNumber = DEFAULT_MESH_COUNT;
	
	/**
	 * 这是顶点数，
	 */
	private int mVertCount = (mWidthNumber + 1) * (mHeightNumber + 1);
	
	private final float[] mVerts = new float[mVertCount * 2];
	private final float[] mOrig = new float[mVertCount * 2];
	
	private FloatValueAnimator mShrinkAnimator;
	
	private float mShrinkCenterX;
	private float mShrinkCenterY;
	
	// 测试用
	private int mFrameCount = 0;
	
	public DisappearView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	public DisappearView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public DisappearView(Context context) {
		super(context);
		init(context);
	}
	
	@SuppressLint("NewApi")
	private void init(Context context) {
		if (Build.VERSION.SDK_INT >= 11) {
			setLayerType(LAYER_TYPE_HARDWARE, null);
		}
	}
	
	/**
	 * generate the bitmap relative to its view
	 * 
	 * @param view
	 * @return bitmap relative to its view
	 */
	private Bitmap generateViewBitmap(View view) {
		view.clearFocus();
		view.setSelected(false);
		
		boolean willNotCache = view.willNotCacheDrawing();
		view.setWillNotCacheDrawing(false);
		
		// Reset the drawing cache background color to fully transparent
		// for the duration of this operation
		int color = view.getDrawingCacheBackgroundColor();
		view.setDrawingCacheBackgroundColor(0);
		
		if (color != 0)
			view.destroyDrawingCache();
		
		view.buildDrawingCache();
		
		Bitmap cacheBitmap = view.getDrawingCache();
		if (cacheBitmap == null) {
			Log.e(TAG, "failed generateViewBitmap(" + view + ")", new RuntimeException());
			return null;
		}
		
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
		cacheBitmap.recycle();
		
		// Restore the view
		view.destroyDrawingCache();
		view.setWillNotCacheDrawing(willNotCache);
		view.setDrawingCacheBackgroundColor(color);
		return bitmap;
	}
	
	private void initParam(View aDisappearView) {
		mBitmap = generateViewBitmap(aDisappearView);
		
		mBitmapWidth = mBitmap.getWidth();
		mBitmapHeight = mBitmap.getHeight();
		
		// 初始化网格顶点
		int index = 0;
		float valueY = 0;
		float valueX = 0;
		float heightFactor = 1.0f * mBitmapHeight / mHeightNumber;
		float widthFactor = 1.0f * mBitmapWidth / mWidthNumber;
		
		for (int indexY = 0; indexY <= mHeightNumber; indexY++) {
			valueY = heightFactor * indexY;
			for (int indexX = 0; indexX <= mWidthNumber; indexX++) {
				valueX = widthFactor * indexX;
				setXY(mVerts, index, valueX, valueY);
				setXY(mOrig, index, valueX, valueY);
				index += 1;
			}
		}
	}
	
	private static void setXY(float[] array, int index, float x, float y) {
		array[index * 2 + 0] = x;
		array[index * 2 + 1] = y;
	}
	
	private static boolean isEqual(float x, float y, float accuracy) {
		if (Math.abs(x - y) < accuracy)
			return true;
		return false;
	}
	
	private static boolean isEqual(float x, float y) {
		return isEqual(x, y, 0.0001f);
	}
	
	/**
	 * 启动收缩的
	 * @param aDisappearView 要被收缩的view
	 * @param shrinkCenterX  收缩点X坐标
	 * @param shrinkCenterY  收缩点Y坐标
	 */
	public void startDisappear(View aDisappearView, float shrinkCenterX, float shrinkCenterY) {
		setVisibility(VISIBLE);
		mShrinkCenterX = shrinkCenterX;
		mShrinkCenterY = shrinkCenterY - 40;
		initParam(aDisappearView);
		
		startShrinkAnimation();
	}
	
	/**
	 * 取消收缩，通常不用调用，除非中途想中断动画
	 */
	public void cancelDisappearAnimation(){
		clearShrinkAnimations();
		endDisappear();
	}
	
	private void endDisappear() {
		setVisibility(GONE);
		if (mBitmap != null && !mBitmap.isRecycled())
			mBitmap.recycle();
		
		mBitmap = null;
	}
	
	private void updateVertexs(float fraction) {
		float valueY = 0;
		float valueX = 0;
		
		float tempX = 0;
		float tempY = 0;
		
		float diffX = 0;
		float diffY = 0;
		
		float distance = 0;
		
		if (DEBUG) {
			Log.d(TAG, "=========updateVertexs  fraction = " + fraction + " mFrameCount = "
					+ (++mFrameCount));
		}
		
		for (int i = 0; i < mVertCount * 2; i += 2) {
			valueX = mOrig[i];
			valueY = mOrig[i + 1];
			
			diffX = mShrinkCenterX - valueX;
			diffY = mShrinkCenterY - valueY;
			tempX = diffX * fraction;
			tempY = diffY * fraction;
			
			distance = (float) Math.hypot(diffX - tempX, diffY - tempY);
			
			if (distance < 1)
				distance = 1;
			
			valueX = valueX + tempX / distance;
			valueY = valueY + tempY / distance;
			
			mVerts[i] = valueX;
			mVerts[i + 1] = valueY;
		}
		
		invalidate();
	}
	
	private void buildAnimationListener(FloatValueAnimator animator, boolean addEndListener) {
		if (addEndListener) {
			animator.addListener(new AnimatorListener() {
				
				@Override
				public void onAnimationStart(FloatValueAnimator animation) {
					
				}
				
				@Override
				public void onAnimationRepeat(FloatValueAnimator animation) {
					
				}
				
				@Override
				public void onAnimationEnd(FloatValueAnimator animation) {
					endDisappear();
				}
				
				@Override
				public void onAnimationCancel(FloatValueAnimator animation) {
					endDisappear();
				}
			});
		}
		
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(FloatValueAnimator animation) {
				updateVertexs(animation.getCurrentValue()[0]);
			}
		});
		
	}
	
	private void clearShrinkAnimations() {
		for (FloatValueAnimator animator : mAnimators) {
			if (animator != null && animator.isRunning())
				animator.cancel();
		}
		mAnimators.clear();
	}
	
	private void startShrinkAnimation() {
		if (DEBUG) {
			mFrameCount = 0;
		}
		
		clearShrinkAnimations();
		
		FloatValueAnimator first = FloatValueAnimator.ofFloat(SHRINK_FIRST_START_FACTOR,
				SHRINK_FIRST_END_FACTOR);
		first.setDuration(SHRINK_FIRST_DURATION);
		
		FloatValueAnimator second = FloatValueAnimator.ofFloat(SHRINK_SECOND_START_FACTOR,
				SHRINK_SECOND_END_FACTOR);
		second.setStartDelay(SHRINK_FIRST_DURATION + 5);
		second.setDuration(SHRINK_SECOND_DURATION);
		
		FloatValueAnimator third = FloatValueAnimator.ofFloat(SHRINK_THIRD_START_FACTOR,
				SHRINK_THIRD_END_FACTOR);
		third.setStartDelay(second.getStartDelay() + 5 + SHRINK_SECOND_DURATION);
		third.setDuration(SHRINK_THIRD_DURATION);
		
		buildAnimationListener(first, false);
		buildAnimationListener(second, false);
		buildAnimationListener(third, true);
		
		mAnimators.add(first);
		mAnimators.add(second);
		mAnimators.add(third);
		
		first.start();
		second.start();
		third.start();
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {
			canvas.drawBitmapMesh(mBitmap, mWidthNumber, mHeightNumber, mVerts, 0, null, 0, null);
		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		clearShrinkAnimations();
	}
	
	@Override
	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		if (visibility == View.INVISIBLE || visibility == View.GONE) {
			clearShrinkAnimations();
		}
	}
}
