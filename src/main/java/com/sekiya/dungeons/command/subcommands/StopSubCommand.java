package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon stop <instanceId> - Force stops an active instance
 */
public class StopSubCommand implements SubCommand {
    private final DungeonManager dungeonManager;
    
    public StopSubCommand(DungeonManager dungeonManager) {
        this.dungeonManager = dungeonManager;
    }
    
    @Override
    public String getName() {
        return "stop";
    }
    
    @Override
    public String getUsage() {
        return "stop <instanceId>";
    }
    
    @Override
    public String getDescription() {
        return "Force stop a dungeon instance";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 1) {
            return false;
        }
        
        String instanceId = args[0];
        DungeonInstance instance = dungeonManager.getInstance(instanceId);
        
        if (instance == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cInstance not found: " + instanceId));
            return true;
        }
        
        dungeonManager.closeInstance(instanceId);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aStopped instance: " + instanceId));
        
        return true;
    }
}
