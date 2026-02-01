package com.sekiya.dungeons;

import com.hypixel.hytale.plugin.JavaPlugin;
import com.hypixel.hytale.plugin.JavaPluginInit;
import com.hypixel.hytale.server.config.Config;

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
import com.sekiya.dungeons.wand.WandManager;
import com.sekiya.dungeons.hud.HUDManager;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Main plugin class for SekiyaDungeons
 * 
 * Implements the Hytale Plugin API with proper lifecycle methods
 * and configuration management using BuilderCodec system.
 * 
 * @author Sekiya
 * @version 1.0.0
 */
public class SekiyaDungeons extends JavaPlugin {
    
    private static SekiyaDungeons instance;
    private Config<com.sekiya.dungeons.config.PluginConfig> config;
    
    // Managers
    private ConfigManager configManager;
    private DungeonManager dungeonManager;
    private PortalManager portalManager;
    private ShardManager shardManager;
    private PartyManager partyManager;
    private WandManager wandManager;
    private HUDManager hudManager;
    private DungeonResetter resetter;
    private CompletionHandler completionHandler;
    
    // Listeners
    private PortalInteractListener portalInteractListener;
    private EntityDeathListener entityDeathListener;
    private PlayerMoveListener playerMoveListener;
    private PlayerQuitListener playerQuitListener;
    private WandInteractListener wandInteractListener;
    
    // Commands
    private DungeonCommand dungeonCommand;
    private PartyCommand partyCommand;
    
    // API
    private DungeonAPI api;
    
    /**
     * Constructor for Hytale Plugin API
     */
    public SekiyaDungeons(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
        
        // Register config with Hytale's codec system
        this.config = this.withConfig("SekiyaDungeons", com.sekiya.dungeons.config.PluginConfig.CODEC);
        
        getLogger().info("SekiyaDungeons initializing...");
    }
    
    /**
     * Get plugin instance
     */
    public static SekiyaDungeons getInstance() {
        return instance;
    }
    
    /**
     * Get plugin configuration
     */
    public com.sekiya.dungeons.config.PluginConfig getPluginConfig() {
        return config.get();
    }
    
    /**
     * Called when plugin is enabled (Hytale lifecycle)
     */
    @Override
    public void onEnable() {
        getLogger().info("SekiyaDungeons setup starting...");
        
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
        
        getLogger().info("╔═══════════════════════════════════════╗");
        getLogger().info("║   SekiyaDungeons v1.0.0               ║");
        getLogger().info("║   Dungeon system active!              ║");
        getLogger().info("╚═══════════════════════════════════════╝");
        getLogger().info("Loaded " + configManager.getAllDungeonTemplates().size() + " dungeon templates");
        getLogger().info("Features: Party System, Auto-Generation, Wand Tool, HUD");
    }
    
    /**
     * Called when plugin is disabled (Hytale lifecycle)
     */
    @Override
    public void onDisable() {
        getLogger().info("SekiyaDungeons shutting down...");
        
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
        
        getLogger().info("SekiyaDungeons disabled");
    }
    
    /**
     * Initializes all managers
     */
    private void initializeManagers(Path dataFolder) {
        getLogger().info("Initializing managers...");
        this.configManager = new ConfigManager(dataFolder);
        this.dungeonManager = new DungeonManager(configManager);
        this.portalManager = new PortalManager();
        this.shardManager = new ShardManager();
        this.partyManager = new PartyManager();
        this.wandManager = new WandManager(configManager);
        this.hudManager = new HUDManager();
        this.resetter = new DungeonResetter();
        this.completionHandler = new CompletionHandler(dungeonManager, portalManager, resetter);
        getLogger().info("Managers initialized");
    }
    
    /**
     * Registers event listeners
     */
    private void registerListeners() {
        getLogger().info("Registering event listeners...");
        this.portalInteractListener = new PortalInteractListener(portalManager, shardManager, dungeonManager);
        this.entityDeathListener = new EntityDeathListener(dungeonManager, completionHandler);
        this.playerMoveListener = new PlayerMoveListener(dungeonManager);
        this.playerQuitListener = new PlayerQuitListener(dungeonManager);
        this.wandInteractListener = new WandInteractListener(wandManager);
        
        // TODO: When Hytale API is available, register these properly
        // Example from actual API:
        // eventManager.registerListener(portalInteractListener);
        // eventManager.registerListener(entityDeathListener);
        // etc.
        
        getLogger().info("Event listeners registered");
    }
    
    /**
     * Registers commands using Hytale's command registry
     */
    private void registerCommands() {
        getLogger().info("Registering commands...");
        this.dungeonCommand = new DungeonCommand(configManager, dungeonManager, portalManager, shardManager);
        this.partyCommand = new PartyCommand(partyManager);
        
        // Register commands using Hytale's command registry
        this.getCommandRegistry().registerCommand(dungeonCommand);
        this.getCommandRegistry().registerCommand(partyCommand);
        
        getLogger().info("Commands registered: /dungeon, /party");
    }
    
    /**
     * Initializes the public API for other plugins
     */
    private void initializeAPI() {
        this.api = new DungeonAPI(dungeonManager, configManager);
        getLogger().info("Public API initialized");
    }
    
    /**
     * Loads all configurations and sets up dungeons
     */
    private void loadConfigurations() {
        getLogger().info("Loading configurations...");
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
        
        getLogger().info("Configurations loaded");
    }
    
    /**
     * Gets the data folder for the plugin
     */
    private Path getDataFolder() {
        // Use the plugin's data directory
        return Paths.get("plugins", "SekiyaDungeons");
    }
    
    // ==================== PUBLIC API GETTERS ====================
    
    /**
     * Gets the dungeon API for other plugins to use
     * @return The public DungeonAPI instance
     */
    public DungeonAPI getAPI() {
        return api;
    }
    
    /**
     * Gets the dungeon manager
     * @return The DungeonManager instance
     */
    public DungeonManager getDungeonManager() {
        return dungeonManager;
    }
    
    /**
     * Gets the config manager
     * @return The ConfigManager instance
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    /**
     * Gets the portal manager
     * @return The PortalManager instance
     */
    public PortalManager getPortalManager() {
        return portalManager;
    }
    
    /**
     * Gets the shard manager
     * @return The ShardManager instance
     */
    public ShardManager getShardManager() {
        return shardManager;
    }
    
    /**
     * Gets the party manager
     * @return The PartyManager instance
     */
    public PartyManager getPartyManager() {
        return partyManager;
    }
    
    /**
     * Gets the wand manager
     * @return The WandManager instance
     */
    public WandManager getWandManager() {
        return wandManager;
    }
    
    /**
     * Gets the HUD manager
     * @return The HUDManager instance
     */
    public HUDManager getHUDManager() {
        return hudManager;
    }
}
