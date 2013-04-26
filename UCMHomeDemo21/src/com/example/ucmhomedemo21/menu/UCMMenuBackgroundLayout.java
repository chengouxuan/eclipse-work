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
	
	public UCMMenuBackgroundLayout(Context context) {
		super(context);
		this.setupViews();
	}

	public UCMMenuBackgroundLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}
	
	private void setupViews() {

		mLeft = new View(this.getContext());
		mLeft.setBackgroundResource(R.drawable.frame_multi_window_left);
		mLeft.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
		this.addView(mLeft);
		
		mMiddle = new View(this.getContext());
		mMiddle.setBackgroundResource(R.drawable.frame_multi_window_mid);
		mMiddle.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
		this.addView(mMiddle);
		
		mRight = new View(this.getContext());
		mRight.setBackgroundResource(R.drawable.frame_multi_window_right);
		mRight.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
		this.addView(mRight);
	}
}
