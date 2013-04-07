package com.example.ucmhomedemo21;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
//import android.widget.GridView;
import android.widget.ListView;
import android.app.AlertDialog;
//import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		setContentView(R.layout.activity_start_screen);
		setContentView(R.layout.home);

		ArrayAdapter<String> adapter = new ListViewDataAdapter<String>(this,
				R.layout.navigation_list_view_item,
				DataSource.navigationStrings);
		ListView listView = (ListView) findViewById(R.id.listView1);

		
		listView.setAdapter(adapter);
		
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
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarButtonImage1);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 3, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarButtonImage2);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 4, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarButtonImage3);
			imageView.setImageDrawable(drawable);
		}
	
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 5, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarButtonImage4);
			imageView.setImageDrawable(drawable);
		}
		
		{
			Drawable drawable = this.clipIconAtIndex(R.drawable.toolbar_1, 6, 11);
			ImageView imageView = (ImageView) this.findViewById(R.id.bottomBarButtonImage5);
			imageView.setImageDrawable(drawable);
		}
	}
}