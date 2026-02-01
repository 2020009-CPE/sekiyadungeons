package com.sekiya.dungeons.enemy;

import java.util.*;

/**
 * Tracks enemies spawned in dungeon rooms
 */
public class EnemyTracker {
    private final Map<String, Set<String>> roomEnemies;  // roomId -> Set of entity UUIDs
    private final Map<String, String> enemyToRoom;       // entity UUID -> roomId
    
    public EnemyTracker() {
        this.roomEnemies = new HashMap<>();
        this.enemyToRoom = new HashMap<>();
    }
    
    /**
     * Registers an enemy for a room
     */
    public void registerEnemy(String roomId, String entityUuid) {
        roomEnemies.computeIfAbsent(roomId, k -> new HashSet<>()).add(entityUuid);
        enemyToRoom.put(entityUuid, roomId);
    }
    
    /**
     * Removes an enemy when it dies
     */
    public void removeEnemy(String entityUuid) {
        String roomId = enemyToRoom.remove(entityUuid);
        if (roomId != null) {
            Set<String> enemies = roomEnemies.get(roomId);
            if (enemies != null) {
                enemies.remove(entityUuid);
            }
        }
    }
    
    /**
     * Gets remaining enemy count for a room
     */
    public int getRemainingEnemies(String roomId) {
        Set<String> enemies = roomEnemies.get(roomId);
        return enemies != null ? enemies.size() : 0;
    }
    
    /**
     * Checks if all enemies in a room are defeated
     */
    public boolean isRoomCleared(String roomId) {
        return getRemainingEnemies(roomId) == 0;
    }
    
    /**
     * Gets the room ID for an enemy
     */
    public String getRoomForEnemy(String entityUuid) {
        return enemyToRoom.get(entityUuid);
    }
    
    /**
     * Clears all enemies for a room
     */
    public void clearRoom(String roomId) {
        Set<String> enemies = roomEnemies.remove(roomId);
        if (enemies != null) {
            for (String uuid : enemies) {
                enemyToRoom.remove(uuid);
            }
        }
    }
    
    /**
     * Clears all tracked enemies
     */
    public void clearAll() {
        roomEnemies.clear();
        enemyToRoom.clear();
    }
    
    /**
     * Gets all enemy UUIDs for a room
     */
    public Set<String> getEnemiesInRoom(String roomId) {
        Set<String> enemies = roomEnemies.get(roomId);
        return enemies != null ? new HashSet<>(enemies) : new HashSet<>();
    }
}
