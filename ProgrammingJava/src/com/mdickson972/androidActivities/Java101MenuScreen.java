package com.mdickson972.androidActivities;

import com.mdickson972.programmingjava.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressWarnings("deprecation")
public class Java101MenuScreen extends ActionBarActivity {
	
	private int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_to_java_screen);

		// Retrieve extras
		Bundle extras = getIntent().getExtras();
		userId = extras.getInt("UserId");

		// Create buttons
		Button whatIsJava = (Button) findViewById(R.id.intro_btn_what_is_java);
		Button vars = (Button) findViewById(R.id.intro_btn_vars);
		Button operators = (Button) findViewById(R.id.intro_btn_operators);
		Button decisionMaking = (Button) findViewById(R.id.intro_btn_decision);
		Button whyUseJava = (Button) findViewById(R.id.intro_btn_why_java);
		Button assessment = (Button) findViewById(R.id.intro_btn_ready);

		// Disable button clicks
		whatIsJava.setSoundEffectsEnabled(false);
		vars.setSoundEffectsEnabled(false);
		operators.setSoundEffectsEnabled(false);
		decisionMaking.setSoundEffectsEnabled(false);
		whyUseJava.setSoundEffectsEnabled(false);
		assessment.setSoundEffectsEnabled(false);

		// Set onClick Listeners
		whatIsJava.setOnClickListener(new btnListener());
		vars.setOnClickListener(new btnListener());
		operators.setOnClickListener(new btnListener());
		decisionMaking.setOnClickListener(new btnListener());
		whyUseJava.setOnClickListener(new btnListener());
		assessment.setOnClickListener(new btnListener());

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

	/**
	 * Sets up a switch to read selected buttons and open appropriate menus for
	 * what is selected
	 * 
	 * @author mdickson972
	 * 
	 */
	private class btnListener implements OnClickListener {

		@Override
		public void onClick(View view) {

			Button button = (Button) view;

			switch (button.getId()) {

			case R.id.intro_btn_what_is_java:
				// Method for What is Java Lesson
				Intent whatIsJava = new Intent(view.getContext(),
						LessonScreen.class);
				String lesson1 = "'What is Java?'";
				whatIsJava.putExtra("lessonCalled", lesson1);
				startActivity(whatIsJava);
				break;

			case R.id.intro_btn_vars:
				// Method for What is Java Lesson
				Intent variables = new Intent(view.getContext(),
						LessonScreen.class);
				String lesson2 = "'Variables'";
				variables.putExtra("lessonCalled", lesson2);
				startActivity(variables);
				break;

			case R.id.intro_btn_operators:
				// Method for What is Java Lesson
				Intent operators = new Intent(view.getContext(),
						LessonScreen.class);
				String lesson3 = "'Operators'";
				operators.putExtra("lessonCalled", lesson3);
				startActivity(operators);
				break;

			case R.id.intro_btn_decision:
				// Method for What is Java Lesson
				Intent decisionMaking = new Intent(view.getContext(),
						LessonScreen.class);
				String lesson4 = "'Decision Making'";
				decisionMaking.putExtra("lessonCalled", lesson4);
				startActivity(decisionMaking);
				break;

			case R.id.intro_btn_why_java:
				// Method for What is Java Lesson
				Intent whyJava = new Intent(view.getContext(),
						LessonScreen.class);
				String lesson5 = "'Why Java?'";
				whyJava.putExtra("lessonCalled", lesson5);
				startActivity(whyJava);
				break;
				
			case R.id.intro_btn_ready:
				// Method for Assessment
				Intent assessment = new Intent(view.getContext(),
						AssessmentScreen.class);
				String assessment1 = "'Java101'";
				assessment.putExtra("topic", assessment1);
				assessment.putExtra("UserId", userId);
				startActivity(assessment);
				break;

			}

		}

	}

}
