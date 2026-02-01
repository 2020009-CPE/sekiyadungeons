package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.config.RoomConfig;
import com.sekiya.dungeons.config.SpawnPointConfig;
import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.util.MessageUtil;

import java.util.ArrayList;

/**
 * Handles spawn point subcommands
 * /dungeon spawn add <dungeon> <roomId> <enemyType> <count>
 * /dungeon spawn remove <dungeon> <roomId> <spawnId>
 */
public class SpawnSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public SpawnSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "spawn";
    }
    
    @Override
    public String getUsage() {
        return "spawn <add|remove> <dungeon> <roomId> <enemyType|spawnId> [count]";
    }
    
    @Override
    public String getDescription() {
        return "Manage enemy spawn points";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 4) {
            return false;
        }
        
        String action = args[0];
        String dungeonName = args[1];
        String roomId = args[2];
        
        DungeonTemplate template = configManager.getDungeonTemplate(dungeonName);
        if (template == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cDungeon not found: " + dungeonName));
            return true;
        }
        
        if ("add".equalsIgnoreCase(action)) {
            return handleAdd(sender, template, roomId, args);
        } else if ("remove".equalsIgnoreCase(action)) {
            return handleRemove(sender, template, roomId, args[3]);
        }
        
        return false;
    }
    
    private boolean handleAdd(Object sender, DungeonTemplate template, String roomId, String[] args) {
        if (args.length < 5) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cUsage: spawn add <dungeon> <roomId> <enemyType> <count>"));
            return true;
        }
        
        String enemyType = args[3];
        int count = Integer.parseInt(args[4]);
        
        // Find the room
        RoomConfig room = template.getRooms().stream()
            .filter(r -> r.getId().equals(roomId))
            .findFirst()
            .orElse(null);
        
        if (room == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cRoom not found: " + roomId));
            return true;
        }
        
        if (room.getSpawnPoints() == null) {
            room.setSpawnPoints(new ArrayList<>());
        }
        
        Location location = getPlayerLocation(sender);
        String spawnId = "spawn_" + (room.getSpawnPoints().size() + 1);
        
        SpawnPointConfig spawn = new SpawnPointConfig(spawnId, location, enemyType, count);
        room.getSpawnPoints().add(spawn);
        
        configManager.saveDungeonTemplate(template);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aAdded spawn point " + spawnId + " for " + enemyType));
        
        return true;
    }
    
    private boolean handleRemove(Object sender, DungeonTemplate template, String roomId, String spawnId) {
        // Find the room
        RoomConfig room = template.getRooms().stream()
            .filter(r -> r.getId().equals(roomId))
            .findFirst()
            .orElse(null);
        
        if (room == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cRoom not found: " + roomId));
            return true;
        }
        
        if (room.getSpawnPoints() == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cNo spawn points in this room"));
            return true;
        }
        
        boolean removed = room.getSpawnPoints().removeIf(sp -> sp.getId().equals(spawnId));
        
        if (removed) {
            configManager.saveDungeonTemplate(template);
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aRemoved spawn point " + spawnId));
        } else {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cSpawn point not found: " + spawnId));
        }
        
        return true;
    }
    
    private Location getPlayerLocation(Object sender) {
        // Placeholder
        return new Location("world", 0, 64, 0);
    }
}
