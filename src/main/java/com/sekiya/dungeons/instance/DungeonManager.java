package com.sekiya.dungeons.instance;

import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all dungeon instances
 */
public class DungeonManager {
    private final ConfigManager configManager;
    private final Map<String, DungeonInstance> instances;
    private final Map<String, String> playerToInstance;
    private int instanceCounter;
    
    public DungeonManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.instances = new ConcurrentHashMap<>();
        this.playerToInstance = new ConcurrentHashMap<>();
        this.instanceCounter = 0;
    }
    
    /**
     * Creates a new dungeon instance
     */
    public DungeonInstance createInstance(String dungeonName) {
        DungeonTemplate template = configManager.getDungeonTemplate(dungeonName);
        if (template == null) {
            return null;
        }
        
        String instanceId = generateInstanceId(dungeonName);
        DungeonInstance instance = new DungeonInstance(instanceId, template);
        instances.put(instanceId, instance);
        
        return instance;
    }
    
    /**
     * Gets an instance by ID
     */
    public DungeonInstance getInstance(String instanceId) {
        return instances.get(instanceId);
    }
    
    /**
     * Gets an active instance for a dungeon, or creates a new one
     */
    public DungeonInstance getOrCreateInstance(String dungeonName) {
        // Look for an existing non-full instance
        for (DungeonInstance instance : instances.values()) {
            if (instance.getDungeonName().equals(dungeonName) && 
                !instance.isFull() && 
                instance.getState() == InstanceState.WAITING) {
                return instance;
            }
        }
        
        // Create new instance
        return createInstance(dungeonName);
    }
    
    /**
     * Gets the instance a player is in
     */
    public DungeonInstance getPlayerInstance(String playerName) {
        String instanceId = playerToInstance.get(playerName);
        return instanceId != null ? instances.get(instanceId) : null;
    }
    
    /**
     * Adds a player to an instance
     */
    public boolean addPlayerToInstance(String playerName, DungeonInstance instance) {
        if (instance.isFull()) {
            return false;
        }
        
        instance.addPlayer(playerName);
        playerToInstance.put(playerName, instance.getInstanceId());
        return true;
    }
    
    /**
     * Removes a player from their instance
     */
    public void removePlayerFromInstance(String playerName) {
        String instanceId = playerToInstance.remove(playerName);
        if (instanceId != null) {
            DungeonInstance instance = instances.get(instanceId);
            if (instance != null) {
                instance.removePlayer(playerName);
                
                // Close instance if empty
                if (instance.getPlayerCount() == 0) {
                    closeInstance(instanceId);
                }
            }
        }
    }
    
    /**
     * Closes and removes an instance
     */
    public void closeInstance(String instanceId) {
        DungeonInstance instance = instances.remove(instanceId);
        if (instance != null) {
            instance.close();
            
            // Remove all players
            for (String player : instance.getPlayers()) {
                playerToInstance.remove(player);
            }
        }
    }
    
    /**
     * Gets all active instances
     */
    public Collection<DungeonInstance> getAllInstances() {
        return new ArrayList<>(instances.values());
    }
    
    /**
     * Gets all instances for a specific dungeon
     */
    public List<DungeonInstance> getInstancesForDungeon(String dungeonName) {
        List<DungeonInstance> result = new ArrayList<>();
        for (DungeonInstance instance : instances.values()) {
            if (instance.getDungeonName().equals(dungeonName)) {
                result.add(instance);
            }
        }
        return result;
    }
    
    /**
     * Generates a unique instance ID
     */
    private String generateInstanceId(String dungeonName) {
        return dungeonName + "_" + (++instanceCounter);
    }
    
    /**
     * Clears all instances
     */
    public void clearAll() {
        instances.clear();
        playerToInstance.clear();
        instanceCounter = 0;
    }
}
