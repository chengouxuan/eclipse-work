package com.example.ucmhomedemo21;

public interface GridViewInfo {

	abstract int getCellHeight(int row, int column);
	abstract int getCellWidth(int row, int column);
	abstract int getPaddingTop();
	abstract int getPaddingRight();
	abstract int getPaddingBottom();
	abstract int getPaddingLeft();
	
}
