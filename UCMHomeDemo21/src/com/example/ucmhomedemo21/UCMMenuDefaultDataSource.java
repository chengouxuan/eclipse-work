package com.example.ucmhomedemo21;

public class UCMMenuDefaultDataSource implements UCMMenuDataSource {
	
	public UCMMenuDefaultDataSource() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getPageTitle(int pagePosition) {
		return String.format("Page %d", pagePosition);
	}

	@Override
	public int getItemIconResourceId(int pagePosition, int itemPosition) {
		return R.drawable.address_input_list_safe;
	}

	@Override
	public String getItemTitle(int pagePosition, int itemPosition) {
		return String.format("Item %d", itemPosition);
	}

	@Override
	public int getPageCount() {
		return 3;
	}

	@Override
	public int getItemCount(int pagePosition) {
		return 8;
	}

	@Override
	public int getColumnCount(int pagePosition) {
		return 4;
	}
}
