package com.sekiya.dungeons.config;

import java.util.Map;

/**
 * Configuration for dungeon rewards
 */
public class RewardConfig {
    private String type;
    private String itemId;
    private int amount;
    
    public RewardConfig() {}
    
    public RewardConfig(String type, String itemId, int amount) {
        this.type = type;
        this.itemId = itemId;
        this.amount = amount;
    }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
    
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
