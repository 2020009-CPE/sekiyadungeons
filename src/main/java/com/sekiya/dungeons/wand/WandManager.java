package com.sekiya.dungeons.wand;

import com.sekiya.dungeons.config.*;
import com.sekiya.dungeons.util.Location;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages dungeon wand sessions for players
 */
public class WandManager {
    private final Map<String, WandSession> sessions;
    private final Map<String, DungeonTemplate> draftDungeons;
    private final ConfigManager configManager;
    
    public WandManager(ConfigManager configManager) {
        this.sessions = new ConcurrentHashMap<>();
        this.draftDungeons = new ConcurrentHashMap<>();
        this.configManager = configManager;
    }
    
    /**
     * Gets or creates a wand session for a player
     */
    public WandSession getSession(String playerName) {
        return sessions.computeIfAbsent(playerName, WandSession::new);
    }
    
    /**
     * Checks if player has an active session
     */
    public boolean hasSession(String playerName) {
        return sessions.containsKey(playerName);
    }
    
    /**
     * Removes a player's wand session
     */
    public void removeSession(String playerName) {
        sessions.remove(playerName);
    }
    
    /**
     * Creates or gets a draft dungeon for editing
     */
    public DungeonTemplate getDraftDungeon(String playerName, String dungeonName) {
        String key = playerName + ":" + dungeonName;
        
        if (draftDungeons.containsKey(key)) {
            return draftDungeons.get(key);
        }
        
        // Check if dungeon exists in config
        DungeonTemplate existing = configManager.getDungeonTemplate(dungeonName);
        if (existing != null) {
            // Clone for editing
            draftDungeons.put(key, existing);
            return existing;
        }
        
        // Create new draft
        DungeonTemplate draft = new DungeonTemplate();
        draft.setName(dungeonName);
        draft.setDisplayName(formatName(dungeonName));
        draft.setRooms(new ArrayList<>());
        draftDungeons.put(key, draft);
        
        return draft;
    }
    
    /**
     * Saves a draft dungeon to the config
     */
    public void saveDraftDungeon(String playerName, String dungeonName) {
        String key = playerName + ":" + dungeonName;
        DungeonTemplate draft = draftDungeons.get(key);
        
        if (draft != null) {
            configManager.saveDungeonTemplate(draft);
            draftDungeons.remove(key);
        }
    }
    
    /**
     * Discards a draft dungeon
     */
    public void discardDraft(String playerName, String dungeonName) {
        String key = playerName + ":" + dungeonName;
        draftDungeons.remove(key);
    }
    
    /**
     * Handles setting portal location
     */
    public void setPortal(WandSession session, Location location) {
        DungeonTemplate draft = getDraftDungeon(session.getPlayerName(), session.getDungeonName());
        draft.setPortalLocation(location);
        session.updateLastAction();
    }
    
    /**
     * Handles setting entry point
     */
    public void setEntry(WandSession session, Location location) {
        DungeonTemplate draft = getDraftDungeon(session.getPlayerName(), session.getDungeonName());
        draft.setEntryPoint(location);
        session.updateLastAction();
    }
    
    /**
     * Handles setting exit point
     */
    public void setExit(WandSession session, Location location) {
        DungeonTemplate draft = getDraftDungeon(session.getPlayerName(), session.getDungeonName());
        draft.setExitPoint(location);
        session.updateLastAction();
    }
    
    /**
     * Handles setting room bounds
     */
    public void setRoomBounds(WandSession session) {
        if (!session.hasBothPositions()) {
            return;
        }
        
        DungeonTemplate draft = getDraftDungeon(session.getPlayerName(), session.getDungeonName());
        int roomNumber = session.getCurrentRoom();
        
        // Find or create room
        RoomConfig room = getOrCreateRoom(draft, roomNumber);
        
        Location pos1 = session.getPosition1();
        Location pos2 = session.getPosition2();
        
        // Set bounds
        room.setMinBounds(new Location(
            pos1.getWorld(),
            Math.min(pos1.getX(), pos2.getX()),
            Math.min(pos1.getY(), pos2.getY()),
            Math.min(pos1.getZ(), pos2.getZ())
        ));
        
        room.setMaxBounds(new Location(
            pos1.getWorld(),
            Math.max(pos1.getX(), pos2.getX()),
            Math.max(pos1.getY(), pos2.getY()),
            Math.max(pos1.getZ(), pos2.getZ())
        ));
        
        session.clearPositions();
        session.updateLastAction();
    }
    
