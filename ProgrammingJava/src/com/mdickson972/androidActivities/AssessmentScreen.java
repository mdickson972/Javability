package com.mdickson972.androidActivities;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mdickson972.Assessment.MultipleChoice;
import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.programmingjava.R;

@SuppressWarnings("deprecation")
public class AssessmentScreen extends ActionBarActivity {

	// Class Constants
	private static final int MAX_QUESTIONS = 10;
	private static final String DB_NAME = "Database.sqlite3";
	private static final String QUERY = "Select * From Questions Where ASSESSMENT_TOPIC = ";

	// Class Variables
	private int correctAnswerCounter = 0;
	private int percentageInt;
	private int userId;
	
	private String topic, selectedAnswer;
	private SQLiteDatabase database;
	private int currentPosition = 0;
	private TextView questionNumber, question, feedback;
	private RadioGroup answers;
	private RadioButton ans1, ans2, ans3, selectedButton;
	private Button checkButton, nextButton;

	private ArrayList<MultipleChoice> allTopicQuestions = new ArrayList<MultipleChoice>();
	private ArrayList<MultipleChoice> randomisedQuestions = new ArrayList<MultipleChoice>();

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assessment_screen);
		
		// -----------------------Buttons-----------------------------------

		// Create buttons
		nextButton = (Button) findViewById(R.id.assessment_next_btn);
		checkButton = (Button) findViewById(R.id.assessment_check_answer_btn);
		

		// Disable button clicks
		nextButton.setSoundEffectsEnabled(false);
		checkButton.setSoundEffectsEnabled(false);
		
		// Assign onClick listeners
		nextButton.setOnClickListener(new btnListener());
		checkButton.setOnClickListener(new btnListener());

		// ---------------------Assign XML Items-----------------------------

		questionNumber = (TextView) findViewById(R.id.assessment_question_number);
		question = (TextView) findViewById(R.id.assessment_question);
		feedback = (TextView) findViewById(R.id.assessment_feedback);

		answers = (RadioGroup) findViewById(R.id.assessment_answer_group);
		ans1 = (RadioButton) findViewById(R.id.assessment_answer1);
		ans2 = (RadioButton) findViewById(R.id.assessment_answer2);
		ans3 = (RadioButton) findViewById(R.id.assessment_answer3);
				

		// ---------------------Database Connection--------------------------

		DatabaseHelper dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.openDataBase();

		// --------------------Bundle and Topic------------------------------

		// Retrieves the string value that was passed in with the intent
		Bundle extras = getIntent().getExtras();
		// Assigns the passed string to the topic variable
		topic = extras.getString("topic");
		userId = extras.getInt("UserId");
		
		// Set Title
		setTitle("Assessment Topic : "+topic);

		// ---------------------Methods Called-------------------------------

		initialSetup();

	}

	/**
	 * A setup method that executes the methods for initial creation
	 * of the questions in the activity.
	 */
	public void initialSetup() {

		// Resets objects to default settings
		checkButton.setEnabled(true);
		nextButton.setEnabled(false);
		answers.clearCheck();
		feedback.setText("");

		// Executes methods
		retrieveQuestions();
		createRandomisedQuestions();
		displayQuestionDetails(currentPosition);
		

	}

	/**
	 * A setup method that executes the methods for updating 
	 * of the questions in the activity.
	 */
	public void refresh() {

		// Resets objects to default settings
		checkButton.setEnabled(true);
		nextButton.setEnabled(false);
		answers.clearCheck();
		feedback.setText("");

		// Executes methods
		displayQuestionDetails(currentPosition);
		
	}
	
	/**
	 * Queries the database to retrieve all the questions for the selected
	 * topic. Then creates Multiple Choice Question objects for each question
	 * and adds them to the allTopicQuestions ArrayList.
	 */
	public void retrieveQuestions() {

		// Clears any previous questions.
		allTopicQuestions.clear();

		// Executes Query to retrieve all questions of the given topic.
		Cursor csr = database.rawQuery(QUERY + topic, null);

		// Moves the cursor to the beginning of the results.
		csr.moveToFirst();

		// Executes the code provided the cursor begins at the start
		// and is not a null value. The loop will terminate once the
		// cursor has passed the last value in the ArrayList.
		if (csr.isFirst() && csr != null) {
			while (!csr.isAfterLast()) {

				// Creates a new MultipleChoice Question object and
				// adds it to the Questions ArrayList.
				allTopicQuestions.add(new MultipleChoice(csr.getInt(1), csr
						.getString(2), csr.getString(3), csr.getString(4), csr
						.getString(5), csr.getString(6), csr.getString(7), csr
						.getString(8)));
				// Moves the cursor to the next result.
				csr.moveToNext();
			}
			csr.close();
		}
	}

	/**
	 * Creates a randomised ArrayList of questions for the current instance of
	 * the assessment.
	 */
	public void createRandomisedQuestions() {

		// Shuffles all possible questions into random order
		Collections.shuffle(allTopicQuestions);

		// Takes the first 10 shuffled questions and
		// places them in the list of questions to be used
		for (int loop = 0; loop < MAX_QUESTIONS; loop++) {
			randomisedQuestions.add(loop, allTopicQuestions.get(loop));
		}
	}

	/**
	 * Displays the details for the current question
	 * 
	 * @param currentPosition
	 */
	public void displayQuestionDetails(int currentPosition) {
		// Current Position assigns the index of the MultipleChoice Question object
		// in the ArrayList

		// Sets the question number to the question number textview
		questionNumber.setText("Question " + (currentPosition + 1));
		// Sets the question to the question textview
		question.setText(randomisedQuestions.get(currentPosition).getQuestion());

		// Creates an ArrayList containing the correct and 2 incorrect answers to the
		// current question
		ArrayList<String> RbArray = new ArrayList<String>();
		RbArray.add(randomisedQuestions.get(currentPosition).getCorrectAnswer());
		RbArray.add(randomisedQuestions.get(currentPosition)
				.getIncorrectAnswer1());
		RbArray.add(randomisedQuestions.get(currentPosition)
				.getIncorrectAnswer2());

		// Randomises the order of the answers.
		// This means that every time the question is called the answers will be
		// in a different order.
		Collections.shuffle(RbArray);

		// Sets the mixed up answers to each of the answer RadioButtons
		ans1.setText(RbArray.get(0));
		ans2.setText(RbArray.get(1));
		ans3.setText(RbArray.get(2));

	}

	/**
	 * Gets the user selected answer and then assigns that answer to a string
	 * for comparison.
	 */
	public void getSelectedAnswer() {

		// get selected radio button from radioGroup
		int selected = answers.getCheckedRadioButtonId();
		selectedButton = (RadioButton) findViewById(selected);

		// Assign answer to string for comparison
		selectedAnswer = selectedButton.getText().toString();

		checkAnswer(selectedAnswer);

	}

	/**
	 * Checks the selected answer to confirm if the question was answered
	 * correctly or not. Prints appropriate feedback depending on which answer
	 * was selected. In the case of a correct answer increments the correct
	 * answer counter.
	 * 
	 * @param selectedAnswer
	 */
	public void checkAnswer(String selectedAnswer) {

		if (selectedAnswer.equals(randomisedQuestions.get(currentPosition)
				.getCorrectAnswer())) {

			feedback.setText(randomisedQuestions.get(currentPosition)
					.getCorrectAnswerFeedback());
			correctAnswerCounter++;

		} else if (selectedAnswer.equals(randomisedQuestions.get(
				currentPosition).getIncorrectAnswer1())) {

			feedback.setText(randomisedQuestions.get(currentPosition)
					.getIncorrectAnswer1Feedback());

		} else if (selectedAnswer.equals(randomisedQuestions.get(
				currentPosition).getIncorrectAnswer2())) {

			feedback.setText(randomisedQuestions.get(currentPosition)
					.getIncorrectAnswer2Feedback());
		}

	}

	/**
	 * Takes the value of correctAnswers from the assessment and calculates the 
	 * resulting score as a percentage based on the MAX_QUESTIONS variable.
	 * @param correctAnswerCounter
	 * @param MAX_QUESTIONS
	 * @return
	 */
	public void calculatePercentage(int correctAnswerCounter, int MAX_QUESTIONS) {
		
		// Creates float to hold the percentage value of the 
		// result and calculates it.
		float percentage = correctAnswerCounter*100;
		percentage /= MAX_QUESTIONS;	
		// Casts the float as an int and returns the int value.
		percentageInt = (int) percentage;
	}
	
	/**
	 * Sets up a switch to read selected buttons and move through assessment
	 * questions
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

			case R.id.assessment_next_btn:
				// When the next button is selected the currentPosition
				// variable is incremented and if it is less than the
				// MAX_VARIABLES constant, will execute the setup code.
				currentPosition++;
				if (currentPosition < MAX_QUESTIONS) {
					refresh();
				} else {	
					calculatePercentage(correctAnswerCounter, MAX_QUESTIONS);
					Intent results = new Intent(view.getContext(),
							AssessmentResultsScreen.class);
					results.putExtra("UserId", userId);
					results.putExtra("Percentage", percentageInt);
					results.putExtra("topic", topic);
					startActivity(results);
					finish();
				}
				break;				

			case R.id.assessment_check_answer_btn:

				if (answers.getCheckedRadioButtonId() == -1) {
					Toast.makeText(getApplicationContext(),
							"Please select an answer", Toast.LENGTH_SHORT)
							.show();
				} else {
					// Disables the check button so if an incorrect answer
					// is selected it cannot be changed in this instance.
					checkButton.setEnabled(false);
					// Enables the next button so the next question can
					// be displayed.
					nextButton.setEnabled(true);
					getSelectedAnswer();
				}
				break;
				
			}

		}
	}
}
