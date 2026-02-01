package com.sekiya.dungeons.config;

import com.sekiya.dungeons.util.Location;
import java.util.List;
import java.util.Map;

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

/**
 * Configuration for a door in a room
 */
class DoorConfig {
    private String type;
    private Location location;
    private Map<String, Integer> size;
    
    public DoorConfig() {}
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    
    public Map<String, Integer> getSize() { return size; }
    public void setSize(Map<String, Integer> size) { this.size = size; }
    
    public int getWidth() { return size != null ? size.getOrDefault("width", 1) : 1; }
    public int getHeight() { return size != null ? size.getOrDefault("height", 3) : 3; }
}

/**
 * Configuration for a single room in a dungeon
 */
public class RoomConfig {
    private String id;
    private int order;
    private Map<String, Location> bounds;
    private DoorConfig door;
    private List<SpawnPointConfig> spawnPoints;
    
    public RoomConfig() {}
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
    
    public Map<String, Location> getBounds() { return bounds; }
    public void setBounds(Map<String, Location> bounds) { this.bounds = bounds; }
    
    public Location getMinBounds() { return bounds != null ? bounds.get("min") : null; }
    public Location getMaxBounds() { return bounds != null ? bounds.get("max") : null; }
    
    public DoorConfig getDoor() { return door; }
    public void setDoor(DoorConfig door) { this.door = door; }
    
    public List<SpawnPointConfig> getSpawnPoints() { return spawnPoints; }
    public void setSpawnPoints(List<SpawnPointConfig> spawnPoints) { this.spawnPoints = spawnPoints; }
}
