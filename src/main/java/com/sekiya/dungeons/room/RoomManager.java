package com.sekiya.dungeons.room;

import com.sekiya.dungeons.config.RoomConfig;
import com.sekiya.dungeons.enemy.EnemyManager;

import java.util.*;

/**
 * Manages rooms in dungeon instances
 */
public class RoomManager {
    private final Map<String, DungeonRoom> rooms;
    private final EnemyManager enemyManager;
    
    public RoomManager(EnemyManager enemyManager) {
        this.rooms = new LinkedHashMap<>();
        this.enemyManager = enemyManager;
    }
    
    /**
     * Initializes rooms from configuration
     */
    public void initializeRooms(List<RoomConfig> roomConfigs) {
        rooms.clear();
        
        // Sort by order
        roomConfigs.sort(Comparator.comparingInt(RoomConfig::getOrder));
        
        for (RoomConfig config : roomConfigs) {
            DungeonRoom room = new DungeonRoom(config);
            rooms.put(room.getId(), room);
        }
        
        // Unlock first room
        if (!rooms.isEmpty()) {
            rooms.values().iterator().next().unlock();
        }
    }
    
    /**
     * Gets a room by ID
     */
    public DungeonRoom getRoom(String roomId) {
        return rooms.get(roomId);
    }
    
    /**
     * Gets all rooms in order
     */
    public List<DungeonRoom> getRooms() {
        return new ArrayList<>(rooms.values());
    }
    
    /**
     * Activates a room (spawns enemies)
     */
    public void activateRoom(String roomId) {
        DungeonRoom room = rooms.get(roomId);
        if (room != null && room.getState() == RoomState.UNLOCKED) {
            room.activate();
            
            // Spawn enemies
            for (var spawnPoint : room.getSpawnPoints()) {
                enemyManager.spawnEnemies(roomId, spawnPoint);
            }
        }
    }
    
    /**
     * Marks a room as cleared and opens the door
     */
    public void clearRoom(String roomId) {
        DungeonRoom room = rooms.get(roomId);
        if (room != null) {
            room.clear();
            
            // Unlock next room
            unlockNextRoom(roomId);
        }
    }
    
    /**
     * Unlocks the next room in sequence
     */
    private void unlockNextRoom(String currentRoomId) {
        List<DungeonRoom> roomList = getRooms();
        
        for (int i = 0; i < roomList.size() - 1; i++) {
            if (roomList.get(i).getId().equals(currentRoomId)) {
                DungeonRoom nextRoom = roomList.get(i + 1);
                if (nextRoom.isLocked()) {
                    nextRoom.unlock();
                }
                break;
            }
        }
    }
    
    /**
     * Checks if all rooms are cleared
     */
    public boolean areAllRoomsCleared() {
        return rooms.values().stream().allMatch(DungeonRoom::isCleared);
    }
    
    /**
     * Resets all rooms to initial state
     */
    public void resetAllRooms() {
        for (DungeonRoom room : rooms.values()) {
            room.reset();
            enemyManager.clearRoomEnemies(room.getId());
        }
        
        // Unlock first room again
        if (!rooms.isEmpty()) {
            rooms.values().iterator().next().unlock();
        }
    }
    
    /**
     * Gets the current active room (first non-cleared room)
     */
    public DungeonRoom getCurrentRoom() {
        for (DungeonRoom room : rooms.values()) {
            if (!room.isCleared()) {
                return room;
            }
        }
        return null;
    }
    
    /**
     * Gets the current room index (0-based)
     */
    public int getCurrentRoomIndex() {
        List<DungeonRoom> roomList = getRooms();
        for (int i = 0; i < roomList.size(); i++) {
            if (!roomList.get(i).isCleared()) {
                return i;
            }
        }
        return roomList.size() - 1;
    }
    
    /**
     * Gets the number of completed rooms
     */
    public int getCompletedRoomCount() {
        return (int) rooms.values().stream()
            .filter(DungeonRoom::isCleared)
            .count();
    }
}
