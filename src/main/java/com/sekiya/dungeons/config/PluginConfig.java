package com.sekiya.dungeons.config;

import com.hypixel.hytale.server.codec.BuilderCodec;
import com.hypixel.hytale.server.codec.Codec;
import com.hypixel.hytale.server.codec.KeyedCodec;

import java.util.HashMap;
import java.util.Map;

/**
 * Main plugin configuration using Hytale's BuilderCodec system
 */
public class PluginConfig {
    
    public static final BuilderCodec<PluginConfig> CODEC = 
        BuilderCodec.builder(PluginConfig.class, PluginConfig::new)
            .append(new KeyedCodec<Boolean>("debugMode", Codec.BOOL),
                    (cfg, val, info) -> cfg.debugMode = val,
                    (cfg, info) -> cfg.debugMode)
            .add()
            .append(new KeyedCodec<Integer>("maxConcurrentInstances", Codec.INT),
                    (cfg, val, info) -> cfg.maxConcurrentInstances = val,
                    (cfg, info) -> cfg.maxConcurrentInstances)
            .add()
            .append(new KeyedCodec<Boolean>("autoReset", Codec.BOOL),
                    (cfg, val, info) -> cfg.autoReset = val,
                    (cfg, info) -> cfg.autoReset)
            .add()
            .append(new KeyedCodec<Integer>("resetDelaySeconds", Codec.INT),
                    (cfg, val, info) -> cfg.resetDelaySeconds = val,
                    (cfg, info) -> cfg.resetDelaySeconds)
            .add()
            .append(new KeyedCodec<Integer>("completionCountdownSeconds", Codec.INT),
                    (cfg, val, info) -> cfg.completionCountdownSeconds = val,
                    (cfg, info) -> cfg.completionCountdownSeconds)
            .add()
            .build();

    private boolean debugMode;
    private int maxConcurrentInstances;
    private boolean autoReset;
    private int resetDelaySeconds;
    private int completionCountdownSeconds;
    private Map<String, String> messages;
    
    public PluginConfig() {
        this.debugMode = false;
        this.maxConcurrentInstances = 10;
        this.autoReset = true;
        this.resetDelaySeconds = 5;
        this.completionCountdownSeconds = 30;
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
    
    public int getCompletionCountdownSeconds() { return completionCountdownSeconds; }
    public void setCompletionCountdownSeconds(int completionCountdownSeconds) {
        this.completionCountdownSeconds = completionCountdownSeconds;
    }
    
    public Map<String, String> getMessages() { return messages; }
    public void setMessages(Map<String, String> messages) { this.messages = messages; }
    
    public String getMessage(String key) {
        return messages.getOrDefault(key, "&cMessage not found: " + key);
    }
}
