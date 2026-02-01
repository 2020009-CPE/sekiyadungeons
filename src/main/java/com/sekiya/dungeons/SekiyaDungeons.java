package com.sekiya.dungeons;

import com.sekiya.dungeons.api.DungeonAPI;
import com.sekiya.dungeons.command.DungeonCommand;
import com.sekiya.dungeons.command.party.PartyCommand;
import com.sekiya.dungeons.completion.CompletionHandler;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.listener.*;
import com.sekiya.dungeons.party.PartyManager;
import com.sekiya.dungeons.portal.PortalManager;
import com.sekiya.dungeons.reset.DungeonResetter;
import com.sekiya.dungeons.shard.ShardManager;
import com.sekiya.dungeons.shard.ShardType;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main plugin class for SekiyaDungeons
 * 
 * This is a placeholder implementation as the actual Hytale plugin API is not yet available.
 * The structure follows typical Minecraft-style plugin patterns and will need to be adapted
 * to Hytale's actual plugin system when it's released.
 */
public class SekiyaDungeons {
    
    // Managers
    private ConfigManager configManager;
    private DungeonManager dungeonManager;
    private PortalManager portalManager;
    private ShardManager shardManager;
    private PartyManager partyManager;
    private DungeonResetter resetter;
    private CompletionHandler completionHandler;
    
    // Listeners
    private PortalInteractListener portalInteractListener;
    private EntityDeathListener entityDeathListener;
    private PlayerMoveListener playerMoveListener;
    private PlayerQuitListener playerQuitListener;
    
    // Commands
    private DungeonCommand dungeonCommand;
    private PartyCommand partyCommand;
    
    // API
    private DungeonAPI api;
    
    /**
     * Plugin initialization
     * In actual Hytale plugin, this would be called by the plugin loader
     */
    public void onEnable() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║   SekiyaDungeons v1.0.0               ║");
        System.out.println("║   Initializing dungeon system...      ║");
        System.out.println("╚═══════════════════════════════════════╝");
        
        // Initialize data folder
        Path dataFolder = getDataFolder();
        
        // Initialize managers
        initializeManagers(dataFolder);
        
        // Register listeners
        registerListeners();
        
        // Register commands
        registerCommands();
        
        // Initialize API
        initializeAPI();
        
        // Load configurations
        loadConfigurations();
        
        System.out.println("[SekiyaDungeons] Plugin enabled successfully!");
        System.out.println("[SekiyaDungeons] Loaded " + configManager.getAllDungeonTemplates().size() + " dungeon templates");
    }
    
    /**
     * Plugin shutdown
     */
    public void onDisable() {
        System.out.println("[SekiyaDungeons] Shutting down...");
        
        // Close all active instances
        if (dungeonManager != null) {
            for (var instance : dungeonManager.getAllInstances()) {
                dungeonManager.closeInstance(instance.getInstanceId());
            }
        }
        
        // Save configurations
        if (configManager != null) {
            configManager.savePluginConfig();
        }
        
        System.out.println("[SekiyaDungeons] Plugin disabled");
    }
    
    /**
     * Initializes all managers
     */
    private void initializeManagers(Path dataFolder) {
        this.configManager = new ConfigManager(dataFolder);
        this.dungeonManager = new DungeonManager(configManager);
        this.portalManager = new PortalManager();
        this.shardManager = new ShardManager();
        this.partyManager = new PartyManager();
        this.resetter = new DungeonResetter();
        this.completionHandler = new CompletionHandler(dungeonManager, portalManager, resetter);
    }
    
    /**
     * Registers event listeners
     */
    private void registerListeners() {
        this.portalInteractListener = new PortalInteractListener(portalManager, shardManager, dungeonManager);
        this.entityDeathListener = new EntityDeathListener(dungeonManager, completionHandler);
        this.playerMoveListener = new PlayerMoveListener(dungeonManager);
        this.playerQuitListener = new PlayerQuitListener(dungeonManager);
        
        // In actual Hytale plugin, register these with the event system
        // Example: eventManager.registerListener(portalInteractListener);
        System.out.println("[SekiyaDungeons] Registered event listeners");
    }
    
    /**
     * Registers commands
     */
    private void registerCommands() {
        this.dungeonCommand = new DungeonCommand(configManager, dungeonManager, portalManager, shardManager);
        this.partyCommand = new PartyCommand(partyManager);
        
        // In actual Hytale plugin, register with command manager
        // Example: commandManager.registerCommand("dungeon", dungeonCommand);
        // Example: commandManager.registerCommand("party", partyCommand);
        System.out.println("[SekiyaDungeons] Registered commands");
    }
    
    /**
     * Initializes the public API
     */
    private void initializeAPI() {
        this.api = new DungeonAPI(dungeonManager, configManager);
        System.out.println("[SekiyaDungeons] API initialized");
    }
    
    /**
     * Loads all configurations
     */
    private void loadConfigurations() {
        configManager.reload();
        
        // Register portals for all dungeons
        for (var template : configManager.getAllDungeonTemplates().values()) {
            portalManager.registerPortal(template);
            
            // Register shard for dungeon
            String shardType = template.getShardType();
            if (shardType != null) {
                ShardType type = template.isShardConsumed() ? ShardType.CONSUMABLE : ShardType.REUSABLE;
                shardManager.registerShard(template.getName(), type, 1);
            }
        }
        
        System.out.println("[SekiyaDungeons] Loaded configurations");
    }
    
    /**
     * Gets the data folder for the plugin
     * In actual implementation, this would be provided by Hytale's plugin system
     */
    private Path getDataFolder() {
        // Placeholder: In actual implementation, use plugin data directory
        return Paths.get("plugins", "SekiyaDungeons");
    }
    
    /**
     * Gets the dungeon API for other plugins
     */
    public DungeonAPI getAPI() {
        return api;
    }
    
    /**
     * Gets the dungeon manager
     */
    public DungeonManager getDungeonManager() {
        return dungeonManager;
    }
    
    /**
     * Gets the config manager
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    /**
     * Gets the portal manager
     */
    public PortalManager getPortalManager() {
        return portalManager;
    }
    
    /**
     * Gets the shard manager
     */
    public ShardManager getShardManager() {
        return shardManager;
    }
    
    /**
     * Gets the party manager
     */
    public PartyManager getPartyManager() {
        return partyManager;
    }
    
    /**
     * Main method for standalone testing
     */
    public static void main(String[] args) {
        SekiyaDungeons plugin = new SekiyaDungeons();
        plugin.onEnable();
        
        // Simulate some operations
        System.out.println("\n--- Running test operations ---");
        
        // Plugin would normally run here until server shutdown
        
        plugin.onDisable();
    }
}
