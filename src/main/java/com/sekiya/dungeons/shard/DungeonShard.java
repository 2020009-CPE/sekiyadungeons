package com.sekiya.dungeons.shard;

/**
 * Represents a dungeon shard item for access
 */
public class DungeonShard {
    private final String dungeonName;
    private final ShardType type;
    private final int tier;
    
    public DungeonShard(String dungeonName, ShardType type, int tier) {
        this.dungeonName = dungeonName;
        this.type = type;
        this.tier = tier;
    }
    
    public DungeonShard(String dungeonName, ShardType type) {
        this(dungeonName, type, 1);
    }
    
    public String getDungeonName() {
        return dungeonName;
    }
    
    public ShardType getType() {
        return type;
    }
    
    public int getTier() {
        return tier;
    }
    
    public boolean isConsumable() {
        return type == ShardType.CONSUMABLE;
    }
    
    public boolean isReusable() {
        return type == ShardType.REUSABLE;
    }
    
    public String getItemId() {
        return dungeonName + "_shard";
    }
    
    public String getDisplayName() {
        return "Shard of " + dungeonName.replace("_", " ");
    }
    
    @Override
    public String toString() {
        return String.format("DungeonShard{dungeon=%s, type=%s, tier=%d}", 
            dungeonName, type, tier);
    }
}
