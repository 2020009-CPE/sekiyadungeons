package com.sekiya.dungeons.boss;

import com.sekiya.dungeons.config.BossRoomConfig;
import com.sekiya.dungeons.room.DungeonRoom;
import com.sekiya.dungeons.room.RoomState;
import com.sekiya.dungeons.util.BlockRegion;
import com.sekiya.dungeons.util.Location;

/**
 * Represents the boss room in a dungeon
 */
public class BossRoom extends DungeonRoom {
    private final String bossType;
    private final Location bossSpawnPoint;
    private final boolean spawnOnEntry;
    private String bossEntityUuid;
    private boolean bossDefeated;
    
    public BossRoom(BossRoomConfig config) {
        super(createRoomConfig(config));
        this.bossType = config.getBossType();
        this.bossSpawnPoint = config.getBossSpawnPoint();
        this.spawnOnEntry = config.isSpawnOnEntry();
        this.bossDefeated = false;
    }
    
    /**
     * Helper to create a RoomConfig from BossRoomConfig
     */
    private static com.sekiya.dungeons.config.RoomConfig createRoomConfig(BossRoomConfig config) {
        com.sekiya.dungeons.config.RoomConfig roomConfig = new com.sekiya.dungeons.config.RoomConfig();
        roomConfig.setId(config.getId());
        roomConfig.setOrder(999); // Boss room is always last
        roomConfig.setBounds(config.getBounds());
        return roomConfig;
    }
    
    public String getBossType() {
        return bossType;
    }
    
    public Location getBossSpawnPoint() {
        return bossSpawnPoint;
    }
    
    public boolean shouldSpawnOnEntry() {
        return spawnOnEntry;
    }
    
    public String getBossEntityUuid() {
        return bossEntityUuid;
    }
    
    public void setBossEntityUuid(String bossEntityUuid) {
        this.bossEntityUuid = bossEntityUuid;
    }
    
    public boolean isBossDefeated() {
        return bossDefeated;
    }
    
    public void setBossDefeated(boolean defeated) {
        this.bossDefeated = defeated;
        if (defeated) {
            this.setState(RoomState.CLEARED);
        }
    }
    
    @Override
    public void reset() {
        super.reset();
        this.bossEntityUuid = null;
        this.bossDefeated = false;
    }
    
    @Override
    public String toString() {
        return String.format("BossRoom{id=%s, bossType=%s, defeated=%s}", 
            getId(), bossType, bossDefeated);
    }
}
