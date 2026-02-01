package com.sekiya.dungeons.command;

import com.hypixel.hytale.server.command.CommandBase;
import com.hypixel.hytale.server.command.CommandContext;
import com.hypixel.hytale.server.entity.Player;
import com.hypixel.hytale.server.util.Message;

import com.sekiya.dungeons.command.subcommands.*;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.portal.PortalManager;
import com.sekiya.dungeons.shard.ShardManager;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Main dungeon command handler extending Hytale's CommandBase
 */
public class DungeonCommand extends CommandBase {
    private final Map<String, SubCommand> subCommands;
    private final ConfigManager configManager;
    private final DungeonManager dungeonManager;
    private final PortalManager portalManager;
    private final ShardManager shardManager;
    
    public DungeonCommand(ConfigManager configManager, DungeonManager dungeonManager,
                          PortalManager portalManager, ShardManager shardManager) {
        super("dungeon", "Main dungeon management command", true); // true = requires OP
        
        this.configManager = configManager;
        this.dungeonManager = dungeonManager;
        this.portalManager = portalManager;
        this.shardManager = shardManager;
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
        registerSubCommand(new GenerateSubCommand(configManager));
    }
    
    /**
     * Registers a subcommand
     */
    private void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName().toLowerCase(), subCommand);
    }
    
    /**
     * Executes the command using Hytale's CommandContext
     */
    @Override
    protected void executeSync(@Nonnull CommandContext ctx) {
        // Check if sender is a player (required for most dungeon commands)
        if (!(ctx.getSender() instanceof Player)) {
            ctx.getSender().sendMessage(Message.raw("&cThis command can only be used by players!"));
            return;
        }
        
        Player player = ctx.senderAs(Player.class);
        String[] args = ctx.getArgs();
        
        // Execute in world context for thread safety
        player.getWorld().execute(() -> {
            if (args.length == 0) {
                sendHelp(player);
                return;
            }
            
            String subCommandName = args[0].toLowerCase();
            SubCommand subCommand = subCommands.get(subCommandName);
            
            if (subCommand == null) {
                player.sendMessage(Message.raw("&cUnknown subcommand: " + subCommandName));
                sendHelp(player);
                return;
            }
            
            // Execute subcommand
            String[] subArgs = new String[args.length - 1];
            System.arraycopy(args, 1, subArgs, 0, subArgs.length);
            
            if (!subCommand.execute(player, subArgs)) {
                player.sendMessage(Message.raw("&cUsage: /dungeon " + subCommand.getUsage()));
            }
        });
    }
    
    /**
     * Sends help message to player
     */
    private void sendHelp(Player player) {
        player.sendMessage(Message.raw("&8&m----------------------------"));
        player.sendMessage(Message.raw("&6&lSekiya Dungeons Commands"));
        player.sendMessage(Message.raw("&8&m----------------------------"));
        
        for (SubCommand cmd : subCommands.values()) {
            player.sendMessage(Message.raw(
                String.format("&e/dungeon %s &7- &f%s", cmd.getUsage(), cmd.getDescription())));
        }
        
        player.sendMessage(Message.raw("&8&m----------------------------"));
    }
}
