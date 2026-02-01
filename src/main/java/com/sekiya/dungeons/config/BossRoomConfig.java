package com.sekiya.dungeons.config;

import com.sekiya.dungeons.util.Location;
import java.util.Map;

/**
 * Configuration for a boss room
 */
public class BossRoomConfig {
    private String id;
    private Map<String, Location> bounds;
    private String bossType;
    private Location bossSpawnPoint;
    private boolean spawnOnEntry;
    
    public BossRoomConfig() {}
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Map<String, Location> getBounds() { return bounds; }
    public void setBounds(Map<String, Location> bounds) { this.bounds = bounds; }
    
    public Location getMinBounds() { return bounds != null ? bounds.get("min") : null; }
    public Location getMaxBounds() { return bounds != null ? bounds.get("max") : null; }
    
    public String getBossType() { return bossType; }
    public void setBossType(String bossType) { this.bossType = bossType; }
    
    public Location getBossSpawnPoint() { return bossSpawnPoint; }
    public void setBossSpawnPoint(Location bossSpawnPoint) { this.bossSpawnPoint = bossSpawnPoint; }
    
    public boolean isSpawnOnEntry() { return spawnOnEntry; }
    public void setSpawnOnEntry(boolean spawnOnEntry) { this.spawnOnEntry = spawnOnEntry; }
}
