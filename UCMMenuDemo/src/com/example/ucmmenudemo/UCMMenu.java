package com.example.ucmmenudemo;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

public class UCMMenu {
	
	private PopupWindow popupWindow;
	private Context context;

	public UCMMenu(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public void show(View anchorView) {
		
		if (this.popupWindow == null) {
			this.popupWindow = this.createPopupWindow();
		}
		
		this.popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, anchorView.getLeft(), anchorView.getTop());
	}
	
	private PopupWindow createPopupWindow() {
		
		PopupWindow popupWindow = new PopupWindow(this.context);
		popupWindow.setWidth(200);
		popupWindow.setHeight(200);
		
		ViewPager viewPager = new ViewPager(this.context);
		viewPager.setAdapter(new UCMMenuViewPagerAdapter(this.context));
		
		popupWindow.setContentView(viewPager);
//		LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		assert(inflater != null);
//		
//		View textView = inflater.inflate(R.layout.text, null);
//		assert(textView != null);
//		popupWindow.setContentView(textView);
		
		popupWindow.setBackgroundDrawable(new ColorDrawable(0x77777777));
		
		return popupWindow;
	}
}
