package com.example.ucmhomedemo21;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class HomeLayoutPortrait extends RelativeLayout {

	public HomeLayoutPortrait(Context context) {
		super(context);
		this.setupViews();
	}

	public HomeLayoutPortrait(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setupViews();
	}

	public HomeLayoutPortrait(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupViews();
	}

	private void setupViews() {

		final int addressBarId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR);
		final int topBarId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.TOP_BAR);
		final int bottomBarId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR);
		final int listViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_LIST_VIEW);
		final int hotSitesViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_LIST_VIEW);
		
		Resources resources = this.getContext().getResources();
		
		float floatPadding = resources.getDimension(R.dimen.common_padding);
		int padding = (int)(0.5f + floatPadding);
		
		RelativeLayout outMostLayout = this;
		
		RelativeLayout upperHalfLayout = new RelativeLayout(this.getContext()); // contain the top bar and the scroll view
		
		{
			upperHalfLayout.setPadding(padding, padding, padding, padding);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP, TRUE);
			params.addRule(RelativeLayout.ABOVE, bottomBarId);
			upperHalfLayout.setLayoutParams(params);
			outMostLayout.addView(upperHalfLayout);
		}
		
		
		RelativeLayout topBarLayout = new RelativeLayout(this.getContext());
		topBarLayout.setId(topBarId);
		
		{
			AddressBarLayout addressBarLayout = new AddressBarLayout(this.getContext());
			addressBarLayout.setId(addressBarId);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			addressBarLayout.setLayoutParams(params);
			topBarLayout.addView(addressBarLayout);
		}
		
		{
			QuickButtonLayout quickButtonLayout = new QuickButtonLayout(this.getContext());
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
//			params.addRule(RelativeLayout.RIGHT_OF, addressBarId);
			
			quickButtonLayout.setLayoutParams(params);
			topBarLayout.addView(quickButtonLayout);
		}
		
		topBarLayout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		upperHalfLayout.addView(topBarLayout);
		
		
		ScrollView scrollView = new ScrollView(this.getContext());
		
		{
			scrollView.setFadingEdgeLength(0);
			scrollView.setHorizontalScrollBarEnabled(false);
			scrollView.setVerticalScrollBarEnabled(false);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.FILL_PARENT);
			params.addRule(RelativeLayout.BELOW, topBarId);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
			
			scrollView.setLayoutParams(params);
			upperHalfLayout.addView(scrollView);
		}
		
		LinearLayout linearLayoutInsideScrollView = new LinearLayout(this.getContext());
		linearLayoutInsideScrollView.setOrientation(LinearLayout.VERTICAL);
		linearLayoutInsideScrollView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		scrollView.addView(linearLayoutInsideScrollView);
		
		{
			HotSitesGridView hotSites = new HotSitesGridView(this.getContext());
			hotSites.setId(hotSitesViewId);
			hotSites.setBackgroundResource(R.drawable.add_url_bg);
			hotSites.setFadingEdgeLength(0);
			hotSites.setPadding(padding, padding, padding, padding);
			hotSites.setVerticalSpacing(0);
			hotSites.setCacheColorHint(resources.getColor(R.color.no_color));
			hotSites.setSelector(resources.getDrawable(R.color.no_color));
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			
			hotSites.setLayoutParams(params);
			linearLayoutInsideScrollView.addView(hotSites);
		}
		
		{
			NavigationExpandableListView navigatioin = new NavigationExpandableListView(this.getContext());
			navigatioin.setId(listViewId);
			navigatioin.setBackgroundResource(R.drawable.add_url_bg);
			navigatioin.setCacheColorHint(resources.getColor(R.color.no_color));
			navigatioin.setChildDivider(resources.getDrawable(android.R.drawable.divider_horizontal_bright));
			navigatioin.setDivider(resources.getDrawable(android.R.drawable.divider_horizontal_bright));
			navigatioin.setGroupIndicator(resources.getDrawable(R.color.no_color));
			navigatioin.setFadingEdgeLength(0);
			
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			
			navigatioin.setLayoutParams(params);
			linearLayoutInsideScrollView.addView(navigatioin);
		}
		
		BottomBarLayout bottomBarLayout = new BottomBarLayout(this.getContext());
		
		{
			bottomBarLayout.setId(bottomBarId);
			bottomBarLayout.setBackgroundResource(R.drawable.controlbar_bg);
			
			float height = resources.getDimension(R.dimen.bottom_bar_height);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, (int)(0.5f + height));
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, TRUE);
			
			bottomBarLayout.setLayoutParams(params);
			outMostLayout.addView(bottomBarLayout);
		}
		
		outMostLayout.setBackgroundResource(R.drawable.default_wallpaper);
	}
}
