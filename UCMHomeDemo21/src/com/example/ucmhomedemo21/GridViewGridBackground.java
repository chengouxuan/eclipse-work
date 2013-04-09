package com.example.ucmhomedemo21;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class GridViewGridBackground extends View {

	private int columns;
	private int rows;

	public GridViewGridBackground(Context context, AttributeSet attr) {
		super(context, attr);
		this.setRows(0);
		this.setColumns(0);
	}
	
	public GridViewGridBackground(Context context) {
		super(context);
		this.setRows(0);
		this.setColumns(0);
	}

	 @Override
	 protected void onDraw(Canvas canvas) {
	
	 super.onDraw(canvas);
	
	 String msg = String.format(
	
	 "measured (width, height) = (%d, %d)\n" +
	 "(width, height) = (%d, %d) = %d\n",
	
	 this.getMeasuredWidth(), this.getMeasuredHeight(),
	 this.getHeight(), this.getWidth()
	
	 );
	
	 Log.i("xxx", msg);
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
}
