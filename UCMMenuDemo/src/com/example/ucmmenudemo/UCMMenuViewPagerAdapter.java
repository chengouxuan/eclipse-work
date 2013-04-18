package com.example.ucmmenudemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class UCMMenuViewPagerAdapter extends PagerAdapter {
	
	private Context context;
	
	private static int generalChildViewId = 10003;

	public UCMMenuViewPagerAdapter(Context context) {
		// TODO Auto-generated constructor stub
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		Log.i("xxx", String.format("object = %s, view = %s", object.toString(), view.toString()));
		return (view == object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		Log.i("xxx", "instantiateItem");
		
		assert container != null;
		
		LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		assert inflater != null;
		
		View view = inflater.inflate(R.layout.text, null);
		assert view != null;
		
		container.addView(view);
		
		return view;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		
		Log.i("xxx", "destroyItem");
		
		View child = container.findViewById(UCMMenuViewPagerAdapter.generalChildViewId);
		assert(child != null);
		
		container.removeView(child);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return String.format("title %d", position);
	}
	
	@Override
	public void startUpdate(ViewGroup container) {
		Log.i("xxx", "startUpdate");
	}
	
	@Override
	public void finishUpdate(ViewGroup container) {
		Log.i("xxx", "finishUpdate");
	}
	
}
