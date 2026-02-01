package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon start <name> - Manually starts a dungeon instance
 */
public class StartSubCommand implements SubCommand {
    private final DungeonManager dungeonManager;
    private final ConfigManager configManager;
    
    public StartSubCommand(DungeonManager dungeonManager, ConfigManager configManager) {
        this.dungeonManager = dungeonManager;
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "start";
    }
    
    @Override
    public String getUsage() {
        return "start <name>";
    }
    
    @Override
    public String getDescription() {
        return "Manually start a dungeon instance";
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
        
        DungeonInstance instance = dungeonManager.createInstance(dungeonName);
        if (instance == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cFailed to create instance!"));
            return true;
        }
        
        instance.start();
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aStarted dungeon instance: " + instance.getInstanceId()));
        
        return true;
    }
}
