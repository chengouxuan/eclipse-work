package com.example.ucmhomedemo21;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		boolean isLandscape = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);
			
		if (isLandscape) {
			
			setContentView(R.layout.home_layout_landscape);
			
		} else {
			
			setContentView(R.layout.home_layout_portrait);
		}

		ListViewDataAdapter adapter = new ListViewDataAdapter(this);
		NavigationListView listView = (NavigationListView) findViewById(R.id.listView1);
		
		listView.setAdapter(adapter);
		
		
		GridViewDataAdapter gridViewDataAdapter = new GridViewDataAdapter(this);
		HotSitesGridView gridView = (HotSitesGridView) findViewById(R.id.gridView1).findViewById(R.id.innerGridView);
		
		gridView.setAdapter(gridViewDataAdapter);
		
		if (isLandscape) {

			listView.setScrollable(true);
			gridView.setScrollable(true);
			gridView.setNumColumns(2);
			gridViewDataAdapter.setNumColumns(2);
			
		} else {
			
			listView.setScrollable(false);
			gridView.setScrollable(false);
			gridView.setNumColumns(3);
			gridViewDataAdapter.setNumColumns(3);
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

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(senderView.toString());
		AlertDialog dialog = builder.create();
		dialog.show();
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
}