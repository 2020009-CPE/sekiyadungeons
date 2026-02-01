package com.sekiya.dungeons.hud;

import com.sekiya.dungeons.instance.DungeonInstance;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages HUD displays for all players
 */
public class HUDManager {
    private final Map<String, DungeonHUD> activeHUDs;
    private final long updateInterval = 1000; // Update every second
    
    public HUDManager() {
        this.activeHUDs = new ConcurrentHashMap<>();
    }
    
    /**
     * Creates and activates HUD for a player
     */
    public void activateHUD(String playerName, DungeonInstance instance) {
        DungeonHUD hud = new DungeonHUD(playerName, instance);
        activeHUDs.put(playerName, hud);
        updateHUD(playerName);
    }
    
    /**
     * Deactivates HUD for a player
     */
    public void deactivateHUD(String playerName) {
        activeHUDs.remove(playerName);
        clearHUDDisplay(playerName);
    }
    
    /**
     * Gets HUD for a player
     */
    public DungeonHUD getHUD(String playerName) {
        return activeHUDs.get(playerName);
    }
    
    /**
     * Checks if player has active HUD
     */
    public boolean hasHUD(String playerName) {
        return activeHUDs.containsKey(playerName);
    }
    
    /**
     * Updates HUD display for a player
     */
    public void updateHUD(String playerName) {
        DungeonHUD hud = activeHUDs.get(playerName);
        if (hud == null) return;
        
        // Get HUD lines
        List<String> lines = hud.getHUDLines();
        
        // Display HUD (implementation depends on Hytale API)
        displayHUD(playerName, lines);
        
        hud.markUpdated();
    }
    
    /**
     * Updates all active HUDs
     */
    public void updateAllHUDs() {
        for (String playerName : activeHUDs.keySet()) {
            updateHUD(playerName);
        }
    }
    
    /**
     * Updates HUDs for all players in an instance
     */
    public void updateInstanceHUDs(DungeonInstance instance) {
        for (String playerName : instance.getPlayers()) {
            if (hasHUD(playerName)) {
                updateHUD(playerName);
            }
        }
    }
    
    /**
     * Displays HUD to player using scoreboard
     * 
     * In actual Hytale implementation, this would use the scoreboard API
     * or action bar / title API to display the HUD
     */
    private void displayHUD(String playerName, List<String> lines) {
        // PLACEHOLDER: This would use Hytale's API to display HUD
        // Options:
        // 1. Scoreboard on the right side
        // 2. Action bar at the bottom
        // 3. Boss bar at the top
        // 4. Title/subtitle system
        
        // Example implementation (pseudocode for Hytale):
        // Player player = getPlayer(playerName);
        // if (player != null) {
        //     Scoreboard scoreboard = player.getScoreboard();
        //     scoreboard.clear();
        //     
        //     Objective objective = scoreboard.createObjective("dungeon_hud");
        //     objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        //     
        //     int score = lines.size();
        //     for (String line : lines) {
        //         objective.setScore(line, score--);
        //     }
        // }
        
        System.out.println("[HUD] Updating for " + playerName);
        for (String line : lines) {
            System.out.println("[HUD]   " + line);
        }
    }
    
    /**
     * Clears HUD display for a player
     */
    private void clearHUDDisplay(String playerName) {
        // PLACEHOLDER: This would clear the HUD using Hytale's API
        // Example:
        // Player player = getPlayer(playerName);
        // if (player != null) {
        //     player.getScoreboard().clearObjective("dungeon_hud");
        // }
        
        System.out.println("[HUD] Cleared for " + playerName);
    }
    
    /**
     * Shows boss health bar to players in instance
     */
    public void showBossHealthBar(DungeonInstance instance, String bossName, double health, double maxHealth) {
        for (String playerName : instance.getPlayers()) {
            showBossHealthBar(playerName, bossName, health, maxHealth);
        }
    }
    
    /**
     * Shows boss health bar to a player
     */
    private void showBossHealthBar(String playerName, String bossName, double health, double maxHealth) {
        // PLACEHOLDER: Would use Hytale's boss bar API
        // Example:
        // Player player = getPlayer(playerName);
        // if (player != null) {
        //     BossBar bossBar = player.getBossBar();
        //     bossBar.setTitle("§c§l" + bossName);
        //     bossBar.setProgress(health / maxHealth);
        //     bossBar.setColor(BossBarColor.RED);
        //     bossBar.setStyle(BossBarStyle.PROGRESS);
        //     bossBar.setVisible(true);
        // }
        
        double percent = (health / maxHealth) * 100.0;
        System.out.println("[BOSS BAR] " + playerName + " - " + bossName + ": " + 
                          String.format("%.1f%%", percent));
    }
    
    /**
     * Hides boss health bar for instance players
     */
    public void hideBossHealthBar(DungeonInstance instance) {
        for (String playerName : instance.getPlayers()) {
            hideBossHealthBar(playerName);
        }
    }
    
    /**
     * Hides boss health bar for a player
     */
    private void hideBossHealthBar(String playerName) {
        // PLACEHOLDER: Would hide Hytale's boss bar
        // Example:
        // Player player = getPlayer(playerName);
        // if (player != null) {
        //     player.getBossBar().setVisible(false);
        // }
        
        System.out.println("[BOSS BAR] Hidden for " + playerName);
    }
    
    /**
     * Sends a title message to players in instance
     */
    public void sendTitle(DungeonInstance instance, String title, String subtitle) {
        for (String playerName : instance.getPlayers()) {
            sendTitle(playerName, title, subtitle);
        }
    }
    
    /**
     * Sends a title message to a player
     */
    private void sendTitle(String playerName, String title, String subtitle) {
        // PLACEHOLDER: Would use Hytale's title API
        // Example:
        // Player player = getPlayer(playerName);
        // if (player != null) {
        //     player.sendTitle(title, subtitle, 10, 70, 20);
        // }
        
        System.out.println("[TITLE] " + playerName + " - " + title);
        if (subtitle != null && !subtitle.isEmpty()) {
            System.out.println("[SUBTITLE] " + playerName + " - " + subtitle);
        }
    }
    
    /**
     * Sends an action bar message to players in instance
     */
    public void sendActionBar(DungeonInstance instance, String message) {
        for (String playerName : instance.getPlayers()) {
            sendActionBar(playerName, message);
        }
    }
    
    /**
     * Sends an action bar message to a player
     */
    private void sendActionBar(String playerName, String message) {
        // PLACEHOLDER: Would use Hytale's action bar API
        // Example:
        // Player player = getPlayer(playerName);
        // if (player != null) {
        //     player.sendActionBar(message);
        // }
        
        System.out.println("[ACTION BAR] " + playerName + " - " + message);
    }
    
    /**
     * Gets all active HUDs
     */
    public Collection<DungeonHUD> getAllHUDs() {
        return activeHUDs.values();
    }
    
    /**
     * Gets the update interval in milliseconds
     */
    public long getUpdateInterval() {
        return updateInterval;
    }
}
