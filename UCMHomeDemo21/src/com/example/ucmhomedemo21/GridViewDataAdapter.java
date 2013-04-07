package com.example.ucmhomedemo21;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
			
			TextView textView = new TextView(getContext());
			textView.setText(DataSource.hotSitesStrings[position]);
			
			view = textView;
//		}
		
		return view;
	}
}
