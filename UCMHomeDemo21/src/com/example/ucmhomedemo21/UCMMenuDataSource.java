package com.example.ucmhomedemo21;

public interface UCMMenuDataSource {

	public abstract String getPageTitle(int pagePosition);
	public abstract int getItemIconResourceId(int pagePosition, int itemPosition);
	public abstract String getItemTitle(int pagePosition, int itemPosition);
	public abstract int getPageCount();
	public abstract int getItemCount(int pagePosition);
	public abstract int getColumnCount(int pagePosition);
}
