package com.example.mazegamerefactored;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.Serializable;


/**
 * Represents a button that is associated with each MazeItem (room) within the game
 *

 */
public class MazeButton extends Canvas implements Serializable {
    private final int myCol;
    private final int myRow;

    /**
     * Constructs a MazeButton based on column and row
     * @param theCol
     * @param theRow
     */
    public MazeButton(int theRow, int theCol) {
        myCol = theCol;
        myRow = theRow;
        setHeight(100);
        setWidth(100);
    }

    /**
     * Set style of MazeButton, adding doors if necessary
     */
    public void setStyle(Room theRoom) {
        var theGfx = getGraphicsContext2D();

        // Fill main box
        theGfx.setFill(Color.BLUE);
        theGfx.fillRect(10, 10, 90, 90);

        // Draw current cell as middle circle
        if (theRoom.getIsCurrentRoom()) {
            theGfx.setFill(Color.CORAL);
            theGfx.fillOval(32.5, 32.5, 45, 45);
        }

        // Set door colors based on Door: Open, Closed or Locked
        if (theRoom.getRowNumber() > 0 && !theRoom.getNorthDoor().getPermenantlyClosed()) {
            Color north = theRoom.getNorthDoor().getOpen() ? Color.BLUE : Color.GREY;
            theGfx.setFill(north);
            theGfx.fillRect(45, 0, 20, 20);
        }
        if (theRoom.getColumnNumber() < 3 && !theRoom.getEastDoor().getPermenantlyClosed()) {
            Color east = theRoom.getEastDoor().getOpen() ? Color.BLUE : Color.GREY;
            theGfx.setFill(east);
            theGfx.fillRect(90, 45, 20, 20);
        }
        if (theRoom.getRowNumber() < 3 && !theRoom.getSouthDoor().getPermenantlyClosed()) {
            Color south = theRoom.getSouthDoor().getOpen() ? Color.BLUE : Color.GREY;
            theGfx.setFill(south);
            theGfx.fillRect(45, 90, 20, 20);
        }
        if (theRoom.getColumnNumber() > 0 && !theRoom.getWestDoor().getPermenantlyClosed()) {
            Color west = theRoom.getWestDoor().getOpen() ? Color.BLUE : Color.GREY;
            theGfx.setFill(west);
            theGfx.fillRect(0, 45, 20, 20);
        }
    }

    /**
     * Get Column position of door
     * @return myCol
     */
    public int getCol() {
        return myCol;
    }

    /**
     * Get Row position of door
     * @return myRow
     */
    public int getRow() {
        return myRow;
    }
}