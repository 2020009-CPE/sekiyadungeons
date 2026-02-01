package com.sekiya.dungeons.config;

import com.sekiya.dungeons.util.Location;
import java.util.Map;

/**
 * Configuration for a door in a room
 */
public class DoorConfig {
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
    
    public void setWidth(int width) {
        if (size == null) {
            size = new java.util.HashMap<>();
        }
        size.put("width", width);
    }
    
    public void setHeight(int height) {
        if (size == null) {
            size = new java.util.HashMap<>();
        }
        size.put("height", height);
    }
}
