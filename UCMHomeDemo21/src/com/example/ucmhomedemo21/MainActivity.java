package com.example.ucmhomedemo21;

import java.lang.reflect.Method;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.ListView;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {
	
	private Handler handler;
	
	private ExpandableListViewDataAdapter listViewAdapter;
	
	public MainActivity() {
		this.listViewAdapter = null;
		this.handler = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if (this.handler == null) {
			this.handler = new Handler();
		}
		
		boolean isLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
			
		if (isLandscape) {
			
			setContentView(R.layout.home_layout_landscape);
			
		} else {
			
			setContentView(R.layout.home_layout_portrait);
		}

		ExpandableListViewDataAdapter adapter = new ExpandableListViewDataAdapter(this);
		NavigationExpandableListView listView = (NavigationExpandableListView) findViewById(R.id.listView1);
		this.listViewAdapter = adapter;
		
		listView.setAdapter(adapter);
		adapter.setExpandableListView(listView);
		
		GridViewDataAdapter gridViewDataAdapter = new GridViewDataAdapter(this);
		HotSitesGridView gridView = (HotSitesGridView) findViewById(R.id.gridView1).findViewById(R.id.innerGridView);
		
		gridView.setAdapter(gridViewDataAdapter);
		gridViewDataAdapter.setGridView(gridView);
				
		if (isLandscape) {

			listView.setScrollable(true);
			
			gridView.setScrollable(true);
			gridView.setNumColumns(2);
			gridViewDataAdapter.setNumColumns(2);
			
			this.setupButtonSelectorsLandscape();
			this.setupImageLandscape();
			
		} else {
			
			listView.setScrollable(false);
			
			gridView.setScrollable(false);
			gridView.setNumColumns(3);
			gridViewDataAdapter.setNumColumns(3);
			
			this.setupButtonSelectorsPortrait();
		}
		
		this.setupDrawables();
		
		this.fixOverScrollModes();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_screen, menu);
		return true;
	}

	public void anyButtonClicked(View senderView) {

	}
	
	private Drawable clipIconAtIndex(int resourceId, int index, int totalIcons) {
		
		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), resourceId);
		int width = bitmap.getWidth() / totalIcons;
		int x = index * width;
		int y = 0;
		bitmap = Bitmap.createBitmap(bitmap, x, y, width, bitmap.getHeight());

		Drawable drawable = new BitmapDrawable(this.getResources(), bitmap);
		return drawable;
	}
	
	private void setupDrawables() {
		
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 2, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarItemLayout1).findViewById(R.id.bottomBarButtonImage);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 3, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarItemLayout2).findViewById(R.id.bottomBarButtonImage);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 4, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarItemLayout3).findViewById(R.id.bottomBarButtonImage);
			imageView.setImageDrawable(drawable);
		}
	
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 5, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarItemLayout4).findViewById(R.id.bottomBarButtonImage);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 6, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarItemLayout5).findViewById(R.id.bottomBarButtonImage);
			imageView.setImageDrawable(drawable);
		}
	}
	
	private void setupButtonSelectorsPortrait() {
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout1).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout2).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout3).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout4).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout5).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
	}
	
	private void setupButtonSelectorsLandscape() {
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout1).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout2).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout3).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout4).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) this.findViewById(R.id.bottomBarItemLayout5).findViewById(R.id.bottomBarButton);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
	}
	
	private void setupImageLandscape() {
		
		View addBg = this.findViewById(R.id.addressBarBackground);
		if (addBg != null) {
			addBg.setBackgroundResource(R.drawable.add_url_bg_h);
		}
		
		View quickButtonBg = this.findViewById(R.id.quickButton);
		if (quickButtonBg != null) {
			quickButtonBg.setBackgroundResource(R.drawable.quick_button);
		}
		
		View quickButton = this.findViewById(R.id.button2);
		if (quickButton != null) {
			quickButton.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}

		EditText editText = (EditText) this.findViewById(R.id.addressBarEditText);
		if (null != editText) {
			editText.setTextColor(0xaaffffff);
			editText.setHintTextColor(0xaaffffff);
		}
		
		EditText editTextSearch = (EditText) this.findViewById(R.id.addressBarEditTextSearch);
		if (null != editTextSearch) {
			editTextSearch.setTextColor(0xaaffffff);
			editTextSearch.setHintTextColor(0xaaffffff);
		}
	}
	
	private void fixOverScrollModes() {

		ScrollView scrollView = (ScrollView) this.findViewById(R.id.linearLayoutOuterScrollView);
		
		if (scrollView != null) {
			
			try {
				
				Class<?> c = Class.forName("android.widget.ScrollView");
				Method  method = c.getMethod("setOverScrollMode", int.class);
				method.invoke(scrollView, 2);
				
			} catch (Exception e) {

				Log.i("scrollview", String.format("%s", e.toString()));
				return;
			}
		}
		

		ListView listView = (ListView) this.findViewById(R.id.listView1);
		
		if (listView != null) {
			
			try {
				
				Class<?> c = Class.forName("com.example.ucmhomedemo21.NavigationExpandableListView");
				Method  method = c.getMethod("setOverScrollMode", int.class);
				method.invoke(listView, 2);
				
			} catch (Exception e) {

				Log.i("listview", String.format("%s", e.toString()));
				return;
			}
			
		}
	}
	
	public void onExpandCollapseGroup(View senderView) {

		NavigationExpandableListView listView = (NavigationExpandableListView) findViewById(R.id.listView1);
		
		int position = Integer.parseInt((String) senderView.getTag());
		
		 if (! listView.isGroupExpanded(position)) {

			 for (int i = 0; i < listViewAdapter.getGroupCount(); ++i) {
				 if (i != position) {
					 if (listView.isGroupExpanded(i)) {
						 listView.collapseGroup(i);
					 }
				 } else {
					 listView.expandGroup(position);
				 }
			 }
			 
			 boolean isLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
				
			 if (isLandscape) {
				 
				 listView.setSelectedGroup(position);
				 
			 } else {

				 ScrollView scrollView = (ScrollView) this.findViewById(R.id.linearLayoutOuterScrollView);
				 Runnable runnable = new RunnableExecuteScroll(scrollView, listView, position);
				 this.handler.postDelayed(runnable, 0);
			 }
			 
		 } else {
			 listView.collapseGroup(position);
		 }
	}

}
