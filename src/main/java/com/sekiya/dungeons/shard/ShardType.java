package com.sekiya.dungeons.shard;

/**
 * Represents different types of dungeon shards
 */
public enum ShardType {
    /**
     * One-time use consumable shard
     */
    CONSUMABLE,
    
    /**
     * Reusable shard that isn't consumed
     */
    REUSABLE,
    
    /**
     * Tier-based shard for difficulty levels
     */
    TIERED
}
