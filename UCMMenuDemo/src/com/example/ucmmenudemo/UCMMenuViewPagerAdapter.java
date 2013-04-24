package com.example.ucmmenudemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class UCMMenuViewPagerAdapter

extends PagerAdapter {
	
	private Context mContext;
	private UCMMenuDataSource mDataSource;
	private UCMMenuGridViewAdapter.OnItemClickListener mOnItemClickListener;
	
	public UCMMenuViewPagerAdapter(Context context, UCMMenuDataSource dataSource, UCMMenuGridViewAdapter.OnItemClickListener onItemClickListener) {
		super();
		mContext = context;
		mDataSource = dataSource;
		mOnItemClickListener = onItemClickListener;
	}

	private int getViewId(int pagePosition) {
		return pagePosition + 1007;
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
		gridView.setAdapter(new UCMMenuGridViewAdapter(mContext, mDataSource, position, mOnItemClickListener));
		gridView.setNumColumns(mDataSource.getColumnCount(position));
		gridView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//		gridView.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		if (container != null) {
			gridView.setId(this.getViewId(position));
			container.addView(gridView);
		}
		
		return gridView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (container != null) {
			View view = container.findViewById(this.getViewId(position));
			container.removeView(view);
		}
	}
}
