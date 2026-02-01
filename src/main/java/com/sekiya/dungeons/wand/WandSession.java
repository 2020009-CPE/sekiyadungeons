package com.sekiya.dungeons.wand;

import com.sekiya.dungeons.util.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the wand state for a player
 */
public class WandSession {
    private final String playerName;
    private String dungeonName;
    private WandMode mode;
    private int currentRoom;
    private Location position1;
    private Location position2;
    private Map<String, Object> sessionData;
    private long lastAction;
    
    public WandSession(String playerName) {
        this.playerName = playerName;
        this.mode = WandMode.SET_ROOM_BOUNDS;
        this.currentRoom = 1;
        this.sessionData = new HashMap<>();
        this.lastAction = System.currentTimeMillis();
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public String getDungeonName() {
        return dungeonName;
    }
    
    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }
    
    public WandMode getMode() {
        return mode;
    }
    
    public void setMode(WandMode mode) {
        this.mode = mode;
        this.lastAction = System.currentTimeMillis();
    }
    
    public int getCurrentRoom() {
        return currentRoom;
    }
    
    public void setCurrentRoom(int currentRoom) {
        this.currentRoom = currentRoom;
    }
    
    public Location getPosition1() {
        return position1;
    }
    
    public void setPosition1(Location position1) {
        this.position1 = position1;
        this.lastAction = System.currentTimeMillis();
    }
    
    public Location getPosition2() {
        return position2;
    }
    
    public void setPosition2(Location position2) {
        this.position2 = position2;
        this.lastAction = System.currentTimeMillis();
    }
    
    public boolean hasBothPositions() {
        return position1 != null && position2 != null;
    }
    
    public void clearPositions() {
        this.position1 = null;
        this.position2 = null;
    }
    
    public Map<String, Object> getSessionData() {
        return sessionData;
    }
    
    public void setSessionData(String key, Object value) {
        sessionData.put(key, value);
    }
    
    public Object getSessionData(String key) {
        return sessionData.get(key);
    }
    
    public long getLastAction() {
        return lastAction;
    }
    
    public void updateLastAction() {
        this.lastAction = System.currentTimeMillis();
    }
    
    /**
     * Gets a summary of the current session state
     */
    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("§6§lWand Session Info\n");
        sb.append("§7Dungeon: §f").append(dungeonName != null ? dungeonName : "None").append("\n");
        sb.append("§7Mode: ").append(mode.getFormattedName()).append("\n");
        sb.append("§7Current Room: §f").append(currentRoom).append("\n");
        
        if (position1 != null) {
            sb.append("§7Position 1: §a✓ §f").append(formatLocation(position1)).append("\n");
        } else {
            sb.append("§7Position 1: §c✗\n");
        }
        
        if (position2 != null) {
            sb.append("§7Position 2: §a✓ §f").append(formatLocation(position2)).append("\n");
        } else {
            sb.append("§7Position 2: §c✗\n");
        }
        
        return sb.toString();
    }
    
    private String formatLocation(Location loc) {
        return String.format("%.0f, %.0f, %.0f", loc.getX(), loc.getY(), loc.getZ());
    }
}
