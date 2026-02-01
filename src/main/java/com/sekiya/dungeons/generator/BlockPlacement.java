package com.sekiya.dungeons.generator;

import com.sekiya.dungeons.util.Location;

/**
 * Represents a block placement in the world
 */
public class BlockPlacement {
    private final Location location;
    private final BlockType blockType;
    private final String metadata; // For additional block data (facing, state, etc.)
    
    public BlockPlacement(Location location, BlockType blockType) {
        this(location, blockType, null);
    }
    
    public BlockPlacement(Location location, BlockType blockType, String metadata) {
        this.location = location;
        this.blockType = blockType;
        this.metadata = metadata;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public BlockType getBlockType() {
        return blockType;
    }
    
    public String getMetadata() {
        return metadata;
    }
    
    @Override
    public String toString() {
        return "BlockPlacement{" +
                "location=" + location +
                ", blockType=" + blockType +
                ", metadata='" + metadata + '\'' +
                '}';
    }
}
