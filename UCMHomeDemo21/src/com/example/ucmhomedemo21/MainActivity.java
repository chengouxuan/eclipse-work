package com.example.ucmhomedemo21;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.AlertDialog;


public class MainActivity extends Activity {

	private static String strings[] = {"1", "2", "3", "abcdef ghijklmn", "5", "6"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_start_screen);
		setContentView(R.layout.home);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.navigation_list_view_item, strings);
		ListView listView = (ListView)findViewById(R.id.listView1);
		listView.setAdapter(adapter);
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