package com.mdickson972.androidActivities;

import java.util.ArrayList;


import com.mdickson972.programmingjava.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LessonScreen extends ActionBarActivity {

	private String lessonCalled;
	private ImageView lessonImageView;
	private ArrayList<Integer> slides = new ArrayList<Integer>();
	private int currentPosition = 0;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lesson_screen);
		lessonImageView = (ImageView) findViewById(R.id.imageView);
		
		// -----------------------Buttons-----------------------------------
		
		// Create buttons
		Button nextButton = (Button) findViewById(R.id.lesson_next_btn);
		Button backButton = (Button) findViewById(R.id.lesson_back_btn);

		// Disable button clicks
		nextButton.setSoundEffectsEnabled(false);
		backButton.setSoundEffectsEnabled(false);
		// Assign onClick listeners
		nextButton.setOnClickListener(new btnListener());
		backButton.setOnClickListener(new btnListener());

		// ------------------------------------------------------------------

		// Retrieves the string value that was passed in with the intent
		Bundle extras = getIntent().getExtras();
		// Assigns the passed string to the lessonCalled variable
		lessonCalled = extras.getString("lessonCalled");

		// Set Title
		setTitle("Lesson Topic : "+lessonCalled);

		// Calls the switchOnLesson method to populate the slides arraylist
		switchOnLesson(lessonCalled);

		// Assigns the appropriate slide to the image view
		assignImage(currentPosition);

	}

	/**
	 * This method takes the string that identifies which lesson is being chosen
	 * and then creates a switch statement to populate the slides ArrayList with
	 * the appropriate images for that lesson.
	 * 
	 * @param lessonCalled
	 */
	public void switchOnLesson(String lessonCalled) {

		switch (lessonCalled) {
		
		//---------------- Java 101 -----------------
		
		case "'What is Java?'":
			slides.add(R.raw.java101_lesson1_1);
			slides.add(R.raw.java101_lesson1_2);
			slides.add(R.raw.java101_lesson1_3);
			slides.add(R.raw.java101_lesson1_4);
			slides.add(R.raw.java101_lesson1_5);
			slides.add(R.raw.java101_lesson1_6);
			slides.add(R.raw.java101_lesson1_7);
			slides.add(R.raw.java101_lesson1_8);
			slides.add(R.raw.java101_lesson1_9);
			break;
			
		case "'Variables'":
			slides.add(R.raw.java101_lesson2_1);
			slides.add(R.raw.java101_lesson2_2);
			slides.add(R.raw.java101_lesson2_3);
			slides.add(R.raw.java101_lesson2_4);
			slides.add(R.raw.java101_lesson2_5);
			slides.add(R.raw.java101_lesson2_6);
			slides.add(R.raw.java101_lesson2_7);
			slides.add(R.raw.java101_lesson2_8);
			break;
			
		case "'Operators'":
			slides.add(R.raw.java101_lesson3_1);
			slides.add(R.raw.java101_lesson3_2);
			slides.add(R.raw.java101_lesson3_3);
			slides.add(R.raw.java101_lesson3_4);
			slides.add(R.raw.java101_lesson3_5);
			slides.add(R.raw.java101_lesson3_6);
			slides.add(R.raw.java101_lesson3_7);
			break;
			
		case "'Decision Making'":
			slides.add(R.raw.java101_lesson4_1);
			slides.add(R.raw.java101_lesson4_2);
			slides.add(R.raw.java101_lesson4_3);
			slides.add(R.raw.java101_lesson4_4);
			slides.add(R.raw.java101_lesson4_5);
			slides.add(R.raw.java101_lesson4_6);
			slides.add(R.raw.java101_lesson4_7);
			slides.add(R.raw.java101_lesson4_8);
			slides.add(R.raw.java101_lesson4_9);
			slides.add(R.raw.java101_lesson4_10);
			slides.add(R.raw.java101_lesson4_11);
			slides.add(R.raw.java101_lesson4_12);
			slides.add(R.raw.java101_lesson4_13);
			break;
			
		case "'Why Java?'":
			slides.add(R.raw.java101_lesson5_1);
			slides.add(R.raw.java101_lesson5_2);
			slides.add(R.raw.java101_lesson5_3);
			slides.add(R.raw.java101_lesson5_4);
			slides.add(R.raw.java101_lesson5_5);
			slides.add(R.raw.java101_lesson5_6);
			slides.add(R.raw.java101_lesson5_7);
			break;
			
		//-------------------------------------------
			
		//------------- Loops -----------------------
		
		case "'Intro to Loops'":
			slides.add(R.raw.loops_lesson3_1);
			slides.add(R.raw.loops_lesson3_2);
			slides.add(R.raw.loops_lesson3_3);
			slides.add(R.raw.loops_lesson3_4);
			break;
			
			
		//-------------------------------------------	

		}

	}
	
	
	
	/**
	 * Takes the current position and assigns the appropriate image to the
	 * imageview
	 * @param currentPosition
	 */
	public void assignImage(int currentPosition) {
		lessonImageView.setImageResource(slides.get(currentPosition));
	}

	
	
	/**
	 * Sets up a switch to read selected buttons and move slides
	 * 
	 * @author mdickson972
	 * 
	 */
	private class btnListener implements OnClickListener {

		@Override
		public void onClick(View view) {

			Button button = (Button) view;

			switch (button.getId()) {

			case R.id.lesson_back_btn:

				if (currentPosition != 0) {
					currentPosition--;
					assignImage(currentPosition);
				} else {
					Toast.makeText(getApplicationContext(), 
							"This is the start of the lesson", 
							Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.lesson_next_btn:

				if (currentPosition != (slides.size()-1)) {
					currentPosition++;
					assignImage(currentPosition);
				} else {
					finish();
					Toast.makeText(getApplicationContext(), 
							"You have completed "+lessonCalled, 
							Toast.LENGTH_SHORT).show();
				}
				break;

			}

		}

	}

}
