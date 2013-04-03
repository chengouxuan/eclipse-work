package com.example.ucmhomedemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.view.View;
import android.app.AlertDialog;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_start_screen);
		setContentView(R.layout.home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_screen, menu);
		return true;
	}
	
	public void anyButtonClicked(View senderView) {
		
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(senderView.toString());
        AlertDialog dialog = builder.create();
        dialog.show();
	}

}
