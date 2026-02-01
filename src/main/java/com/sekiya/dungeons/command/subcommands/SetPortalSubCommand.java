package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon setportal <name> - Sets the portal location for a dungeon
 */
public class SetPortalSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public SetPortalSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "setportal";
    }
    
    @Override
    public String getUsage() {
        return "setportal <name>";
    }
    
    @Override
    public String getDescription() {
        return "Set portal location (at your position)";
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
        template.setPortalLocation(location);
        
        configManager.saveDungeonTemplate(template);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aSet portal location for " + dungeonName));
        
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
