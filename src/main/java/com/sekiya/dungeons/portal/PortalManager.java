package com.sekiya.dungeons.portal;

import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.util.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages dungeon portals
 */
public class PortalManager {
    private final Map<String, DungeonPortal> portals;
    private final Map<Location, DungeonPortal> portalsByLocation;
    
    public PortalManager() {
        this.portals = new HashMap<>();
        this.portalsByLocation = new HashMap<>();
    }
    
    /**
     * Registers a portal for a dungeon
     */
    public void registerPortal(DungeonTemplate template) {
        if (template.getPortalLocation() == null) {
            return;
        }
        
        DungeonPortal portal = new DungeonPortal(template.getName(), template.getPortalLocation());
        portals.put(template.getName(), portal);
        portalsByLocation.put(template.getPortalLocation(), portal);
    }
    
    /**
     * Gets a portal by dungeon name
     */
    public DungeonPortal getPortal(String dungeonName) {
        return portals.get(dungeonName);
    }
    
    /**
     * Gets a portal at a location
     */
    public DungeonPortal getPortalAtLocation(Location location) {
        // Check exact location first
        DungeonPortal exact = portalsByLocation.get(location);
        if (exact != null) {
            return exact;
        }
        
        // Check nearby locations (within 3 blocks)
        for (Map.Entry<Location, DungeonPortal> entry : portalsByLocation.entrySet()) {
            if (entry.getKey().distance(location) <= 3.0) {
                return entry.getValue();
            }
        }
        
        return null;
    }
    
    /**
     * Activates a portal
     */
    public boolean activatePortal(String dungeonName) {
        DungeonPortal portal = portals.get(dungeonName);
        if (portal != null && portal.isInactive()) {
            portal.activate();
            return true;
        }
        return false;
    }
    
    /**
     * Deactivates a portal
     */
    public void deactivatePortal(String dungeonName) {
        DungeonPortal portal = portals.get(dungeonName);
        if (portal != null) {
            portal.deactivate();
        }
    }
    
    /**
     * Sets a portal to closing state
     */
    public void closePortal(String dungeonName) {
        DungeonPortal portal = portals.get(dungeonName);
        if (portal != null) {
            portal.close();
        }
    }
    
    /**
     * Removes a portal
     */
    public void removePortal(String dungeonName) {
        DungeonPortal portal = portals.remove(dungeonName);
        if (portal != null) {
            portalsByLocation.remove(portal.getLocation());
        }
    }
    
    /**
     * Clears all portals
     */
    public void clearAll() {
        portals.clear();
        portalsByLocation.clear();
    }
}
