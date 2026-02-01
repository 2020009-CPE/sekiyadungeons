package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon create <name> - Creates a new dungeon template
 */
public class CreateSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public CreateSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "create";
    }
    
    @Override
    public String getUsage() {
        return "create <name>";
    }
    
    @Override
    public String getDescription() {
        return "Create a new dungeon template";
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
        
        // Create new template
        DungeonTemplate template = new DungeonTemplate();
        template.setName(dungeonName);
        template.setDisplayName(dungeonName.replace("_", " "));
        
        configManager.saveDungeonTemplate(template);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aCreated dungeon template: " + dungeonName));
        MessageUtil.sendMessage(sender, "&7Use /dungeon setportal to configure the portal location");
        
        return true;
    }
}
