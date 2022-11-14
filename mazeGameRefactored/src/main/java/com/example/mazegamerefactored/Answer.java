package com.example.mazegamerefactored;

import java.io.Serializable;

/**
 * Answer represents the answer text and wether the answer is a correct answer
 *
 */
public class Answer implements Serializable {

    private final int answerId;
    private final String answerText;
    private final boolean isCorrect;

    /**
     * Constructs an Answer from an answer id, answer text, and whether or not the answer is correct
     * @param theAnswerId unique idetifier of Answer
     * @param theAnswerText contents of answer
     * @param theIsCorrect wether or not answer is a correct answer
     */
    public Answer(int theAnswerId, String theAnswerText, boolean theIsCorrect) {
        answerId = theAnswerId;
        answerText = theAnswerText;
        isCorrect = theIsCorrect;
    }

    /**
     * Get the ID of the answer
     * @return answerId
     */
    public int getAnswerId() {
        return answerId;
    }

    /**
     * Get the answer's text
     * @return answerText
     */
    public String getAnswerText() {
        return answerText;
    }

    /**
     * Get wheter the answer is a correct answer
     * @return
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * String represetion of Answer includes answer ID, answer text, and wheether answer is a correct answer
     * @return String represetion of Answer
     */
    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }
}