    /**
     * Handles adding spawn point
     */
    public void addSpawnPoint(WandSession session, Location location, String enemyType, int count) {
        DungeonTemplate draft = getDraftDungeon(session.getPlayerName(), session.getDungeonName());
        int roomNumber = session.getCurrentRoom();
        
        RoomConfig room = getOrCreateRoom(draft, roomNumber);
        
        if (room.getSpawnPoints() == null) {
            room.setSpawnPoints(new ArrayList<>());
        }
        
        SpawnPointConfig spawnPoint = new SpawnPointConfig();
        spawnPoint.setId("spawn_" + (room.getSpawnPoints().size() + 1));
        spawnPoint.setLocation(location);
        spawnPoint.setEnemyType(enemyType);
        spawnPoint.setCount(count);
        
        room.getSpawnPoints().add(spawnPoint);
        session.updateLastAction();
    }
    
    /**
     * Handles setting door location
     */
    public void setDoor(WandSession session, Location location) {
        DungeonTemplate draft = getDraftDungeon(session.getPlayerName(), session.getDungeonName());
        int roomNumber = session.getCurrentRoom();
        
        RoomConfig room = getOrCreateRoom(draft, roomNumber);
        
        DoorConfig door = new DoorConfig();
        door.setType("BLOCK_BARRIER");
        door.setLocation(location);
        door.setWidth(3);
        door.setHeight(3);
        
        room.setDoor(door);
        session.updateLastAction();
    }
    
    /**
     * Handles setting boss spawn location
     */
    public void setBossSpawn(WandSession session, Location location, String bossType) {
        DungeonTemplate draft = getDraftDungeon(session.getPlayerName(), session.getDungeonName());
        
        BossRoomConfig bossRoom = draft.getBossRoom();
        if (bossRoom == null) {
            bossRoom = new BossRoomConfig();
            draft.setBossRoom(bossRoom);
        }
        
        bossRoom.setBossSpawnPoint(location);
        bossRoom.setBossType(bossType);
        bossRoom.setSpawnOnEntry(true);
        
        session.updateLastAction();
    }
    
    /**
     * Gets or creates a room in the template
     */
    private RoomConfig getOrCreateRoom(DungeonTemplate draft, int roomNumber) {
        List<RoomConfig> rooms = draft.getRooms();
        if (rooms == null) {
            rooms = new ArrayList<>();
            draft.setRooms(rooms);
        }
        
        // Find existing room
        for (RoomConfig room : rooms) {
            if (room.getOrder() == roomNumber) {
                return room;
            }
        }
        
        // Create new room
        RoomConfig newRoom = new RoomConfig();
        newRoom.setId("room_" + roomNumber);
        newRoom.setOrder(roomNumber);
        rooms.add(newRoom);
        
        // Sort by order
        rooms.sort(Comparator.comparingInt(RoomConfig::getOrder));
        
        return newRoom;
    }
    
    /**
     * Formats dungeon name for display
     */
    private String formatName(String name) {
        String[] parts = name.split("_");
        StringBuilder formatted = new StringBuilder();
        
        for (String part : parts) {
            if (formatted.length() > 0) {
                formatted.append(" ");
            }
            formatted.append(Character.toUpperCase(part.charAt(0)));
            formatted.append(part.substring(1).toLowerCase());
        }
        
        return formatted.toString();
    }
    
    /**
     * Gets inspection info for a location
     */
    public String getInspectionInfo(Location location) {
        StringBuilder info = new StringBuilder();
        info.append("§6§lLocation Info\n");
        info.append("§7World: §f").append(location.getWorld()).append("\n");
        info.append("§7X: §f").append(String.format("%.2f", location.getX())).append("\n");
        info.append("§7Y: §f").append(String.format("%.2f", location.getY())).append("\n");
        info.append("§7Z: §f").append(String.format("%.2f", location.getZ())).append("\n");
        
        // Add info about nearby rooms/dungeons
        // This would query the config to see if location is in any dungeon
        
        return info.toString();
    }
    
    /**
     * Gets all active sessions
     */
    public Collection<WandSession> getAllSessions() {
        return sessions.values();
    }
}
