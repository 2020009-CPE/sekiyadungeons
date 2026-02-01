package com.sekiya.dungeons.api.events;

import com.sekiya.dungeons.room.DungeonRoom;

/**
 * Event fired when a room is cleared
 */
public class RoomClearEvent {
    private final String instanceId;
    private final DungeonRoom room;
    
    public RoomClearEvent(String instanceId, DungeonRoom room) {
        this.instanceId = instanceId;
        this.room = room;
    }
    
    public String getInstanceId() {
        return instanceId;
    }
    
    public DungeonRoom getRoom() {
        return room;
    }
}
