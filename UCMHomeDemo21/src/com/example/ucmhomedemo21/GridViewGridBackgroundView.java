package com.example.ucmhomedemo21;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class GridViewGridBackgroundView extends View {

	private int columns;
	private int rows;
	
	private Paint paint;
	private int lineMargin;
	
	private GridViewInfo gridViewInfo;
	
	private void setupPaint() {
		
		 this.paint = new Paint();
		 this.paint.setStrokeWidth(1);
		 this.paint.setARGB(127, 127, 127, 127);
		 
		 DashPathEffect effect = new DashPathEffect(new float[]{5, 5}, 0);
		 
		 this.paint.setPathEffect(effect);
	}
	
	private void setupInstanceVariables() {
		this.setRows(0);
		this.setColumns(0);
		this.setupPaint();
		this.lineMargin = 15;
		this.setGridViewInfo(null);
	}
	
	public GridViewGridBackgroundView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setupInstanceVariables();
	}

	public GridViewGridBackgroundView(Context context, AttributeSet attr) {
		super(context, attr);
		this.setupInstanceVariables();
	}
	
	public GridViewGridBackgroundView(Context context) {
		super(context);
		this.setupInstanceVariables();
	}

	 @Override
	 protected void onDraw(Canvas canvas) {
	
		 super.onDraw(canvas);
	
		 double cellWidth = 0;
		 if (gridViewInfo == null && this.columns != 0) {
			 cellWidth = 1.0 * this.getMeasuredWidth() / this.columns;
		 } else if (gridViewInfo != null){
			 cellWidth = this.gridViewInfo.getCellWidth(0, 0);
		 }
		 
		 int lineMargin = this.lineMargin;
		 
		 for (int i = 1; i < this.getColumns(); ++i) {
			 int x = (int)(i * cellWidth + 0.5);
			 canvas.drawLine(x, 0 + lineMargin, x, this.getHeight() - lineMargin, this.paint);
		 }
		 
		 double cellHeight = 0;
		 if (gridViewInfo == null && this.rows != 0) {
			 cellHeight = 1.0 * this.getMeasuredHeight() / this.rows;
		 } else if (gridViewInfo != null) {
			 cellHeight = this.gridViewInfo.getCellHeight(0, 0);
		 }
		 
		 for (int i = 1; i < this.getRows(); ++i) {
			 int y = (int)(i * cellHeight + 0.5);
			 canvas.drawLine(0 + lineMargin, y, this.getWidth() - lineMargin, y, this.paint);
		 }
	 }

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public GridViewInfo getGridViewInfo() {
		return gridViewInfo;
	}

	public void setGridViewInfo(GridViewInfo gridViewInfo) {
		this.gridViewInfo = gridViewInfo;
	}
}
