package com.example.mazegamerefactored;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents an AbstractQuestionGenerator which loads questions from a sqlite database
 */
public class SqliteQuestionGenerator extends AbstractQuestionGenerator implements Serializable {


    /**
     * Constructs a SqliteQuestionGenerator of the selected question set
     * @param theTriviaId selected question set
     */
    public SqliteQuestionGenerator(int theTriviaId) {
        super(theTriviaId);
    }

    /**
     * Defines the behavior for loading questions from a sqlite database
     * @return Queue of Questions
     */
    @Override
    Queue<Question> loadQuestions() {
        Queue<Question> questions = new LinkedList<>();
        var db = new Database();
        try {
            var conn = db.getConnection();

            PreparedStatement pstmt1 = conn.prepareStatement("SELECT questionId, questionText, isMultipleChoice, isTypedResponse FROM Question WHERE triviaId = ?");
            pstmt1.setInt(1, getTriviaId());
            ResultSet rs = pstmt1.executeQuery();

            while (rs.next()) {
                var questionId = rs.getInt("questionId");
                var questionText = rs.getString("questionText");
                var isMultipleChoice = rs.getBoolean("isMultipleChoice");
                var isTypedResponse = rs.getBoolean("isTypedResponse");
                var q = new Question(questionId, questionText, isMultipleChoice, isTypedResponse);

                PreparedStatement pstmt = conn.prepareStatement("SELECT answerId, answerText, isCorrect FROM Answer WHERE questionId = ?");
                pstmt.setInt(1, q.getQuestionId());
                ResultSet rs2 = pstmt.executeQuery();
                while (rs2.next()) {
                    q.addAnswer(new Answer(
                            rs2.getInt("answerId"),
                            rs2.getString("answerText"),
                            rs2.getBoolean("isCorrect")
                    ));
                }
                questions.add(q);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return questions;
    }
}
