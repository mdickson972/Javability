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
public class AssessmentResultsScreen extends ActionBarActivity {

	// Class Constants
	private static final String DB_NAME = "Database.sqlite3";
	private static final String RETRIEVEAL_QUERY_PART1 = "Select * from Results where UserId =";
	private static final String RETRIEVEAL_QUERY_PART2 = " and Topic =";
	
	// Class Variables
	private String topic;
	private int userId, currentResult, queryCount;

	private Button homeBtn, switchBtn;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper; 
	private TopicResultSet oldResults, updatedResults;
	private Boolean switchOn = false;
	private TextView assessmentScore, previousTitle, previousLast, previous2nd,
			previous3rd, previous4th, previousBest, previousLastResult,
			previous2ndResult, previous3rdResult, previous4thResult,
			previousBestResult;
	
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

		// Previous Results Screen
		previousTitle = (TextView) findViewById(R.id.previous_title);
		previousLast = (TextView) findViewById(R.id.previous_last);
		previous2nd = (TextView) findViewById(R.id.previous_2nd_last);
		previous3rd = (TextView) findViewById(R.id.previous_3rd_last);
		previous4th = (TextView) findViewById(R.id.previous_4th_last);
		previousLastResult = (TextView) findViewById(R.id.previous_last_result);
		previous2ndResult = (TextView) findViewById(R.id.previous_2nd_last_result);
		previous3rdResult = (TextView) findViewById(R.id.previous_3rd_last_result);
		previous4thResult = (TextView) findViewById(R.id.previous_4th_last_result);
		previousBest = (TextView) findViewById(R.id.previous_best);
		previousBestResult = (TextView) findViewById(R.id.previous_best_result);

		// --------------------Bundle and Topic------------------------------

		// Retrieves the string value that was passed in with the intent
		Bundle extras = getIntent().getExtras();
		
		// Assigns the passed string to the topic variable
		topic = extras.getString("topic");
		userId = extras.getInt("UserId");
		currentResult = extras.getInt("Percentage");
		
		setTitle("Assessment Topic : "+topic);
		
		// ---------------------Database Connection--------------------------
		dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.getWritableDatabase();
				
		// ------------------------------------------------------------------
		
		// Assign user's result to TextView using the calculate percantage
		// method to calculate the result.
		assessmentScore.setText(currentResult+"%");
		
		// Sets title to current score title
		previousTitle.setText(R.string.assessment_results_title);
		
		// --------------------Methods Called--------------------------------
		
