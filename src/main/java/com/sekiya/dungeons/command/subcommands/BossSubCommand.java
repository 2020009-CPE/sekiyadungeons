package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.config.BossRoomConfig;
import com.sekiya.dungeons.config.ConfigManager;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.util.MessageUtil;

import java.util.HashMap;

/**
 * /dungeon boss set <dungeon> <bossType> - Sets the boss for a dungeon
 */
public class BossSubCommand implements SubCommand {
    private final ConfigManager configManager;
    
    public BossSubCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    @Override
    public String getName() {
        return "boss";
    }
    
    @Override
    public String getUsage() {
        return "boss set <dungeon> <bossType>";
    }
    
    @Override
    public String getDescription() {
        return "Set the boss for a dungeon";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        if (args.length < 3 || !args[0].equalsIgnoreCase("set")) {
            return false;
        }
        
        String dungeonName = args[1];
        String bossType = args[2];
        
        DungeonTemplate template = configManager.getDungeonTemplate(dungeonName);
        if (template == null) {
            MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&cDungeon not found: " + dungeonName));
            return true;
        }
        
        // Create or update boss room
        BossRoomConfig bossRoom = template.getBossRoom();
        if (bossRoom == null) {
            bossRoom = new BossRoomConfig();
            bossRoom.setId("boss_room");
            bossRoom.setBounds(new HashMap<>());
            template.setBossRoom(bossRoom);
        }
        
        bossRoom.setBossType(bossType);
        bossRoom.setSpawnOnEntry(true);
        
        // Set spawn point to player location
        Location location = getPlayerLocation(sender);
        bossRoom.setBossSpawnPoint(location);
        
        configManager.saveDungeonTemplate(template);
        
        MessageUtil.sendMessage(sender, MessageUtil.formatWithPrefix("&aSet boss type to " + bossType + " for " + dungeonName));
        
        return true;
    }
    
    private Location getPlayerLocation(Object sender) {
        // Placeholder
        return new Location("world", 0, 64, 0);
    }
}
