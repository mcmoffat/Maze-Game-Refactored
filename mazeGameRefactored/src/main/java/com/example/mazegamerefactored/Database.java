package com.example.mazegamerefactored;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

/**
 * Represents the connection to sqlite
 */
public class Database implements Serializable {



    /**
     * Constructor generates database if it does not already exist
     */
    public Database() {
        generateDatabaseIfNotExists();
    }

    /**
     * Gets the connection to the program's database
     * @return connection to database
     */
    public Connection getConnection() {
        Connection connection = null;
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:/Users/mikemoffat/IdeaProjects/mazeGameRefactored/trivia_maze.db");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }

    /**
     * If database is not already created, initializes hard coded Questons and Answers
     */
    private void generateDatabaseIfNotExists() {
        // TODO Check for existence of database file.
        // For now we will create a new db file every time until these questions are finalized.
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("DROP TABLE IF EXISTS SavedGames");
            statement.executeUpdate("CREATE TABLE SavedGames (gameID INTEGER PRIMARY KEY, gameDate string, gameString string)");

            // TODO Find specific trivia questions
            statement.executeUpdate("DROP TABLE IF EXISTS Trivia");
            statement.executeUpdate("CREATE TABLE Trivia (triviaId INTEGER PRIMARY KEY, triviaName string)");

            statement.executeUpdate("DROP TABLE IF EXISTS Question");
            statement.executeUpdate("CREATE TABLE Question (questionId INTEGER PRIMARY KEY, triviaId INTEGER, questionText string, isMultipleChoice Boolean, isTypedResponse Boolean)");

            statement.executeUpdate("DROP TABLE IF EXISTS Answer");
            statement.executeUpdate("CREATE TABLE Answer (answerId INTEGER PRIMARY KEY, questionId INTEGER, answerText string, isCorrect boolean)");

            // Create "Basic Test" Trivia
            statement.executeUpdate("INSERT INTO Trivia (triviaName) VALUES ('Basic Test Trivia')");
            var keys = statement.getGeneratedKeys();
            keys.next();
            int triviaId = keys.getInt(1);

            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'Which answer is correct?', true, false)");
            keys = statement.getGeneratedKeys();
            keys.next();
            int questionId1 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'Pick the answer that is not incorrect?', true, false)");
            keys = statement.getGeneratedKeys();
            keys.next();
            int questionId2 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'Best answer please?', true, false)");
            keys = statement.getGeneratedKeys();
            keys.next();
            int questionId3 = keys.getInt(1);

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId1 + ", 'Incorrect', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId1 + ", 'Correct', 1)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId2 + ", 'Incorrect', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId2 + ", 'Correct', 1)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId3 + ", 'Incorrect', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId3 + ", 'Correct', 1)");



            // Create "Capitals of States" Trivia
            statement.executeUpdate("INSERT INTO Trivia (triviaName) VALUES ('Capitals of States')");
            keys = statement.getGeneratedKeys();
            keys.next();
            triviaId = keys.getInt(1);

            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'What is the Capital of Washington?', false, true)");
            keys = statement.getGeneratedKeys();
            keys.next();
            questionId1 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'What is the Capital of Oregon?', true, false)");
            keys = statement.getGeneratedKeys();
            keys.next();
            questionId2 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'What is the Capital of California?', true, false)");
            keys = statement.getGeneratedKeys();
            keys.next();
            questionId3 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'What is the Capital of Texas?', false, true)");
            keys = statement.getGeneratedKeys();
            keys.next();
            int questionId4 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'What is the Capital of Florida?', true, false)");
            keys = statement.getGeneratedKeys();
            keys.next();
            int questionId5 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'What is the Capital of Pennsylvania?', true, false)");
            keys = statement.getGeneratedKeys();
            keys.next();
            int questionId6 = keys.getInt(1);
            statement.executeUpdate("INSERT INTO Question (triviaId, questionText, isMultipleChoice, isTypedResponse) VALUES (" + triviaId + ", 'What is the Capital of Georgia?', false, true)");
            keys = statement.getGeneratedKeys();
            keys.next();
            int questionId7 = keys.getInt(1);

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId1 + ", 'Seattle', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId1 + ", 'Bellevue', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId1 + ", 'Olympia', 1)");

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId2 + ", 'Portland', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId2 + ", 'Salem', 1)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId2 + ", 'Beaverton', 0)");

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId3 + ", 'Los Angeles', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId3 + ", 'San Francisco', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId3 + ", 'Sacramento', 1)");

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId4 + ", 'Houston', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId4 + ", 'Dallas', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId4 + ", 'Austin', 1)");

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId5 + ", 'Miami', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId5 + ", 'Orlando', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId5 + ", 'Tallahassee', 1)");

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId6 + ", 'Pittsburgh', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId6 + ", 'Gettysburgh', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId6 + ", 'Harrisburgh', 1)");

            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId7 + ", 'Augusta', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId7 + ", 'Savannah', 0)");
            statement.executeUpdate("INSERT INTO Answer (questionId, answerText, isCorrect) VALUES (" + questionId7 + ", 'Atlanta', 1)");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }
}
