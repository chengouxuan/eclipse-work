package com.example.ucmhomedemo21;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;

public class UCMMenuInternal extends PopupWindow {
	
	private boolean mAllowDismiss;
	
	private int mLocationX;
	private int mLocationY;

	public UCMMenuInternal(Context context) {
		super(context);
		mAllowDismiss = false;
	}
	
	public void forceDismiss() {
		super.dismiss();
	}

	@Override
	public void dismiss() {
		if (mAllowDismiss) {
			mAllowDismiss = false;
			super.dismiss();
		}
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
		mLocationX = x;
		mLocationY = y;
	}
	
	public int getLocationX() {
		return mLocationX;
	}
	
	public int getLocationY() {
		return mLocationY;
	}
}
