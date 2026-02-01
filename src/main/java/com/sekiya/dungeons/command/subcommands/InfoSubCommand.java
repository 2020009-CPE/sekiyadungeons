package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon info <name> - Shows dungeon details
 */
public class InfoSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public InfoSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "info";
    }
    
    @Override
    public String getUsage() {
        return "info <name>";
    }
    
    @Override
    public String getDescription() {
        return "Show dungeon details";
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
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        MessageUtil.sendMessage(sender, "&6&lDungeon: &f" + template.getDisplayName());
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        MessageUtil.sendMessage(sender, "&7Name: &f" + template.getName());
        MessageUtil.sendMessage(sender, String.format("&7Players: &f%d-%d", 
            template.getMinPlayers(), template.getMaxPlayers()));
        MessageUtil.sendMessage(sender, "&7Time Limit: &f" + template.getTimeLimit() + "s");
        MessageUtil.sendMessage(sender, "&7Countdown: &f" + template.getCompletionCountdown() + "s");
        
        if (template.getRooms() != null) {
            MessageUtil.sendMessage(sender, "&7Rooms: &f" + template.getRooms().size());
        }
        
        if (template.getBossRoom() != null) {
            MessageUtil.sendMessage(sender, "&7Boss: &f" + template.getBossRoom().getBossType());
        }
        
        MessageUtil.sendMessage(sender, "&7Portal: &f" + 
            (template.getPortalLocation() != null ? "Configured" : "Not set"));
        MessageUtil.sendMessage(sender, "&7Entry: &f" + 
            (template.getEntryPoint() != null ? "Configured" : "Not set"));
        MessageUtil.sendMessage(sender, "&7Exit: &f" + 
            (template.getExitPoint() != null ? "Configured" : "Not set"));
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        
        return true;
    }
}
