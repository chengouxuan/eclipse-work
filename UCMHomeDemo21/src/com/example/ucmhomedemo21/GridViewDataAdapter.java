package com.example.ucmhomedemo21;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewDataAdapter

extends BaseAdapter

implements GridViewInfo, GridItemPositionInfo {
	

	private View commonItemView;
	private GridView gridView;
	
	private Context context;
	private int numColumns;
	
	public GridViewDataAdapter(Context context) {
		this.setContext(context);
		this.setNumColumns(0);
		this.commonItemView = null;
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
		
//		if (convertView == null) {

			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			View xmlView = inflater.inflate(R.layout.hot_sites_view_item, null);
		
			
			TextView textView = (TextView) xmlView.findViewById(R.id.siteDescriptionString);
			textView.setText(DataSource.hotSitesStrings[position]);

			ImageView icon = (ImageView)xmlView.findViewById(R.id.siteIcon);
			Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sina);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
			icon.setImageDrawable(bitmapDrawable);
			
			GridItemBackground background = (GridItemBackground) xmlView.findViewById(R.id.background);
			background.setIndex(position);
			background.setPositionInfo(this);
			
			view = xmlView;
//		}
			
		this.commonItemView = view;
		
		int gridViewContentWidth = this.gridView.getMeasuredWidth() - this.gridView.getPaddingLeft() - this.gridView.getPaddingRight();
		int gridViewContentHeight = this.gridView.getMeasuredHeight() - this.gridView.getPaddingTop() - this.gridView.getPaddingBottom();
		
		int minHeight = (int)(gridViewContentHeight / this.getNumRows() + 0.5);
		int minWidth = (int)(gridViewContentWidth / this.getNumColumns() + 0.5);
//		
		view.setMinimumHeight(minHeight);
		view.setMinimumWidth(minWidth);
//		
//		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(minWidth, MeasureSpec.AT_MOST);
//		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(minHeight, MeasureSpec.AT_MOST);
//		
//		view.measure(widthMeasureSpec, heightMeasureSpec);
		
//		view.requestLayout();
		
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
	
	
	@Override
	public int getCellHeight(int row, int column) {
		
		View view = this.commonItemView;
		
		if (view != null) {
			return view.getMeasuredHeight();
		} else {
			return 0;
		}
	}

	@Override
	public int getCellWidth(int row, int column) {
		View view = this.commonItemView;
		
		if (view != null) {
			return view.getMeasuredWidth();
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
	public int getPaddingTop() {
		if (null != this.gridView) {
			return gridView.getPaddingTop();
		} else {
			return 0;
		}
	}

	@Override
	public int getPaddingRight() {
		if (null != this.gridView) {
			return gridView.getPaddingRight();
		} else {
			return 0;
		}
	}

	@Override
	public int getPaddingBottom() {
		if (null != this.gridView) {
			return gridView.getPaddingBottom();
		} else {
			return 0;
		}
	}

	@Override
	public int getPaddingLeft() {
		if (null != this.gridView) {
			return gridView.getPaddingLeft();
		} else {
			return 0;
		}
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

}
