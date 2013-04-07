package com.example.ucmhomedemo21;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class HotSitesGridView extends GridView {

	public HotSitesGridView(Context context) {
		super(context);
	}

	public HotSitesGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HotSitesGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
