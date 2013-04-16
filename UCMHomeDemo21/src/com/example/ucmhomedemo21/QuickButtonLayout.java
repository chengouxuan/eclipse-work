package com.example.ucmhomedemo21;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

public class QuickButtonLayout extends RelativeLayout {

	public QuickButtonLayout(Context context) {
		super(context);
		this.setupViews();
	}

	public QuickButtonLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}

	public QuickButtonLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupViews();
	}

	private void setupViews() {
		
		final int backgroundId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.QUICK_BUTTON_BACKGROUND);
		final int buttonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.QUICK_BUTTON);
		
		RelativeLayout outMostLayout = this;
		Resources resources = this.getContext().getResources();
		
		{
			// setup background image
			
			ImageView background = new ImageView(this.getContext());
			background.setId(backgroundId);
			background.setImageResource(R.drawable.add_url_bg);
			background.setScaleType(ScaleType.FIT_XY);

			float width = resources.getDimension(R.dimen.quick_button_width);
			float height = resources.getDimension(R.dimen.address_bar_common_height);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(width + 0.5f), (int)(height + 0.5f));
			
			background.setLayoutParams(params);
			outMostLayout.addView(background);
		}
		
		{
			// setup button
			
			Button button = new Button(this.getContext());
			button.setId(buttonId);
			button.setBackgroundResource(R.drawable.quick_button_selector);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
			params.addRule(RelativeLayout.ALIGN_LEFT, backgroundId);
			params.addRule(RelativeLayout.ALIGN_BOTTOM, backgroundId);
			params.addRule(RelativeLayout.ALIGN_RIGHT, backgroundId);
			params.addRule(RelativeLayout.ALIGN_TOP, backgroundId);
			
			button.setLayoutParams(params);
			outMostLayout.addView(button);
		}
	}
}
