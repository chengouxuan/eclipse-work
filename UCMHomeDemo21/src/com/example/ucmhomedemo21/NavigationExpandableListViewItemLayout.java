package com.example.ucmhomedemo21;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavigationExpandableListViewItemLayout

extends RelativeLayout {

	private ExpandableListViewController expandlistViewController;

	public NavigationExpandableListViewItemLayout(Context context) {
		super(context);
		this.setupViews();
	}

	public NavigationExpandableListViewItemLayout(Context context,
			AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}

	public NavigationExpandableListViewItemLayout(Context context,
			AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupViews();
	}

	private void setupViews() {
		
		Resources resources = this.getContext().getResources();
		
		final int iconId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_ICON);
		final int textViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_TEXT);
		final int detailTextViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_DETAIL_TEXT);
		final int buttonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_BUTTON);
		final int accessoryIconId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_ACCESSORY_ICON);
		
		float floatPadding = resources.getDimension(R.dimen.common_padding);
		int padding = (int)(0.5f + floatPadding);
		
		RelativeLayout outMostLayout = this;

		{
			// setup icon
			
			ImageView icon = new ImageView(this.getContext());
			icon.setId(iconId);
			icon.setScaleType(ScaleType.FIT_CENTER);
			
			float width = resources.getDimension(R.dimen.navigation_item_icon_width);
			float height = resources.getDimension(R.dimen.navigation_item_icon_height);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(0.5f + width), (int)(0.5f + height));
			params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			params.setMargins(padding, 0, 0, 0);
			
			icon.setLayoutParams(params);
			outMostLayout.addView(icon);
		}
		
		{
			// setup string on the left
			
			TextView textView = new TextView(this.getContext());
			textView.setId(textViewId);
			textView.setTextColor(resources.getColor(R.color.navigation_item_text_color));
			textView.setPadding(padding, 0, 0, 0);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			params.addRule(RelativeLayout.RIGHT_OF, iconId);
			
			textView.setLayoutParams(params);
			outMostLayout.addView(textView);
		}
		
		{
			// setup detail string on the right
			
			TextView textView = new TextView(this.getContext());
			textView.setId(detailTextViewId);
			textView.setTextColor(resources.getColor(R.color.navigation_item_detail_text_color));
			textView.setPadding(padding, 0, 0, 0);
			
			float textSize = 13;
			textView.setTextSize(textSize);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			params.addRule(RelativeLayout.LEFT_OF, accessoryIconId);
			
			textView.setLayoutParams(params);
			outMostLayout.addView(textView);
		}
		
		{
			// setup accessory icon at the right edge
			
			ImageView icon = new ImageView(this.getContext());
			icon.setId(accessoryIconId);
			icon.setScaleType(ScaleType.FIT_CENTER);
			icon.setImageResource(R.drawable.quick_button);

			float width = resources.getDimension(R.dimen.navigation_item_accessory_icon_width);
			float height = resources.getDimension(R.dimen.navigation_item_accessory_icon_height);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(0.5f + width), (int)(0.5f + height));
			params.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
			
			icon.setLayoutParams(params);
			outMostLayout.addView(icon);
		}
		
		{
			// setup the button
			
			Button button = new Button(this.getContext());
			button.setId(buttonId);
			button.setBackgroundResource(R.drawable.navigation_item_button_selector);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (expandlistViewController != null) {
						expandlistViewController.groupClicked(v);
					}
				}
			});
			
			float height = this.getContext().getResources().getDimension(R.dimen.navigation_item_height);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, (int)(0.5f + height));
			
			button.setLayoutParams(params);
			outMostLayout.addView(button);
		}
		
	}

	public ExpandableListViewController getExpandlistViewController() {
		return expandlistViewController;
	}

	public void setExpandlistViewController(ExpandableListViewController expandlistViewController) {
		this.expandlistViewController = expandlistViewController;
	}
}
