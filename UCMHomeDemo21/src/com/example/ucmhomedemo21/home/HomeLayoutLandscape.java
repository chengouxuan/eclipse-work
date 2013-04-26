package com.example.ucmhomedemo21.home;

import com.example.ucmhomedemo21.GlobalViewIds;
import com.example.ucmhomedemo21.R;
import com.example.ucmhomedemo21.common.AddressBarLayout;
import com.example.ucmhomedemo21.common.BottomBarItemLayout;
import com.example.ucmhomedemo21.common.QuickButtonLayout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeLayoutLandscape extends RelativeLayout {

	public HomeLayoutLandscape(Context context) {
		super(context);
		this.setupViews();
	}

	public HomeLayoutLandscape(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}

	public HomeLayoutLandscape(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupViews();
	}

	private void setupViews() {

		final int topBarId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.TOP_BAR);
		final int listViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_LIST_VIEW);
		final int hotSitesViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_LIST_VIEW);

		final int itemId1 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM1);
		final int itemId2 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM2);
		final int itemId3 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM3);
		final int itemId4 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM4);
		final int itemId5 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM5);
		
		final int addressBarId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR);
		
		Resources resources = this.getContext().getResources();
		
		float floatPadding = resources.getDimension(R.dimen.common_padding);
		int padding = (int)(0.5f + floatPadding);
		
		RelativeLayout outMostLayout = this;
		
		LinearLayout upperLayout = new LinearLayout(this.getContext());
		{
			upperLayout.setId(topBarId);
			upperLayout.setOrientation(LinearLayout.HORIZONTAL);
			upperLayout.setBackgroundResource(R.drawable.toolbar_bg_horizental);
			
			float height = resources.getDimension(R.dimen.address_bar_common_height);
			upperLayout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, (int)(0.5f + height)));
			outMostLayout.addView(upperLayout);
		}

		LinearLayout downLayout = new LinearLayout(this.getContext());
		{
			downLayout.setOrientation(LinearLayout.HORIZONTAL);
			downLayout.setPadding(padding, padding, padding, padding);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
			params.addRule(RelativeLayout.BELOW, topBarId);
			downLayout.setLayoutParams(params);
			outMostLayout.addView(downLayout);
		}

		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId1);
			item.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
			upperLayout.addView(item);
		}
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId2);
			item.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
			upperLayout.addView(item);
		}

		{
			AddressBarLayout addressBarLayout = new AddressBarLayout(this.getContext());
			addressBarLayout.setId(addressBarId);
			float width = resources.getDimension(R.dimen.address_bar_width_landscape);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int)(0.5f + width), LayoutParams.WRAP_CONTENT);
			addressBarLayout.setLayoutParams(params);
			upperLayout.addView(addressBarLayout);
		}
		
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId3);
			item.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
			upperLayout.addView(item);
		}
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId4);
			item.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
			upperLayout.addView(item);
		}
		{
			BottomBarItemLayout item = new BottomBarItemLayout(this.getContext());
			item.setId(itemId5);
			item.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
			upperLayout.addView(item);
		}
		
		{
			QuickButtonLayout quickButtonLayout = new QuickButtonLayout(this.getContext());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			
			quickButtonLayout.setLayoutParams(params);
			upperLayout.addView(quickButtonLayout);
		}

		{
			HotSitesGridView hotSites = new HotSitesGridView(this.getContext());
			hotSites.setVerticalScrollBarEnabled(false);
			hotSites.setId(hotSitesViewId);
			hotSites.setBackgroundResource(R.drawable.add_url_bg);
			hotSites.setFadingEdgeLength(0);
			hotSites.setPadding(padding, padding, padding, padding);
			hotSites.setVerticalSpacing(0);
			hotSites.setCacheColorHint(resources.getColor(R.color.no_color));
			hotSites.setSelector(resources.getDrawable(R.color.no_color));
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1);
			
			hotSites.setLayoutParams(params);
			downLayout.addView(hotSites);
		}
		
		{
			NavigationExpandableListView navigatioin = new NavigationExpandableListView(this.getContext());
			navigatioin.setVerticalScrollBarEnabled(false);
			navigatioin.setId(listViewId);
			navigatioin.setBackgroundResource(R.drawable.add_url_bg);
			navigatioin.setCacheColorHint(resources.getColor(R.color.no_color));
			navigatioin.setChildDivider(resources.getDrawable(android.R.drawable.divider_horizontal_bright));
			navigatioin.setDivider(resources.getDrawable(android.R.drawable.divider_horizontal_bright));
			navigatioin.setGroupIndicator(resources.getDrawable(R.color.no_color));
			navigatioin.setFadingEdgeLength(0);
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 2);
			
			navigatioin.setLayoutParams(params);
			downLayout.addView(navigatioin);
		}
		
		outMostLayout.setBackgroundResource(R.drawable.default_wallpaper);
	}
}
