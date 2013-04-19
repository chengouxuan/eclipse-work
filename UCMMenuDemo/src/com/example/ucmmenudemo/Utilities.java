package com.example.ucmmenudemo;

import android.content.res.Resources;

public class Utilities {

	public Utilities() {
		// TODO Auto-generated constructor stub
	}


	public static int getRoundedDimension(Resources resources, int resourceId) {
		float dimen = resources.getDimension(resourceId);
		return (int)(0.5f + dimen);
	}
}
