package com.example.ucmhomedemo21;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ListViewDataAdapter<T> extends ArrayAdapter<T> {
	


	public ListViewDataAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public ListViewDataAdapter(Context context, int resource,
			int textViewResourceId) {
		super(context, resource, textViewResourceId);
	}

	public ListViewDataAdapter(Context context, int textViewResourceId,
			T[] objects) {
		super(context, textViewResourceId, objects);
	}

	public ListViewDataAdapter(Context context, int textViewResourceId,
			List<T> objects) {
		super(context, textViewResourceId, objects);
	}

	public ListViewDataAdapter(Context context, int resource,
			int textViewResourceId, T[] objects) {
		super(context, resource, textViewResourceId, objects);
	}

	public ListViewDataAdapter(Context context, int resource,
			int textViewResourceId, List<T> objects) {
		super(context, resource, textViewResourceId, objects);
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		
		
//		if (convertView == null) {
			if (position != 0) {
				
				TextView textView = new TextView(getContext());
				textView.setText(DataSource.navigationStrings[position]);
				
				view = textView;
			} else {
				

				GridView gridView = new HotSitesGridView(getContext());
				gridView.setAdapter(new GridViewDataAdapter(getContext()));
				gridView.setNumColumns(DataSource.hotSitesColumns);
				
				view = gridView;
			}
//		}
		
		return view;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
}
