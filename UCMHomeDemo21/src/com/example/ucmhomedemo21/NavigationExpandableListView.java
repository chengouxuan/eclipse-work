package com.example.ucmhomedemo21;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ExpandableListView;

public class NavigationExpandableListView extends ExpandableListView {

	private boolean scrollable;

	private ListViewInfo listViewInfo;
	
	public NavigationExpandableListView(Context context) {
		super(context);
		this.setScrollable(false);
	}

	public NavigationExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setScrollable(false);
	}

	public NavigationExpandableListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		this.setScrollable(false);
	}

	public boolean isScrollable() {
		return this.scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (!this.isScrollable()) {

			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
			
		} else {
			
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public ListViewInfo getListViewInfo() {
		return listViewInfo;
	}

	public void setListViewInfo(ListViewInfo listViewInfo) {
		this.listViewInfo = listViewInfo;
	}

}
