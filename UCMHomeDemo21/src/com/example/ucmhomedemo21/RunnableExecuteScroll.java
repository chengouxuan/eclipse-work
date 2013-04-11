package com.example.ucmhomedemo21;

import android.view.View;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.ScrollView;

public class RunnableExecuteScroll implements Runnable {

	private ScrollView scrollView;
	private ListView listView;
	private int childViewPosition;
	
	public RunnableExecuteScroll(ScrollView scrollView, ListView listView, int childViewPosition) {
		this.scrollView = scrollView;
		this.listView = listView;
		this.childViewPosition = childViewPosition;
		
	}

	@Override
	public void run() {

		View aChildView = this.listView.getChildAt((int) this.childViewPosition);
		int offset = RunnableExecuteScroll.computeTopOnRoot((View) aChildView)
				- RunnableExecuteScroll.computeTopOnRoot(this.scrollView) - this.scrollView.getPaddingTop();

		this.scrollView.scrollTo(0, offset);
	}

	public static int computeTopOnRoot(View view) {
		
		int top = view.getTop();
		ViewParent viewParent = view.getParent();
		
		while (true) {

			if (viewParent instanceof View) {
				view = (View) viewParent;
				top += view.getTop();
			}

			if (viewParent == null) {
				break;
			}
			
			viewParent = viewParent.getParent();

			if (viewParent == null) {
				break;
			}
			
		}
		
		return top;
	}
}
