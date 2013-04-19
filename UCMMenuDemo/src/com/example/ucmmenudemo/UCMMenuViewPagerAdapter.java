package com.example.ucmmenudemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class UCMMenuViewPagerAdapter

extends PagerAdapter {
	
	private Context mContext;
	
	private UCMMenuDataSource mDataSource;
	
	public UCMMenuViewPagerAdapter(Context context, UCMMenuDataSource dataSource) {
		super();
		mContext = context;
		mDataSource = dataSource;
	}

	@Override
	public int getCount() {
		return mDataSource.getPageCount();
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (view == object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		TextView textView = new TextView(mContext);
		textView.setText(String.format("page %d", position));
		
		if (container != null) {
			container.addView(textView);
		}
		
		return textView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		;
	}
}
