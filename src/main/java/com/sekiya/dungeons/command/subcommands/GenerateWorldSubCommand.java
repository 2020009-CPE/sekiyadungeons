package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.SekiyaDungeons;
import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.generator.DungeonGenerator;
import com.sekiya.dungeons.generator.GenerationConfig;

/**
 * /dungeon generateworld <name> [theme] [difficulty] [seed]
 * Generates a dungeon with actual world structure
 */
public class GenerateWorldSubCommand implements SubCommand {
    private final SekiyaDungeons plugin;
    
    public GenerateWorldSubCommand(SekiyaDungeons plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getName() {
        return "generateworld";
    }
    
    @Override
    public String getDescription() {
        return "Generate a dungeon with world structure";
    }
    
    @Override
    public String getUsage() {
        return "/dungeon generateworld <name> [theme] [difficulty] [seed]";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            sendMessage(sender, "§cUsage: " + getUsage());
            sendMessage(sender, "§7Themes: STONE_CRYPT, CAVE, DESERT_TOMB, ICE_CAVERN, NETHER_FORTRESS, OCEAN_TEMPLE");
            sendMessage(sender, "§7Difficulties: EASY, NORMAL, HARD, NIGHTMARE");
            return false;
        }
        
        String name = args[0];
        
        // Check if dungeon already exists
        if (plugin.getConfigManager().getDungeonTemplate(name) != null) {
            sendMessage(sender, "§cDungeon '" + name + "' already exists!");
            return false;
        }
        
        // Parse optional parameters
        String theme = args.length > 1 ? args[1].toUpperCase() : "STONE_CRYPT";
        String difficulty = args.length > 2 ? args[2].toUpperCase() : "NORMAL";
        long seed = 0;
        
        if (args.length > 3) {
            try {
                seed = Long.parseLong(args[3]);
            } catch (NumberFormatException e) {
                // Use hash of string as seed
                seed = args[3].hashCode();
            }
        }
        
        // Validate theme
        String[] validThemes = {"STONE_CRYPT", "CAVE", "DESERT_TOMB", "ICE_CAVERN", 
                               "NETHER_FORTRESS", "OCEAN_TEMPLE"};
        boolean validTheme = false;
        for (String t : validThemes) {
            if (t.equals(theme)) {
                validTheme = true;
                break;
            }
        }
        
        if (!validTheme) {
            sendMessage(sender, "§cInvalid theme! Valid themes: STONE_CRYPT, CAVE, DESERT_TOMB, ICE_CAVERN, NETHER_FORTRESS, OCEAN_TEMPLE");
            return false;
        }
        
        // Validate difficulty
        String[] validDifficulties = {"EASY", "NORMAL", "HARD", "NIGHTMARE"};
        boolean validDifficulty = false;
        for (String d : validDifficulties) {
            if (d.equals(difficulty)) {
                validDifficulty = true;
                break;
            }
        }
        
        if (!validDifficulty) {
            sendMessage(sender, "§cInvalid difficulty! Valid difficulties: EASY, NORMAL, HARD, NIGHTMARE");
            return false;
        }
        
        sendMessage(sender, "§aGenerating dungeon with world structure...");
        sendMessage(sender, "§7Theme: §f" + theme);
        sendMessage(sender, "§7Difficulty: §f" + difficulty);
        sendMessage(sender, "§7Seed: §f" + (seed == 0 ? "Random" : seed));
        
        try {
            // Create generation config
            GenerationConfig config = new GenerationConfig();
            config.setDifficulty(difficulty);
            config.setSeed(seed);
            config.setGenerateWorld(true);
            config.setTheme(theme);
            
            // Generate dungeon
            DungeonGenerator generator = new DungeonGenerator();
            DungeonTemplate template = generator.generate(name, config);
            
            // Save template
            plugin.getConfigManager().saveDungeonTemplate(template);
            
            sendMessage(sender, "§aSuccessfully generated dungeon: §e" + template.getDisplayName());
            sendMessage(sender, "§7Rooms: §f" + (template.getRooms() != null ? template.getRooms().size() : 0));
            sendMessage(sender, "§7Boss: §f" + (template.getBossRoom() != null ? "Yes" : "No"));
            sendMessage(sender, "§7World blocks generated: Yes");
            sendMessage(sender, "§7Next steps:");
            sendMessage(sender, "  §e/dungeon setportal " + name);
            sendMessage(sender, "  §e/dungeon setentry " + name);
            sendMessage(sender, "  §e/dungeon setexit " + name);
            
        } catch (Exception e) {
            sendMessage(sender, "§cError generating dungeon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    
    private void sendMessage(Object sender, String message) {
        // Placeholder - would use actual Hytale messaging API
        System.out.println("[" + sender + "] " + message);
    }
}
