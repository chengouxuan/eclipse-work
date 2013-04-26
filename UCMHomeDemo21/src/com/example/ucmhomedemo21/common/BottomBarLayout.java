package com.example.ucmhomedemo21.common;

import com.example.ucmhomedemo21.GlobalViewIds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class BottomBarLayout extends LinearLayout {

	public BottomBarLayout(Context context) {
		super(context);
		this.setupViews();
	}

	public BottomBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}

	private void setupViews() {
		
		LinearLayout outMostLayout = this;
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.FILL_PARENT, 1.0f);

		final int itemId1 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM1);
		final int itemId2 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM2);
		final int itemId3 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM3);
		final int itemId4 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM4);
		final int itemId5 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM5);

		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId1);
			
			item.setLayoutParams(params);
			outMostLayout.addView(item);
		}
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId2);
			
			item.setLayoutParams(params);
			outMostLayout.addView(item);
		}
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId3);
			
			item.setLayoutParams(params);
			outMostLayout.addView(item);
		}
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId4);
			
			item.setLayoutParams(params);
			outMostLayout.addView(item);
		}
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId5);
			
			item.setLayoutParams(params);
			outMostLayout.addView(item);
		}
	}
}
