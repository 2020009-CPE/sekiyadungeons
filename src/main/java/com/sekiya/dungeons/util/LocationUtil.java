package com.sekiya.dungeons.util;

/**
 * Utility class for working with locations
 */
public class LocationUtil {
    
    /**
     * Checks if a location is within a bounded region
     */
    public static boolean isInRegion(Location loc, Location min, Location max) {
        if (!loc.getWorld().equals(min.getWorld())) {
            return false;
        }
        
        return loc.getX() >= min.getX() && loc.getX() <= max.getX() &&
               loc.getY() >= min.getY() && loc.getY() <= max.getY() &&
               loc.getZ() >= min.getZ() && loc.getZ() <= max.getZ();
    }
    
    /**
     * Gets the center location between two points
     */
    public static Location getCenter(Location loc1, Location loc2) {
        double x = (loc1.getX() + loc2.getX()) / 2;
        double y = (loc1.getY() + loc2.getY()) / 2;
        double z = (loc1.getZ() + loc2.getZ()) / 2;
        return new Location(loc1.getWorld(), x, y, z);
    }
    
    /**
     * Serializes a location to a string
     */
    public static String serialize(Location loc) {
        return String.format("%s,%.2f,%.2f,%.2f,%.2f,%.2f",
            loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), 
            loc.getYaw(), loc.getPitch());
    }
    
    /**
     * Deserializes a location from a string
     */
    public static Location deserialize(String str) {
        String[] parts = str.split(",");
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid location string: " + str);
        }
        
        String world = parts[0];
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        
        if (parts.length >= 6) {
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);
            return new Location(world, x, y, z, yaw, pitch);
        }
        
        return new Location(world, x, y, z);
    }
}
