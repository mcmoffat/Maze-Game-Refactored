package com.example.mazegamerefactored;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;

/**
 * Game represents model component of the application. Game saves the current state of a game.
 */
public class Game extends Observable implements Serializable {
    String savedGameName;
    Question currentQuestion;
    Room[][] rooms;
    Room currentRoom;
    Door doorToBeUnlockedByCurrentQuestion;
    Room roomToBeEnteredByCurrentQuestion;
    // Next move column of mazeItems
    int tryCol;
    // Next move row of mazeItems
    int tryRow;
    Boolean gameHasBeenLost;
    Boolean gameHasBeenWon;
    private static int boardSize = 4;
    private AbstractQuestionGenerator questionGenerator;
    private int currentColumn = 0;
    private int currentRow = 0;

    public String toString() {
        return savedGameName;
    }

    /**
     * Constructs a a game given an AbstractQuestionGenerator
     *
     * @param questionBuilder
     */
    public Game(AbstractQuestionGenerator questionBuilder) {
        this.questionGenerator = questionBuilder;

        this.rooms = new Room[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                rooms[i][j] = new Room();
                rooms[i][j].setRowNumber(i);
                rooms[i][j].setColumnNumber(j);
            }
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(i < boardSize - 1) {
                    rooms[i][j].setSouthRoom(rooms[i + 1][j]);
                    rooms[i][j].setSouthDoor(new Door());
                }

                if(j < boardSize - 1) {
                    rooms[i][j].setEastRoom(rooms[i][j + 1]);
                    rooms[i][j].setEastDoor(new Door());
                }

                if(i > 0) {
                    rooms[i][j].setNorthRoom(rooms[i - 1][j]);
                    rooms[i][j].setNorthDoor(rooms[i - 1][j].getSouthDoor());
                }

                if(j > 0) {
                    rooms[i][j].setWestRoom(rooms[i][j - 1]);
                    rooms[i][j].setWestDoor(rooms[i][j - 1].getEastDoor());
                }
            }
        }

        currentRoom = rooms[0][0];
        rooms[0][0].isCurrentRoom = true;
        rooms[boardSize - 1][boardSize - 1].setToExitRoom();
    }

    /**
     * Determines whether some series of moves would allow player to reach the exit from current position
     *
     * @return boolean true of path to exit exists, false if not
     */
    public boolean existsPathToExit(){
        Boolean[][] visited = new Boolean[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                visited[i][j] = false;
            }
        }
        return searchForExit(currentRoom, visited, currentRoom.getRowNumber(), currentRoom.getColumnNumber());
    }

    /**
     * recursive method used by existsPathToExit
     *
     * @return boolean true of path to exit exists, false if not
     */
    private boolean searchForExit(Room searchRoom, Boolean[][] theVisitedArray, int theRow, int theColumn) {
        if (searchRoom.getIsExitRoom()) {
            return true;
        }
        theVisitedArray[theRow][theColumn] = true;
        if(searchRoom.getNorthDoor() != null && !searchRoom.getNorthDoor().getPermenantlyClosed()
                && !theVisitedArray[theRow - 1][theColumn] && searchForExit(searchRoom.getNorthRoom(), theVisitedArray, theRow - 1, theColumn)) {
            return true;
        } else if(searchRoom.getEastDoor() != null && !searchRoom.getEastDoor().getPermenantlyClosed()
                && !theVisitedArray[theRow][theColumn + 1] &&
                searchForExit(searchRoom.getEastRoom(), theVisitedArray, theRow, theColumn + 1)) {
            return true;
        } else if(searchRoom.getSouthDoor() != null && !searchRoom.getSouthDoor().getPermenantlyClosed()
                && !theVisitedArray[theRow + 1][theColumn] && searchForExit(searchRoom.getSouthRoom(), theVisitedArray, theRow + 1, theColumn)) {
            return true;
        } else if(searchRoom.getWestDoor() != null && !searchRoom.getWestDoor().getPermenantlyClosed()
                && !theVisitedArray[theRow][theColumn - 1]  && searchForExit(searchRoom.getWestRoom(), theVisitedArray, theRow, theColumn - 1)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Makes state changes to signify a change in current room
     *
     * @param theNewCurrentRoom the room to be set as the current room
     */
    public void setCurrentRoom(Room theNewCurrentRoom) {
        currentRoom.setIsCurrentRoom(false);
        currentRoom = theNewCurrentRoom;
        currentRoom.setIsCurrentRoom(true);
        currentRow = currentRoom.getRowNumber();
        currentColumn = currentRoom.getColumnNumber();
        sendBoardToView();
    }

    /**
     * Sends next question to the view
     */
    void getQuestion() {
        if (currentQuestion != null) {
            throw new RuntimeException("Current Question already in progress.");
        }
        currentQuestion = questionGenerator.popQuestion();
        ConsoleView.showQuestion(currentQuestion);
    }

    /**
     * Changes game state after correct answer entered
     */
    void updateAfterCorrectAnswer(){
        doorToBeUnlockedByCurrentQuestion.openDoor();
        currentRoom.isCurrentRoom = false;
        roomToBeEnteredByCurrentQuestion.isCurrentRoom = true;
        currentRoom = roomToBeEnteredByCurrentQuestion;
        currentRow = tryRow;
        currentColumn = tryCol;

        if (currentRoom.getIsExitRoom()) {
            gameHasBeenWon = true;
            tellViewThatGameIsWon();
        } else {
            sendBoardToView();
        }
    }

    /**
     * Changes game state after incorrect answer entered
     */
    void updateAfterIncorrectAnswer(){
        doorToBeUnlockedByCurrentQuestion.permenantlyCloseDoor();
        roomToBeEnteredByCurrentQuestion = null;

        if (!existsPathToExit()) {
            gameHasBeenLost = true;
            tellVIewThatGameIsLost();
        } else {


            sendBoardToView();
        }
    }

    /**
     * Display board on the view
     */
    void sendBoardToView() {ConsoleView.showGameScreen(rooms);}

    /**
     * Display message that game has been won onn the view
     */
    void tellViewThatGameIsWon(){ConsoleView.showFinishedGame(true);}

    /**
     * Display message that the game has been lost on the view
     */
    void tellVIewThatGameIsLost(){ConsoleView.showFinishedGame(false);}

    /**
     * Returns number of columns on board
     *
     * @return boardSize
     */
    int getColCount() {return boardSize;}

    /**
     * Returns number of rows on board
     *
     * @return boardSize
     */
    int getRowCount() {return boardSize;}

    /**
     * Returns current row
     *
     * @return currentRow
     */
    int getCurRow() {return currentRow;}

    /**
     * Returns current column
     *
     * @return currentColumn
     */
    int getCurCol(){return currentColumn;}

    /**
     * Returns array of room on the board
     *
     * @return rooms
     */
    Room[][] getRooms(){return rooms;}



}
