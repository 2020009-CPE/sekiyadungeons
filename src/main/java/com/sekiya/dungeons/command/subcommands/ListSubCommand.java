package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.util.MessageUtil;

import java.util.Map;

/**
 * /dungeon list - Lists all dungeons
 */
public class ListSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public ListSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "list";
    }
    
    @Override
    public String getUsage() {
        return "list";
    }
    
    @Override
    public String getDescription() {
        return "List all dungeon templates";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        Map<String, DungeonTemplate> dungeons = configManager.getAllDungeonTemplates();
        
        if (dungeons.isEmpty()) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cNo dungeons configured"));
            return true;
        }
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        MessageUtil.sendMessage(sender, "&6&lDungeon Templates");
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        
        for (DungeonTemplate template : dungeons.values()) {
            MessageUtil.sendMessage(sender, String.format("&e%s &7- &f%s", 
                template.getName(), template.getDisplayName()));
            MessageUtil.sendMessage(sender, String.format("  &7Players: &f%d-%d &7| Rooms: &f%d", 
                template.getMinPlayers(), template.getMaxPlayers(), 
                template.getRooms() != null ? template.getRooms().size() : 0));
        }
        
        MessageUtil.sendMessage(sender, "&8&m-----------------------");
        
        return true;
    }
}
