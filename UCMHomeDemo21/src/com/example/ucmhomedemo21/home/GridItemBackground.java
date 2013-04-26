package com.example.ucmhomedemo21.home;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GridItemBackground extends View {

	private int index;
	
	private GridItemPositionInfo positionInfo;

	private Paint paint;
	
	private void setupPaint() {
		
		 this.paint = new Paint();
		 this.paint.setStrokeWidth(1);
		 this.paint.setARGB(127, 127, 127, 127);
		 
		 DashPathEffect effect = new DashPathEffect(new float[]{5, 5}, 0);
		 
		 this.paint.setPathEffect(effect);
	}
	
	public GridItemBackground(Context context) {
		super(context);
		this.setIndex(0);
		this.setupPaint();
	}

	public GridItemBackground(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setIndex(0);
		this.setupPaint();
	}

	public GridItemBackground(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setIndex(0);
		this.setupPaint();
	}

	public GridItemPositionInfo getPositionInfo() {
		return positionInfo;
	}

	public void setPositionInfo(GridItemPositionInfo positionInfo) {
		this.positionInfo = positionInfo;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	 @Override
	 protected void onDraw(Canvas canvas) {
		 
		 super.onDraw(canvas);
		 
		 if (this.positionInfo != null && !this.positionInfo.isLeftMostItem(this.index)) {
			 
			 canvas.drawLine(0, 0, 0, this.getHeight(), this.paint);
		 }
		 
		 if (this.positionInfo != null && !this.positionInfo.isTopMostItem(this.index)) {
			 
			 canvas.drawLine(0, 0, this.getWidth(), 0, this.paint);
		 }
	 }

}
