package com.example.ucmhomedemo21.common;

import com.example.ucmhomedemo21.GlobalViewIds;
import com.example.ucmhomedemo21.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BottomBarItemLayout extends RelativeLayout {

	public BottomBarItemLayout(Context context) {
		super(context);
		this.setupViews();
	}

	public BottomBarItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}

	public BottomBarItemLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupViews();
	}

	private void setupViews() {
		
		RelativeLayout outMostLayout = this;
		
		final int backgroundImageViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_BUTTON_IMAGE);
		final int buttonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_BUTTON);
		
		{
			// setup image
			
			ImageView imageView = new ImageView(this.getContext());
			imageView.setId(backgroundImageViewId);

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
			
			imageView.setLayoutParams(params);
			outMostLayout.addView(imageView);
		}
		
		{
			// setup button
			
			Button button = new Button(this.getContext());
			button.setId(buttonId);
			
			button.setBackgroundResource(R.color.no_color);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
			params.addRule(RelativeLayout.ALIGN_BOTTOM, backgroundImageViewId);
			params.addRule(RelativeLayout.ALIGN_LEFT, backgroundImageViewId);
			params.addRule(RelativeLayout.ALIGN_RIGHT, backgroundImageViewId);
			params.addRule(RelativeLayout.ALIGN_TOP, backgroundImageViewId);
			
			button.setLayoutParams(params);
			outMostLayout.addView(button);
		}
	}
}
