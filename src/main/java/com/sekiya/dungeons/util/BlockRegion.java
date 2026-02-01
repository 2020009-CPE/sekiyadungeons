package com.sekiya.dungeons.util;

/**
 * Represents a 3D rectangular region
 */
public class BlockRegion {
    private final Location min;
    private final Location max;
    
    public BlockRegion(Location pos1, Location pos2) {
        if (!pos1.getWorld().equals(pos2.getWorld())) {
            throw new IllegalArgumentException("Positions must be in the same world");
        }
        
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());
        
        this.min = new Location(pos1.getWorld(), minX, minY, minZ);
        this.max = new Location(pos1.getWorld(), maxX, maxY, maxZ);
    }
    
    public Location getMin() {
        return min;
    }
    
    public Location getMax() {
        return max;
    }
    
    public boolean contains(Location loc) {
        return LocationUtil.isInRegion(loc, min, max);
    }
    
    public Location getCenter() {
        return LocationUtil.getCenter(min, max);
    }
    
    public int getVolume() {
        int dx = (int) (max.getX() - min.getX() + 1);
        int dy = (int) (max.getY() - min.getY() + 1);
        int dz = (int) (max.getZ() - min.getZ() + 1);
        return dx * dy * dz;
    }
    
    @Override
    public String toString() {
        return String.format("BlockRegion{min=%s, max=%s}", min, max);
    }
}
