package com.example.ucmhomedemo21;

import android.widget.ScrollView;

public class RunnableExecuteScroll implements Runnable {

	private ScrollView scrollView;
	private int x;
	private int y;
	
	public RunnableExecuteScroll(ScrollView scrollView, int x, int y) {
		this.scrollView = scrollView;
		this.x = x;
		this.y = y;
	}

	@Override
	public void run() {
		this.scrollView.scrollTo(x, y);
	}

}
