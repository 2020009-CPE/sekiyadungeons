package com.sekiya.dungeons.api;

import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.instance.DungeonManager;

import java.util.Collection;
import java.util.Map;

/**
 * Public API for other plugins to interact with SekiyaDungeons
 */
public class DungeonAPI {
    private final DungeonManager dungeonManager;
    private final ConfigManager configManager;
    
    public DungeonAPI(DungeonManager dungeonManager, ConfigManager configManager) {
        this.dungeonManager = dungeonManager;
        this.configManager = configManager;
    }
    
    /**
     * Gets a dungeon template by name
     */
    public DungeonTemplate getDungeonTemplate(String name) {
        return configManager.getDungeonTemplate(name);
    }
    
    /**
     * Gets all registered dungeon templates
     */
    public Map<String, DungeonTemplate> getAllDungeonTemplates() {
        return configManager.getAllDungeonTemplates();
    }
    
    /**
     * Gets a dungeon instance by ID
     */
    public DungeonInstance getDungeonInstance(String instanceId) {
        return dungeonManager.getInstance(instanceId);
    }
    
    /**
     * Gets the instance a player is currently in
     */
    public DungeonInstance getPlayerInstance(String playerName) {
        return dungeonManager.getPlayerInstance(playerName);
    }
    
    /**
     * Gets all active dungeon instances
     */
    public Collection<DungeonInstance> getAllInstances() {
        return dungeonManager.getAllInstances();
    }
    
    /**
     * Checks if a player is in a dungeon
     */
    public boolean isPlayerInDungeon(String playerName) {
        return dungeonManager.getPlayerInstance(playerName) != null;
    }
    
    /**
     * Creates a new dungeon instance
     */
    public DungeonInstance createInstance(String dungeonName) {
        return dungeonManager.createInstance(dungeonName);
    }
    
    /**
     * Checks if a dungeon exists
     */
    public boolean dungeonExists(String dungeonName) {
        return configManager.hasDungeonTemplate(dungeonName);
    }
}
