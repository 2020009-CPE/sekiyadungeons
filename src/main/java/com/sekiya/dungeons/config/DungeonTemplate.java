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
    
    // Enhanced features
    private String difficulty; // EASY, NORMAL, HARD, NIGHTMARE
    private boolean requiresParty;
    private int requiredPartySize;
    private int minLevel;
    private List<String> requiredItems;
    private long cooldownMillis;
    private boolean scalingEnabled;
    private double healthScalingPerPlayer;
    private double damageScalingPerPlayer;
    private int maxLives;
    private boolean hasCheckpoints;
    private List<String> bonusObjectives;
    private boolean timeAttackMode;
    private int timeAttackBonusSeconds;
    
    public DungeonTemplate() {
        this.rooms = new ArrayList<>();
        this.rewards = new ArrayList<>();
        this.minPlayers = 1;
        this.maxPlayers = 4;
        this.timeLimit = 1800;
        this.completionCountdown = 30;
        this.shardConsumed = true;
        
        // Enhanced defaults
        this.difficulty = "NORMAL";
        this.requiresParty = false;
        this.requiredPartySize = 0;
        this.minLevel = 0;
        this.requiredItems = new ArrayList<>();
        this.cooldownMillis = 0;
        this.scalingEnabled = true;
        this.healthScalingPerPlayer = 0.3; // 30% more health per player
        this.damageScalingPerPlayer = 0.2; // 20% more damage per player
        this.maxLives = 0; // 0 = unlimited
        this.hasCheckpoints = false;
        this.bonusObjectives = new ArrayList<>();
        this.timeAttackMode = false;
        this.timeAttackBonusSeconds = 0;
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
    
    // Enhanced getters and setters
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    
    public boolean isRequiresParty() { return requiresParty; }
    public void setRequiresParty(boolean requiresParty) { this.requiresParty = requiresParty; }
    
    public int getRequiredPartySize() { return requiredPartySize; }
    public void setRequiredPartySize(int requiredPartySize) { this.requiredPartySize = requiredPartySize; }
    
    public int getMinLevel() { return minLevel; }
    public void setMinLevel(int minLevel) { this.minLevel = minLevel; }
    
    public List<String> getRequiredItems() { return requiredItems; }
    public void setRequiredItems(List<String> requiredItems) { this.requiredItems = requiredItems; }
    
    public long getCooldownMillis() { return cooldownMillis; }
    public void setCooldownMillis(long cooldownMillis) { this.cooldownMillis = cooldownMillis; }
    
    public boolean isScalingEnabled() { return scalingEnabled; }
    public void setScalingEnabled(boolean scalingEnabled) { this.scalingEnabled = scalingEnabled; }
    
    public double getHealthScalingPerPlayer() { return healthScalingPerPlayer; }
    public void setHealthScalingPerPlayer(double healthScalingPerPlayer) { 
        this.healthScalingPerPlayer = healthScalingPerPlayer; 
    }
    
    public double getDamageScalingPerPlayer() { return damageScalingPerPlayer; }
    public void setDamageScalingPerPlayer(double damageScalingPerPlayer) { 
        this.damageScalingPerPlayer = damageScalingPerPlayer; 
    }
    
    public int getMaxLives() { return maxLives; }
    public void setMaxLives(int maxLives) { this.maxLives = maxLives; }
    
    public boolean isHasCheckpoints() { return hasCheckpoints; }
    public void setHasCheckpoints(boolean hasCheckpoints) { this.hasCheckpoints = hasCheckpoints; }
    
    public List<String> getBonusObjectives() { return bonusObjectives; }
    public void setBonusObjectives(List<String> bonusObjectives) { this.bonusObjectives = bonusObjectives; }
    
    public boolean isTimeAttackMode() { return timeAttackMode; }
    public void setTimeAttackMode(boolean timeAttackMode) { this.timeAttackMode = timeAttackMode; }
    
    public int getTimeAttackBonusSeconds() { return timeAttackBonusSeconds; }
    public void setTimeAttackBonusSeconds(int timeAttackBonusSeconds) { 
        this.timeAttackBonusSeconds = timeAttackBonusSeconds; 
    }
}
