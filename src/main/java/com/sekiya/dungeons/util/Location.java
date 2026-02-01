package com.sekiya.dungeons.util;

/**
 * Represents a 3D location in the world
 */
public class Location {
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    
    public Location(String world, double x, double y, double z) {
        this(world, x, y, z, 0f, 0f);
    }
    
    public Location(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public String getWorld() {
        return world;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getZ() {
        return z;
    }
    
    public float getYaw() {
        return yaw;
    }
    
    public float getPitch() {
        return pitch;
    }
    
    public int getBlockX() {
        return (int) Math.floor(x);
    }
    
    public int getBlockY() {
        return (int) Math.floor(y);
    }
    
    public int getBlockZ() {
        return (int) Math.floor(z);
    }
    
    public double distance(Location other) {
        if (!this.world.equals(other.world)) {
            return Double.MAX_VALUE;
        }
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        double dz = this.z - other.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    @Override
    public String toString() {
        return String.format("Location{world=%s, x=%.2f, y=%.2f, z=%.2f}", world, x, y, z);
    }
}
