package com.example.ucmhomedemo21;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HotSitesItemLayout extends RelativeLayout {

	public HotSitesItemLayout(Context context) {
		super(context);
		this.setupViews();
	}

	public HotSitesItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}

	public HotSitesItemLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupViews();
	}

	private void setupViews() {
		
		RelativeLayout outMostLayout = this;
		Resources resources = this.getContext().getResources();
		
		final int iconId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_ITEM_ICON);
		final int descriptionTextId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_ITEM_DESCRIIPTION_TEXT_ID);
		final int backgroundId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_ITEM_BACKGROUND);
				
		{
			GridItemBackground background = new GridItemBackground(this.getContext());
			background.setId(backgroundId);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
			params.addRule(RelativeLayout.ALIGN_LEFT, iconId);
			params.addRule(RelativeLayout.ALIGN_RIGHT, descriptionTextId);
			params.addRule(RelativeLayout.ALIGN_TOP, descriptionTextId);
			params.addRule(RelativeLayout.ALIGN_BOTTOM, descriptionTextId);
//			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
//			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
//			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
//			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
			
			background.setLayoutParams(params);
			outMostLayout.addView(background);
		}
		
		{
			ImageView icon = new ImageView(this.getContext());
			icon.setId(iconId);
			
			float width = resources.getDimension(R.dimen.hot_sites_item_icon_width);
			float height = resources.getDimension(R.dimen.hot_sites_item_icon_height);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(0.5f + width), (int)(0.5f + height));
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
			params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			
			icon.setLayoutParams(params);
			outMostLayout.addView(icon);
		}
		
		{
			TextView textView = new TextView(this.getContext());
			textView.setId(descriptionTextId);
			textView.setLines(1);
			textView.setTextSize(resources.getDimension(R.dimen.hot_sites_item_description_text_size));
			textView.setTextColor(resources.getColor(R.color.hot_sites_item_description_text_color));
			textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
			params.addRule(RelativeLayout.RIGHT_OF, iconId);
			params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			
			textView.setLayoutParams(params);
			outMostLayout.addView(textView);
		}

		float floatPadding = resources.getDimension(R.dimen.common_padding);
		int padding = (int)(0.5f + floatPadding);
		
		outMostLayout.setPadding(padding, padding, padding, padding);
		
		outMostLayout.setBackgroundResource(R.drawable.navigation_item_button_selector);
	}
}
