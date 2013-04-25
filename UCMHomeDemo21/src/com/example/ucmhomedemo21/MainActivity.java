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
import android.widget.ImageView.ScaleType;
import android.widget.ScrollView;
import android.widget.ListView;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MainActivity

extends Activity

implements LaunchController, ExpandableListViewController {
	
	private static boolean finishLaunching = false;
	private static boolean isInitializing = true;
	
	private Handler handler;
	
	private View contentViewLandscape;
	private View contentViewPortrait;
	private View startScreenView;
	
	private ExpandableListViewDataAdapter listViewAdapter;
	
	private UCMMenu menu;
	
	public MainActivity() {
		this.listViewAdapter = null;
		this.handler = null;
		this.contentViewLandscape = null;
		this.contentViewPortrait = null;
		this.startScreenView = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		if (this.handler == null) {
			this.handler = new Handler();
		}
		
		if (MainActivity.isInitializing) {

			if (this.startScreenView == null) {
				this.startScreenView = MainActivity.createStartScreenView(this);
			}
			
			this.setContentView(this.startScreenView);
			
			if (! MainActivity.finishLaunching) {
				MainActivity.finishLaunching = true;
				this.handler.postDelayed(new RunnableLauncher(this), 2 * 1000);
			}
			
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
		} else {

			this.setupHomeContentView();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_screen, menu);
		return true;
	}
	
	private void setupHomeContentView() {

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
		
		boolean isLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
		View contentView = null;

		if (isLandscape) {

			if (this.contentViewLandscape == null) {
				this.contentViewLandscape = new HomeLayoutLandscape(this);
			}
			
			contentView = this.contentViewLandscape;
			
		} else {

			if (this.contentViewPortrait == null) {
				this.contentViewPortrait = new HomeLayoutPortrait(this);
			}
			
			contentView = this.contentViewPortrait;
		}

		final int listViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_LIST_VIEW);
		final int hotSitesViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_LIST_VIEW);
		
		ExpandableListViewDataAdapter adapter = new ExpandableListViewDataAdapter(this);
		NavigationExpandableListView listView = (NavigationExpandableListView) contentView.findViewById(listViewId);
		this.listViewAdapter = adapter;
		this.listViewAdapter.setExpandableListViewController(this);
		
		listView.setAdapter(adapter);
		adapter.setExpandableListView(listView);
		
		GridViewDataAdapter gridViewDataAdapter = new GridViewDataAdapter(this);
		HotSitesGridView gridView = (HotSitesGridView) contentView.findViewById(hotSitesViewId);
		
		gridView.setAdapter(gridViewDataAdapter);
		gridViewDataAdapter.setGridView(gridView);
				
		if (isLandscape) {
			
			gridViewDataAdapter.setItemMinimumHeight(0);

			listView.setScrollable(true);
			
			gridView.setScrollable(true);
			gridView.setNumColumns(2);
			gridViewDataAdapter.setNumColumns(2);
			
			this.setupButtonSelectorsLandscape(contentView);
			this.setupImageLandscape(contentView);
			
		} else {

			float height = this.getResources().getDimension(R.dimen.hot_sites_item_height_portrait);
			gridViewDataAdapter.setItemMinimumHeight((int)(0.5f + height));
			listView.setScrollable(false);
			
			gridView.setScrollable(false);
			gridView.setNumColumns(3);
			gridViewDataAdapter.setNumColumns(3);
			
			this.setupButtonSelectorsPortrait(contentView);
		}
		
		this.setupDrawables(contentView);
		
		this.fixOverScrollModes(contentView);

		this.setContentView(contentView);
	}

	public void anyButtonClicked(View senderView) {

	}
	
	
	private void setupDrawables(View rootView) {

		final int itemId1 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM1);
		final int itemId2 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM2);
		final int itemId3 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM3);
		final int itemId4 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM4);
		final int itemId5 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM5);
		
		final int buttonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_BUTTON_IMAGE);
		
		{
			Drawable drawable = Utilities.clipIcon(this.getResources(), R.drawable.toolbar_1, 0, 1, 2, 11);
			ImageView imageView = (ImageView) rootView.findViewById(itemId1).findViewById(buttonId);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = Utilities.clipIcon(this.getResources(), R.drawable.toolbar_1, 0, 1, 3, 11);
			ImageView imageView = (ImageView) rootView.findViewById(itemId2).findViewById(buttonId);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = Utilities.clipIcon(this.getResources(), R.drawable.toolbar_1, 0, 1, 4, 11);
			ImageView imageView = (ImageView) rootView.findViewById(itemId3).findViewById(buttonId);
			imageView.setImageDrawable(drawable);
		}
	
		{
			Drawable drawable = Utilities.clipIcon(this.getResources(), R.drawable.toolbar_1, 0, 1, 5, 11);
			ImageView imageView = (ImageView) rootView.findViewById(itemId4).findViewById(buttonId);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = Utilities.clipIcon(this.getResources(), R.drawable.toolbar_1, 0, 1, 6, 11);
			ImageView imageView = (ImageView) rootView.findViewById(itemId5).findViewById(buttonId);
			imageView.setImageDrawable(drawable);
		}
	}
	
	private void setupButtonSelectorsPortrait(View rootView) {
		
		final int buttonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_BUTTON);
		final int itemId1 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM1);
		final int itemId2 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM2);
		final int itemId3 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM3);
		final int itemId4 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM4);
		final int itemId5 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM5);
		
		{
			Button button = (Button) rootView.findViewById(itemId1).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
		{
			Button button = (Button) rootView.findViewById(itemId2).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
		{
			Button button = (Button) rootView.findViewById(itemId3).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					showMenu(v);
				}
			});
		}
		{
			Button button = (Button) rootView.findViewById(itemId4).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
		}
		{
			Button button = (Button) rootView.findViewById(itemId5).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_portrait);
			
			button.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 System.exit(0);
	             }
	         });
		}
	}
	
	private void setupButtonSelectorsLandscape(View rootView) {

		final int buttonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_BUTTON);
		final int itemId1 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM1);
		final int itemId2 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM2);
		final int itemId3 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM3);
		final int itemId4 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM4);
		final int itemId5 = GlobalViewIds.getIdOf(GlobalViewIds.Ids.BOTTOM_BAR_ITEM5);
		
		{
			Button button = (Button) rootView.findViewById(itemId1).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) rootView.findViewById(itemId2).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) rootView.findViewById(itemId3).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) rootView.findViewById(itemId4).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}
		{
			Button button = (Button) rootView.findViewById(itemId5).findViewById(buttonId);
			button.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
			
			button.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 System.exit(0);
	             }
	         });
		}
	}
	
	private void setupImageLandscape(View rootView) {
		
		ImageView addBg = (ImageView) rootView.findViewById(GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_BACKGROUND));
		if (addBg != null) {
			addBg.setImageResource(R.drawable.add_url_bg_h);
		}
		
		ImageView quickButtonBg = (ImageView) rootView.findViewById(GlobalViewIds.getIdOf(GlobalViewIds.Ids.QUICK_BUTTON_BACKGROUND));
		if (quickButtonBg != null) {
			quickButtonBg.setImageResource(R.drawable.quick_button);
		}

		Button quickButton = (Button) rootView.findViewById(GlobalViewIds.getIdOf(GlobalViewIds.Ids.QUICK_BUTTON));
		if (quickButton != null) {
			quickButton.setBackgroundResource(R.drawable.bottom_bar_item_selector_landscape);
		}


		EditText editText = (EditText) rootView.findViewById(GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_EDIT_TEXT));
		if (null != editText) {
			editText.setTextColor(0xaaffffff);
			editText.setHintTextColor(0xaaffffff);
		}
		
		EditText editTextSearch = (EditText) rootView.findViewById(GlobalViewIds.getIdOf(GlobalViewIds.Ids.ADDRESS_BAR_EDIT_TEXT_SEARCH));
		if (null != editTextSearch) {
			editTextSearch.setTextColor(0xaaffffff);
			editTextSearch.setHintTextColor(0xaaffffff);
		}
	}
	
	private void fixOverScrollModes(View rootView) {
		
		final int scrollViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.SCROLL_VIEW);
		final int listViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_LIST_VIEW);

		ScrollView scrollView = (ScrollView) rootView.findViewById(scrollViewId);
		
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
		

		ListView listView = (ListView) rootView.findViewById(listViewId);
		
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

		final int listViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_LIST_VIEW);
		NavigationExpandableListView listView = (NavigationExpandableListView) this.findViewById(listViewId);
		
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

				 final int scrollViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.SCROLL_VIEW);
				 
				 ScrollView scrollView = (ScrollView) this.findViewById(scrollViewId);
				 Runnable runnable = new RunnableExecuteScroll(scrollView, listView, position);
				 this.handler.postDelayed(runnable, 0);
			 }
			 
		 } else {
			 listView.collapseGroup(position);
		 }
	}

	@Override
	public void onFinishLaunching() {
		MainActivity.isInitializing = false;
		this.setupHomeContentView();
	}

	private static View createStartScreenView(Context context) {
		
		ImageView view = new ImageView(context);
		
		view.setScaleType(ScaleType.CENTER_CROP);
		view.setImageResource(R.drawable.init_logo);
		
		return view;
	}

	@Override
	public void groupClicked(View senderView) {
		this.onExpandCollapseGroup(senderView);
	}

	private void showMenu(View anchorView) {
		
		if (this.menu == null) {
			this.menu = new UCMMenu(this, new UCMMenuDefaultDataSource(this), new UCMMenu.OnItemClickListener() {
				@Override
				public void onItemClick(int pagePosition, int itemPosition) {
					Log.i("xxx", String.format("item %d at page %d clicked", itemPosition, pagePosition));
				}
			});
		}

		if (this.menu.isShowing()) {
			this.menu.dismiss();
		} else {
			this.menu.show(anchorView);
		}
	}
}
