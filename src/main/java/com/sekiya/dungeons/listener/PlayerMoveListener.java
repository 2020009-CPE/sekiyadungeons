package com.sekiya.dungeons.listener;

import com.sekiya.dungeons.boss.BossRoom;
import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.room.DungeonRoom;
import com.sekiya.dungeons.room.RoomState;
import com.sekiya.dungeons.util.Location;

/**
 * Listens for player movement to trigger room activation
 * Note: This is a placeholder. Actual implementation depends on Hytale's event system
 */
public class PlayerMoveListener {
    private final DungeonManager dungeonManager;
    
    public PlayerMoveListener(DungeonManager dungeonManager) {
        this.dungeonManager = dungeonManager;
    }
    
    /**
     * Handles player movement
     * This would be registered as a Hytale event listener
     */
    public void onPlayerMove(Object player, Location from, Location to) {
        String playerName = getPlayerName(player);
        DungeonInstance instance = dungeonManager.getPlayerInstance(playerName);
        
        if (instance == null || !instance.isActive()) {
            return;
        }
        
        // Check if player entered a new room
        for (DungeonRoom room : instance.getRoomManager().getRooms()) {
            if (room.isPlayerInRoom(to) && !room.isPlayerInRoom(from)) {
                onPlayerEnterRoom(instance, room);
            }
        }
        
        // Check boss room
        BossRoom bossRoom = instance.getBossRoom();
        if (bossRoom != null && bossRoom.isPlayerInRoom(to) && !bossRoom.isPlayerInRoom(from)) {
            onPlayerEnterBossRoom(instance, bossRoom);
        }
    }
    
    /**
     * Handles player entering a room
     */
    private void onPlayerEnterRoom(DungeonInstance instance, DungeonRoom room) {
        if (room.getState() == RoomState.UNLOCKED) {
            // Activate room (spawn enemies)
            instance.getRoomManager().activateRoom(room.getId());
        }
    }
    
    /**
     * Handles player entering boss room
     */
    private void onPlayerEnterBossRoom(DungeonInstance instance, BossRoom bossRoom) {
        if (bossRoom.getState() == RoomState.UNLOCKED && !bossRoom.isBossDefeated()) {
            if (bossRoom.shouldSpawnOnEntry() && bossRoom.getBossEntityUuid() == null) {
                // Spawn boss
                instance.getBossManager().spawnBoss(bossRoom);
                
                // Broadcast boss spawn message
                com.sekiya.dungeons.util.MessageUtil.broadcastToDungeon(
                    instance.getPlayers(), 
                    "&c&lBOSS FIGHT! Defeat the guardian!"
                );
            }
        }
    }
    
    /**
     * Gets player name (placeholder)
     */
    private String getPlayerName(Object player) {
        // Placeholder: Use Hytale's player API
        return "Player";
    }
}
