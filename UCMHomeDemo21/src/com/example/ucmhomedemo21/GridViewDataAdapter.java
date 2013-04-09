package com.example.ucmhomedemo21;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewDataAdapter extends BaseAdapter {
	

	private Context context;
	
	public GridViewDataAdapter(Context context) {
		this.setContext(context);
	}

	protected Context getContext() {
		return context;
	}
	
	protected void setContext(Context context) {
		this.context = context; 
	}


	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getCount() {
		return DataSource.hotSitesStrings.length;
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		
//		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View xmlView = inflater.inflate(R.layout.hot_sites_view_item, null);
		
			
			TextView textView = (TextView) xmlView.findViewById(R.id.siteDescriptionString);
			textView.setText(DataSource.hotSitesStrings[position]);

			ImageView icon = (ImageView)xmlView.findViewById(R.id.siteIcon);
			Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sina);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
			icon.setImageDrawable(bitmapDrawable);
			
			view = xmlView;
//		}
		
		return view;
	}
}
