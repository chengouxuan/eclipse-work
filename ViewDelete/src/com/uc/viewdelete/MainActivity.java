package com.uc.viewdelete;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class MainActivity extends Activity {
	
	private View mView;
	
	private DisappearView mDisappearView;
	
	private boolean mEnable = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		mDisappearView = (DisappearView) findViewById(R.id.disappear_view);
		mView = findViewById(R.id.test_view);
		final View view = findViewById(R.id.trashcan);
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!mEnable)
					return;
				
				final int[] loc = new int[2];
				v.getLocationInWindow(loc);
				mDisappearView.startDisappear(mView, loc[0]+v.getWidth()/2, loc[1]+v.getHeight()/2);
				mView.setVisibility(View.INVISIBLE);
				mEnable = false;
			}
		});
		
		findViewById(R.id.revert).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mEnable = true;
				mDisappearView.cancelDisappearAnimation();
				mView.setVisibility(View.VISIBLE);
			}
		});
		
	}
	
}