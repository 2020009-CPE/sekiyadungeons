package com.sekiya.dungeons.enemy;

import com.sekiya.dungeons.util.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Manages enemy spawning in dungeons
 */
public class EnemyManager {
    private final EnemyTracker tracker;
    
    public EnemyManager() {
        this.tracker = new EnemyTracker();
    }
    
    /**
     * Spawns enemies at a spawn point
     * Returns list of spawned entity UUIDs
     */
    public List<String> spawnEnemies(String roomId, SpawnPoint spawnPoint) {
        List<String> spawnedUuids = new ArrayList<>();
        
        Location loc = spawnPoint.getLocation();
        String enemyType = spawnPoint.getEnemyType();
        int count = spawnPoint.getCount();
        
        // Spawn enemies using Hytale API
        // This is a placeholder - actual implementation depends on Hytale's entity spawning API
        for (int i = 0; i < count; i++) {
            String entityUuid = spawnEnemy(loc, enemyType);
            if (entityUuid != null) {
                tracker.registerEnemy(roomId, entityUuid);
                spawnedUuids.add(entityUuid);
            }
        }
        
        return spawnedUuids;
    }
    
    /**
     * Spawns a single enemy at a location
     * Placeholder for actual Hytale API implementation
     */
    private String spawnEnemy(Location location, String enemyType) {
        // Placeholder: In real implementation, use Hytale's entity spawn API
        // Example: world.spawnEntity(location, EntityType.valueOf(enemyType))
        String uuid = java.util.UUID.randomUUID().toString();
        System.out.println(String.format("Spawned %s at %s (UUID: %s)", 
            enemyType, location, uuid));
        return uuid;
    }
    
    /**
     * Gets the enemy tracker
     */
    public EnemyTracker getTracker() {
        return tracker;
    }
    
    /**
     * Handles enemy death
     */
    public void onEnemyDeath(String entityUuid) {
        String roomId = tracker.getRoomForEnemy(entityUuid);
        tracker.removeEnemy(entityUuid);
        
        if (roomId != null && tracker.isRoomCleared(roomId)) {
            System.out.println("Room " + roomId + " has been cleared!");
        }
    }
    
    /**
     * Clears all enemies in a room (used for reset)
     */
    public void clearRoomEnemies(String roomId) {
        // Get all enemy UUIDs and despawn them
        for (String uuid : tracker.getEnemiesInRoom(roomId)) {
            despawnEnemy(uuid);
        }
        tracker.clearRoom(roomId);
    }
    
    /**
     * Despawns an enemy entity
     * Placeholder for actual Hytale API implementation
     */
    private void despawnEnemy(String entityUuid) {
        // Placeholder: In real implementation, use Hytale's entity removal API
        System.out.println("Despawned entity: " + entityUuid);
    }
    
    /**
     * Clears all enemies in the dungeon
     */
    public void clearAll() {
        // Get all room IDs before clearing
        Set<String> roomIds = tracker.getAllRoomIds();
        
        // Despawn all tracked enemies
        for (String roomId : roomIds) {
            clearRoomEnemies(roomId);
        }
    }
}
