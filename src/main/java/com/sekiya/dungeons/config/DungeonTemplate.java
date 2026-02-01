package com.sekiya.dungeons.config;

import com.sekiya.dungeons.util.Location;
import java.util.ArrayList;
import java.util.List;

/**
 * Template configuration for a dungeon
 */
public class DungeonTemplate {
    private String name;
    private String displayName;
    private Location portalLocation;
    private Location entryPoint;
    private Location exitPoint;
    private String shardType;
    private boolean shardConsumed;
    private int minPlayers;
    private int maxPlayers;
    private int timeLimit;
    private List<RoomConfig> rooms;
    private BossRoomConfig bossRoom;
    private int completionCountdown;
    private List<RewardConfig> rewards;
    
    public DungeonTemplate() {
        this.rooms = new ArrayList<>();
        this.rewards = new ArrayList<>();
        this.minPlayers = 1;
        this.maxPlayers = 4;
        this.timeLimit = 1800;
        this.completionCountdown = 30;
        this.shardConsumed = true;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    
    public Location getPortalLocation() { return portalLocation; }
    public void setPortalLocation(Location portalLocation) { this.portalLocation = portalLocation; }
    
    public Location getEntryPoint() { return entryPoint; }
    public void setEntryPoint(Location entryPoint) { this.entryPoint = entryPoint; }
    
    public Location getExitPoint() { return exitPoint; }
    public void setExitPoint(Location exitPoint) { this.exitPoint = exitPoint; }
    
    public String getShardType() { return shardType; }
    public void setShardType(String shardType) { this.shardType = shardType; }
    
    public boolean isShardConsumed() { return shardConsumed; }
    public void setShardConsumed(boolean shardConsumed) { this.shardConsumed = shardConsumed; }
    
    public int getMinPlayers() { return minPlayers; }
    public void setMinPlayers(int minPlayers) { this.minPlayers = minPlayers; }
    
    public int getMaxPlayers() { return maxPlayers; }
    public void setMaxPlayers(int maxPlayers) { this.maxPlayers = maxPlayers; }
    
    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }
    
    public List<RoomConfig> getRooms() { return rooms; }
    public void setRooms(List<RoomConfig> rooms) { this.rooms = rooms; }
    
    public BossRoomConfig getBossRoom() { return bossRoom; }
    public void setBossRoom(BossRoomConfig bossRoom) { this.bossRoom = bossRoom; }
    
    public int getCompletionCountdown() { return completionCountdown; }
    public void setCompletionCountdown(int completionCountdown) { this.completionCountdown = completionCountdown; }
    
    public List<RewardConfig> getRewards() { return rewards; }
    public void setRewards(List<RewardConfig> rewards) { this.rewards = rewards; }
}
