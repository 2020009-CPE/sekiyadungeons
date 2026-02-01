package com.sekiya.dungeons.api.events;

import com.sekiya.dungeons.boss.BossRoom;

/**
 * Event fired when a boss is defeated
 */
public class BossDefeatEvent {
    private final String instanceId;
    private final BossRoom bossRoom;
    private final String bossEntityUuid;
    
    public BossDefeatEvent(String instanceId, BossRoom bossRoom, String bossEntityUuid) {
        this.instanceId = instanceId;
        this.bossRoom = bossRoom;
        this.bossEntityUuid = bossEntityUuid;
    }
    
    public String getInstanceId() {
        return instanceId;
    }
    
    public BossRoom getBossRoom() {
        return bossRoom;
    }
    
    public String getBossEntityUuid() {
        return bossEntityUuid;
    }
}
