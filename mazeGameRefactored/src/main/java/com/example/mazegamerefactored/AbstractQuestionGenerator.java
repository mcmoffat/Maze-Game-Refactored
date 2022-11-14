package com.example.mazegamerefactored;

import java.io.Serializable;
import java.util.Queue;

/**
 * Represents a generator from which questions can be drawn for use by MazeGame
 */
public abstract class AbstractQuestionGenerator implements Serializable {


    private final Queue<Question> questions;
    private final int triviaId;

    /**
     * Defines behavior which returns a Queue of Questions
     * @return Queue of Questions
     */
    abstract Queue<Question> loadQuestions();

    /**
     * Constructs parent of concrete child object of the given trivia id
     * loads the questions and assignes them to variable questions
     * @param theTriviaId of trivia set
     */
    public AbstractQuestionGenerator(int theTriviaId) {
        triviaId = theTriviaId;
        questions = loadQuestions();
    }

    /**
     * Removes and returns the next question from the set of questions
     * @return next Question from questions
     */
    public Question popQuestion() {
        Question q = questions.remove();
        questions.add(q);
        return q;
    }

    /**
     * Get the trivia id repsenting the type of triva set of the genetator
     * @return triviaId
     */
    public int getTriviaId() {
        return triviaId;
    }

    /**
     * Get the number of question in the question set
     * @return size of question set
     */
    public int getQuestionCount() {
        return questions.size();
    }
}
