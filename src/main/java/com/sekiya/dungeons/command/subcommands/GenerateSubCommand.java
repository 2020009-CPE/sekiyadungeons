package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.generator.DungeonGenerator;
import com.sekiya.dungeons.generator.GenerationConfig;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon generate <name> [difficulty] [seed] - Generates a random dungeon
 */
public class GenerateSubCommand implements SubCommand {
    private final ConfigManager configManager;
    private final DungeonGenerator generator;
    
    public GenerateSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
        this.generator = new DungeonGenerator();
    }
    
    @Override
    public String getName() {
        return "generate";
    }
    
    @Override
    public String getUsage() {
        return "generate <name> [difficulty] [seed]";
    }
    
    @Override
    public String getDescription() {
        return "Generate a random dungeon";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String dungeonName = args[0];
        
        if (configManager.hasDungeonTemplate(dungeonName)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cDungeon already exists: " + dungeonName));
            return true;
        }
        
        // Parse difficulty
        String difficulty = args.length >= 2 ? args[1].toUpperCase() : "NORMAL";
        if (!isValidDifficulty(difficulty)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cInvalid difficulty! Use: EASY, NORMAL, HARD, or NIGHTMARE"));
            return true;
        }
        
        // Parse seed
        long seed = 0;
        if (args.length >= 3) {
            try {
                seed = Long.parseLong(args[2]);
            } catch (NumberFormatException e) {
                // Use string hash as seed
                seed = args[2].hashCode();
            }
        }
        
        // Create generation config
        GenerationConfig genConfig = new GenerationConfig();
        genConfig.setDifficulty(difficulty);
        genConfig.setSeed(seed);
        
        // Adjust generation parameters based on difficulty
        adjustGenerationConfig(genConfig, difficulty);
        
        // Generate dungeon
        DungeonTemplate template = generator.generate(dungeonName, genConfig);
        configManager.saveDungeonTemplate(template);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aGenerated dungeon: " + dungeonName));
        MessageUtil.sendMessage(sender, String.format("&7Difficulty: &f%s &7| Seed: &f%d", difficulty, seed));
        MessageUtil.sendMessage(sender, String.format("&7Rooms: &f%d &7| Has Boss: &f%s", 
            template.getRooms().size(), template.getBossRoom() != null ? "Yes" : "No"));
        MessageUtil.sendMessage(sender, "&7Use /dungeon setportal " + dungeonName + " to configure the portal location");
        
        return true;
    }
    
    private boolean isValidDifficulty(String difficulty) {
        return difficulty.equals("EASY") || difficulty.equals("NORMAL") || 
               difficulty.equals("HARD") || difficulty.equals("NIGHTMARE");
    }
    
    private void adjustGenerationConfig(GenerationConfig config, String difficulty) {
        switch (difficulty) {
            case "EASY":
                config.setMinRooms(2);
                config.setMaxRooms(4);
                config.setMinEnemiesPerRoom(2);
                config.setMaxEnemiesPerRoom(5);
                break;
            case "HARD":
                config.setMinRooms(4);
                config.setMaxRooms(7);
                config.setMinEnemiesPerRoom(5);
                config.setMaxEnemiesPerRoom(10);
                break;
            case "NIGHTMARE":
                config.setMinRooms(5);
                config.setMaxRooms(8);
                config.setMinEnemiesPerRoom(7);
                config.setMaxEnemiesPerRoom(12);
                break;
            case "NORMAL":
            default:
                // Use defaults
                break;
        }
    }
}
