package com.example.mazegamerefactored;

import java.io.Serializable;

/**
 * Room represents a room on the board that contains up to 4 doors
 */
public class Room implements Serializable {
    private Boolean doorsAssigned;
    private Boolean isExitRoom = false;
    Boolean isCurrentRoom = false;
    private Door northDoor;
    private Room northRoom;
    private Door eastDoor;
    private Room eastRoom;
    private Door southDoor;
    private Room southRoom;
    private Door westDoor;
    private Room westRoom;

    private int rowNumber;
    private int columnNumber;

    public Room(){
        doorsAssigned = false;
    }

    public Boolean getIsCurrentRoom() {
        return isCurrentRoom;
    }

    public void setIsCurrentRoom(Boolean currentRoom) {
        isCurrentRoom = currentRoom;
    }

    public Door getNorthDoor() {
        return northDoor;
    }

    public Room getNorthRoom() {
        return northRoom;
    }

    public Door getEastDoor() {
        return eastDoor;
    }

    public Room getEastRoom() {
        return eastRoom;
    }

    public Door getSouthDoor() {
        return southDoor;
    }

    public Room getSouthRoom() {
        return southRoom;
    }

    public Door getWestDoor() {
        return westDoor;
    }

    public Room getWestRoom() {
        return westRoom;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }




    public void setNorthRoom(Room theNorthRoom) {
        northRoom = theNorthRoom;
    }

    public void setNorthDoor(Door theNorthDoor) {
        northDoor = theNorthDoor;
    }

    public void setEastRoom(Room theNorthRoom) {
        eastRoom = theNorthRoom;
    }

    public void setEastDoor(Door theNorthDoor) {
        eastDoor = theNorthDoor;
    }

    public void setSouthRoom(Room theNorthRoom) {
        southRoom = theNorthRoom;
    }

    public void setSouthDoor(Door theNorthDoor) {
        southDoor = theNorthDoor;
    }

    public void setWestRoom(Room theNorthRoom) {
        westRoom = theNorthRoom;
    }

    public void setWestDoor(Door theNorthDoor) {
        westDoor = theNorthDoor;
    }

    public void setToExitRoom() {
        isExitRoom = true;
    }

    public Boolean getIsExitRoom(){return isExitRoom;}

    public void setRowNumber(int rowNumber) {this.rowNumber = rowNumber;}

    public void setColumnNumber(int columnNumber) {this.columnNumber = columnNumber; }


}
