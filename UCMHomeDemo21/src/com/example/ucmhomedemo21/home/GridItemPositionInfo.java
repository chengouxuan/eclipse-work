package com.example.ucmhomedemo21.home;

public interface GridItemPositionInfo {

	public abstract boolean isRightMostItem(int index);
	public abstract boolean isLeftMostItem(int index);
	public abstract boolean isTopMostItem(int index);
	public abstract boolean isBottomItem(int index);
}
