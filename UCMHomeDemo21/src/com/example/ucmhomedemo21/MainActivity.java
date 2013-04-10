package com.example.ucmhomedemo21;

import java.nio.channels.Selector;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.ViewParent;

import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
//import android.widget.GridView;
import android.widget.ListView;
import android.app.AlertDialog;
import android.content.res.Configuration;
//import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {
	
	private Handler handler;
	
	private ExpandableListViewDataAdapter listViewAdapter;
	
	public MainActivity() {
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
		listView.setListViewInfo(adapter);
		
		GridViewDataAdapter gridViewDataAdapter = new GridViewDataAdapter(this);
		HotSitesGridView gridView = (HotSitesGridView) findViewById(R.id.gridView1).findViewById(R.id.innerGridView);
		
		gridView.setAdapter(gridViewDataAdapter);
		gridViewDataAdapter.setGridView(gridView);
		
		GridViewGridBackgroundView gridBackgroundView = (GridViewGridBackgroundView) findViewById(R.id.gridBackground);
		gridBackgroundView.setGridViewInfo(gridViewDataAdapter);
		
		if (isLandscape) {

			listView.setScrollable(true);
			
			gridView.setScrollable(true);
			gridView.setNumColumns(2);
			gridViewDataAdapter.setNumColumns(2);
			
			gridBackgroundView.setColumns(2);
			gridBackgroundView.setRows(gridViewDataAdapter.getNumRows());
			
			this.setupButtonSelectorsLandscape();
			
		} else {
			
			listView.setScrollable(false);
			
			gridView.setScrollable(false);
			gridView.setNumColumns(3);
			gridViewDataAdapter.setNumColumns(3);

			gridBackgroundView.setColumns(3);
			gridBackgroundView.setRows(gridViewDataAdapter.getNumRows());
			
			this.setupButtonSelectorsPortrait();
		}
		
		this.setupDrawables();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_screen, menu);
		return true;
	}

	public void anyButtonClicked(View senderView) {

//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage(senderView.toString());
//		AlertDialog dialog = builder.create();
//		dialog.show();
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
	
	public void onExpandCollapseGroup(View senderView) {

		NavigationExpandableListView listView = (NavigationExpandableListView) findViewById(R.id.listView1);
		
		int position = Integer.parseInt((String) senderView.getTag());
		
		 if (! listView.isGroupExpanded(position)) {

			 for (int i = 0; i < listViewAdapter.getGroupCount(); ++i) {
				 if (i != position) {
					 listView.collapseGroup(i);
				 } else {
					 listView.expandGroup(position);
				 }
			 }
			 
			 boolean isLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
				
			 if (isLandscape) {
				 
				 int offset = MainActivity.computeTopOnRoot((View) senderView.getParent())
						 - MainActivity.computeTopOnRoot(listView) - listView.getPaddingTop();

//				 this.handler.postDelayed(new RunnableListViewExecuteScroll(listView, 0, offset), 100);
				 listView.scrollTo(0, offset);
				 
			 } else {

				 ScrollView scrollView = (ScrollView) this.findViewById(R.id.scrollView1);
				 int offset = MainActivity.computeTopOnRoot((View) senderView.getParent())
						 - MainActivity.computeTopOnRoot(scrollView) - scrollView.getPaddingTop();
				 
				 this.handler.postDelayed(new RunnableExecuteScroll(scrollView, 0, offset), 0);
			 }
			 
		 } else {
			 listView.collapseGroup(position);
		 }
	}

	protected static int computeTopOnRoot(View view) {
		
		int top = view.getTop();
		ViewParent viewParent = view.getParent();
		
		while (true) {

			if (viewParent instanceof View) {
				view = (View) viewParent;
				top += view.getTop();
			}
			
			viewParent = viewParent.getParent();
			
			if (viewParent == null) {
				break;
			}
			
		}
		
		return top;
	}
}
