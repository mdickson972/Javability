package com.mdickson972.User;

import android.util.Log;


public class User {
	
	// Class Variables
	/**
	 * The variable that holds the value of the
	 * userId.
	 */
	private int userId;
	/**
	 * The variable that holds the value of the
	 * user name.
	 */
	private String username;
	/**
	 * The variable that holds the value of the
	 * password.
	 */
	private String password;
	/**
	 * The variable that holds the value of the
	 * emailAddress.
	 */
	private String emailAddress;
	
	
	// Constructors
		
	/**
	 * Default Constructor
	 */
	public User () {
		
	}
	
	/**
	 * Full Argument Based Constructor
	 * @param userId
	 * @param username
	 * @param password
	 * @param emailAddress
	 * @param overallProgress
	 */
	public User (int userId, String username, String password,
						String emailAddress) {
		
		this.setUserId(userId);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmailAddress(emailAddress);
		
	}
	
	
	// Getters and Setters	
	
	/**
	 * Returns the userId value
	 * @return
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * Assigns the userId variable
	 * @param userId
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * Returns the user name value
	 * @return
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Assigns the user name variable
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * Returns the password value
	 * @return
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Assigns the password variable
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Returns the emailAddress value
	 * @return
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * Assigns the emailAddress variable
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
	// Methods
	/**
	 * Displays all held details for a user object
	 * Uses Log.i to print to console for debugging
	 */
	public void displayUserDetails() {
		
		Log.i("User Details", "-----------User Details-----------");
		Log.i("User Details", "User Id: "+userId);
		Log.i("User Details", "Username: "+username);
		Log.i("User Details", "Password: "+password);
		Log.i("User Details", "Email Address: "+emailAddress);
		
		
		
		
		
		
	}
	
	
}
