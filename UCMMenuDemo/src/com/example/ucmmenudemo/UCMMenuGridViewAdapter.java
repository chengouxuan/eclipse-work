package com.example.ucmmenudemo;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UCMMenuGridViewAdapter extends BaseAdapter {

	private Context mContext;
	UCMMenuDataSource mDataSource;
	int mPagePosition;
	
	public UCMMenuGridViewAdapter(Context context, UCMMenuDataSource dataSource, int pagePosition) {
		mContext = context;
		mDataSource = dataSource;
		mPagePosition = pagePosition;
	}

	@Override
	public int getCount() {
		return mDataSource.getItemCount(mPagePosition);
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		RelativeLayout layout = new RelativeLayout(mContext);
		layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		layout.setMinimumHeight(Utilities.getRoundedDimension(mContext.getResources(), R.dimen.menu_item_minimal_height));
//		layout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		final int iconId = 10003;
		final int titleId = 10004;
		
		ImageButton button = new ImageButton(mContext);
		button.setBackgroundResource(R.drawable.item_button_selector);
		RelativeLayout.LayoutParams buttonLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		buttonLayoutParams.addRule(RelativeLayout.ALIGN_BOTTOM, titleId);
		button.setLayoutParams(buttonLayoutParams);
		layout.addView(button);
		
		ImageView icon = new ImageView(mContext);
		icon.setId(iconId);
		icon.setScaleType(ScaleType.CENTER_INSIDE);
		icon.setImageResource(mDataSource.getItemIconResourceId(mPagePosition, position));
		icon.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		layout.addView(icon);
		
		TextView title = new TextView(mContext);
		title.setId(titleId);
		title.setText(mDataSource.getItemTitle(mPagePosition, position));
		title.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		RelativeLayout.LayoutParams titleLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		titleLayoutParams.addRule(RelativeLayout.BELOW, iconId);
		title.setLayoutParams(titleLayoutParams);
		layout.addView(title);
		
		return layout;
	}

	
}
