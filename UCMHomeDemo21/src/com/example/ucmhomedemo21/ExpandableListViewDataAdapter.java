package com.example.ucmhomedemo21;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


public class ExpandableListViewDataAdapter

implements ExpandableListAdapter, ListViewInfo {

	private Context context;
	private ExpandableListView expandableListView;
	
	private View commonGroupView;
	private View commonChildView;
	
	ExpandableListViewDataAdapter(Context context) {
		this.setContext(context);
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.navigation_expandable_list_view_common_child_view_layout, null);
		this.commonChildView = view;
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public long getCombinedChildId(long groupId, long childId) {
		return DataSource.navigationStrings.length + childId;
	}

	@Override
	public long getCombinedGroupId(long groupId) {
		return groupId;
	}

	@Override
	public Object getGroup(int arg0) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return DataSource.navigationStrings.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		View view = convertView;
		
		
//		if (convertView == null) {
//			if (position != 0) {
				
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				
				View xmlView = inflater.inflate(R.layout.navigation_list_view_item, null);
				
				TextView textView = (TextView)xmlView.findViewById(R.id.navigationString);
				textView.setText(DataSource.navigationStrings[groupPosition]);
				
				ImageView icon = (ImageView)xmlView.findViewById(R.id.navigationIcon);
				Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.glob);
				BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
				icon.setImageDrawable(bitmapDrawable);
				
				TextView detailTextView = (TextView)xmlView.findViewById(R.id.navigationDetailString);
				detailTextView.setText(DataSource.navigationDetailStrings[groupPosition]);
				
				xmlView.findViewById(R.id.button).setTag(String.format("%d", groupPosition));
				
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
		
		this.commonGroupView = view;
		
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void onGroupCollapsed(int arg0) {
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		
//		for (int i = 0; i < this.getGroupCount(); ++i) {
//			if (i != groupPosition) {
//				this.getExpandableListView().collapseGroup(i);
//			}
//		}
	}

	@Override
	public void registerDataSetObserver(DataSetObserver arg0) {
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver arg0) {
	}

	protected void setContext(Context context) {
		this.context = context;
	}
	
	protected Context getContext() {
		return this.context;
	}

	public ExpandableListView getExpandableListView() {
		return expandableListView;
	}

	public void setExpandableListView(ExpandableListView expandableListView) {
		this.expandableListView = expandableListView;
	}

	@Override
	public int getListViewHeight() {
		
		if (this.commonGroupView != null && this.commonGroupView != null) {
			int childMeasuredHeight = this.commonChildView.getMeasuredHeight();
			return commonGroupView.getMeasuredHeight() * this.getGroupCount() + childMeasuredHeight;
		} else {
			return 0;
		}
	}
}
