package com.sekiya.dungeons.api.events;

import com.sekiya.dungeons.instance.DungeonInstance;
import java.util.Set;

/**
 * Event fired when a dungeon is completed
 */
public class DungeonCompleteEvent {
    private final DungeonInstance instance;
    private final Set<String> players;
    private final long completionTimeSeconds;
    
    public DungeonCompleteEvent(DungeonInstance instance, Set<String> players, long completionTime) {
        this.instance = instance;
        this.players = players;
        this.completionTimeSeconds = completionTime;
    }
    
    public DungeonInstance getInstance() {
        return instance;
    }
    
    public Set<String> getPlayers() {
        return players;
    }
    
    public long getCompletionTimeSeconds() {
        return completionTimeSeconds;
    }
}
