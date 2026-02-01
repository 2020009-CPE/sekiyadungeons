package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon delete <name> - Deletes a dungeon template
 */
public class DeleteSubCommand implements SubCommand {
    private final ConfigManager configManager;
    private final DungeonManager dungeonManager;
    
    public DeleteSubCommand(ConfigManager configManager, DungeonManager dungeonManager) {
        this.configManager = configManager;
        this.dungeonManager = dungeonManager;
    }
    
    @Override
    public String getName() {
        return "delete";
    }
    
    @Override
    public String getUsage() {
        return "delete <name>";
    }
    
    @Override
    public String getDescription() {
        return "Delete a dungeon template";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String dungeonName = args[0];
        
        if (!configManager.hasDungeonTemplate(dungeonName)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cDungeon not found: " + dungeonName));
            return true;
        }
        
        // Check if there are active instances
        if (!dungeonManager.getInstancesForDungeon(dungeonName).isEmpty()) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cCannot delete dungeon with active instances!"));
            return true;
        }
        
        configManager.deleteDungeonTemplate(dungeonName);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aDeleted dungeon template: " + dungeonName));
        
        return true;
    }
}
