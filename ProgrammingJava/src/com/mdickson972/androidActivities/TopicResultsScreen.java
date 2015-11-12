package com.mdickson972.androidActivities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.ResultSet.TopicResultSet;
import com.mdickson972.programmingjava.R;

@SuppressWarnings("deprecation")
public class TopicResultsScreen extends ActionBarActivity {

	// Class Constants
	private static final String DB_NAME = "Database.sqlite3";
	private static final String RETRIEVEAL_QUERY_PART1 = "Select * from Results where UserId =";
	private static final String RETRIEVEAL_QUERY_PART2 = " and Topic =";
	
	// Class Variables
	private String topic;
	private int userId, queryCount;

	private Button homeBtn, switchBtn;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper; 
	private TopicResultSet topicResults;
	private TextView assessmentScore, overallTitle, overallLast, overall2nd,
			overall3rd, overall4th, overallBest, overallLastResult,
			overall2ndResult, overall3rdResult, overall4thResult,
			overallBestResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results_screen);
		
		// -----------------------Buttons-----------------------------------

		homeBtn = (Button) findViewById(R.id.results_home_btn);
		switchBtn = (Button) findViewById(R.id.results_switch_btn);

		homeBtn.setSoundEffectsEnabled(false);
		switchBtn.setSoundEffectsEnabled(false);

		homeBtn.setOnClickListener(new btnListener());
		switchBtn.setOnClickListener(new btnListener());

		// ---------------------Assign XML Items-----------------------------

		// Current Results Screen
		assessmentScore = (TextView) findViewById(R.id.results_assessment_result);

		// overall Results Screen
		overallTitle = (TextView) findViewById(R.id.previous_title);
		overallLast = (TextView) findViewById(R.id.previous_last);
		overall2nd = (TextView) findViewById(R.id.previous_2nd_last);
		overall3rd = (TextView) findViewById(R.id.previous_3rd_last);
		overall4th = (TextView) findViewById(R.id.previous_4th_last);
		overallLastResult = (TextView) findViewById(R.id.previous_last_result);
		overall2ndResult = (TextView) findViewById(R.id.previous_2nd_last_result);
		overall3rdResult = (TextView) findViewById(R.id.previous_3rd_last_result);
		overall4thResult = (TextView) findViewById(R.id.previous_4th_last_result);
		overallBest = (TextView) findViewById(R.id.previous_best);
		overallBestResult = (TextView) findViewById(R.id.previous_best_result);

		// --------------------Bundle and Topic------------------------------

		// Retrieves the string value that was passed in with the intent
		Bundle extras = getIntent().getExtras();
		
		// Assigns the passed string to the topic variable
		topic = extras.getString("topic");
		userId = extras.getInt("UserId");
		
		// Set activity title
		setTitle("Topic : "+topic);
		
		// ---------------------Database Connection--------------------------
		dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.getWritableDatabase();
				
		// --------------------Methods Called--------------------------------

		setLayout();
		getResults();
		assignValues();
		
	}
	
	/**
	 * Connects to database to retrieve result data recorded for this user on this
	 * assessment. Creates a new result set object (oldResults) to hold the data while in use.
	 * If no data is held currently then a new TopicResultSet object is created with 0 values held
	 * for previous attempts.
	 */
	public void getResults() {
		
		Cursor csr = database.rawQuery(RETRIEVEAL_QUERY_PART1 + userId + RETRIEVEAL_QUERY_PART2 + topic+";", null);
		
		queryCount = csr.getCount();
		
		Log.i("Query Results Count", ""+queryCount);
		
		if ((queryCount!=0)&&(csr.moveToFirst())) {
			topicResults = new TopicResultSet(csr.getInt(0), csr.getString(1), csr.getInt(2), 
					csr.getInt(3), csr.getInt(4), csr.getInt(5), csr.getInt(6));
			Log.i("Result Status", "Record Retrieved");
		} else {
			topicResults = new TopicResultSet(userId, topic, 0, 0, 0, 0, 0);
			Log.i("Result Status", "No Record Available. New Object Created");
		}
		
		csr.close();
		topicResults.displayResultSet();
	}
	
	/**
	 * Creates a string array and casts the results from the result set into
	 * it. Then assigns each element of the string array to its appropriate 
	 * text view on the activity.
	 */
	public void assignValues() {
		
		String[] results = {Integer.toString(topicResults.getPreviousLast()),
							Integer.toString(topicResults.getPrevious2nd()),
							Integer.toString(topicResults.getPrevious3rd()),
							Integer.toString(topicResults.getPrevious4th()),
							Integer.toString(topicResults.getBestResult())};
		
		overallLastResult.setText(results[0]);
		overall2ndResult.setText(results[1]);
		overall3rdResult.setText(results[2]);
		overall4thResult.setText(results[3]);
		overallBestResult.setText(results[4]);
				
	}
	
	/**
	 * Sets visibility setting of xml items to their appropriate setting.
	 */
	public void setLayout() {
		
		// Sets all current results items to invisible
		assessmentScore.setVisibility(View.INVISIBLE);
		switchBtn.setVisibility(View.INVISIBLE);
		
		// Sets title to previous score title
		overallTitle.setText(R.string.previous_title);
		

		// Sets all previous results items to invisible
		overallTitle.setVisibility(View.VISIBLE);
		overallLast.setVisibility(View.VISIBLE);
		overall2nd.setVisibility(View.VISIBLE);
		overall3rd.setVisibility(View.VISIBLE);
		overall4th.setVisibility(View.VISIBLE);
		overallLastResult.setVisibility(View.VISIBLE);
		overall2ndResult.setVisibility(View.VISIBLE);
		overall3rdResult.setVisibility(View.VISIBLE);
		overall4thResult.setVisibility(View.VISIBLE);
		overallBest.setVisibility(View.VISIBLE);
		overallBestResult.setVisibility(View.VISIBLE);
		
		// Calls method to assign previous values to textviews
		

	}

	/**
	 * Sets up a switch to read selected buttons and move between
	 * layouts, along with providing a way back to the menu screen. 
	 * @author mdickson972
	 * 
	 */
	private class btnListener implements OnClickListener {

		@Override
		public void onClick(View view) {

			// Creates a button object to switch on
			Button button = (Button) view;

			// Switch statement to choose which button is
			// being selected and which code to execute
			switch (button.getId()) {

			case R.id.results_home_btn:
				database.close();
				finish();
				break;

			
			}
		}
	}
}
