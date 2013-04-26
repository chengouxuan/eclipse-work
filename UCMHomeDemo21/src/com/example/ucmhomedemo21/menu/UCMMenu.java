package com.example.ucmhomedemo21.menu;


import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ucmhomedemo21.R;
import com.example.ucmhomedemo21.Utilities;

public class UCMMenu

extends Object {

	private Context mContext;
	
	private ViewPager mViewPager;
	private LinearLayout mHeadersLayout;
	private View mHeaderViews[];
	private UCMMenuInternal mPopupWindow;
	private LinearLayout mLinearLayoutVertical;
	private RelativeLayout mContainViewRoot;
	private UCMMenuBackgroundLayout mBackground;
	
	private UCMMenuSliderView mSliderView;
	
	private UCMMenuDataSource mDataSource;
	
	private PagerAdapter mViewPagerAdapter;
	
	private Handler mHandler;
	
	private EnterType mEnterType;
	
	public interface OnItemClickListener {
		abstract public void onItemClick(int pagePosition, int itemPosition);
	}

	private OnItemClickListener mOnItemClickListener;

	private View mAnchorView;

	private int mArrowRightWidth;

	private int mArrowLeftWidth;

	private final static float VIEW_PAGER_WEIGHT = 4;
	private final static float HEADERS_LAYOUT_WEIGHT = 1;
	
	public UCMMenu(Context context, UCMMenuDataSource dataSource, OnItemClickListener onItemClickListener) {
		mContext = context;
		mDataSource = dataSource;
		mOnItemClickListener = onItemClickListener;
	}
	
	public boolean isShowing() {
		return (mPopupWindow != null && mPopupWindow.isShowing());
	}
	
	public enum EnterType {
		DOWN,
		UP
	}
	
	private void setupArrowPosition(int popupWindowShowPositionX, int popupWindowWidth) {
		
		Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
		int width = display.getWidth();
		
		mArrowLeftWidth = popupWindowWidth / 2;
		
		int diff = popupWindowShowPositionX + popupWindowWidth - width;
		
		if (diff > 0) {
			mArrowLeftWidth += diff;
		}

		if (popupWindowShowPositionX < 0) {
			mArrowLeftWidth += popupWindowShowPositionX;
		}
		
		mArrowRightWidth = popupWindowWidth - mArrowLeftWidth;
	}
	
	public void show(View anchorView, EnterType showType) {

		mEnterType = showType;

		int x = this.getRelativeLeft(anchorView);
		int y = this.getRelativeTop(anchorView);
		
		int xOffset = (int)(anchorView.getWidth() / 2f - this.getPopupWindowWidth() / 2f + 0.5f);
		
		this.setupArrowPosition(x + xOffset, this.getPopupWindowWidth());
		
		setupViewsIfNeeded();
		
		if (showType == EnterType.UP) {
			mPopupWindow.showAtLocation(anchorView, Gravity.LEFT | Gravity.TOP, x + xOffset, y - mPopupWindow.getHeight(), UCMMenuInternal.EnterType.UP);
		} else {
			mPopupWindow.showAtLocation(anchorView, Gravity.LEFT | Gravity.TOP, x + xOffset, y + anchorView.getHeight(), UCMMenuInternal.EnterType.DOWN);
		}
	}

	public void dismiss() {
		
		if (mPopupWindow == null || !mPopupWindow.isShowing() || mPopupWindow.isAnimating()) {
			return;
		}
		
		mPopupWindow.forceDismiss();
	}
	
	private void setupViewsIfNeeded() {
		
		if (mPopupWindow != null) {
			return;
		}
		
		mPopupWindow = new UCMMenuInternal(mContext);
		mPopupWindow.setAnimationAnchorX(mArrowLeftWidth);
		mPopupWindow.setWidth(this.getPopupWindowWidth());
		mPopupWindow.setHeight(this.getPopupWindowHeight());
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
		
		mPopupWindow.setOutsideTouchable(true); 
		mPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					dismiss();
				}
				
				return false;
			}
		});
		
		mLinearLayoutVertical = new LinearLayout(mContext);
		mLinearLayoutVertical.setOrientation(LinearLayout.VERTICAL);
		
		
		{
			mHeadersLayout = new LinearLayout(mContext);
			mHeadersLayout.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, HEADERS_LAYOUT_WEIGHT);
			mHeadersLayout.setLayoutParams(params);
			mLinearLayoutVertical.addView(mHeadersLayout);
			
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
			
			if (mHandler == null) {
				mHandler = new Handler();
			}
			
			this.onViewPagerPageScrolled(0, 0, 0);
		}
		
		{
			this.createSliderViewIfNeeded();
			
			mLinearLayoutVertical.addView(mSliderView);
		}
		
		this.createViewPagerAdapterIfNeeded();
		
		{
			mViewPager = new ViewPager(mContext);
			mViewPager.setAdapter(mViewPagerAdapter);
			
			mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				
				@Override
				public void onPageSelected(int position) {
					highlightTitle(position);
				}

				@Override
				public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
					
					if (mHandler == null) {
						mHandler = new Handler();
					}
					
					mHandler.postDelayed(new Runnable() {
						@Override
						public void run() {
							onViewPagerPageScrolled(position, positionOffset, positionOffsetPixels);
						}
					}, 0);
					
				}

				@Override
				public void onPageScrollStateChanged(int arg0) {}
			});
			
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, 0, VIEW_PAGER_WEIGHT);
			mViewPager.setLayoutParams(params);
			mLinearLayoutVertical.addView(mViewPager);
			
			this.fixViewPagerOverScrollMode();
		}
		
		
		if (mEnterType == EnterType.UP) {
			mBackground = new UCMMenuBackgroundLayout(mContext, UCMMenuBackgroundLayout.ArrowType.ON_BOTTOM, mArrowLeftWidth, mArrowRightWidth);
		} else {
			mBackground = new UCMMenuBackgroundLayout(mContext, UCMMenuBackgroundLayout.ArrowType.ON_TOP, mArrowLeftWidth, mArrowRightWidth);
		}
		
		
		mContainViewRoot = new RelativeLayout(mContext);
		
		mBackground.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mContainViewRoot.addView(mBackground);
		

		int contentPadding = Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_content_padding);
		mLinearLayoutVertical.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		mLinearLayoutVertical.setPadding(contentPadding, contentPadding, contentPadding, contentPadding);
		mContainViewRoot.addView(mLinearLayoutVertical);
		
		
		
		mPopupWindow.setContentView(mContainViewRoot);
	}
	
	private int getPopupWindowHeight() {
		return Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_height);
	}

	private int getPopupWindowWidth() {
		return Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_width);
	}

	private void createViewPagerAdapterIfNeeded() {
		if (mViewPagerAdapter == null) {
			mViewPagerAdapter = new UCMMenuViewPagerAdapter(mContext, mDataSource, new UCMMenuGridViewAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(int pagePosition, int itemPosition) {
					if (mOnItemClickListener != null) {
						mOnItemClickListener.onItemClick(pagePosition, itemPosition);
					}
				}
			});
		}
	}

	private void createSliderViewIfNeeded() {
		
		if (mSliderView == null) {
			
			mSliderView = new UCMMenuSliderView(
					mContext,
					Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_slider_width),
					Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_slider_height),
					this.getSliderTotalWidth()
					);
			
			mSliderView.setBackgroundResource(R.drawable.list_divider);
			mSliderView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			int height = Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_seperator_height);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(this.getSliderTotalWidth(), height);
			mSliderView.setLayoutParams(params);
		}
	}

	private int getSliderTotalWidth() {
		return Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_seperator_total_width);
	}

	private void fixViewPagerOverScrollMode() {
		try {
			
			Class<?> c = Class.forName("android.support.v4.view.ViewPager");
			Method  method = c.getMethod("setOverScrollMode", int.class);
			method.invoke(mViewPager, 2);
			
		} catch (Exception e) {

			Log.i("ucmmenudemo", String.format("%s", e.toString()));
			return;
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
	
	private void onViewPagerPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		this.createSliderViewIfNeeded();
		this.createViewPagerAdapterIfNeeded();
		
		float w = mSliderView.getSliderWidth();
		float l = this.getSliderTotalWidth();
		float t = (l - w * mViewPagerAdapter.getCount()) / (mViewPagerAdapter.getCount() * 2);
		
		float positionRate = (t + w / 2 + (w + 2 * t) * (position + positionOffset)) / l;
		
		mSliderView.setPositionRate(positionRate);
	}
}
