package com.example.ucmhomedemo21;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewDataAdapter

extends BaseAdapter

implements GridItemPositionInfo {
	
	private GridView gridView;
	
	private Context context;
	private int numColumns;
	private int itemMinimalHeight;

	private ExpandableListViewController expandableListViewController;
	
	public GridViewDataAdapter(Context context) {
		this.setContext(context);
		this.setNumColumns(0);
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
		
		int columns = this.getNumColumns();
		
		if (columns == 0) {
			return DataSource.hotSitesStrings.length;
		}
		
		return DataSource.hotSitesStrings.length / columns * columns;
	}
	
	@Override
	public Object getItem(int position) {
		return null;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;

		final int descriptionTextId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_ITEM_DESCRIIPTION_TEXT_ID);
		final int backgroundId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_ITEM_BACKGROUND);
		final int iconId = GlobalViewIds.getIdOf(GlobalViewIds.Ids.HOT_SITES_ITEM_ICON);
		
		View theView = new HotSitesItemLayout(this.getContext());

		TextView textView = (TextView) theView.findViewById(descriptionTextId);
		textView.setText(DataSource.hotSitesStrings[position]);

		ImageView icon = (ImageView)theView.findViewById(iconId);
		Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sina);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		icon.setImageDrawable(bitmapDrawable);

		GridItemBackground background = (GridItemBackground) theView.findViewById(backgroundId);
		background.setIndex(position);
		background.setPositionInfo(this);
		
		view = theView;
		
		int gridViewContentWidth = this.gridView.getMeasuredWidth() - this.gridView.getPaddingLeft() - this.gridView.getPaddingRight();
		int gridViewContentHeight = this.gridView.getMeasuredHeight() - this.gridView.getPaddingTop() - this.gridView.getPaddingBottom();
		
		if (gridViewContentWidth < 0) {
			gridViewContentWidth = 0;
		}
		
		if (gridViewContentHeight < 0) {
			gridViewContentHeight = 0;
		}
		
		int minHeight = (int)(1.0 * gridViewContentHeight / this.getNumRows() + 0.5);
		int minWidth = (int)(1.0 * gridViewContentWidth / this.getNumColumns() + 0.5);
		
		if (this.itemMinimalHeight > 0) {
			minHeight = this.itemMinimalHeight;
		}
		
		view.setMinimumHeight(minHeight);
		view.setMinimumWidth(minWidth);
		
		background.setMinimumHeight(minHeight);
		background.setMinimumWidth(minWidth);
		
		return view;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int columns) {
		this.numColumns = columns;
	}
	
	public int getNumRows() {
		
		int columns = this.getNumColumns();
		
		if (columns != 0) {
			return (this.getCount() + columns - 1) / columns;
		} else {
			return 0;
		}
	}

	public GridView getGridView() {
		return gridView;
	}

	public void setGridView(GridView gridView) {
		this.gridView = gridView;
	}

	@Override
	public boolean isRightMostItem(int index) {
		int columns = this.getNumColumns();
		return (index % columns) == (columns - 1);
	}

	@Override
	public boolean isLeftMostItem(int index) {
		int columns = this.getNumColumns();
		return (index % columns) == 0;
	}

	@Override
	public boolean isTopMostItem(int index) {
		int columns = this.getNumColumns();
		return index / columns == 0;
	}

	@Override
	public boolean isBottomItem(int index) {
		int columns = this.getNumColumns();
		int rows = this.getNumRows();
		return (index / columns) == (rows - 1);
	}

	public int getItemMinimumHeight() {
		return this.itemMinimalHeight;
	}

	public void setItemMinimumHeight(int itemMinimalHeight) {
		this.itemMinimalHeight = itemMinimalHeight;
	}

	public ExpandableListViewController getExpandableListViewController() {
		return expandableListViewController;
	}

	public void setExpandableListViewController(
			ExpandableListViewController expandableListViewController) {
		this.expandableListViewController = expandableListViewController;
	}

}
