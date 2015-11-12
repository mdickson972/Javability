package com.mdickson972.androidActivities;

import java.util.ArrayList;
import java.util.Collections;

import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.programmingjava.R;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class OverallProgressScreen extends ActionBarActivity {

	// Class Constants
	private static final String DB_NAME = "Database.sqlite3";
	private static final String GET_BEST_QUERY = "Select BestScore from Results where UserId = ";
	private static final double TOTAL_TOPICS = 8;
	private static final double TOTAL_POSSIBLE_SCORE = TOTAL_TOPICS * 100;

	// Class Variables
	private int userId;
	private double totalScore;
	private ArrayList<Integer> scores = new ArrayList<>(Collections.nCopies(8,
			0));
	private Boolean switchOn = false;
	private SQLiteDatabase database;
	private Button homeBtn, switchBtn, java101, selectionStatements, loops,
			methods, arrays, arrays2d, constructors, inheritance;
	private TextView overallScore, overallTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overall_progress_screen);

		// ---------------------------Bundles and Titles----------------------

		// Retrieve extras
		Bundle extras = getIntent().getExtras();
		userId = extras.getInt("UserId");

		// Set Title
		setTitle("Overall Progress");

		// ----------------------------Assign XML Items-----------------------

		// Current Results Screen
		overallTitle = (TextView) findViewById(R.id.overall_title);
		overallScore = (TextView) findViewById(R.id.overall_result);

		// ----------------------------Buttons--------------------------------

		// Create buttons
		java101 = (Button) findViewById(R.id.overall_btn_java_101);
		selectionStatements = (Button) findViewById(R.id.overall_btn_selection_statements);
		loops = (Button) findViewById(R.id.overall_btn_loops);
		methods = (Button) findViewById(R.id.overall_btn_methods);
		arrays = (Button) findViewById(R.id.overall_btn_arrays);
		arrays2d = (Button) findViewById(R.id.overall_btn_2d_arrays);
		constructors = (Button) findViewById(R.id.overall_btn_constructors);
		inheritance = (Button) findViewById(R.id.overall_btn_inheritance);

		homeBtn = (Button) findViewById(R.id.overall_home_btn);
		switchBtn = (Button) findViewById(R.id.overall_switch_btn);

		// Disable button clicks
		java101.setSoundEffectsEnabled(false);
		selectionStatements.setSoundEffectsEnabled(false);
		loops.setSoundEffectsEnabled(false);
		methods.setSoundEffectsEnabled(false);
		arrays.setSoundEffectsEnabled(false);
		arrays2d.setSoundEffectsEnabled(false);
		constructors.setSoundEffectsEnabled(false);
		inheritance.setSoundEffectsEnabled(false);

		homeBtn.setSoundEffectsEnabled(false);
		switchBtn.setSoundEffectsEnabled(false);

		// Set onClick Listeners
		java101.setOnClickListener(new btnListener());
		selectionStatements.setOnClickListener(new btnListener());
		loops.setOnClickListener(new btnListener());
		methods.setOnClickListener(new btnListener());
		arrays.setOnClickListener(new btnListener());
		arrays2d.setOnClickListener(new btnListener());
		constructors.setOnClickListener(new btnListener());
		inheritance.setOnClickListener(new btnListener());

		homeBtn.setOnClickListener(new btnListener());
		switchBtn.setOnClickListener(new btnListener());

		// ----------------------------Database--------------------------------
		DatabaseHelper dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.openDataBase();

		// ----------------------------Method Calls----------------------------

		getScores();
		calculateOverallScore();
		assignTextViews();

	}

	/**
	 * Retrieves the scores from the database and assigns them to an int array
	 * to be passed to the user result set object.
	 */
	public void getScores() {

		// Executes Query to retrieve all questions of the given topic.
		Cursor csr = database.rawQuery(GET_BEST_QUERY + userId, null);

		Log.i("Get Scores", "Cursor count = " + csr.getCount());

		// Moves the cursor to the beginning of the results.
		csr.moveToFirst();

		int index = 0;

		if ((csr != null) && (csr.isFirst())) {
			while (!csr.isAfterLast()) {
				scores.set(index, csr.getInt(0));
				csr.moveToNext();
				index++;
			}
		}
		csr.close();
	}

	/**
	 * Calculates the total percentage of the application that has been
	 * completed by the user.
	 */
	public void calculateOverallScore() {
		// Creates double to hold the sum of the best scores.
		double totalSum = 0;
		// Loops through the arraylist of best scores and addes each score to
		// the totalSum
		// variable.
		for (int loop = 0; loop < scores.size(); loop++) {
			totalSum += scores.get(loop);
		}
		// Multiplies the sum by 100.
		totalSum *= 100;
		// Divides the sum*100 value by the total possible points to give
		// percentage and then assigns that value to the variable totalScore.
		totalScore = totalSum / TOTAL_POSSIBLE_SCORE;
	}

	/**
	 * Takes results from UserResultSet object and casts them as string to be
	 * assigned to text views in the xml.
	 */
	public void assignTextViews() {
		// Creates a string with the double value to be used in the text view.
		String totalString = String.valueOf(totalScore);
		// Assign score to overallScore text view.
		overallScore.setText(totalString+"%");
	}

	/**
	 * Changes the layout of the activity to display the different topics
	 * available in the app to select which to view scores from.
	 */
	public void switchToTopicList() {

		// Changes boolean for on click listener
		switchOn = true;

		// Changes the image for the button to show
		// current results button
		switchBtn.setBackgroundResource(R.drawable.overall_btn_overall_results);

		// Sets all current results items to invisible
		overallScore.setVisibility(View.INVISIBLE);
		overallTitle.setVisibility(View.INVISIBLE);

		// Sets all previous results items to invisible
		java101.setVisibility(View.VISIBLE);
		selectionStatements.setVisibility(View.VISIBLE);
		loops.setVisibility(View.VISIBLE);
		methods.setVisibility(View.VISIBLE);
		arrays.setVisibility(View.VISIBLE);
		arrays2d.setVisibility(View.VISIBLE);
		constructors.setVisibility(View.VISIBLE);
		inheritance.setVisibility(View.VISIBLE);
	}

	/**
	 * Changes the layout of the activity to display the overall score for the
	 * user's progress through the application.
	 */
	public void switchToOverallScore() {

		// Changes boolean for on click listener
		switchOn = false;

		// Changes the image for the button to show
		// current results button
		switchBtn.setBackgroundResource(R.drawable.overall_btn_topics);

		// Sets all current results items to invisible
		overallScore.setVisibility(View.VISIBLE);
		overallTitle.setVisibility(View.VISIBLE);

		// Sets all previous results items to inINVISIBLE
		java101.setVisibility(View.INVISIBLE);
		selectionStatements.setVisibility(View.INVISIBLE);
		loops.setVisibility(View.INVISIBLE);
		methods.setVisibility(View.INVISIBLE);
		arrays.setVisibility(View.INVISIBLE);
		arrays2d.setVisibility(View.INVISIBLE);
		constructors.setVisibility(View.INVISIBLE);
		inheritance.setVisibility(View.INVISIBLE);
	}

	/**
	 * Sets up a switch to read selected buttons and move between layouts, along
	 * with providing a way back to the menu screen.
	 * 
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

			case R.id.overall_home_btn:
				finish();
				break;

			case R.id.overall_switch_btn:
				if (switchOn == true) {
					switchToOverallScore();
				} else {
					switchToTopicList();
				}
				break;

			case R.id.overall_btn_java_101:
				// Method for Java 101 topic menu
				Intent java101 = new Intent(view.getContext(),
						TopicResultsScreen.class);
				java101.putExtra("UserId", userId);
				java101.putExtra("topic", "'Java101'");
				startActivity(java101);
				break;
				
			case R.id.overall_btn_selection_statements:
				// Method for Selection Statements topic menu
				Intent selection = new Intent(view.getContext(),
						TopicResultsScreen.class);
				selection.putExtra("UserId", userId);
				selection.putExtra("topic", "'SelectionStatements'");
				startActivity(selection);
				break;
				
			case R.id.overall_btn_loops:
				// Method for Loops topic menu
				Intent loops = new Intent(view.getContext(),
						TopicResultsScreen.class);
				loops.putExtra("UserId", userId);
				loops.putExtra("topic", "'Loops'");
				startActivity(loops);
				break;
				
			case R.id.overall_btn_methods:
				// Method for Methods topic menu
				Intent methods = new Intent(view.getContext(),
						TopicResultsScreen.class);
				methods.putExtra("UserId", userId);
				methods.putExtra("topic", "'Methods'");
				startActivity(methods);
				break;
				
			case R.id.overall_btn_arrays:
				// Method for Arrays topic menu
				Intent arrays = new Intent(view.getContext(),
						TopicResultsScreen.class);
				arrays.putExtra("UserId", userId);
				arrays.putExtra("topic", "'Arrays'");
				startActivity(arrays);
				break;
				
			case R.id.overall_btn_2d_arrays:
				// Method for 2D Arrays topic menu
				Intent arrays2d = new Intent(view.getContext(),
						TopicResultsScreen.class);
				arrays2d.putExtra("UserId", userId);
				arrays2d.putExtra("topic", "'2dArrays'");
				startActivity(arrays2d);
				break;
				
			case R.id.overall_btn_constructors:
				// Method for Constructors topic menu
				Intent constructors = new Intent(view.getContext(),
						TopicResultsScreen.class);
				constructors.putExtra("UserId", userId);
				constructors.putExtra("topic", "'Constructors'");
				startActivity(constructors);
				break;
				
			case R.id.overall_btn_inheritance:
				// Method for Inheritance topic menu
				Intent inheritance = new Intent(view.getContext(),
						TopicResultsScreen.class);
				inheritance.putExtra("UserId", userId);
				inheritance.putExtra("topic", "'Inheritance'");
				startActivity(inheritance);
				break;
			}
		}
	}
}
