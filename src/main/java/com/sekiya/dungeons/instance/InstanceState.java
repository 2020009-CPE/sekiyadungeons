package com.sekiya.dungeons.instance;

/**
 * Represents the state of a dungeon instance
 */
public enum InstanceState {
    /**
     * Instance is waiting for players
     */
    WAITING,
    
    /**
     * Instance is active with players inside
     */
    ACTIVE,
    
    /**
     * Instance is in progress (rooms being cleared)
     */
    IN_PROGRESS,
    
    /**
     * Boss fight is active
     */
    BOSS_FIGHT,
    
    /**
     * Instance is completing (countdown active)
     */
    COMPLETING,
    
    /**
     * Instance is resetting
     */
    RESETTING,
    
    /**
     * Instance is closed
     */
    CLOSED
}
