package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon setexit <name> - Sets the exit point for a dungeon
 */
public class SetExitSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public SetExitSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "setexit";
    }
    
    @Override
    public String getUsage() {
        return "setexit <name>";
    }
    
    @Override
    public String getDescription() {
        return "Set dungeon exit point (at your position)";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String dungeonName = args[0];
        DungeonTemplate template = configManager.getDungeonTemplate(dungeonName);
        
        if (template == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cDungeon not found: " + dungeonName));
            return true;
        }
        
        // Get player location (placeholder)
        Location location = getPlayerLocation(sender);
        template.setExitPoint(location);
        
        configManager.saveDungeonTemplate(template);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aSet exit point for " + dungeonName));
        
        return true;
    }
    
    /**
     * Gets player location (placeholder)
     */
    private Location getPlayerLocation(Object sender) {
        // Placeholder: Use Hytale's player location API
        return new Location("world", 0, 64, 0);
    }
}
