package com.example.ucmmenudemo;

import java.lang.reflect.Array;
import java.util.Arrays;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UCMMenu {

	private Context mContext;
	
	private ViewPager mViewPager;
	private LinearLayout mHeadersLayout;
	private PopupWindow mPopupWindow;
	private LinearLayout mContainViewRoot;
	private UCMMenuDataSource mDataSource;
	
	private PagerAdapter mViewPagerAdapter;

	private final static float VIEW_PAGER_WEIGHT = 3;
	private final static float HEADERS_LAYOUT_WEIGHT = 1;
	
	public UCMMenu(Context context, UCMMenuDataSource dataSource) {
		mContext = context;
		mDataSource = dataSource;
	}
	
	public void show(View anchorView) {

		setupViewsIfNeeded();
		
		mPopupWindow.showAtLocation(anchorView, Gravity.BOTTOM, anchorView.getLeft(), anchorView.getTop());
	}
	
	public void setWidth(int width) {
		this.setupViewsIfNeeded();
		mPopupWindow.setWidth(width);
	}
	
	public void setHeight(int height) {
		this.setupViewsIfNeeded();
		mPopupWindow.setHeight(height);
	}
	
	private void setupViewsIfNeeded() {
		
		if (mPopupWindow != null) {
			return;
		}
		
		mPopupWindow = new PopupWindow(mContext);
		mPopupWindow.setWidth(Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_width));
		mPopupWindow.setHeight(Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_height));
		mPopupWindow.setAnimationStyle(R.style.Animation);
		
		
		mContainViewRoot = new LinearLayout(mContext);
		mContainViewRoot.setOrientation(LinearLayout.VERTICAL);
		mContainViewRoot.setBackgroundResource(R.drawable.menu_select);
		
		
		mPopupWindow.setContentView(mContainViewRoot);
		
		
		{	
			mHeadersLayout = new LinearLayout(mContext);
			mHeadersLayout.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, HEADERS_LAYOUT_WEIGHT);
			mHeadersLayout.setLayoutParams(params);
			mContainViewRoot.addView(mHeadersLayout);
			
			int pageCount = mDataSource.getPageCount();
			
			for (int i = 0; i < pageCount; ++i) {
				
				TextView textView = new TextView(mContext);
				textView.setText(mDataSource.getPageTitle(i));
				textView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
				mHeadersLayout.addView(textView);
			}
		}
		
		{
			View seperatorView = new View(mContext);
			seperatorView.setBackgroundResource(R.drawable.list_divider);
			int padding = Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_seperator_padding);
			seperatorView.setPadding(padding, 0, 0, padding);
			int height = Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_seperator_height);
			seperatorView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, height));
			mContainViewRoot.addView(seperatorView);
		}
		
		
		{
			mViewPagerAdapter = new UCMMenuViewPagerAdapter(mContext, mDataSource);
			
			mViewPager = new ViewPager(mContext);
			mViewPager.setAdapter(mViewPagerAdapter);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, VIEW_PAGER_WEIGHT);
			mViewPager.setLayoutParams(params);
			mContainViewRoot.addView(mViewPager);
		}
	}
}
