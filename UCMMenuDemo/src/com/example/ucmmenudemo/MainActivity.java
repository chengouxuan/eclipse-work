package com.example.ucmmenudemo;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity

extends Activity

implements UCMMenuDataSource {
	
	private UCMMenu mMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showPopupWindow(View senderView) {
		if (mMenu == null) {
			mMenu = new UCMMenu(this, this);
		}

		View anchorView = this.findViewById(R.id.textView1);
		mMenu.show(anchorView);
	}

	@Override
	public String getPageTitle(int pagePosition) {
		return String.format("Page %d", pagePosition);
	}

	@Override
	public int getItemIconResourceId(int pagePosition, int itemPosition) {
		return R.drawable.address_input_list_safe;
	}

	@Override
	public String getItemTitle(int pagePosition, int itemPosition) {
		return String.format("Item %d", itemPosition);
	}

	@Override
	public int getPageCount() {
		return 3;
	}

	@Override
	public int getItemCount(int pagePosition) {
		return 8;
	}

	@Override
	public int getColumnCount(int pagePosition) {
		return 4;
	}
}
