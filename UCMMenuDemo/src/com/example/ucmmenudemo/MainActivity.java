package com.example.ucmmenudemo;

import com.example.ucmmenudemo.UCMMenu.OnItemClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity

extends Activity

implements UCMMenuDataSource, OnItemClickListener {
	
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
			mMenu = new UCMMenu(this, this, this);
		}

		if (! mMenu.isShowing()) {
			View anchorView = this.findViewById(R.id.textView1);
			mMenu.show(anchorView);
		} else {
			mMenu.dismiss();
		}
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

	@Override
	public void onItemClick(int pagePosition, int itemPosition) {
		Log.i("ucmmenudemo", String.format("page %d, item %d clicked", pagePosition, itemPosition));
	}
}
