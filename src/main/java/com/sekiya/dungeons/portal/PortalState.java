package com.sekiya.dungeons.portal;

/**
 * Represents the state of a dungeon portal
 */
public enum PortalState {
    /**
     * Portal is inactive and cannot be entered
     */
    INACTIVE,
    
    /**
     * Portal is active and players can enter
     */
    ACTIVE,
    
    /**
     * Portal is closing after dungeon completion
     */
    CLOSING
}
