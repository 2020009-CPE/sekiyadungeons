package com.sekiya.dungeons.config;

import com.sekiya.dungeons.util.Location;

/**
 * Configuration for a single spawn point
 */
public class SpawnPointConfig {
    private String id;
    private Location location;
    private String enemyType;
    private int count;
    
    public SpawnPointConfig() {}
    
    public SpawnPointConfig(String id, Location location, String enemyType, int count) {
        this.id = id;
        this.location = location;
        this.enemyType = enemyType;
        this.count = count;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    
    public String getEnemyType() { return enemyType; }
    public void setEnemyType(String enemyType) { this.enemyType = enemyType; }
    
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}
