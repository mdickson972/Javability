package com.mdickson972.Assessment;

public class Question {
	
	// Variables

	/**
	 * Holds the value for the question id
	 */
	private int questionId;
	/**
	 * Holds the value for the question
	 */
	private String question;
	/**
	 * Holds the value for the correct answer
	 */
	private String correctAnswer;
	/**
	 * Holds the feedback for the correct answer
	 */
	private String correctAnswerFeedback;
	
	// Constructors
	
	/**
	 * Default constructor
	 */
	public Question(){
		
	}
	
	/**
	 * Argument Based Constructor
	 * @param questionId
	 * @param question
	 * @param correctAnswer
	 * @param correctAnswerFeedback
	 */
	public Question(int questionId, String question, String correctAnswer, String correctAnswerFeedback) {
		this.questionId = questionId;
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.setCorrectAnswerFeedback(correctAnswerFeedback);
	}
	
	//Getters and Setters

	/**
	 * Returns the questionId value
	 * @return
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * Assigns the value for the questionId
	 * @param questionId
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	/**
	 * Returns the question value
	 * @return
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * Assigns the value for the question
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	/**
	 * Returns the correctAnswer value
	 * @return
	 */
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	/**
	 * Assigns the value for the correctAnswer
	 * @param correctAnswer
	 */
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	/**
	 * Returns the value of the correct answers feedback
	 * @return
	 */
	public String getCorrectAnswerFeedback() {
		return correctAnswerFeedback;
	}
	/**
	 * Assigns the value for the correct answers feedback
	 * @param correctAnswer
	 */
	public void setCorrectAnswerFeedback(String correctAnswerFeedback) {
		this.correctAnswerFeedback = correctAnswerFeedback;
	}
	
	
	
	
	
	
	

}
