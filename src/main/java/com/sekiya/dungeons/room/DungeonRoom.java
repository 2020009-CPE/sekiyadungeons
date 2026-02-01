package com.sekiya.dungeons.room;

import com.sekiya.dungeons.config.RoomConfig;
import com.sekiya.dungeons.enemy.SpawnPoint;
import com.sekiya.dungeons.room.RoomState;
import com.sekiya.dungeons.util.BlockRegion;
import com.sekiya.dungeons.util.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in a dungeon instance
 */
public class DungeonRoom {
    private final String id;
    private final int order;
    private final BlockRegion bounds;
    private final RoomDoor door;
    private final List<SpawnPoint> spawnPoints;
    private RoomState state;
    
    public DungeonRoom(RoomConfig config) {
        this.id = config.getId();
        this.order = config.getOrder();
        this.bounds = new BlockRegion(config.getMinBounds(), config.getMaxBounds());
        this.state = RoomState.LOCKED;
        this.spawnPoints = new ArrayList<>();
        
        // Initialize spawn points
        if (config.getSpawnPoints() != null) {
            for (var spConfig : config.getSpawnPoints()) {
                spawnPoints.add(new SpawnPoint(
                    spConfig.getId(),
                    spConfig.getLocation(),
                    spConfig.getEnemyType(),
                    spConfig.getCount()
                ));
            }
        }
        
        // Initialize door
        if (config.getDoor() != null) {
            this.door = new RoomDoor(
                config.getDoor().getLocation(),
                config.getDoor().getType(),
                config.getDoor().getWidth(),
                config.getDoor().getHeight()
            );
        } else {
            this.door = null;
        }
    }
    
    public String getId() {
        return id;
    }
    
    public int getOrder() {
        return order;
    }
    
    public BlockRegion getBounds() {
        return bounds;
    }
    
    public RoomDoor getDoor() {
        return door;
    }
    
    public List<SpawnPoint> getSpawnPoints() {
        return spawnPoints;
    }
    
    public RoomState getState() {
        return state;
    }
    
    public void setState(RoomState state) {
        this.state = state;
    }
    
    public boolean isPlayerInRoom(Location playerLocation) {
        return bounds.contains(playerLocation);
    }
    
    public void unlock() {
        this.state = RoomState.UNLOCKED;
    }
    
    public void activate() {
        this.state = RoomState.ACTIVE;
    }
    
    public void clear() {
        this.state = RoomState.CLEARED;
        if (door != null) {
            door.open();
        }
    }
    
    public void reset() {
        this.state = RoomState.LOCKED;
        if (door != null) {
            door.close();
        }
    }
    
    public boolean isLocked() {
        return state == RoomState.LOCKED;
    }
    
    public boolean isCleared() {
        return state == RoomState.CLEARED;
    }
    
    @Override
    public String toString() {
        return String.format("DungeonRoom{id=%s, order=%d, state=%s}", id, order, state);
    }
}
