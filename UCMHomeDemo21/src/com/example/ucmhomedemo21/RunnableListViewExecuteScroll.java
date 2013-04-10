package com.example.ucmhomedemo21;

import android.widget.ListView;

public class RunnableListViewExecuteScroll implements Runnable {


	private ListView listView;
	private int x;
	private int y;
	
	public RunnableListViewExecuteScroll(ListView scrollView, int x, int y) {
		this.listView = scrollView;
		this.x = x;
		this.y = y;
	}

	@Override
	public void run() {
		this.listView.scrollTo(x, y);
	}

}
