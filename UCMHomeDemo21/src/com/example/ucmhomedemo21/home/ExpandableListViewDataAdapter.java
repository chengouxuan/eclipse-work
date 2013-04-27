package com.example.ucmhomedemo21.home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.ucmhomedemo21.GlobalViewIds;
import com.example.ucmhomedemo21.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


public class ExpandableListViewDataAdapter implements ExpandableListAdapter {

	boolean useWebView = false;
//	boolean useWebView = true;

	private Context context;
	private ExpandableListViewController expandableListViewController;
	private ExpandableListView expandableListView;
	
	public ExpandableListViewDataAdapter(Context context) {
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

		View view = null;
		
		if (useWebView && 0 <= groupPosition && groupPosition < DataSource.fileName.length) {
			
			WebView webView = new WebView(this.getContext());
			webView.getSettings().setDefaultTextEncodingName("utf-8");
			InputStream stream = null;
			String data = "";
			try {
				
				stream = this.getContext().getAssets().open(DataSource.fileName[groupPosition]);
				BufferedReader r = new BufferedReader(new InputStreamReader(stream));
				
				String x = null;
				while((x = r.readLine()) != null){
					data += x;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				Log.i("xxx", "fail to load data");
			}

			webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null); 
			
			view = webView; 
			
		} else {

			TextView textView = new TextView(this.getContext());
			textView.setText("child view of an expandable list view");
			textView.setTextColor(this.getContext().getResources().getColor(R.color.common_gray_color));
			textView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			
			view = textView;
		}
		
		float minHeight = this.getContext().getResources().getDimension(R.dimen.navigation_child_item_height);
		view.setMinimumHeight((int)(0.5f + minHeight));
		
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

		final int iconId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_ICON);
		final int textViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_TEXT);
		final int detailTextViewId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_DETAIL_TEXT);
		final int buttonId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.NAVIGATION_ITEM_BUTTON);
		
		NavigationExpandableListViewItemLayout theView = new NavigationExpandableListViewItemLayout(this.getContext());
		
		TextView textView = (TextView)theView.findViewById(textViewId);
		textView.setText(DataSource.navigationStrings[groupPosition]);
		
		ImageView icon = (ImageView)theView.findViewById(iconId);
		Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.glob);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		icon.setImageDrawable(bitmapDrawable);
		
		TextView detailTextView = (TextView)theView.findViewById(detailTextViewId);
		detailTextView.setText(DataSource.navigationDetailStrings[groupPosition]);
		
		theView.findViewById(buttonId).setTag(String.format("%d", groupPosition));
		
		float height = this.getContext().getResources().getDimension(R.dimen.navigation_item_height);
		theView.setMinimumHeight((int)(0.5f + height));
		
		theView.setExpandlistViewController(this.expandableListViewController);
		
		view = theView;
		
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
		
		for (int i = 0; i < this.getGroupCount(); ++i) {
			if (i != groupPosition) {
				this.getExpandableListView().collapseGroup(i);
			}
		}
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

	public ExpandableListViewController getExpandableListViewController() {
		return expandableListViewController;
	}

	public void setExpandableListViewController(
			ExpandableListViewController expandableListViewController) {
		this.expandableListViewController = expandableListViewController;
	}

}
