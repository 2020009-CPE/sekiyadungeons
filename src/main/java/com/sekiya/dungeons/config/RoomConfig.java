package com.sekiya.dungeons.config;

import com.sekiya.dungeons.util.Location;
import java.util.List;
import java.util.Map;

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
    
    public void setMinBounds(Location min) {
        if (bounds == null) {
            bounds = new java.util.HashMap<>();
        }
        bounds.put("min", min);
    }
    
    public void setMaxBounds(Location max) {
        if (bounds == null) {
            bounds = new java.util.HashMap<>();
        }
        bounds.put("max", max);
    }
    
    public DoorConfig getDoor() { return door; }
    public void setDoor(DoorConfig door) { this.door = door; }
    
    public List<SpawnPointConfig> getSpawnPoints() { return spawnPoints; }
    public void setSpawnPoints(List<SpawnPointConfig> spawnPoints) { this.spawnPoints = spawnPoints; }
}
