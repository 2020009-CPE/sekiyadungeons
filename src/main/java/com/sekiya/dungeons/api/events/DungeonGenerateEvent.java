package com.sekiya.dungeons.api.events;

import com.sekiya.dungeons.config.DungeonTemplate;

/**
 * Event fired when a dungeon is auto-generated
 */
public class DungeonGenerateEvent {
    private final DungeonTemplate template;
    private final long seed;
    
    public DungeonGenerateEvent(DungeonTemplate template, long seed) {
        this.template = template;
        this.seed = seed;
    }
    
    public DungeonTemplate getTemplate() {
        return template;
    }
    
    public long getSeed() {
        return seed;
    }
}
