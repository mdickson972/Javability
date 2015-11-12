package com.mdickson972.androidActivities;

import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.programmingjava.R;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressWarnings({ "deprecation" })
public class MainMenuScreen extends ActionBarActivity {
	
	private static final String DB_NAME = "Database.sqlite3";
	private static final String BEST_SCORE_QUERY_PART1 = "Select BestScore from Results where UserId = ";
	private static final String BEST_SCORE_QUERY_PART2 = " and Topic = ";
	
	private String username;
	private int userId;
	private SQLiteDatabase database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_screen);
		
		// Retrieve extras
		Bundle extras = getIntent().getExtras();
		username = extras.getString("Username");
		userId = extras.getInt("UserId");
		
		// Set Title
		setTitle("Logged in as : "+username);
		
		// Create buttons
		Button java101 = (Button) findViewById(R.id.btn_java_101);
		Button selectionStatements = (Button) findViewById(R.id.btn_selection_statements);
		Button loops = (Button) findViewById(R.id.btn_loops);
		Button methods = (Button) findViewById(R.id.btn_methods);
		Button arrays = (Button) findViewById(R.id.btn_arrays);
		Button arrays2d = (Button) findViewById(R.id.btn_2d_arrays);
		Button constructors = (Button) findViewById(R.id.btn_constructors);
		Button inheritance = (Button) findViewById(R.id.btn_inheritance);
		
		// Disable button clicks
		java101.setSoundEffectsEnabled(false);
		selectionStatements.setSoundEffectsEnabled(false);
		loops.setSoundEffectsEnabled(false);
		methods.setSoundEffectsEnabled(false);
		arrays.setSoundEffectsEnabled(false);
		arrays2d.setSoundEffectsEnabled(false);
		constructors.setSoundEffectsEnabled(false);
		inheritance.setSoundEffectsEnabled(false);
		
		// Set onClick Listeners
		java101.setOnClickListener(new btnListener());
		selectionStatements.setOnClickListener(new btnListener());
		loops.setOnClickListener(new btnListener());
		methods.setOnClickListener(new btnListener());
		arrays.setOnClickListener(new btnListener());
		arrays2d.setOnClickListener(new btnListener());
		constructors.setOnClickListener(new btnListener());
		inheritance.setOnClickListener(new btnListener());		
		
		DatabaseHelper dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.openDataBase();
		
	}
	
	/**
	 * Launches the Options menu when the intent is created.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
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
		case R.id.menu_sign_out:
			this.finish();
			Toast.makeText(getApplicationContext(), "Signed Out as "+username, Toast.LENGTH_SHORT).show();			
			break;
			
		case R.id.view_user_data:
			Intent userDetails = new Intent(getApplicationContext(),
					UserDetailsScreen.class);
			userDetails.putExtra("UserId", userId);
			startActivity(userDetails);
			
			break;			
		
		case R.id.view_progress:
			// Code for opening the view progress screen			
			Intent overallProgress = new Intent(getApplicationContext(),
					OverallProgressScreen.class);
			overallProgress.putExtra("UserId", userId);
			startActivity(overallProgress);
			
			break;
			
		}
		
		return super.onOptionsItemSelected(item);
	
	}
	
	/**
	 * Checks to see if the user has gotten at least 70% in the previous topic's
	 * assessment. If they have it returns true, if not, false.
	 * @param topic
	 * @return
	 */
	public boolean checkUnlocked(String topic) {
		
		Cursor csr = database.rawQuery(BEST_SCORE_QUERY_PART1+userId+BEST_SCORE_QUERY_PART2+topic, null);
		
		// Moves the cursor to the beginning of the results.
		csr.moveToFirst();
		
		// Checks to see if there is a value held in the cursor
		// Then checks to see if that value is 70 or higher
		if ((csr.getCount()>0) && (csr.getInt(0)>=70)) {
			return true;
		} else {
			return false;
		}
		
		
		
	}
	
	/**
	 * Sets up a switch to read selected buttons and open
	 * appropriate menus for what is selected
	 * Each case checks the best score held for the previous modules assessment.
	 * If the previous modules best is 70% or higher then the lesson can be assessed.
	 * If not then a toast informs the user that lesson has not yet been unlocked.
	 * @author mdickson972
	 *
	 */
	private class btnListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			
			Button button = (Button) view;
			
			// Switch statement to choose operation based on button selected
			switch (button.getId()) {
			
			case R.id.btn_java_101:
				// Method for Java 101 topic menu
				Intent java101 = new Intent(view.getContext(),
						Java101MenuScreen.class);
				java101.putExtra("UserId", userId);
				startActivity(java101);
				break;
				
			case R.id.btn_selection_statements:
				// Method for Selection Statements topic menu
				if (checkUnlocked("'Java101'")==true) {
				Intent selection = new Intent(view.getContext(),
						ComingSoonScreen.class);
				selection.putExtra("UserId", userId);
				startActivity(selection);
				} else {
					Toast.makeText(getApplicationContext(), "Module not yet Unlocked", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btn_loops:
				// Method for Loops topic menu
				if (checkUnlocked("'SelectionStatements'")==true) {
				Intent loops = new Intent(view.getContext(),
						LoopsMenuScreen.class);
				loops.putExtra("UserId", userId);
				startActivity(loops);
				} else {
					Toast.makeText(getApplicationContext(), "Module not yet Unlocked", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btn_methods:
				// Method for Methods topic menu
				if (checkUnlocked("'Loops'")==true) {
				Intent methods = new Intent(view.getContext(),
						ComingSoonScreen.class);
				methods.putExtra("UserId", userId);
				startActivity(methods);
				} else {
					Toast.makeText(getApplicationContext(), "Module not yet Unlocked", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btn_arrays:
				// Method for Arrays topic menu
				if (checkUnlocked("'Methods'")==true) {
				Intent arrays = new Intent(view.getContext(),
						ComingSoonScreen.class);
				arrays.putExtra("UserId", userId);
				startActivity(arrays);
				} else {
					Toast.makeText(getApplicationContext(), "Module not yet Unlocked", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btn_2d_arrays:
				// Method for 2D Arrays topic menu
				if (checkUnlocked("'Arrays'")==true) {
				Intent arrays2d = new Intent(view.getContext(),
						ComingSoonScreen.class);
				arrays2d.putExtra("UserId", userId);
				startActivity(arrays2d);
				} else {
					Toast.makeText(getApplicationContext(), "Module not yet Unlocked", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btn_constructors:
				// Method for Constructors topic menu
				if (checkUnlocked("'2DArrays'")==true) {
				Intent constructors = new Intent(view.getContext(),
						ComingSoonScreen.class);
				constructors.putExtra("UserId", userId);
				startActivity(constructors);
				} else {
					Toast.makeText(getApplicationContext(), "Module not yet Unlocked", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case R.id.btn_inheritance:
				// Method for Inheritance topic menu
				if (checkUnlocked("'Constructors'")==true) {
				Intent inheritance = new Intent(view.getContext(),
						ComingSoonScreen.class);
				inheritance.putExtra("UserId", userId);
				startActivity(inheritance);
				} else {
					Toast.makeText(getApplicationContext(), "Module not yet Unlocked", Toast.LENGTH_SHORT).show();
				}
				break;
			
				
			}	
			
		}
		
	}


	
	
	
}
