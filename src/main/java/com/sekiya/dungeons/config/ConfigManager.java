package com.sekiya.dungeons.config;

import com.sekiya.dungeons.storage.DungeonStorage;
import com.sekiya.dungeons.storage.JsonDungeonStorage;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages configuration loading and caching
 */
public class ConfigManager {
    private final DungeonStorage storage;
    private PluginConfig pluginConfig;
    private final Map<String, DungeonTemplate> dungeonTemplates;
    
    public ConfigManager(Path dataFolder) {
        this.storage = new JsonDungeonStorage(dataFolder);
        this.dungeonTemplates = new HashMap<>();
        load();
    }
    
    /**
     * Loads all configurations
     */
    public void load() {
        this.pluginConfig = storage.loadConfig();
        
        dungeonTemplates.clear();
        List<DungeonTemplate> templates = storage.loadAllDungeons();
        for (DungeonTemplate template : templates) {
            dungeonTemplates.put(template.getName(), template);
        }
    }
    
    /**
     * Reloads all configurations
     */
    public void reload() {
        load();
    }
    
    /**
     * Gets the plugin configuration
     */
    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }
    
    /**
     * Saves the plugin configuration
     */
    public void savePluginConfig() {
        storage.saveConfig(pluginConfig);
    }
    
    /**
     * Gets a dungeon template by name
     */
    public DungeonTemplate getDungeonTemplate(String name) {
        return dungeonTemplates.get(name);
    }
    
    /**
     * Gets all dungeon templates
     */
    public Map<String, DungeonTemplate> getAllDungeonTemplates() {
        return new HashMap<>(dungeonTemplates);
    }
    
    /**
     * Saves a dungeon template
     */
    public void saveDungeonTemplate(DungeonTemplate template) {
        dungeonTemplates.put(template.getName(), template);
        storage.saveDungeon(template);
    }
    
    /**
     * Deletes a dungeon template
     */
    public void deleteDungeonTemplate(String name) {
        dungeonTemplates.remove(name);
        storage.deleteDungeon(name);
    }
    
    /**
     * Checks if a dungeon template exists
     */
    public boolean hasDungeonTemplate(String name) {
        return dungeonTemplates.containsKey(name);
    }
}
