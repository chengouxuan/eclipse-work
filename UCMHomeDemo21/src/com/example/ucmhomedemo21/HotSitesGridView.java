package com.example.ucmhomedemo21;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

public class HotSitesGridView extends GridView {
	
	private boolean scrollable;

	public HotSitesGridView(Context context) {
		super(context);
		this.setScrollable(false);
	}

	public HotSitesGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setScrollable(false);
	}

	public HotSitesGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setScrollable(false);
	}



	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if (! this.isScrollable()) {
			
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
			
		} else {
			
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}
}
