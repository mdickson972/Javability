package com.mdickson972.ResultSet;

import android.util.Log;

public class TopicResultSet {
	
	// Class Variables
	
	/**
	 * Holds the value of the userId 
	 */
	private int userId;
	/**
	 * Holds the value of the topic 
	 */
	private String topic;
	/**
	 * Holds the value of the most recent attempt 
	 */
	private int previousLast;
	/**
	 * Holds the value of the second most recent attempt
	 */
	private int previous2nd;
	/**
	 * Holds the value of the third most recent attempt
	 */
	private int previous3rd;
	/**
	 * Holds the value of the fourth most recent attempt
	 */
	private int previous4th;
	/**
	 * Holds the value of the bestResult obtained. 
	 */
	private int bestResult;
	
	
	// Constructors
	
	/**
	 * Default Constructor
	 */
	public TopicResultSet() {
		
	}
	/**
	 * Argument Based Constructor
	 * @param userId
	 * @param topic
	 * @param previousLast
	 * @param previous2nd
	 * @param previous3rd
	 * @param previous4th
	 * @param bestResult
	 */
	public TopicResultSet(int userId, String topic, int previousLast, int previous2nd, 
									int previous3rd, int previous4th, int bestResult) {
		
		this.userId = userId;
		this.topic = topic;
		this.previousLast = previousLast;
		this.previous2nd = previous2nd;
		this.previous3rd = previous3rd;
		this.previous4th = previous4th;
		this.bestResult = bestResult;

		
	}

	/**
	 * Gets the value assigned to the userId variable
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * Assigns the entered value to the userId
	 * variable
	 * @param userId 
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * Gets the value assigned to the topic variable
	 * @return the topic
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * Assigns the entered value to the topic
	 * variable
	 * @param topic 
	 */
	public void setTopic(String topic) {
		this.topic = topic;
	}

	/**
	 * Gets the value assigned to the previousLast variable
	 * @return the previousLast
	 */
	public int getPreviousLast() {
		return previousLast;
	}

	/**
	 * Assigns the entered value to the previousLast
	 * variable
	 * @param previousLast 
	 */
	public void setPreviousLast(int previousLast) {
		this.previousLast = previousLast;
	}

	/**
	 * Gets the value assigned to the previous2nd variable
	 * @return the previous2nd
	 */
	public int getPrevious2nd() {
		return previous2nd;
	}

	/**
	 * Assigns the entered value to the previous2nd
	 * variable
	 * @param previous2nd 
	 */
	public void setPrevious2nd(int previous2nd) {
		this.previous2nd = previous2nd;
	}

	/**
	 * Gets the value assigned to the previous3rd variable
	 * @return the previous3rd
	 */
	public int getPrevious3rd() {
		return previous3rd;
	}

	/**
	 * Assigns the entered value to the previous3rd
	 * variable
	 * @param previous3rd 
	 */
	public void setPrevious3rd(int previous3rd) {
		this.previous3rd = previous3rd;
	}

	/**
	 * Gets the value assigned to the previous4th variable
	 * @return the previous4th
	 */
	public int getPrevious4th() {
		return previous4th;
	}

	/**
	 * Assigns the entered value to the previous4th
	 * variable
	 * @param previous4th
	 */
	public void setPrevious4th(int previous4th) {
		this.previous4th = previous4th;
	}

	/**
	 * Gets the value assigned to the bestResult variable
	 * @return the bestResult
	 */
	public int getBestResult() {
		return bestResult;
	}

	/**
	 * Assigns the entered value to the bestResult
	 * variable
	 * @param bestResult
	 */
	public void setBestResult(int bestResult) {
		this.bestResult = bestResult;
	}
	
	// Methods
	/**
	 * Displays all held details in a result set object
	 * Uses Log.i to print to console for debugging
	 */
	public void displayResultSet() {
		
		Log.i("Result Set Details", "-----Result Set-----");		
		Log.i("Result Set Details", "User Id : "+getUserId());
		Log.i("Result Set Details", "Topic : "+getTopic());
		Log.i("Result Set Details", "Last Result : "+getPreviousLast());
		Log.i("Result Set Details", "2nd Last Result : "+getPrevious2nd());
		Log.i("Result Set Details", "3rd Last Result : "+getPrevious3rd());
		Log.i("Result Set Details", "4th Last Result : "+getPrevious4th());
		Log.i("Result Set Details", "Best Result : "+getBestResult());
		
		
		
	}
	
	
	
	

}
