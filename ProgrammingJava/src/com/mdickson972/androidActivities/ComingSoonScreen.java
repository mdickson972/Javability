package com.mdickson972.androidActivities;

import com.mdickson972.programmingjava.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

@SuppressWarnings("deprecation")
public class ComingSoonScreen extends ActionBarActivity {
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coming_soon_screen);
				
	}
	
	/**
	 * Launches the Options menu when the intent is created.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.back_menu, menu);
		return true;
	}

	/**
	 * Controls what happens when an item from the options menu
	 * is selected
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		switch (id) {
		
		// Code for sign out and closing the intent.
		// Brings user back out to the login screen.
		case R.id.back_button:
			this.finish();		
			break;			
		}
		
		return super.onOptionsItemSelected(item);
	
	}
	
	

}
