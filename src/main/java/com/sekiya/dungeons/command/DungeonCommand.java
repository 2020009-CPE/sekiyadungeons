package com.sekiya.dungeons.command;

import com.sekiya.dungeons.command.subcommands.*;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.portal.PortalManager;
import com.sekiya.dungeons.shard.ShardManager;
import com.sekiya.dungeons.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Main dungeon command handler
 */
public class DungeonCommand {
    private final Map<String, SubCommand> subCommands;
    
    public DungeonCommand(ConfigManager configManager, DungeonManager dungeonManager,
                         PortalManager portalManager, ShardManager shardManager) {
        this.subCommands = new HashMap<>();
        
        // Register all subcommands
        registerSubCommand(new CreateSubCommand(configManager));
        registerSubCommand(new DeleteSubCommand(configManager, dungeonManager));
        registerSubCommand(new ListSubCommand(configManager));
        registerSubCommand(new InfoSubCommand(configManager));
        registerSubCommand(new ReloadSubCommand(configManager, portalManager));
        registerSubCommand(new StartSubCommand(dungeonManager, configManager));
        registerSubCommand(new StopSubCommand(dungeonManager));
        registerSubCommand(new GiveShardSubCommand(shardManager, configManager));
        registerSubCommand(new SetPortalSubCommand(configManager));
        registerSubCommand(new SetEntrySubCommand(configManager));
        registerSubCommand(new SetExitSubCommand(configManager));
        registerSubCommand(new RoomSubCommand(configManager));
        registerSubCommand(new SpawnSubCommand(configManager));
        registerSubCommand(new BossSubCommand(configManager));
        registerSubCommand(new GenerateSubCommand(configManager)); // NEW
    }
    
    /**
     * Registers a subcommand
     */
    private void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }
    
    /**
     * Executes the command
     */
    public boolean onCommand(Object sender, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        String subCommandName = args[0].toLowerCase();
        SubCommand subCommand = subCommands.get(subCommandName);
        
        if (subCommand == null) {
            MessageUtil.sendMessage(sender, "&cUnknown subcommand: " + subCommandName);
            sendHelp(sender);
            return true;
        }
        
        // Check permission
        if (!hasPermission(sender, "sekiyadungeons.command." + subCommandName)) {
            MessageUtil.sendMessage(sender, "&cYou don't have permission to use this command!");
            return true;
        }
        
        // Execute subcommand
        String[] subArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subArgs, 0, subArgs.length);
        
        if (!subCommand.execute(sender, subArgs)) {
            MessageUtil.sendMessage(sender, "&cUsage: /dungeon " + subCommand.getUsage());
        }
        
        return true;
    }
    
    /**
     * Sends help message
     */
    private void sendHelp(Object sender) {
        MessageUtil.sendMessage(sender, "&8&m----------------------------");
        MessageUtil.sendMessage(sender, "&6&lSekiya Dungeons Commands");
        MessageUtil.sendMessage(sender, "&8&m----------------------------");
        
        for (SubCommand cmd : subCommands.values()) {
            MessageUtil.sendMessage(sender, 
                String.format("&e/dungeon %s &7- &f%s", cmd.getUsage(), cmd.getDescription()));
        }
        
        MessageUtil.sendMessage(sender, "&8&m----------------------------");
    }
    
    /**
     * Checks if sender has permission
     * Placeholder for actual Hytale permission system
     */
    private boolean hasPermission(Object sender, String permission) {
        // Placeholder: Use Hytale's permission API
        return true; // For now, allow all
    }
}
