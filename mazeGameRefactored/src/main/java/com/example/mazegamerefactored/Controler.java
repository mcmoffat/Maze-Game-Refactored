package com.example.mazegamerefactored;

import java.io.*;
import java.util.ArrayList;

/**
 * Controller is the control module of the program. Program execution begins from main method.
 * Processes input from view and updates Game accordingly.
 */
public class Controler implements Serializable {
    Game loadedGame;
    ConsoleView view;
    ArrayList<Game> savedGames = new ArrayList<>();

    /**
     * Main method that runs program
     *
     * @param args
     */
    public static void main(String[] args) {
        Controler theControler = new Controler();

        // Deserialization
        try {
            FileInputStream file = new FileInputStream("serialization.bin");
            ObjectInputStream in = new ObjectInputStream(file);
            theControler.savedGames = (ArrayList<Game>) in.readObject();
            in.close();
            file.close();
            System.out.println("mySavedGames has been deserialized\n");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
           System.out.println("ClassNotFoundException:  " +
                   ex);
        }


        ConsoleView.launchViewFromControler(theControler);
        System.out.println("returned to main method");
        //theControler.view.showMainMenu();
    }

    /**
     * Creates a new game within the executing program
     *
     * @param theTriviaId
     */
    void createGame(int theTriviaId) {
        loadedGame = new Game(new SqliteQuestionGenerator(theTriviaId));
        loadedGame.sendBoardToView();
    }

    /**
     * Method for view to call upon a saved game being selected from main menu
     *
     * @param theGameSelected
     */
    public void gameSelected(Game theGameSelected) {
        loadedGame = theGameSelected;
        loadedGame.sendBoardToView(); // tell the game to send its state to the view to be displayed
    }

    /**
     * Method for view to call upon a room being selected to attempt entry
     *
     * @param theRow row of the room user is attempting to enter
     * @param theCol column of the room user is attempting to enter
     */
    void roomSelected(int theRow, int theCol) {
        if (loadedGame.getCurCol() == theCol && loadedGame.getCurRow() - 1 == theRow) {
            if (loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getNorthDoor().getOpen()) {
                loadedGame.setCurrentRoom(loadedGame.getRooms()[loadedGame.getCurRow() - 1][loadedGame.getCurCol()]);
            } else if (!loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getNorthDoor().getPermenantlyClosed()) {
                loadedGame.tryCol = theCol;
                loadedGame.tryRow = theRow;
                loadedGame.roomToBeEnteredByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow() - 1][loadedGame.getCurCol()];
                loadedGame.doorToBeUnlockedByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getNorthDoor();
                loadedGame.getQuestion();
            }
        } else if (loadedGame.getCurCol() + 1 == theCol && loadedGame.getCurRow() == theRow) {
            if (loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getEastDoor().getOpen()) {
                loadedGame.setCurrentRoom(loadedGame.getRooms()[loadedGame.getCurRow() ][loadedGame.getCurCol() + 1]);
            } else if (!loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getEastDoor().getPermenantlyClosed()) {
                loadedGame.tryCol = theCol;
                loadedGame.tryRow = theRow;
                loadedGame.roomToBeEnteredByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol() + 1];
                loadedGame.doorToBeUnlockedByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getEastDoor();
                loadedGame.getQuestion();
            }
        } else if (loadedGame.getCurCol() == theCol && loadedGame.getCurRow() + 1 == theRow) {
                if (loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getSouthDoor().getOpen()) {
                    loadedGame.setCurrentRoom(loadedGame.getRooms()[loadedGame.getCurRow() + 1][loadedGame.getCurCol()]);
                } else if (!loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getSouthDoor().getPermenantlyClosed()) {
                    loadedGame.tryCol = theCol;
                    loadedGame.tryRow = theRow;
                    loadedGame.roomToBeEnteredByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow() + 1][loadedGame.getCurCol() ];
                    loadedGame.doorToBeUnlockedByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getSouthDoor();
                    loadedGame.getQuestion();
                }
        } else if (loadedGame.getCurCol() - 1 == theCol && loadedGame.getCurRow() == theRow) {
                if (loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getWestDoor().getOpen()) {
                    loadedGame.setCurrentRoom(loadedGame.getRooms()[loadedGame.getCurRow() ][loadedGame.getCurCol() - 1]);
                } else if (!loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getWestDoor().getPermenantlyClosed()) {
                    loadedGame.tryCol = theCol;
                    loadedGame.tryRow = theRow;
                    loadedGame.roomToBeEnteredByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol() - 1];
                    loadedGame.doorToBeUnlockedByCurrentQuestion = loadedGame.getRooms()[loadedGame.getCurRow()][loadedGame.getCurCol()].getWestDoor();
                    loadedGame.getQuestion();
                }
        } else {
            loadedGame.sendBoardToView();
        }
    }

    /**
     * processes answer submitted to the current question
     *
     * @param theAnswer string answer submitted from view
     */
    void checkAnswer(String theAnswer) {
        System.out.println("Check Answer: " + theAnswer);
        if (loadedGame.currentQuestion == null) {
            throw new RuntimeException("Cannot answer question that is not created.");
        }
        boolean correct = loadedGame.currentQuestion.getCorrectTypedResponseAnswer().getAnswerText().equals(theAnswer);
        loadedGame.currentQuestion = null;
        // If correct answer, then we'll update our position
        if (correct) {
            loadedGame.updateAfterCorrectAnswer();
        } else {
           loadedGame.updateAfterIncorrectAnswer();
        }
    }

    /**
     * processes answer submitted to current question
     *
     * @param theAnswerId ID of multiple choice answer submitted
     */
    void checkAnswer(int theAnswerId) {
        System.out.println("Check Answer: " + theAnswerId);
        if (loadedGame.currentQuestion == null) {
            throw new RuntimeException("Cannot answer question that is not created.");
        }
        boolean correct = loadedGame.currentQuestion.getCorrectAnswer() == theAnswerId;
        loadedGame.currentQuestion = null;
        // If correct answer, then we'll update our position
        if (correct) {
            loadedGame.updateAfterCorrectAnswer();
        } else {
            loadedGame.updateAfterIncorrectAnswer();
        }

    }

    /**
     * Adds the current game to the list of saved games to be serialized under the entered name
     *
     * @param theEnteredGameName name under which to save game as
     */
    void saveState(String theEnteredGameName) {
        loadedGame.savedGameName = theEnteredGameName;
        if (!savedGames.contains(loadedGame)) {
            savedGames.add(loadedGame);
        }
        try {
            FileOutputStream file = new FileOutputStream("serialization.bin");

            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.savedGames);
            out.close();
            file.close();
            System.out.println("mySavedGames has been serialized");


        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
        loadedGame.sendBoardToView();
    }

    /**
     * Removes current game from list of saved games
     */
    public void deleteGame() {
        savedGames.remove(loadedGame);
        view.showMainMenu();
    }
}




