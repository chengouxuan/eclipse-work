package com.example.ucmhomedemo21.menu;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

public class UCMMenuInternal extends PopupWindow {
	
	private boolean mAllowDismiss;
	private boolean mAnimating;
	
	private Handler mHandler;
	
	private int mAnimationAnchorX;

	public enum EnterType {
		DOWN,
		UP
	}
	
	private EnterType mEnterType;
	
	public UCMMenuInternal(Context context) {
		super(context);
		mAllowDismiss = false;
	}
	
	private void superDismiss() {
		super.dismiss();
	}
	
	public void forceDismiss() {
		
		this.showPopupWindowExitAnimation();
	}

	@Override
	public void dismiss() {
		if (mAllowDismiss) {
			super.dismiss();
		}
	}
	

	public void showAtLocation(View parent, int gravity, int x, int y, EnterType showType) {
		mEnterType = showType;
		this.showAtLocation(parent, gravity, x, y);
	}
	
	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		
		super.showAtLocation(parent, gravity, x, y);
		
		if (mHandler == null) {
			mHandler = new Handler();
		}
		
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				showPopupWindowEnterAnimation();
			}
		}, 0);
	}

	public boolean isAnimating() {
		return mAnimating;
	}

	private void showPopupWindowEnterAnimation() {
		
		Animation animation = null;
		
		if (mEnterType == EnterType.UP) {
			
			animation = new TranslateAnimation(0f, 0f, this.getContentView().getHeight(), 0f);
			animation.setDuration(350);
			
		} else {

			AnimationSet set = new AnimationSet(true);
			
			ScaleAnimation expand = new ScaleAnimation(0.5f, 1.1f, 0.5f, 1.1f, mAnimationAnchorX, this.getContentView().getHeight() * 0.1f);
			expand.setDuration(230);
			ScaleAnimation shrink = new ScaleAnimation(1.05f, 1f, 1.05f, 1f, mAnimationAnchorX, this.getContentView().getHeight() * 0.1f);
			shrink.setDuration(70);
			shrink.setStartOffset(230);
			AlphaAnimation alpha = new AlphaAnimation(0f, 1f);
			alpha.setDuration(230);
			
			set.addAnimation(expand);
			set.addAnimation(shrink);
			set.addAnimation(alpha);
			
			animation = set;
		}
		
		this.getContentView().startAnimation(animation);
		
		animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				mAnimating = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimating = false;
			}
		});
	}
	
	private void showPopupWindowExitAnimation() {
		
		Animation animation = null;
		
		if (mEnterType == EnterType.UP) {
			
			animation = new TranslateAnimation(0f, 0f, 0f, this.getContentView().getHeight());
			animation.setDuration(300);
			
		} else {
			
			AnimationSet set = new AnimationSet(true);
			
			set.addAnimation(new ScaleAnimation(1f, 0f, 1f, 0f, mAnimationAnchorX, this.getContentView().getHeight() * 0.1f));
			set.addAnimation(new AlphaAnimation(1f, 0f));
			set.setDuration(300);
			
			animation = set;
		}
		
		this.getContentView().startAnimation(animation);
		
		animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				mAnimating = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimating = false;
				superDismiss();
			}
		});
	}

	public void setAnimationAnchorX(int mDismissAnchorX) {
		this.mAnimationAnchorX = mDismissAnchorX;
	}
}
