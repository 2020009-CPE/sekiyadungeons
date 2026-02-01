package com.sekiya.dungeons.enemy;

import com.sekiya.dungeons.util.Location;

/**
 * Represents a spawn point for enemies in a dungeon room
 */
public class SpawnPoint {
    private final String id;
    private final Location location;
    private final String enemyType;
    private final int count;
    
    public SpawnPoint(String id, Location location, String enemyType, int count) {
        this.id = id;
        this.location = location;
        this.enemyType = enemyType;
        this.count = count;
    }
    
    public String getId() {
        return id;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public String getEnemyType() {
        return enemyType;
    }
    
    public int getCount() {
        return count;
    }
    
    @Override
    public String toString() {
        return String.format("SpawnPoint{id=%s, type=%s, count=%d, location=%s}", 
            id, enemyType, count, location);
    }
}
