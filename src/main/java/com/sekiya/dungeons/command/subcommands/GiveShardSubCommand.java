package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.shard.ShardManager;
import com.sekiya.dungeons.shard.ShardType;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * /dungeon give shard <player> <dungeonName> [amount] - Gives dungeon shards to a player
 */
public class GiveShardSubCommand implements SubCommand {
    private final ShardManager shardManager;
    private final ConfigManager configManager;
    
    public GiveShardSubCommand(ShardManager shardManager, ConfigManager configManager) {
        this.shardManager = shardManager;
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "give";
    }
    
    @Override
    public String getUsage() {
        return "give shard <player> <dungeonName> [amount]";
    }
    
    @Override
    public String getDescription() {
        return "Give dungeon shards to a player";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 3 || !args[0].equalsIgnoreCase("shard")) {
            return false;
        }
        
        String playerName = args[1];
        String dungeonName = args[2];
        int amount = args.length >= 4 ? Integer.parseInt(args[3]) : 1;
        
        if (!configManager.hasDungeonTemplate(dungeonName)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cDungeon not found: " + dungeonName));
            return true;
        }
        
        // Get or create player object (placeholder)
        Object player = getPlayer(playerName);
        if (player == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cPlayer not found: " + playerName));
            return true;
        }
        
        // Give shard
        if (shardManager.giveShard(player, dungeonName, amount)) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix(
                String.format("&aGave %d x %s shard to %s", amount, dungeonName, playerName)));
        } else {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cFailed to give shard!"));
        }
        
        return true;
    }
    
    /**
     * Gets player object (placeholder)
     */
    private Object getPlayer(String name) {
        // Placeholder: Use Hytale's player lookup API
        return name; // Return the name as placeholder
    }
}
