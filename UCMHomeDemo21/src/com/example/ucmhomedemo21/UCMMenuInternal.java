package com.example.ucmhomedemo21;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

public class UCMMenuInternal extends PopupWindow {
	
	private boolean mAllowDismiss;
	private boolean mAnimating;
	
	private Handler mHandler;
	
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
		
		Animation animation = new TranslateAnimation(0f, 0f, this.getContentView().getHeight(), 0f);
		animation.setDuration(500);
		this.getContentView().startAnimation(animation);
		
		animation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				mAnimating = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimating = false;
			}
		});
	}
	
	private void showPopupWindowExitAnimation() {
		
		Animation animation = new TranslateAnimation(0f, 0f, 0f, this.getContentView().getHeight());
		animation.setDuration(300);
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
	
}
