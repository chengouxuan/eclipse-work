package com.example.ucmhomedemo21.menu;

import com.example.ucmhomedemo21.R;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class UCMMenuSliderView extends LinearLayout {


	View mSliderImage;
	int mSliderWidth;
	int mSliderHeight;
	float mPositionRate;
	int mTotalWidth;
	
	public UCMMenuSliderView(Context context) {
		super(context);
	}
	
	public UCMMenuSliderView(Context context, int sliderWidth, int sliderHeight, int totalWidth) {
		
		super(context);
		
		mSliderWidth = sliderWidth;
		mSliderHeight = sliderHeight;
		mTotalWidth = totalWidth;
		
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
		int rightMargin = this.getTotalWidth() - mSliderWidth - leftMargin;
		
		LinearLayout.LayoutParams params = (LayoutParams) (mSliderImage.getLayoutParams());
		params.setMargins(leftMargin, 0, rightMargin, 0);
	}

	private void setupViews() {
		
		if (mSliderImage == null) {
			
			mSliderImage = new View(this.getContext());
			
			mSliderImage.setLayoutParams(new LinearLayout.LayoutParams(mSliderWidth, mSliderHeight));
			mSliderImage.setBackgroundResource(R.drawable.menu_slidebar);
			
			this.addView(mSliderImage);
		}
	}
	
	private int getTotalWidth() {
		return mTotalWidth;
	}
}
