package com.example.ucmmenudemo;

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

		float x = 1.0f * this.getWidth() * mPositionRate;
		
		int leftMargin = (int)(0.5f + x);
		
		LinearLayout.LayoutParams params = (LayoutParams) (mSliderImage.getLayoutParams());
		params.setMargins(leftMargin, 0, 0, 0);
		
		this.requestLayout();
	}

	private void setupViews() {
		
		if (mSliderImage == null) {
			
			mSliderImage = new View(this.getContext());
			mSliderImage.setLayoutParams(new LinearLayout.LayoutParams(mSliderWidth, mSliderHeight));
			mSliderImage.setBackgroundResource(R.drawable.menu_slidebar);
			
			this.addView(mSliderImage);
		}
	}
}
