package com.example.ucmhomedemo21;

import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewDataAdapter extends BaseAdapter {
	
	
	private Context context;

	protected Context getContext() {
		return context;
	}
	
	protected void setContext(Context context) {
		this.context = context;
	}
	
	ListViewDataAdapter(Context context) {
		this.setContext(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		
		
//		if (convertView == null) {
//			if (position != 0) {
				
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				View xmlView = inflater.inflate(R.layout.navigation_list_view_item, null);
				
				TextView textView = (TextView)xmlView.findViewById(R.id.navigationString);
				textView.setText(DataSource.navigationStrings[position]);
				
				ImageView icon = (ImageView)xmlView.findViewById(R.id.navigationIcon);
				Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.glob);
				BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
				icon.setImageDrawable(bitmapDrawable);
				
				TextView detailTextView = (TextView)xmlView.findViewById(R.id.navigationDetailString);
				detailTextView.setText(DataSource.navigationDetailStrings[position]);
				
				view = xmlView;
//			} else {
//				
//
//				GridView gridView = new HotSitesGridView(getContext());
//				gridView.setAdapter(new GridViewDataAdapter(getContext()));
//				gridView.setNumColumns(DataSource.hotSitesColumns);
//				
//				view = gridView;
//			}
//		}
		
		return view;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getCount() {
		return DataSource.navigationStrings.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}
}
