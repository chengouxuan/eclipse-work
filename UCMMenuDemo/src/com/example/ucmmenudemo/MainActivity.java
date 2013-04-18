package com.example.ucmmenudemo;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	private UCMMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		ViewPager viewPager = new ViewPager(this);
//		viewPager.setAdapter(new UCMMenuViewPagerAdapter(this));
//		setContentView(viewPager);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showPopupWindow(View senderView) {
		if (this.menu == null) {
			this.menu = new UCMMenu(this);
		}

		View anchorView = this.findViewById(R.id.textView1);
		menu.show(anchorView);
	}
}
