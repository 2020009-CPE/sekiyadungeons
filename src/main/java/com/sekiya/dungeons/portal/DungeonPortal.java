package com.sekiya.dungeons.portal;

import com.sekiya.dungeons.util.Location;

/**
 * Represents a portal to a dungeon
 */
public class DungeonPortal {
    private final String dungeonName;
    private final Location location;
    private PortalState state;
    private long activationTime;
    
    public DungeonPortal(String dungeonName, Location location) {
        this.dungeonName = dungeonName;
        this.location = location;
        this.state = PortalState.INACTIVE;
        this.activationTime = 0;
    }
    
    public String getDungeonName() {
        return dungeonName;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public PortalState getState() {
        return state;
    }
    
    public void setState(PortalState state) {
        this.state = state;
        if (state == PortalState.ACTIVE) {
            this.activationTime = System.currentTimeMillis();
        }
    }
    
    public long getActivationTime() {
        return activationTime;
    }
    
    public boolean isActive() {
        return state == PortalState.ACTIVE;
    }
    
    public boolean isInactive() {
        return state == PortalState.INACTIVE;
    }
    
    public boolean isClosing() {
        return state == PortalState.CLOSING;
    }
    
    public void activate() {
        setState(PortalState.ACTIVE);
        updateVisualState();
    }
    
    public void deactivate() {
        setState(PortalState.INACTIVE);
        updateVisualState();
    }
    
    public void close() {
        setState(PortalState.CLOSING);
        updateVisualState();
    }
    
    /**
     * Updates the visual state of the portal (particles, blocks, etc.)
     * Placeholder for actual Hytale API implementation
     */
    private void updateVisualState() {
        switch (state) {
            case ACTIVE:
                spawnActiveEffects();
                break;
            case CLOSING:
                spawnClosingEffects();
                break;
            case INACTIVE:
                spawnInactiveEffects();
                break;
        }
    }
    
    /**
     * Spawns particle effects for active portal
     */
    private void spawnActiveEffects() {
        // Placeholder: Use Hytale's particle API
        System.out.println("Spawning active portal effects at " + location);
    }
    
    /**
     * Spawns particle effects for closing portal
     */
    private void spawnClosingEffects() {
        // Placeholder: Use Hytale's particle API
        System.out.println("Spawning closing portal effects at " + location);
    }
    
    /**
     * Removes effects for inactive portal
     */
    private void spawnInactiveEffects() {
        // Placeholder: Clear particles
        System.out.println("Portal inactive at " + location);
    }
    
    @Override
    public String toString() {
        return String.format("DungeonPortal{dungeon=%s, state=%s, location=%s}", 
            dungeonName, state, location);
    }
}
