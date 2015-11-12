package com.mdickson972.Assessment;

public class MultipleChoice extends Question {
	
	// Variables
	
	/**
	 * Holds value for first incorrect answer
	 */
	private String incorrectAnswer1;
	/**
	 * Holds value for second incorrect answer
	 */
	private String incorrectAnswer2;
	/**
	 * Holds feedback for first incorrect answer
	 */
	private String incorrectAnswer1Feedback;
	/**
	 * Holds feedback for second incorrect answer
	 */
	private String incorrectAnswer2Feedback;
	
	// Constructors
	
	/**
	 * Default Constructor
	 */
	public MultipleChoice(){
		
	}
	
	/**
	 * Argument Based Constructor
	 * @param questionId
	 * @param question
	 * @param correctAnswer
	 * @param correctAnswerFeedback
	 * @param incorrectAnswer1
	 * @param incorrectAnswer2
	 * @param incorrectAnswer1Feedback
	 * @param incorrectAnswer2Feedback
	 */
	public MultipleChoice(int questionId, String question, String correctAnswer,
					String incorrectAnswer1, String incorrectAnswer2, String correctAnswerFeedback, 
					String incorrectAnswer1Feedback, String incorrectAnswer2Feedback) {
		
		super(questionId, question, correctAnswer, correctAnswerFeedback);
		this.incorrectAnswer1 = incorrectAnswer1;
		this.incorrectAnswer2 = incorrectAnswer2;
		this.setIncorrectAnswer1Feedback(incorrectAnswer1Feedback);
		this.setIncorrectAnswer2Feedback(incorrectAnswer2Feedback);
		
		
	}
	
	// Getters and Setters
	
	/**
	 * Returns the value of the first incorrect answer
	 * @return
	 */
	public String getIncorrectAnswer1() {
		return incorrectAnswer1;
	}
	/**
	 * Assigns the value of the first incorrect answer
	 * @param incorrectAnswer1
	 */
	public void setIncorrectAnswer1(String incorrectAnswer1) {
		this.incorrectAnswer1 = incorrectAnswer1;
	}
	/**
	 * Returns the value of the second incorrect answer
	 * @return
	 */
	public String getIncorrectAnswer2() {
		return incorrectAnswer2;
	}
	/**
	 * Assigns the value of the second incorrect answer
	 * @param incorrectAnswer2
	 */
	public void setIncorrectAnswer2(String incorrectAnswer2) {
		this.incorrectAnswer2 = incorrectAnswer2;
	}
	/**
	 * Returns the value of the first incorrect answers feedback
	 * @return
	 */
	public String getIncorrectAnswer1Feedback() {
		return incorrectAnswer1Feedback;
	}
	/**
	 * Assigns the value of the first incorrect answers feedback
	 * @param incorrectAnswer1Feedback
	 */
	public void setIncorrectAnswer1Feedback(String incorrectAnswer1Feedback) {
		this.incorrectAnswer1Feedback = incorrectAnswer1Feedback;
	}
	/**
	 * Returns the value of the second incorrect answers feedback
	 * @return
	 */
	public String getIncorrectAnswer2Feedback() {
		return incorrectAnswer2Feedback;
	}
	/**
	 * Assigns the value of the second incorrect answers feedback
	 * @param incorrectAnswer2Feedback
	 */
	public void setIncorrectAnswer2Feedback(String incorrectAnswer2Feedback) {
		this.incorrectAnswer2Feedback = incorrectAnswer2Feedback;
	}
	
	
	

}
