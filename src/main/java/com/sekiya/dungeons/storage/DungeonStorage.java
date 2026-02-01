package com.sekiya.dungeons.storage;

import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.config.PluginConfig;
import java.util.List;

/**
 * Interface for dungeon data storage
 */
public interface DungeonStorage {
    
    /**
     * Saves a dungeon template
     */
    void saveDungeon(DungeonTemplate template);
    
    /**
     * Loads a dungeon template by name
     */
    DungeonTemplate loadDungeon(String name);
    
    /**
     * Deletes a dungeon template
     */
    void deleteDungeon(String name);
    
    /**
     * Loads all dungeon templates
     */
    List<DungeonTemplate> loadAllDungeons();
    
    /**
     * Saves the plugin configuration
     */
    void saveConfig(PluginConfig config);
    
    /**
     * Loads the plugin configuration
     */
    PluginConfig loadConfig();
}
