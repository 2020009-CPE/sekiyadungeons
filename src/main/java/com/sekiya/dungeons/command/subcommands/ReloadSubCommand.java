package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.portal.PortalManager;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon reload - Reloads configurations
 */
public class ReloadSubCommand implements SubCommand {
    private final ConfigManager configManager;
    private final PortalManager portalManager;
    
    public ReloadSubCommand(ConfigManager configManager, PortalManager portalManager) {
        this.configManager = configManager;
        this.portalManager = portalManager;
    }
    
    @Override
    public String getName() {
        return "reload";
    }
    
    @Override
    public String getUsage() {
        return "reload";
    }
    
    @Override
    public String getDescription() {
        return "Reload plugin configuration";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        try {
            configManager.reload();
            
            // Re-register portals
            portalManager.clearAll();
            for (var template : configManager.getAllDungeonTemplates().values()) {
                portalManager.registerPortal(template);
            }
            
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aConfiguration reloaded successfully!"));
        } catch (Exception e) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cError reloading configuration: " + e.getMessage()));
            e.printStackTrace();
        }
        
        return true;
    }
}
