package com.sekiya.dungeons.listener;

import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.portal.DungeonPortal;
import com.sekiya.dungeons.portal.PortalManager;
import com.sekiya.dungeons.shard.ShardManager;
import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * Listens for portal interaction events
 * Note: This is a placeholder. Actual implementation depends on Hytale's event system
 */
public class PortalInteractListener {
    private final PortalManager portalManager;
    private final ShardManager shardManager;
    private final DungeonManager dungeonManager;
    
    public PortalInteractListener(PortalManager portalManager, ShardManager shardManager, 
                                  DungeonManager dungeonManager) {
        this.portalManager = portalManager;
        this.shardManager = shardManager;
        this.dungeonManager = dungeonManager;
    }
    
    /**
     * Handles player interaction with a portal block/entity
     * This would be registered as a Hytale event listener
     */
    public void onPortalInteract(Object player, Location location) {
        DungeonPortal portal = portalManager.getPortalAtLocation(location);
        if (portal == null) {
            return;
        }
        
        String playerName = getPlayerName(player);
        String dungeonName = portal.getDungeonName();
        
        // Check if portal is inactive
        if (portal.isInactive()) {
            // Check if player has required shard
            if (!shardManager.hasRequiredShard(player, dungeonName)) {
                MessageUtil.sendMessage(player, MessageUtil.formatWithPrefix("&cYou don't have the required shard!"));
                return;
            }
            
            // Consume shard if required
            if (shardManager.consumeShard(player, dungeonName)) {
                portal.activate();
                MessageUtil.sendMessage(player, MessageUtil.formatWithPrefix("&aPortal activated! Step through to enter."));
            }
            return;
        }
        
        // Check if portal is active
        if (portal.isActive()) {
            // Create or get instance
            DungeonInstance instance = dungeonManager.getOrCreateInstance(dungeonName);
            if (instance == null) {
                MessageUtil.sendMessage(player, MessageUtil.formatWithPrefix("&cDungeon not found!"));
                return;
            }
            
            // Check if instance is full
            if (instance.isFull()) {
                MessageUtil.sendMessage(player, MessageUtil.formatWithPrefix("&cThis dungeon instance is full!"));
                return;
            }
            
            // Add player to instance
            dungeonManager.addPlayerToInstance(playerName, instance);
            
            // Teleport player to entry point
            teleportPlayer(player, instance.getEntryPoint());
            MessageUtil.sendMessage(player, MessageUtil.formatWithPrefix("&6Entering the dungeon..."));
        }
        
        // Don't allow entry if portal is closing
        if (portal.isClosing()) {
            MessageUtil.sendMessage(player, MessageUtil.formatWithPrefix("&cThe portal is closing!"));
        }
    }
    
    /**
     * Gets player name (placeholder)
     */
    private String getPlayerName(Object player) {
        // Placeholder: Use Hytale's player API
        return "Player";
    }
    
    /**
     * Teleports a player (placeholder)
     */
    private void teleportPlayer(Object player, Location location) {
        // Placeholder: Use Hytale's teleport API
        System.out.println("Teleporting player to " + location);
    }
}
