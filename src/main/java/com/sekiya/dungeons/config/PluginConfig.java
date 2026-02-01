package com.sekiya.dungeons.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Main plugin configuration
 */
public class PluginConfig {
    private boolean debugMode;
    private int maxConcurrentInstances;
    private boolean autoReset;
    private int resetDelaySeconds;
    private Map<String, String> messages;
    
    public PluginConfig() {
        this.debugMode = false;
        this.maxConcurrentInstances = 10;
        this.autoReset = true;
        this.resetDelaySeconds = 5;
        this.messages = new HashMap<>();
        initializeDefaultMessages();
    }
    
    private void initializeDefaultMessages() {
        messages.put("portal_activated", "&aPortal activated! Step through to enter the dungeon.");
        messages.put("portal_inactive", "&cThis portal is inactive. You need a shard to activate it.");
        messages.put("no_shard", "&cYou don't have the required shard!");
        messages.put("dungeon_started", "&6The dungeon has begun! Clear all rooms to face the boss.");
        messages.put("room_cleared", "&aRoom cleared! The door has opened.");
        messages.put("boss_spawned", "&c&lBOSS FIGHT! Defeat the guardian to complete the dungeon!");
        messages.put("dungeon_complete", "&6&lDUNGEON COMPLETED! Well done!");
        messages.put("countdown", "&eReturning to the surface in &c{time}&e seconds...");
        messages.put("teleporting", "&aTeleporting you back...");
    }
    
    public boolean isDebugMode() { return debugMode; }
    public void setDebugMode(boolean debugMode) { this.debugMode = debugMode; }
    
    public int getMaxConcurrentInstances() { return maxConcurrentInstances; }
    public void setMaxConcurrentInstances(int maxConcurrentInstances) { 
        this.maxConcurrentInstances = maxConcurrentInstances; 
    }
    
    public boolean isAutoReset() { return autoReset; }
    public void setAutoReset(boolean autoReset) { this.autoReset = autoReset; }
    
    public int getResetDelaySeconds() { return resetDelaySeconds; }
    public void setResetDelaySeconds(int resetDelaySeconds) { 
        this.resetDelaySeconds = resetDelaySeconds; 
    }
    
    public Map<String, String> getMessages() { return messages; }
    public void setMessages(Map<String, String> messages) { this.messages = messages; }
    
    public String getMessage(String key) {
        return messages.getOrDefault(key, "&cMessage not found: " + key);
    }
}
