package com.mdickson972.androidActivities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.programmingjava.R;

@SuppressWarnings("deprecation")
public class RegistrationScreen extends ActionBarActivity {

	// Class Constants
	private final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private final String EMAIL_QUERY = "Select * From UserData Where EmailAddress = '";
	private final String USERNAME_QUERY = "Select * From UserData Where Username = '";
	private static final String DB_NAME = "Database.sqlite3";

	// Class Variables
	private String username, password, emailAddress;
	private Boolean checkFormVerifiction, checkNoDuplicity;
	private EditText userField, passField1, passField2, emailField1,
			emailField2;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_screen);

		// ---------------------Buttons--------------------------------------
		Button register = (Button) findViewById(R.id.reg_register_btn);

		register.setSoundEffectsEnabled(false);

		register.setOnClickListener(new btnListener());

		// ---------------------Assign XML Items-----------------------------

		userField = (EditText) findViewById(R.id.reg_username);
		passField1 = (EditText) findViewById(R.id.reg_password1);
		passField2 = (EditText) findViewById(R.id.reg_password2);
		emailField1 = (EditText) findViewById(R.id.reg_email1);
		emailField2 = (EditText) findViewById(R.id.reg_email2);

		passField1.setTypeface(Typeface.DEFAULT);
		passField2.setTypeface(Typeface.DEFAULT);

		// ---------------------Database Connection--------------------------

		dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.openDataBase();

		// ------------------------------------------------------------------

	}

	/**
	 * Resets default values for each variable.
	 */
	public void refresh() {
		username = "";
		password = "";
		emailAddress = "";
		checkFormVerifiction = false;
		checkNoDuplicity = false;
	}

	/**
	 * Takes user entry from EditText Fields and assigns them to class
	 * variables.
	 */
	public void assignVariables() {

		username = userField.getText().toString();
		password = passField1.getText().toString();
		emailAddress = emailField1.getText().toString();

	}

	/**
	 * Compares the values of both entered passwords to ensure they match.
	 * 
	 * @return a true or false value to determine if the user has input the
	 *         correct password twice
	 */
	public Boolean comparePasswords() {

		// If statement to compare passwords
		if (passField1.getText().toString()
				.equals(passField2.getText().toString())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Compares the values of both entered email addresses to ensure they match.
	 * 
	 * @return a true or false value to determine if the user has input the
	 *         correct email address twice
	 */
	public Boolean compareEmails() {

		// If statement to ensure email addresses are the same
		// and to ensure that they are in correct email address format.
		if (emailField1.getText().toString()
				.equals(emailField2.getText().toString())
				&& (emailField1.getText().toString().matches(EMAIL_REGEX))) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks each part of the registration forms verification
	 * and flags true if they pass, or display appropriate
	 * error message toast if they fail.
	 */
	public void formVerifiection() {
		
		if (comparePasswords()==true){
			if (compareEmails()==true){
				checkFormVerifiction = true;
			} else {
				Toast.makeText(getApplicationContext(),
						"Entered Email Addresses do not match", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Entered Passwords do not match", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Queries database to check that the entered user name is not already registered.
	 * @return - True if not registered, false if it is registered. 
	 */
	public Boolean checkUsernameDuplicity() {
		
		Cursor csr = database.rawQuery(USERNAME_QUERY + userField.getText().toString()+"'", null);
		if (csr.getCount()==0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Queries database to check that the entered email address is not already registered.
	 * @return - True if not registered, false if it is registered. 
	 */
	public Boolean checkEmailDuplicity() {
		
		Cursor csr = database.rawQuery(EMAIL_QUERY + emailField1.getText().toString()+"'", null);
		if (csr.getCount()==0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Checks the database to ensure that the entered username and email address
	 * is not already registered with another user account.
	 * Provides appropriate error messages via toast to inform the user accordingly.
	 */
	public void noDuplicity() {
		
		if (checkUsernameDuplicity()==true) {
			if (checkEmailDuplicity()==true) {
				checkNoDuplicity = true;
			} else {
				Toast.makeText(getApplicationContext(),
						"Entered Email Addresses is already registered", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Entered Username is already registered", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Calls database connection and passes user's values to it 
	 * @param username
	 * @param password
	 * @param email
	 */
	public void saveUserDetails() {
		System.out.println(dbHelper.newUser(database, "UserData", 
				username, password, emailAddress));
		Toast.makeText(getApplicationContext(),
				R.string.toast_register_sucessful, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Calls methods in appropriate order with validation.
	 */
	public void run() {

		// Resets values 
		refresh();
		// Checks form validation
		formVerifiection();
		
		// Checks database for potential duplicities
		noDuplicity();

		// Runs code and creates user account if
		// entered data passes validation.
		if ((checkFormVerifiction == true)&& (checkNoDuplicity == true)) {
			assignVariables();
			saveUserDetails();
			database.close();
			finish();
		}
	}

	/**
	 * Sets up a switch to read selected button when click is detected. *
	 * 
	 * @author mdickson972
	 */
	private class btnListener implements OnClickListener {

		@Override
		public void onClick(View view) {

			Button button = (Button) view;

			switch (button.getId()) {

			case R.id.reg_register_btn:
				run();
				break;
			}
		}
	}

}
