package com.sekiya.dungeons.generator;

import com.sekiya.dungeons.config.*;
import com.sekiya.dungeons.util.Location;

import java.util.*;

/**
 * Procedurally generates dungeon templates
 */
public class DungeonGenerator {
    
    /**
     * Generates a dungeon template based on configuration
     */
    public DungeonTemplate generate(String name, GenerationConfig config) {
        Random random = config.getSeed() != 0 
            ? new Random(config.getSeed()) 
            : new Random();
        
        DungeonTemplate template = new DungeonTemplate();
        template.setName(name);
        template.setDisplayName(formatName(name));
        
        // Set basic properties based on difficulty
        applyDifficulty(template, config.getDifficulty());
        
        // Generate rooms
        int roomCount = random.nextInt(config.getMaxRooms() - config.getMinRooms() + 1) + config.getMinRooms();
        List<RoomConfig> rooms = new ArrayList<>();
        
        int xOffset = 0;
        for (int i = 0; i < roomCount; i++) {
            RoomConfig room = generateRoom(i + 1, xOffset, random, config);
            rooms.add(room);
            
            // Calculate offset for next room
            Location maxBounds = room.getMaxBounds();
            if (maxBounds != null) {
                xOffset = (int) maxBounds.getX() + 5; // 5 block gap between rooms
            }
        }
        
        template.setRooms(rooms);
        
        // Generate boss room if enabled
        if (config.isIncludeBoss()) {
            BossRoomConfig bossRoom = generateBossRoom(xOffset, random, config);
            template.setBossRoom(bossRoom);
        }
        
        // Generate rewards
        if (config.isRandomLoot()) {
            List<RewardConfig> rewards = generateRewards(random, config);
            template.setRewards(rewards);
        }
        
        // Set placeholder locations (would be set by admin commands)
        template.setPortalLocation(new Location("world", 0, 64, 0));
        template.setEntryPoint(new Location("dungeon_" + name, 0, 64, 0));
        template.setExitPoint(new Location("world", 0, 64, 5));
        
        template.setShardType(name + "_shard");
        template.setShardConsumed(true);
        
        return template;
    }
    
    /**
     * Generates a single room
     */
    private RoomConfig generateRoom(int order, int xOffset, Random random, GenerationConfig config) {
        RoomConfig room = new RoomConfig();
        room.setId("room_" + order);
        room.setOrder(order);
        
        // Generate room dimensions
        int width = random.nextInt(config.getMaxRoomWidth() - config.getMinRoomWidth() + 1) 
            + config.getMinRoomWidth();
        int height = random.nextInt(config.getMaxRoomHeight() - config.getMinRoomHeight() + 1) 
            + config.getMinRoomHeight();
        int length = random.nextInt(config.getMaxRoomLength() - config.getMinRoomLength() + 1) 
            + config.getMinRoomLength();
        
        // Set bounds
        Map<String, Location> bounds = new HashMap<>();
        bounds.put("min", new Location("dungeon", xOffset, 60, 0));
        bounds.put("max", new Location("dungeon", xOffset + width, 60 + height, length));
        room.setBounds(bounds);
        
        // Create door
        DoorConfig door = new DoorConfig();
        door.setType("BLOCK_BARRIER");
        door.setLocation(new Location("dungeon", xOffset + width, 64, length / 2));
        Map<String, Integer> doorSize = new HashMap<>();
        doorSize.put("width", 3);
        doorSize.put("height", 3);
        door.setSize(doorSize);
        room.setDoor(door);
        
        // Generate spawn points
        int enemyCount = random.nextInt(config.getMaxEnemiesPerRoom() - config.getMinEnemiesPerRoom() + 1) 
            + config.getMinEnemiesPerRoom();
        
        List<SpawnPointConfig> spawnPoints = new ArrayList<>();
        int spawnsPerPoint = Math.max(1, enemyCount / 3); // Distribute enemies across ~3 spawn points
        
        for (int i = 0; i < 3 && enemyCount > 0; i++) {
            int count = Math.min(spawnsPerPoint, enemyCount);
            enemyCount -= count;
            
            // Random position within room
            int spawnX = xOffset + random.nextInt(width - 2) + 1;
            int spawnZ = random.nextInt(length - 2) + 1;
            
            SpawnPointConfig spawn = new SpawnPointConfig();
            spawn.setId("spawn_" + (i + 1));
            spawn.setLocation(new Location("dungeon", spawnX, 64, spawnZ));
            spawn.setEnemyType(config.getEnemyTypes()[random.nextInt(config.getEnemyTypes().length)]);
            spawn.setCount(count);
            
            spawnPoints.add(spawn);
        }
        
        room.setSpawnPoints(spawnPoints);
        
        return room;
    }
    
