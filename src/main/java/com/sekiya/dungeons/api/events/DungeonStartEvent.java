package com.sekiya.dungeons.api.events;

import com.sekiya.dungeons.instance.DungeonInstance;
import java.util.Set;

/**
 * Event fired when a dungeon instance starts
 */
public class DungeonStartEvent {
    private final DungeonInstance instance;
    private final Set<String> players;
    private boolean cancelled;
    
    public DungeonStartEvent(DungeonInstance instance, Set<String> players) {
        this.instance = instance;
        this.players = players;
        this.cancelled = false;
    }
    
    public DungeonInstance getInstance() {
        return instance;
    }
    
    public Set<String> getPlayers() {
        return players;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
