package com.example.ucmmenudemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.GridView;
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

		GridView gridView = new GridView(mContext);
		gridView.setAdapter(new UCMMenuGridViewAdapter(mContext, mDataSource, position));
		gridView.setNumColumns(mDataSource.getColumnCount(position));
		gridView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//		gridView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		if (container != null) {
			container.addView(gridView);
		}
		
		return gridView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
	}
}
