package com.example.ucmhomedemo21.menu;

import com.example.ucmhomedemo21.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class UCMMenuBackgroundLayout extends LinearLayout {

	private View mLeft;
	private View mMiddle;
	private View mRight;

	private float mLeftWeight;
	private float mMiddleWeight;
	private float mRightWeight;
	
	public enum ArrowType {
		ON_TOP,
		ON_BOTTOM
	}
	
	private ArrowType mArrowType;
	
	public UCMMenuBackgroundLayout(Context context, ArrowType arrowType, int arrowLeftWidth, int arrowRightWidth) {
		super(context);
		mArrowType = arrowType;
		this.setupViews(arrowLeftWidth, arrowRightWidth);
	}

	public UCMMenuBackgroundLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews(0, 0);
	}
	
	private void setupViews(int arrowLeftWidth, int arrowRightWidth) {

		this.setupWeights(arrowLeftWidth, arrowRightWidth);
		
		if (mArrowType == ArrowType.ON_BOTTOM) {
			this.setupViewsArrowOnBottom();
		} else {
			this.setupViewsArrowOnTop();
		}
	}

	private void setupViewsArrowOnTop() {
		
		mLeft = new View(this.getContext());
		mLeft.setBackgroundResource(R.drawable.frame_multi_window_left_ls);
		mLeft.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, mLeftWeight));
		this.addView(mLeft);
		
		mMiddle = new View(this.getContext());
		mMiddle.setBackgroundResource(R.drawable.frame_multi_window_mid_ls);
		mMiddle.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, mMiddleWeight));
		this.addView(mMiddle);
		
		mRight = new View(this.getContext());
		mRight.setBackgroundResource(R.drawable.frame_multi_window_right_ls);
		mRight.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, mRightWeight));
		this.addView(mRight);
	}
	
	private void setupViewsArrowOnBottom() {

		mLeft = new View(this.getContext());
		mLeft.setBackgroundResource(R.drawable.frame_multi_window_left);
		mLeft.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, mLeftWeight));
		this.addView(mLeft);
		
		mMiddle = new View(this.getContext());
		mMiddle.setBackgroundResource(R.drawable.frame_multi_window_mid);
		mMiddle.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, mMiddleWeight));
		this.addView(mMiddle);
		
		mRight = new View(this.getContext());
		mRight.setBackgroundResource(R.drawable.frame_multi_window_right);
		mRight.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, mRightWeight));
		this.addView(mRight);
	}
	
	private void setupWeights(int arrowLeftWidth, int arrowRightWidth) {
		
		float L = arrowLeftWidth;
		float R = arrowRightWidth;
		float b = (L + R) / 3f;
		float a = L - b / 2f;
		float c = R - b / 2f;
		
		if (a < 0) {
			c += a;
			a -= a;
		}
		
		if (c < 0) {
			a += c;
			c -= c;
		}
		
		if (Math.abs(b) < 0.0001) {
			b = 1f;
		}
		
		mLeftWeight = a / b;
		mRightWeight = c / b;
		mMiddleWeight = 1f;
	}
}
