package com.sekiya.dungeons.room;

import com.sekiya.dungeons.util.Location;

/**
 * Represents a door or barrier in a dungeon room
 */
public class RoomDoor {
    private final Location location;
    private final String type;
    private final int width;
    private final int height;
    private boolean isOpen;
    
    public RoomDoor(Location location, String type, int width, int height) {
        this.location = location;
        this.type = type;
        this.width = width;
        this.height = height;
        this.isOpen = false;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public String getType() {
        return type;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public boolean isOpen() {
        return isOpen;
    }
    
    public void open() {
        if (!isOpen) {
            isOpen = true;
            updateDoorState();
        }
    }
    
    public void close() {
        if (isOpen) {
            isOpen = false;
            updateDoorState();
        }
    }
    
    /**
     * Updates the physical door state in the world
     * Placeholder for actual Hytale API implementation
     */
    private void updateDoorState() {
        if ("BLOCK_BARRIER".equals(type)) {
            if (isOpen) {
                removeBarrierBlocks();
            } else {
                placeBarrierBlocks();
            }
        }
    }
    
    /**
     * Places barrier blocks to create the door
     * Placeholder for actual Hytale API implementation
     */
    private void placeBarrierBlocks() {
        // Placeholder: Use Hytale's block placement API
        System.out.println(String.format("Placing %dx%d barrier at %s", width, height, location));
    }
    
    /**
     * Removes barrier blocks to open the door
     * Placeholder for actual Hytale API implementation
     */
    private void removeBarrierBlocks() {
        // Placeholder: Use Hytale's block removal API
        System.out.println(String.format("Removing %dx%d barrier at %s", width, height, location));
    }
    
    @Override
    public String toString() {
        return String.format("RoomDoor{type=%s, size=%dx%d, open=%s}", type, width, height, isOpen);
    }
}
