package com.sekiya.dungeons.room;

/**
 * Represents the state of a dungeon room
 */
public enum RoomState {
    /**
     * Room is locked and not yet accessible
     */
    LOCKED,
    
    /**
     * Room is unlocked but enemies haven't spawned yet
     */
    UNLOCKED,
    
    /**
     * Room is active with enemies spawned
     */
    ACTIVE,
    
    /**
     * All enemies are defeated
     */
    CLEARED,
    
    /**
     * Room is being reset
     */
    RESETTING
}
