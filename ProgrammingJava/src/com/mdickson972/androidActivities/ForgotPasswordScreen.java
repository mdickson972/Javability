package com.mdickson972.androidActivities;

import com.mdickson972.DatabaseHelper.DatabaseHelper;
import com.mdickson972.Email.Mail;
import com.mdickson972.programmingjava.R;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class ForgotPasswordScreen extends ActionBarActivity {

	// Class Constants
	private static final String DB_NAME = "Database.sqlite3";
	private static final String QUERY = "Select Password from UserData where EmailAddress = '";
	private static final String SYSTEM_EMAIL_ADDRESS = "no.reply.javability@gmail.com";
	private static final String SYSTEM_EMAIL_PASSWORD = "javability2015";

	// Class Variables
	private String emailAddress;
	private String password;
	private SQLiteDatabase database;
	private EditText emailEntry;
	private Boolean registeredEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgot_password_screen);

		// ---------------------Assign XML Items-----------------------------

		Button sendEmailBtn = (Button) findViewById(R.id.forgot_password_submit_btn);
		sendEmailBtn.setSoundEffectsEnabled(false);
		sendEmailBtn.setOnClickListener(new btnListener());

		emailEntry = (EditText) findViewById(R.id.forgot_email_entry);

		// ---------------------Database Connection--------------------------

		DatabaseHelper dbHelper = new DatabaseHelper(this, DB_NAME);
		database = dbHelper.openDataBase();

	}

	/**
	 * Initialises all variables to null values so that each time the send email 
	 * button is pressed the email is checked again.
	 */
	public void setup() {
		emailAddress = "";
		password = "";
		registeredEmail = false;
		
	}

	/**
	 * Retrieves the password for the user account associated with the entered
	 * email address.
	 */
	public void retrievePassword() {

		emailAddress = emailEntry.getText().toString();
		String queryAddress = emailAddress + "'";

		// Executes Query to retrieve user details of the given user name.
		Cursor csr = database.rawQuery(QUERY + queryAddress, null);

		// Assigns values from database to the user object.
		if (csr.moveToFirst()) {
			if (csr.getString(0) != null) {
				password = csr.getString(0);
				registeredEmail = true;
			}

		}

		// Closes instance of the cursor.
		csr.close();

	}
	
	/**
	 * Sends an email to the email address provided containing the password
	 * of the user account associated with that email address.
	 */
	public void sendEmail() {

		System.out.println("into sendEmail");

		String[] emailArray = { emailAddress };

		Mail mail = new Mail(SYSTEM_EMAIL_ADDRESS, SYSTEM_EMAIL_PASSWORD);

		mail.setTo(emailArray);
		mail.setFrom(SYSTEM_EMAIL_ADDRESS);
		mail.setSubject("This is an automated system message, please do not reply.");
		mail.setBody("Your password is : " + password);

		// Enabling Strict mode to allow email to be sent.
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		try {
			if (mail.send()) {
				Toast.makeText(getApplicationContext(),
						"Email was sent successfully.", Toast.LENGTH_LONG)
						.show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Email was not sent.",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "An error has occured. Email was not sent.",
					Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Sets up a switch to read selected buttons and launch appropriate method
	 * upon click.
	 * 
	 * @author mdickson972
	 * 
	 */
	private class btnListener implements OnClickListener {

		@Override
		public void onClick(View view) {

			Button button = (Button) view;

			switch (button.getId()) {

			case R.id.forgot_password_submit_btn:
				setup();
				retrievePassword();
				if (registeredEmail == true) {
					sendEmail();
				} else {
					Toast.makeText(getApplicationContext(),
							"Email Address is not registered.",
							Toast.LENGTH_SHORT).show();
				}

				break;
			}
		}
	}
}
