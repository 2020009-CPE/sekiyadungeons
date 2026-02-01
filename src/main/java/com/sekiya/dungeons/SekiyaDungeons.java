package com.sekiya.dungeons;

// Note: These imports require the Hytale API JAR to be in the libs/ folder
// Uncomment when the Hytale API is available:
// import com.hypixel.hytale.server.core.plugin.JavaPlugin;
// import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main plugin class for SekiyaDungeons
 * 
 * IMPORTANT: This class is designed to work with the Hytale Plugin API
 * Package: com.hypixel.hytale.server.core.plugin.JavaPlugin
 * 
 * To use the actual Hytale API:
 * 1. Add HytaleServer.jar or Hytale API JAR to libs/ folder
 * 2. Uncomment the extends clause below
 * 3. Uncomment the constructor
 * 4. Replace method signatures to match Hytale's lifecycle
 * 
 * Current structure follows the template from:
 * https://github.com/realBritakee/hytale-template-plugin
 * 
 * @author Sekiya
 * @version 1.0.0
 */
public class SekiyaDungeons /* extends JavaPlugin */ {
    
    private static SekiyaDungeons instance;
    private Logger logger;
    
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
     * Uncomment this when Hytale API is available
     */
    /*
    public SekiyaDungeons(@Nonnull JavaPluginInit init) {
        super(init);
        instance = this;
        this.logger = getLogger();
        logger.at(Level.INFO).log("SekiyaDungeons plugin loaded!");
    }
    */
    
    /**
     * Temporary constructor for standalone testing
     * Remove this when using actual Hytale API
     */
    public SekiyaDungeons() {
        instance = this;
        this.logger = Logger.getLogger("SekiyaDungeons");
    }
    
    /**
     * Get plugin instance
     */
    public static SekiyaDungeons getInstance() {
        return instance;
    }
    
    /**
     * Get logger
     */
    public Logger getLogger() {
        return logger;
    }
    
    /**
     * Called when plugin is set up (Hytale lifecycle)
     * In Hytale API, this is setup() method
     * 
     * Use this for:
     * - Loading configuration
     * - Registering event listeners  
     * - Registering commands
     * - Starting services
     */
    // @Override // Uncomment when extending JavaPlugin
    protected void setup() {
        logger.log(Level.INFO, "SekiyaDungeons setup starting...");
        
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
        
        logger.log(Level.INFO, "SekiyaDungeons setup complete!");
    }
    
    /**
     * Called when plugin is enabled (Hytale lifecycle)
     * In Hytale API, this is start() method
     */
    // @Override // Uncomment when extending JavaPlugin
    protected void start() {
        logger.log(Level.INFO, "╔═══════════════════════════════════════╗");
        logger.log(Level.INFO, "║   SekiyaDungeons v1.0.0               ║");
        logger.log(Level.INFO, "║   Dungeon system active!              ║");
        logger.log(Level.INFO, "╚═══════════════════════════════════════╝");
        logger.log(Level.INFO, "Loaded " + configManager.getAllDungeonTemplates().size() + " dungeon templates");
        logger.log(Level.INFO, "Features: Party System, Auto-Generation, Wand Tool, HUD");
    }
    
    /**
     * Called when plugin is disabled (Hytale lifecycle)
     * In Hytale API, this is shutdown() method
     */
    // @Override // Uncomment when extending JavaPlugin
    public void shutdown() {
        logger.log(Level.INFO, "SekiyaDungeons shutting down...");
        
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
        
        logger.log(Level.INFO, "SekiyaDungeons disabled");
    }
    
    /**
     * Temporary methods for standalone testing
     * Remove these when using actual Hytale API
     */
    public void onEnable() {
        setup();
        start();
    }
    
    public void onDisable() {
        shutdown();
    }
    
    /**
     * Initializes all managers
     */
    private void initializeManagers(Path dataFolder) {
        logger.log(Level.INFO, "Initializing managers...");
        this.configManager = new ConfigManager(dataFolder);
        this.dungeonManager = new DungeonManager(configManager);
        this.portalManager = new PortalManager();
        this.shardManager = new ShardManager();
        this.partyManager = new PartyManager();
        this.wandManager = new WandManager(configManager);
        this.hudManager = new HUDManager();
        this.resetter = new DungeonResetter();
        this.completionHandler = new CompletionHandler(dungeonManager, portalManager, resetter);
        logger.log(Level.INFO, "Managers initialized");
    }
    
    /**
     * Registers event listeners
     * 
     * When Hytale API is available, use proper event registration:
     * eventManager.registerListener(listener);
     */
    private void registerListeners() {
        logger.log(Level.INFO, "Registering event listeners...");
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
        
        logger.log(Level.INFO, "Event listeners registered");
    }
    
    /**
     * Registers commands
     * 
     * When Hytale API is available, use proper command registration:
     * commandManager.registerCommand("dungeon", dungeonCommand);
     */
    private void registerCommands() {
        logger.log(Level.INFO, "Registering commands...");
        this.dungeonCommand = new DungeonCommand(configManager, dungeonManager, portalManager, shardManager);
        this.partyCommand = new PartyCommand(partyManager);
        
        // TODO: When Hytale API is available, register these properly
        // Example from actual API:
        // commandManager.registerCommand("dungeon", dungeonCommand);
        // commandManager.registerCommand("party", partyCommand);
        
        logger.log(Level.INFO, "Commands registered: /dungeon, /party");
    }
    
    /**
     * Initializes the public API for other plugins
     */
    private void initializeAPI() {
        this.api = new DungeonAPI(dungeonManager, configManager);
        logger.log(Level.INFO, "Public API initialized");
    }
    
    /**
     * Loads all configurations and sets up dungeons
     */
    private void loadConfigurations() {
        logger.log(Level.INFO, "Loading configurations...");
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
        
        logger.log(Level.INFO, "Configurations loaded");
    }
    
    /**
     * Gets the data folder for the plugin
     * 
     * When using actual Hytale API, this would be:
     * return getPluginDataFolder(); // or similar method provided by JavaPlugin
     */
    private Path getDataFolder() {
        // TODO: When Hytale API is available, use:
        // return getPluginDataFolder();
        
        // Temporary placeholder for testing
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
    
    /**
     * Main method for standalone testing
     * Remove this when deploying as actual Hytale plugin
     */
    public static void main(String[] args) {
        System.out.println("=== SekiyaDungeons Standalone Test ===\n");
        
        SekiyaDungeons plugin = new SekiyaDungeons();
        plugin.onEnable();
        
        System.out.println("\n--- Plugin is running ---");
        System.out.println("In actual Hytale server, the plugin would run until server shutdown");
        System.out.println("Press Ctrl+C to stop (or wait for automatic shutdown)\n");
        
        // Simulate runtime
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        plugin.onDisable();
        
        System.out.println("\n=== Test Complete ===");
    }
}
