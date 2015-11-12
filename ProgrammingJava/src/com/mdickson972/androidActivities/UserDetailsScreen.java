package com.mdickson972.androidActivities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.User.User;
import com.mdickson972.programmingjava.R;

@SuppressWarnings("deprecation")
public class UserDetailsScreen extends ActionBarActivity {
	
	// Class Constants
	private static final String DB_NAME = "Database.sqlite3";
	private static final String USER_QUERY = "Select * from UserData where UserId = ";
	
	// Class Variables
	private int userId;
	private User user;
	private Boolean passwordVisible = false;
	private String newPassword;
	
	private Button homeBtn, changePasswordBtn;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private TextView userIdValue, usernameValue, emailAddressValue;
	private EditText currentPasswordField, newPasswordField, confirmNewPasswordField;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_details_screen);
		
		// -----------------------Buttons-----------------------------------

		homeBtn = (Button) findViewById(R.id.user_details_home_btn);
		changePasswordBtn = (Button) findViewById(R.id.user_details_change_password_btn);

		homeBtn.setSoundEffectsEnabled(false);
		changePasswordBtn.setSoundEffectsEnabled(false);

		homeBtn.setOnClickListener(new btnListener());
		changePasswordBtn.setOnClickListener(new btnListener());

		// ---------------------Assign XML Items-----------------------------
		
		userIdValue = (TextView) findViewById(R.id.user_details_user_id_value);
		usernameValue = (TextView) findViewById(R.id.user_details_username_value);
		emailAddressValue = (TextView) findViewById(R.id.user_details_emailaddress_value);

		currentPasswordField = (EditText) findViewById(R.id.user_details_current_password_field);
		newPasswordField = (EditText) findViewById(R.id.user_details_new_password_field);
		confirmNewPasswordField = (EditText) findViewById(R.id.user_details_confirm_new_password_field);

		

		// --------------------Bundle and Topic------------------------------

		// Retrieves the string value that was passed in with the intent
		Bundle extras = getIntent().getExtras();
		
		// Assigns the passed string to the topic variable
		userId = extras.getInt("UserId");
		
		// Set activity title
		setTitle("");
		
		// ---------------------Database Connection--------------------------
		dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.getWritableDatabase();
				
		// --------------------Methods Called--------------------------------
		getUserDetails();

		
	}
	
	/**
	 * Retrieves user details for the logged in user.
	 * Then calls the assign method to assign values to text views.
	 */
	public void getUserDetails() {
		
		Cursor csr = database.rawQuery(USER_QUERY+userId, null);
		csr.moveToFirst();
		user = new User(csr.getInt(0), csr.getString(1), csr.getString(2), csr.getString(3));
		assignValues();
	}
	
	/**
	 * Assigns user data values to text views to be seen by the user.
	 */
	public void assignValues() {
		
		String userIdString = ""+user.getUserId();
		
		userIdValue.setText(userIdString);
		usernameValue.setText(user.getUsername());
		emailAddressValue.setText(user.getEmailAddress());		
	}
	
	/**
	 * Sets the fields related to password to visible.
	 */
	public void passwordVisible() {
		
		passwordVisible = true;
		changePasswordBtn.setBackgroundResource(R.drawable.user_details_btn_save);
	
		currentPasswordField.setVisibility(View.VISIBLE);
		newPasswordField.setVisibility(View.VISIBLE);
		confirmNewPasswordField.setVisibility(View.VISIBLE);
	}
	
	/**
	 * Sets the fields related to password to invisible.
	 */
	public void passwordInvisible() {
		// Method call to save new password settings
		
		passwordVisible = false;
		changePasswordBtn.setBackgroundResource(R.drawable.user_details_btn_change_password);
		
		currentPasswordField.setVisibility(View.INVISIBLE);
		newPasswordField.setVisibility(View.INVISIBLE);
		confirmNewPasswordField.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * Calls appropriate methods to validate and save new password to database.
	 */
	public void saveNewPassword() {
		if (validateCurrentPassword()==true) {
			if (compareNewPasswords()==true) {
				writeToDatabase();
				Toast.makeText(getApplicationContext(), "Password successfully changed.", 
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "New passwords do not match.", 
									Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Incorrect current password entered.", 
								Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * Checks to see if the user entered current password matches the held password.
	 * @return
	 */
	public boolean validateCurrentPassword() {
		
		String currentPasswordFieldString = 
					currentPasswordField.getText().toString();
		
		if (currentPasswordFieldString.equals(user.getPassword())){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Checks to see if the new password and confirm new password fields are equal.
	 * @return
	 */
	public boolean compareNewPasswords() {
		
		if (confirmNewPasswordField.getText().toString()
				.equals(newPasswordField.getText().toString())){
			newPassword = newPasswordField.getText().toString();
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Writes new password to the database updating from previously stored.
	 */
	public void writeToDatabase() {
		
		dbHelper.updateUserPassword(database, userId, newPassword);
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

			case R.id.user_details_home_btn:
				database.close();
				finish();
				break;
				
			case R.id.user_details_change_password_btn:
				if (passwordVisible==true){
					// Code for saving password and resetting layout to invisible
					saveNewPassword();
					passwordInvisible();
				} else {
					// Code for switching layout to password visible
					passwordVisible();
				}
				break;			
			}
		}
	}
}
