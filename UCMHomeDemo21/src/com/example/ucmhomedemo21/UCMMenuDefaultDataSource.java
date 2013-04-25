package com.example.ucmhomedemo21;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class UCMMenuDefaultDataSource implements UCMMenuDataSource {
	
	private Context mContext;
	
	private String mItemTitles[] = {
			"�����ǩ",	"��ǩ/��ʷ",	"ˢ��",		"ҹ��ģʽ",	"�˻�",		"����/Ӧ��",	"ȫ��",		"�˳�",
			"ϵͳ����",	"Ƥ������",	"��Ӧ��Ļ",	"��ҳģʽ",	"��ͼ",		"ת��",		"�޺����",	"���ȵ���",
			"������ҳ",	"ҳ������",	"������",	"��ͼͿѻ",	"�������",	"ʡ����ѯ",	"�ٱ���ַ",	"����˵��"
	};
	
	private String mPageTitles[] = {
			"����",	"����",	"����"
	};
	
	class Position {
		public int resourceId;
		public int row;
		public int column;
		public Position(int ri, int r, int c) { resourceId = ri; row = r; column = c; }
	};
	
	private Position mIconPositions[] = {

			new Position(R.drawable.panel_common, 2, 0),
			new Position(R.drawable.panel_common, 0, 0),
			new Position(R.drawable.panel_common, 1, 1),
			new Position(R.drawable.panel_setting, 0, 0),
			new Position(R.drawable.panel_common, 2, 1),
			new Position(R.drawable.panel_common, 1, 2),
			new Position(R.drawable.panel_setting, 1, 1),
			new Position(R.drawable.panel_common, 2, 2),
			
			new Position(R.drawable.panel_setting, 0, 1),
			new Position(R.drawable.panel_setting, 0, 2),
			new Position(R.drawable.panel_setting, 4, 0),
			new Position(R.drawable.panel_toolbar, 3, 1),
			new Position(R.drawable.panel_setting, 3, 1),
			new Position(R.drawable.panel_common, 1, 2),
			new Position(R.drawable.panel_setting, 1, 1),
			new Position(R.drawable.panel_common, 2, 2),
			
			new Position(R.drawable.panel_common, 2, 0),
			new Position(R.drawable.panel_common, 0, 0),
			new Position(R.drawable.panel_common, 1, 1),
			new Position(R.drawable.panel_setting, 0, 0),
			new Position(R.drawable.panel_common, 2, 1),
			new Position(R.drawable.panel_common, 1, 2),
			new Position(R.drawable.panel_setting, 1, 1),
			new Position(R.drawable.panel_common, 2, 2),
	};
	
	private int mItemsPerPage = 8;
	private int mPages = 3;
	private int mColumns = 4;
	
	public UCMMenuDefaultDataSource(Context context) {
		mContext = context;
	}
	
	private int getItemIndex(int pagePosition, int itemPosition) {
		return mItemsPerPage * pagePosition + itemPosition;
	}
	
	private int getTotalRows(int resourceId) {
		if (resourceId == R.drawable.panel_common) {
			return 3;
		} else if (resourceId == R.drawable.panel_setting) {
			return 6;
		} else if (resourceId == R.drawable.panel_toolbar) {
			return 4;
		} else {
			return 0;
		}
	}
	
	private int getTotalColumns(int resourceId) {
		return 3;
	}
	
	@Override
	public String getPageTitle(int pagePosition) {
		return mPageTitles[pagePosition];
	}
	
	@Override
	public Drawable getItemIcon(int pagePosition, int itemPosition) {
		Position position = mIconPositions[this.getItemIndex(pagePosition, itemPosition)];
		return Utilities.clipIcon(
				mContext.getResources(), position.resourceId,
				position.row, this.getTotalRows(position.resourceId),
				position.column, this.getTotalColumns(position.resourceId)
				);
	}
	
	@Override
	public String getItemTitle(int pagePosition, int itemPosition) {
		return mItemTitles[this.getItemIndex(pagePosition, itemPosition)];
	}

	@Override
	public int getPageCount() {
		return mPages;
	}
	
	@Override
	public int getItemCount(int pagePosition) {
		return mItemsPerPage;
	}
	
	@Override
	public int getColumnCount(int pagePosition) {
		return mColumns;
	}
}
