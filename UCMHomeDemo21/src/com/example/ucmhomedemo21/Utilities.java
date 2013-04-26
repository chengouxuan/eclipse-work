package com.example.ucmhomedemo21;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Utilities extends Object {

	public Utilities() {}


	public static int getRoundedDimension(Resources resources, int resourceId) {
		float dimen = resources.getDimension(resourceId);
		return (int)(0.5f + dimen);
	}
	
	public static Drawable clipIcon(Resources resource, int resourceId, int row, int totalRows, int column, int totalColumns) {
		
		Bitmap bitmap = BitmapFactory.decodeResource(resource, resourceId);
		
		float width = 1f * bitmap.getWidth() / totalColumns;
		int x = (int)(0.5f + column * width);
		
		float height = 1f * bitmap.getHeight() / totalRows;
		int y = (int)(0.5f + row * height);
		
		bitmap = Bitmap.createBitmap(bitmap, x, y, (int)(0.5f + width), (int)(0.5f + height));
		
		Drawable drawable = new BitmapDrawable(resource, bitmap);
		return drawable;
	}
}
