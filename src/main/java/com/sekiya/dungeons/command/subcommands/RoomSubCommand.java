package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.config.RoomConfig;
import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.util.MessageUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles room-related subcommands
 * /dungeon room add <dungeon> <roomId>
 * /dungeon room setbounds <dungeon> <roomId> <order>
 */
public class RoomSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public RoomSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "room";
    }
    
    @Override
    public String getUsage() {
        return "room <add|setbounds> <dungeon> <roomId> [order]";
    }
    
    @Override
    public String getDescription() {
        return "Manage dungeon rooms";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 3) {
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
        } else if ("setbounds".equalsIgnoreCase(action)) {
            return handleSetBounds(sender, template, roomId);
        }
        
        return false;
    }
    
    private boolean handleAdd(Object sender, DungeonTemplate template, String roomId, String[] args) {
        int order = args.length >= 4 ? Integer.parseInt(args[3]) : 1;
        
        RoomConfig room = new RoomConfig();
        room.setId(roomId);
        room.setOrder(order);
        
        if (template.getRooms() == null) {
            template.setRooms(new ArrayList<>());
        }
        
        template.getRooms().add(room);
        configManager.saveDungeonTemplate(template);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aAdded room " + roomId + " to " + template.getName()));
        
        return true;
    }
    
    private boolean handleSetBounds(Object sender, DungeonTemplate template, String roomId) {
        // Find the room
        RoomConfig room = template.getRooms().stream()
            .filter(r -> r.getId().equals(roomId))
            .findFirst()
            .orElse(null);
        
        if (room == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cRoom not found: " + roomId));
            return true;
        }
        
        // Get player location as corner (placeholder - would need WorldEdit-style selection)
        Location location = getPlayerLocation(sender);
        
        if (room.getBounds() == null) {
            room.setBounds(new HashMap<>());
        }
        
        if (room.getMinBounds() == null) {
            room.getBounds().put("min", location);
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aSet first corner. Run again to set second corner."));
        } else {
            room.getBounds().put("max", location);
            configManager.saveDungeonTemplate(template);
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aSet room bounds for " + roomId));
        }
        
        return true;
    }
    
    private Location getPlayerLocation(Object sender) {
        // Placeholder
        return new Location("world", 0, 64, 0);
    }
}
