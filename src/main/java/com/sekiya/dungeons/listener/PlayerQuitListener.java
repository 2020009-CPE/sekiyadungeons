package com.sekiya.dungeons.listener;

import com.sekiya.dungeons.instance.DungeonManager;

/**
 * Listens for player quit/disconnect events
 * Note: This is a placeholder. Actual implementation depends on Hytale's event system
 */
public class PlayerQuitListener {
    private final DungeonManager dungeonManager;
    
    public PlayerQuitListener(DungeonManager dungeonManager) {
        this.dungeonManager = dungeonManager;
    }
    
    /**
     * Handles player quit events
     * This would be registered as a Hytale event listener
     */
    public void onPlayerQuit(Object player) {
        String playerName = getPlayerName(player);
        
        // Remove player from any active dungeon instance
        dungeonManager.removePlayerFromInstance(playerName);
    }
    
    /**
     * Gets player name (placeholder)
     */
    private String getPlayerName(Object player) {
        // Placeholder: Use Hytale's player API
        return "Player";
    }
}
