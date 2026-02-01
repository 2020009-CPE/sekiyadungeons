package com.sekiya.dungeons.instance;

import com.sekiya.dungeons.boss.BossManager;
import com.sekiya.dungeons.boss.BossRoom;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.enemy.EnemyManager;
import com.sekiya.dungeons.room.RoomManager;
import com.sekiya.dungeons.util.Location;

import java.util.*;

/**
 * Represents an active dungeon instance
 */
public class DungeonInstance {
    private final String instanceId;
    private final DungeonTemplate template;
    private final RoomManager roomManager;
    private final EnemyManager enemyManager;
    private final BossManager bossManager;
    private final Set<String> players;
    private InstanceState state;
    private BossRoom bossRoom;
    private long startTime;
    private long completionTime;
    
    public DungeonInstance(String instanceId, DungeonTemplate template) {
        this.instanceId = instanceId;
        this.template = template;
        this.enemyManager = new EnemyManager();
        this.roomManager = new RoomManager(enemyManager);
        this.bossManager = new BossManager();
        this.players = new HashSet<>();
        this.state = InstanceState.WAITING;
        this.startTime = 0;
        this.completionTime = 0;
        
        // Initialize rooms
        if (template.getRooms() != null) {
            roomManager.initializeRooms(template.getRooms());
        }
        
        // Initialize boss room
        if (template.getBossRoom() != null) {
            this.bossRoom = new BossRoom(template.getBossRoom());
        }
    }
    
    public String getInstanceId() {
        return instanceId;
    }
    
    public DungeonTemplate getTemplate() {
        return template;
    }
    
    public String getDungeonName() {
        return template.getName();
    }
    
    public RoomManager getRoomManager() {
        return roomManager;
    }
    
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }
    
    public BossManager getBossManager() {
        return bossManager;
    }
    
    public BossRoom getBossRoom() {
        return bossRoom;
    }
    
    public InstanceState getState() {
        return state;
    }
    
    public void setState(InstanceState state) {
        this.state = state;
    }
    
    public Set<String> getPlayers() {
        return new HashSet<>(players);
    }
    
    public int getPlayerCount() {
        return players.size();
    }
    
    public void addPlayer(String playerName) {
        players.add(playerName);
        if (state == InstanceState.WAITING && players.size() >= template.getMinPlayers()) {
            start();
        }
    }
    
    public void removePlayer(String playerName) {
        players.remove(playerName);
    }
    
    public boolean hasPlayer(String playerName) {
        return players.contains(playerName);
    }
    
    public long getStartTime() {
        return startTime;
    }
    
    public long getCompletionTime() {
        return completionTime;
    }
    
    public long getElapsedTime() {
        if (startTime == 0) {
            return 0;
        }
        long endTime = completionTime > 0 ? completionTime : System.currentTimeMillis();
        return (endTime - startTime) / 1000; // Return in seconds
    }
    
    public void start() {
        this.state = InstanceState.ACTIVE;
        this.startTime = System.currentTimeMillis();
    }
    
    public void complete() {
        this.state = InstanceState.COMPLETING;
        this.completionTime = System.currentTimeMillis();
    }
    
    public void close() {
        this.state = InstanceState.CLOSED;
    }
    
    public boolean isActive() {
        return state == InstanceState.ACTIVE || state == InstanceState.IN_PROGRESS || 
               state == InstanceState.BOSS_FIGHT;
    }
    
    public boolean isFull() {
        return players.size() >= template.getMaxPlayers();
    }
    
    public Location getEntryPoint() {
        return template.getEntryPoint();
    }
    
    public Location getExitPoint() {
        return template.getExitPoint();
    }
    
    @Override
    public String toString() {
        return String.format("DungeonInstance{id=%s, dungeon=%s, state=%s, players=%d}", 
            instanceId, template.getName(), state, players.size());
    }
}
