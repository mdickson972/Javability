package com.mdickson972.androidActivities;

import com.mdickson972.programmingjava.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressWarnings("deprecation")
public class LoopsMenuScreen extends ActionBarActivity {

	private int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loops_menu_screen);

		// Retrieve extras
		Bundle extras = getIntent().getExtras();
		userId = extras.getInt("UserId");

		// Create buttons
		Button assignmentOperators = (Button) findViewById(R.id.loops_assignment);
		Button increment = (Button) findViewById(R.id.loops_btn_increment);
		Button introToLoops = (Button) findViewById(R.id.loops_btn_intro);
		Button whileLoops = (Button) findViewById(R.id.loops_btn_while);
		Button doWhileLoops = (Button) findViewById(R.id.loops_btn_do_while);
		Button forLoops = (Button) findViewById(R.id.loops_btn_for);
		Button assessment = (Button) findViewById(R.id.loops_btn_ready);

		// Disable button clicks
		assignmentOperators.setSoundEffectsEnabled(false);
		increment.setSoundEffectsEnabled(false);
		introToLoops.setSoundEffectsEnabled(false);
		whileLoops.setSoundEffectsEnabled(false);
		doWhileLoops.setSoundEffectsEnabled(false);
		forLoops.setSoundEffectsEnabled(false);
		assessment.setSoundEffectsEnabled(false);

		// Set onClick Listeners
		assignmentOperators.setOnClickListener(new btnListener());
		increment.setOnClickListener(new btnListener());
		introToLoops.setOnClickListener(new btnListener());
		whileLoops.setOnClickListener(new btnListener());
		doWhileLoops.setOnClickListener(new btnListener());
		forLoops.setOnClickListener(new btnListener());
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

			// Switch statement to choose operation based on button selected
			switch (button.getId()) {

			case R.id.loops_assignment:
				// Method for Assignment Operators Lesson
				// Intent assignmentOperators = new Intent(view.getContext(),
				// VideoLessonScreen.class);
				// assignmentOperators.putExtra("lessonCalled",
				// "'Assignment operators'");
				// startActivity(assignmentOperators);

				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=M2x4iX2X0UY")));

				break;

			case R.id.loops_btn_increment:
				// Method for Incrementing / Decrementing Lesson
				// Intent increment = new Intent(view.getContext(),
				// VideoLessonScreen.class);
				// increment.putExtra("lessonCalled",
				// "'Increment/Decrement operators'");
				// startActivity(increment);

				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=7z7kSu4SbQI")));

				break;

			case R.id.loops_btn_intro:
				// Method for Intro to Loops Lesson
				Intent intro = new Intent(view.getContext(), LessonScreen.class);
				String lesson3 = "'Intro to Loops'";
				intro.putExtra("lessonCalled", lesson3);
				startActivity(intro);
				break;

			case R.id.loops_btn_while:
				// Method for While loops Lesson
				// Intent whileLoops = new Intent(view.getContext(),
				// VideoLessonScreen.class);
				// whileLoops.putExtra("lessonCalled", "'While loops'");
				// startActivity(whileLoops);

				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=ZqRJC75OPZ4")));

				break;

			case R.id.loops_btn_do_while:
				// Method for Do while loops lesson
				// Intent doWhile = new Intent(view.getContext(),
				// VideoLessonScreen.class);
				// doWhile.putExtra("lessonCalled", "'Do...while loops'");
				// startActivity(doWhile);

				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=3nm3YqOoqGk")));

				break;

			case R.id.loops_btn_for:
				// Method for for loops lesson
				// Intent forLoops = new Intent(view.getContext(),
				// VideoLessonScreen.class);
				// forLoops.putExtra("lessonCalled", "'For loops'");
				// startActivity(forLoops);

				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://www.youtube.com/watch?v=SZMx_vBh-ss")));

				break;

			case R.id.loops_btn_ready:
				// Method for the Assessment
				Intent assessment = new Intent(view.getContext(),
						AssessmentScreen.class);
				String assessment1 = "'Loops'";
				assessment.putExtra("topic", assessment1);
				assessment.putExtra("UserId", userId);
				startActivity(assessment);
				break;
			}
		}
	}
}
