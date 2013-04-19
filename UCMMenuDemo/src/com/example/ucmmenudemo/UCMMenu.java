package com.example.ucmmenudemo;

import java.util.Arrays;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UCMMenu extends Object {

	private Context mContext;
	
	private ViewPager mViewPager;
	private LinearLayout mHeadersLayout;
	private View mHeaderViews[];
	private PopupWindow mPopupWindow;
	private LinearLayout mContainViewRoot;
	
	private UCMMenuDataSource mDataSource;
	
	private PagerAdapter mViewPagerAdapter;
	
	public interface OnItemClickListener {
		abstract public void onItemClick(int pagePosition, int itemPosition);
	}

	private OnItemClickListener mOnItemClickListener;

	private final static float VIEW_PAGER_WEIGHT = 3;
	private final static float HEADERS_LAYOUT_WEIGHT = 1;
	
	public UCMMenu(Context context, UCMMenuDataSource dataSource, OnItemClickListener onItemClickListener) {
		mContext = context;
		mDataSource = dataSource;
		mOnItemClickListener = onItemClickListener;
	}
	
	public void show(View anchorView) {
		setupViewsIfNeeded();
		mPopupWindow.showAtLocation(anchorView, Gravity.BOTTOM, anchorView.getLeft(), anchorView.getTop());
	}
	
	private void setupViewsIfNeeded() {
		
		if (mPopupWindow != null) {
			return;
		}
		
		mPopupWindow = new PopupWindow(mContext);
		mPopupWindow.setWidth(Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_width));
		mPopupWindow.setHeight(Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_height));
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
		mPopupWindow.setAnimationStyle(R.style.Animation);
		
		
		mContainViewRoot = new LinearLayout(mContext);
		mContainViewRoot.setOrientation(LinearLayout.VERTICAL);
		mContainViewRoot.setBackgroundResource(R.drawable.frame_multi_window_mid);
		
		
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
				textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
				textView.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.FILL_PARENT, 1));
				
				textView.setTag(String.format("%d", i));
				textView.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View view) {
						int pagePosition = Integer.parseInt((String)view.getTag());
						selectPage(pagePosition);
					}
				});
				
				mHeadersLayout.addView(textView);
				
				if (mHeaderViews == null) {
					mHeaderViews = new View[pageCount];
				}
				
				mHeaderViews[i] = textView;
			}
			
			this.highlightTitle(0);
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

		mViewPagerAdapter = new UCMMenuViewPagerAdapter(mContext, mDataSource, new UCMMenuGridViewAdapter.OnItemClickListener() {
			
			@Override
			public void onItemClick(int pagePosition, int itemPosition) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(pagePosition, itemPosition);
				}
			}
		});
		
		{
			mViewPager = new ViewPager(mContext);
			mViewPager.setAdapter(mViewPagerAdapter);
			
			mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					highlightTitle(position);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}

				@Override
				public void onPageScrollStateChanged(int arg0) {}
			});
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, VIEW_PAGER_WEIGHT);
			mViewPager.setLayoutParams(params);
			mContainViewRoot.addView(mViewPager);
		}
	}
	
	private void highlightTitle(int position) {
		
		for (int i = 0; i < mHeaderViews.length; ++i) {
			
			TextView textView = (TextView)(mHeaderViews[i]);
			
			if (i == position) {
				textView.setTextColor(mContext.getResources().getColor(R.color.white_color));
			} else {
				textView.setTextColor(mContext.getResources().getColor(R.color.gray_color));
			}
		}
	}
	
	private void selectPage(int pagePosition) {
		if (pagePosition != mViewPager.getCurrentItem()) {
			mViewPager.setCurrentItem(pagePosition, true);
			this.highlightTitle(pagePosition);
		}
	}
}
