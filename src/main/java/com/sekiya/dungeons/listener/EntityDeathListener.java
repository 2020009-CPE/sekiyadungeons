package com.sekiya.dungeons.listener;

import com.sekiya.dungeons.boss.BossManager;
import com.sekiya.dungeons.boss.BossRoom;
import com.sekiya.dungeons.completion.CompletionHandler;
import com.sekiya.dungeons.enemy.EnemyManager;
import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.room.RoomManager;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * Listens for entity death events
 * Note: This is a placeholder. Actual implementation depends on Hytale's event system
 */
public class EntityDeathListener {
    private final DungeonManager dungeonManager;
    private final CompletionHandler completionHandler;
    
    public EntityDeathListener(DungeonManager dungeonManager, CompletionHandler completionHandler) {
        this.dungeonManager = dungeonManager;
        this.completionHandler = completionHandler;
    }
    
    /**
     * Handles entity death events
     * This would be registered as a Hytale event listener
     */
    public void onEntityDeath(String entityUuid, Object entity) {
        // Check all instances for this entity
        for (DungeonInstance instance : dungeonManager.getAllInstances()) {
            if (!instance.isActive()) {
                continue;
            }
            
            EnemyManager enemyManager = instance.getEnemyManager();
            RoomManager roomManager = instance.getRoomManager();
            BossManager bossManager = instance.getBossManager();
            BossRoom bossRoom = instance.getBossRoom();
            
            // Check if it's a boss
            if (bossRoom != null && bossManager.isBoss(bossRoom, entityUuid)) {
                handleBossDeath(instance, bossRoom, entityUuid);
                return;
            }
            
            // Check if it's a room enemy
            String roomId = enemyManager.getTracker().getRoomForEnemy(entityUuid);
            if (roomId != null) {
                handleRoomEnemyDeath(instance, roomManager, enemyManager, roomId, entityUuid);
                return;
            }
        }
    }
    
    /**
     * Handles boss death
     */
    private void handleBossDeath(DungeonInstance instance, BossRoom bossRoom, String entityUuid) {
        instance.getBossManager().onBossDeath(bossRoom, entityUuid);
        
        // Broadcast boss defeat
        String message = MessageUtil.formatWithPrefix("&6&lBOSS DEFEATED! The dungeon is complete!");
        for (String playerName : instance.getPlayers()) {
            MessageUtil.sendMessage(playerName, message);
        }
        
        // Fire boss defeat event
        // new BossDefeatEvent(instance.getInstanceId(), bossRoom, entityUuid);
        
        // Trigger completion
        completionHandler.handleCompletion(instance);
    }
    
    /**
     * Handles regular enemy death in a room
     */
    private void handleRoomEnemyDeath(DungeonInstance instance, RoomManager roomManager, 
                                     EnemyManager enemyManager, String roomId, String entityUuid) {
        enemyManager.onEnemyDeath(entityUuid);
        
        // Check if room is cleared
        if (enemyManager.getTracker().isRoomCleared(roomId)) {
            roomManager.clearRoom(roomId);
            
            // Broadcast room cleared
            String message = MessageUtil.formatWithPrefix("&aRoom cleared! The door has opened.");
            for (String playerName : instance.getPlayers()) {
                MessageUtil.sendMessage(playerName, message);
            }
            
            // Fire room clear event
            // new RoomClearEvent(instance.getInstanceId(), roomManager.getRoom(roomId));
            
            // Check if all rooms are cleared (boss room unlocked)
            if (roomManager.areAllRoomsCleared() && instance.getBossRoom() != null) {
                String bossMessage = MessageUtil.formatWithPrefix("&c&lAll rooms cleared! The boss awaits...");
                for (String playerName : instance.getPlayers()) {
                    MessageUtil.sendMessage(playerName, bossMessage);
                }
                
                instance.getBossRoom().unlock();
            }
        }
    }
}
