package com.mdickson972.androidActivities;

import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.User.User;
import com.mdickson972.programmingjava.R;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class LoginScreen extends ActionBarActivity {

	// Class Constants
	private static final String DB_NAME = "Database.sqlite3";
	private static final String QUERY = "Select * From UserData Where Username = '";

	// Class Variables
	private SQLiteDatabase database;
	private EditText userField, passField;
	private String username, password;
	private static User user;
	private Boolean passwordCheck = false;
	private int failCount= 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		
		// Set Title
		setTitle("");

		// ---------------------Buttons--------------------------------------

		// Create buttons
		Button register = (Button) findViewById(R.id.login_register_btn);
		Button signIn = (Button) findViewById(R.id.signIn_btn);
		Button forgotPassword = (Button) findViewById(R.id.forgotPass_btn);

		// Disable button clicks
		register.setSoundEffectsEnabled(false);
		signIn.setSoundEffectsEnabled(false);
		forgotPassword.setSoundEffectsEnabled(false);

		// Set onClick Listeners
		register.setOnClickListener(new btnListener());
		signIn.setOnClickListener(new btnListener());
		forgotPassword.setOnClickListener(new btnListener());

		// ---------------------Assign XML Items-----------------------------

		userField = (EditText) findViewById(R.id.login_username_field);
		passField = (EditText) findViewById(R.id.login_password_field);

		// ---------------------Database Connection--------------------------

		DatabaseHelper dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.openDataBase();

	}

	/**
	 * Takes the login information entered and assigns it to string variables to
	 * be used in login validation.
	 */
	public void editToString() {

		username = userField.getText().toString();
		password = passField.getText().toString();

	}

	/**
	 * Retrieves the user details for that user name from the database and then
	 * assigns the details to a user object.
	 */
	public void retrieveUser() {

		// Resets user object for new input.
		user = new User();

		// Executes Query to retrieve user details of the given user name.
		Cursor csr = database.rawQuery(QUERY + username + "'", null);

		// Assigns values from database to the user object.
		if (csr.moveToFirst()) {
			user = new User(csr.getInt(0), csr.getString(1), csr.getString(2),
					csr.getString(3));
		}

		// Closes instance of the cursor.
		csr.close();

	}

	/**
	 * Compares the user entered password with the password saved in the
	 * database. Sets passwordCheck boolean to true if they are the same, and to
	 * false if they are not the same.
	 */
	public void checkPassword() {

		if (password.equals(user.getPassword())) {
			passwordCheck = true;
		} else {
			passwordCheck = false;
		}
	}

	/**
	 * Executes the three validation methods to determine if the user details
	 * entered is valid.
	 */
	public void loginValidation() {

		editToString();
		retrieveUser();
		checkPassword();

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

			case R.id.login_register_btn:
				Intent register = new Intent(view.getContext(),
						RegistrationScreen.class);
				startActivity(register);
				break;

			case R.id.signIn_btn:
				loginValidation();
				if (passwordCheck == true) {
					Intent mainMenu = new Intent(view.getContext(),
							MainMenuScreen.class);
					mainMenu.putExtra("Username", username);
					mainMenu.putExtra("UserId", user.getUserId());
					startActivity(mainMenu);
					Toast.makeText(getApplicationContext(), "Welcome "+username, 
									Toast.LENGTH_SHORT).show();
					user.displayUserDetails();
					
					// Clear user details
					userField.setText("");
					passField.setText("");
					
				} else if (failCount<=3){
					Toast.makeText(getApplicationContext(),
							"Invalid Login Details.", Toast.LENGTH_SHORT).show();
				} else if (failCount>3){
					Toast.makeText(getApplicationContext(),
							"Invalid Login Details.\nRemember, Username and Password are case sensitive.", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.forgotPass_btn:
				Intent forgotPass = new Intent(view.getContext(),
						ForgotPasswordScreen.class);
				startActivity(forgotPass);
				break;

			}
		}
	}
}
