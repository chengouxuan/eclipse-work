package com.example.ucmhomedemo21;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class UCMMenuSliderView extends LinearLayout {

	View mSliderImage;
	int mSliderWidth;
	int mSliderHeight;
	float mPositionRate;
	
	public UCMMenuSliderView(Context context, int sliderWidth, int sliderHeight) {
		
		super(context);
		
		mSliderWidth = sliderWidth;
		mSliderHeight = sliderHeight;
		
		this.setupViews();

		mPositionRate = 0f;
		
		this.onPositionRateChange();
	}
	
	public int getSliderWidth() {
		return mSliderWidth;
	}
	
	public void setPositionRate(float rate) {

		if (Math.abs(rate - mPositionRate) < 0.0001) { 
			return;
		}
		
		mPositionRate = rate;
		
		if (mPositionRate < 0) {
			mPositionRate = 0;
		}
		
		if (1 < mPositionRate) {
			mPositionRate = 1;
		}
		
		this.onPositionRateChange();
	}
	
	private void onPositionRateChange() {

		this.updateSliderViewPosition();
		this.requestLayout();
	}
	
	private void updateSliderViewPosition() {

		float x = 1.0f * this.getTotalWidth() * mPositionRate - 0.5f * mSliderWidth;
		
		int leftMargin = (int)(0.5f + x);
		
		LinearLayout.LayoutParams params = (LayoutParams) (mSliderImage.getLayoutParams());
		params.setMargins(leftMargin, 0, 0, 0);
	}

	private void setupViews() {
		
		if (mSliderImage == null) {
			
			mSliderImage = new View(this.getContext());
			mSliderImage.setLayoutParams(new LinearLayout.LayoutParams(mSliderWidth, mSliderHeight));
			mSliderImage.setBackgroundResource(R.drawable.menu_slidebar);
			
			this.addView(mSliderImage);
		}
	}
	
	public int getTotalWidth() {
		int ret = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();
		return ret >= 0 ? ret : 0;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		if (changed) {
			this.updateSliderViewPosition();
		}
		
		super.onLayout(changed, l, t, r, b);
	}
}
