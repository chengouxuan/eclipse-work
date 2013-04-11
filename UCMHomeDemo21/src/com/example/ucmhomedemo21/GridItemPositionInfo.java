package com.example.ucmhomedemo21;

public interface GridItemPositionInfo {

	abstract boolean isRightMostItem(int index);
	abstract boolean isLeftMostItem(int index);
	abstract boolean isTopMostItem(int index);
	abstract boolean isBottomItem(int index);
}
