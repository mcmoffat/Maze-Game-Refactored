package com.example.mazegamerefactored;

import java.io.Serializable;

/**
 * Door represents a door between two room on a game board
 */
public class Door implements Serializable {
    private Boolean isOpen;
    private Boolean isPermenantlyClosed;

    /**
     * Constructs a Door object
     */
    public Door() {
        isOpen = false;
        isPermenantlyClosed = false;
    }

    /**
     * Set door to open
     */
    public void openDoor() {
        isOpen = true;
    }

    /**
     * Permanently closes door
     */
    public void permenantlyCloseDoor() {
        isOpen = false;
        isPermenantlyClosed = true;
    }

    /**
     * Returns whether door is open
     *
     * @return isOpen
     */
    public Boolean getOpen() {
        return isOpen;
    }

    /**
     * Returns whether door is permanently closed
     *
     * @return isPemenantlyClosed
     */
    public Boolean getPermenantlyClosed() {
        return isPermenantlyClosed;
    }
}
