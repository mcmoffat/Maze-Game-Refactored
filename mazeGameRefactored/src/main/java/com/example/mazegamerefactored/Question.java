package com.example.mazegamerefactored;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Question represents the question text and maintains reference to correct Answer and/or dispalyed answer options
 */
public class Question implements Serializable {

    private final int questionId;
    private final String questionText;
    private final List<Answer> answers;
    private int correctAnswer;
    private final Boolean isMultipleChoice;
    private final Boolean isTypedResponse;

    /**
     * Constructs a question from question id, question text, and what type of question it is
     *
     * @param theQuestionId unique identifier of question
     * @param theQuestionText question to be displayed to player
     * @param theIsMultipleChoice whether or not this question is a multiple choice question
     * @param theIsTypedResponse whether or not this question is a typed response question
     */
    public Question(int theQuestionId, String theQuestionText, Boolean theIsMultipleChoice, Boolean theIsTypedResponse) {
        questionId = theQuestionId;
        questionText = theQuestionText;
        isMultipleChoice = theIsMultipleChoice;
        isTypedResponse = theIsTypedResponse;
        answers = new ArrayList<>();
    }

    /**
     * Get the question ID
     * @return questionId
     */
    public int getQuestionId() {

        return questionId;
    }

    /**
     * Get the question to be displayed to player
     * @return questionText
     */
    public String getQuestionText() {

        return questionText;
    }

    /**
     * Get a List of answers associated with the question
     * @return answers
     */
    public List<Answer> getAnswers() {

        return answers;
    }

    /**
     * Adds the Answer to the collection of answers for this quesiton
     * If answer is a correct answer, sets it to correctAnswer of Question
     * @param theAnswer
     */
    public void addAnswer(Answer theAnswer) {
        if (theAnswer.isCorrect())
            correctAnswer = theAnswer.getAnswerId();
        answers.add(theAnswer);
    }

    /**
     * Get the Answer to the Question that is correct
     * @return correctAnswer
     */
    public int getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Get the Answer to the quesiton in the case that correctAnswer has not been assigned
     * @return Answer that is correct
     */
    public Answer getCorrectTypedResponseAnswer() {
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                return answer;
            }
        }
        throw new RuntimeException("Do not have a correct answer set for this question.");
    }

    /**
     * Get wether or not Question is a multiple choice question
     * @return isMultipleChoice
     */
    public Boolean getIsMultipleChoice() {
        return isMultipleChoice;
    }

    /**
     * Get wether or not Question is a typed response question
     * @return isMultipleChoice
     */
    public Boolean getIsTypedResponse() {
        return isTypedResponse;
    }

    /**
     * String represetion of Question includes question ID, question text, answeers, and correct answer
     * @return String represetion of Question
     */
    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", answers=" + answers +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