		getResults();
		checkBest();
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
			oldResults = new TopicResultSet(csr.getInt(0), csr.getString(1), csr.getInt(2), 
					csr.getInt(3), csr.getInt(4), csr.getInt(5), csr.getInt(6));
			Log.i("Result Status", "Record Retrieved");
		} else {
			oldResults = new TopicResultSet(userId, topic, 0, 0, 0, 0, 0);
			Log.i("Result Status", "No Record Available. New Object Created");
		}
		
		csr.close();
		oldResults.displayResultSet();
	}
	
	/**
	 * Compares the current result to the held best result.
	 * If the current result is better than the currently held best
	 * then the bestResult variable is over written with the current
	 * and new bestResult.
	 */
	public void checkBest() {
		
		if (currentResult>oldResults.getBestResult()) {
			oldResults.setBestResult(currentResult);
		} 		
	}
	
	/**
	 * Creates a string array and casts the results from the result set into
	 * it. Then assigns each element of the string array to its appropriate 
	 * text view on the activity.
	 */
	public void assignValues() {
		
		String[] results = {Integer.toString(oldResults.getPreviousLast()),
							Integer.toString(oldResults.getPrevious2nd()),
							Integer.toString(oldResults.getPrevious3rd()),
							Integer.toString(oldResults.getPrevious4th()),
							Integer.toString(oldResults.getBestResult())};
		
		previousLastResult.setText(results[0]);
		previous2ndResult.setText(results[1]);
		previous3rdResult.setText(results[2]);
		previous4thResult.setText(results[3]);
		previousBestResult.setText(results[4]);
				
	}
	
	/**
	 * Calls the updateResultsTable method from the DatabaseHelper class.
	 * Deletes the currently held values for this assessment by this user and
	 * creates a new record with the updated values.
	 */
	public void updateResults() {
		
		// Creates a new TopicResultSet object to hold the new results that will be updated to the database
		updatedResults = new TopicResultSet(userId, topic, currentResult, oldResults.getPreviousLast(), oldResults.getPrevious2nd(), 
										oldResults.getPrevious3rd(), oldResults.getBestResult());
		
		// Chooses code depending on if there is an existing record held in the database
		if (queryCount!=0){
			// Code for updating record in database
			dbHelper.updateResultsTable(database, "Results", updatedResults.getUserId(), updatedResults.getTopic(), updatedResults.getPreviousLast(), 
										updatedResults.getPrevious2nd(), updatedResults.getPrevious3rd(), updatedResults.getPrevious4th(), 
										updatedResults.getBestResult());
		} else {
			// Code for adding record to database
			dbHelper.newResultsTable(database, "Results", updatedResults.getUserId(), updatedResults.getTopic(), updatedResults.getPreviousLast(), 
										updatedResults.getPrevious2nd(), updatedResults.getPrevious3rd(), updatedResults.getPrevious4th(), 
										updatedResults.getBestResult());
		}
	}
	
	/**
	 * Changes the layout of the activity to display the last
	 *  4 previous attempts results.
	 */
	public void switchToPrevious() {
		
		// Changes boolean for on click listener
		switchOn = true;
		
		// Changes the image for the button to show
		// current results button
		switchBtn.setBackgroundResource(R.drawable.results_btn_current_restults);
		
		// Sets title to previous score title
		previousTitle.setText(R.string.previous_title);

		// Sets all current results items to invisible
		assessmentScore.setVisibility(View.INVISIBLE);

		// Sets all previous results items to invisible
		previousLast.setVisibility(View.VISIBLE);
		previous2nd.setVisibility(View.VISIBLE);
		previous3rd.setVisibility(View.VISIBLE);
		previous4th.setVisibility(View.VISIBLE);
		previousLastResult.setVisibility(View.VISIBLE);
		previous2ndResult.setVisibility(View.VISIBLE);
		previous3rdResult.setVisibility(View.VISIBLE);
		previous4thResult.setVisibility(View.VISIBLE);
		previousBest.setVisibility(View.VISIBLE);
		previousBestResult.setVisibility(View.VISIBLE);
		
		// Calls method to assign previous values to textviews
		assignValues();

	}

	/**
	 * Changes the layout of the activity to display the
	 * current attempts result.
	 */
	public void switchToCurrent() {
		
		// Changes boolean for on click listener
		switchOn = false;
		
		// Changes the image for the button to show
		// all results button
		switchBtn.setBackgroundResource(R.drawable.results_btn_all_results);
		
		// Sets title to current score title
		previousTitle.setText(R.string.assessment_results_title);

		// Sets all current results items to visible
		assessmentScore.setVisibility(View.VISIBLE);

		// Sets all previous results items to invisible
		previousLast.setVisibility(View.INVISIBLE);
		previous2nd.setVisibility(View.INVISIBLE);
		previous3rd.setVisibility(View.INVISIBLE);
		previous4th.setVisibility(View.INVISIBLE);
		previousLastResult.setVisibility(View.INVISIBLE);
		previous2ndResult.setVisibility(View.INVISIBLE);
		previous3rdResult.setVisibility(View.INVISIBLE);
		previous4thResult.setVisibility(View.INVISIBLE);
		previousBest.setVisibility(View.INVISIBLE);
		previousBestResult.setVisibility(View.INVISIBLE);

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
				updateResults();
				database.close();
				finish();
				break;

			case R.id.results_switch_btn:
				if(switchOn == true){
					switchToCurrent();
				} else {
					switchToPrevious();
				}
				break;
			}
		}
	}
}
