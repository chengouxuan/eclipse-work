package com.example.ucmmenudemo;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class UCMMenu extends Object {

	private Context mContext;
	
	private ViewPager mViewPager;
	private LinearLayout mHeadersLayout;
	private View mHeaderViews[];
	private PopupWindow mPopupWindow;
	private LinearLayout mContainViewRoot;
	
	private UCMMenuSliderView mSliderView;
	
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
	
	public boolean isShowing() {
		return (mPopupWindow != null && mPopupWindow.isShowing());
	}
	
	public void show(View anchorView) {
		
		setupViewsIfNeeded();
		 
		int x = this.getRelativeLeft(anchorView);
		int y = this.getRelativeTop(anchorView);
		
		mPopupWindow.showAtLocation(anchorView, Gravity.LEFT | Gravity.TOP, x, y - mPopupWindow.getHeight());
	}
	
	public void dismiss() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
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
			mSliderView = new UCMMenuSliderView(
					mContext,
					Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_slider_width),
					Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_slider_height)
					);
			
			mSliderView.setBackgroundResource(R.drawable.list_divider);
			int padding = Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_seperator_padding);
			mSliderView.setPadding(padding, 0, padding, 0);
			int height = Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_seperator_height);
			mSliderView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, height));
			mContainViewRoot.addView(mSliderView);
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
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					
					Log.i("ucmmenudemo", String.format("onPageScrolled, position = %d, positioinOffset = %f, positionOffsetPixels = %d",
							position, positionOffset, positionOffsetPixels));
					
					if (mSliderView != null) {
						float positionRate = (positionOffset + position) / mViewPagerAdapter.getCount();
						mSliderView.setPositionRate(positionRate);
						
						Log.i("ucmmenudemo", String.format("positionRate = %f", positionRate));
					}
				}

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
	
	private int getRelativeLeft(View myView) {
	    if (myView.getParent() == myView.getRootView()) {
	    	return myView.getLeft();
	    } else {
	    	return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	    }
	}

	private int getRelativeTop(View myView) {
	    if (myView.getParent() == myView.getRootView()) {
	    	return myView.getTop();
	    } else {
	    	return myView.getTop() + getRelativeTop((View) myView.getParent());
	    }
	}
}