    /**
     * Generates a boss room
     */
    private BossRoomConfig generateBossRoom(int xOffset, Random random, GenerationConfig config) {
        BossRoomConfig bossRoom = new BossRoomConfig();
        bossRoom.setId("boss_room");
        
        // Boss rooms are larger
        int width = 40;
        int height = 25;
        int length = 40;
        
        // Set bounds
        Map<String, Location> bounds = new HashMap<>();
        bounds.put("min", new Location("dungeon", xOffset, 60, 0));
        bounds.put("max", new Location("dungeon", xOffset + width, 60 + height, length));
        bossRoom.setBounds(bounds);
        
        // Set boss
        bossRoom.setBossType(config.getBossTypes()[random.nextInt(config.getBossTypes().length)]);
        bossRoom.setBossSpawnPoint(new Location("dungeon", xOffset + width / 2, 64, length / 2));
        bossRoom.setSpawnOnEntry(true);
        
        return bossRoom;
    }
    
    /**
     * Generates random rewards
     */
    private List<RewardConfig> generateRewards(Random random, GenerationConfig config) {
        List<RewardConfig> rewards = new ArrayList<>();
        
        int rewardCount = random.nextInt(config.getMaxRewards() - config.getMinRewards() + 1) 
            + config.getMinRewards();
        
        String[] rewardItems = {"diamond", "emerald", "gold_ingot", "enchanted_book", "rare_artifact"};
        
        for (int i = 0; i < rewardCount; i++) {
            String item = rewardItems[random.nextInt(rewardItems.length)];
            int amount = random.nextInt(5) + 1;
            
            rewards.add(new RewardConfig("item", item, amount));
        }
        
        // Always add XP reward
        rewards.add(new RewardConfig("experience", "xp", random.nextInt(1000) + 500));
        
        return rewards;
    }
    
    /**
     * Applies difficulty-based settings
     */
    private void applyDifficulty(DungeonTemplate template, String difficulty) {
        switch (difficulty.toUpperCase()) {
            case "EASY":
                template.setMinPlayers(1);
                template.setMaxPlayers(8);
                template.setTimeLimit(3600); // 1 hour
                template.setCompletionCountdown(30);
                break;
            case "HARD":
                template.setMinPlayers(2);
                template.setMaxPlayers(4);
                template.setTimeLimit(1200); // 20 minutes
                template.setCompletionCountdown(20);
                break;
            case "NIGHTMARE":
                template.setMinPlayers(3);
                template.setMaxPlayers(4);
                template.setTimeLimit(900); // 15 minutes
                template.setCompletionCountdown(15);
                break;
            case "NORMAL":
            default:
                template.setMinPlayers(1);
                template.setMaxPlayers(4);
                template.setTimeLimit(1800); // 30 minutes
                template.setCompletionCountdown(30);
                break;
        }
    }
    
    /**
     * Formats dungeon name for display
     */
    private String formatName(String name) {
        return Arrays.stream(name.split("_"))
            .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
            .reduce((a, b) -> a + " " + b)
            .orElse(name);
    }
}
