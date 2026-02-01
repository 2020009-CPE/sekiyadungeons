package com.sekiya.dungeons.hud;

import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.room.DungeonRoom;

import java.util.*;

/**
 * Manages HUD display for a player in a dungeon
 */
public class DungeonHUD {
    private final String playerName;
    private final DungeonInstance instance;
    private long lastUpdate;
    
    public DungeonHUD(String playerName, DungeonInstance instance) {
        this.playerName = playerName;
        this.instance = instance;
        this.lastUpdate = System.currentTimeMillis();
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public DungeonInstance getInstance() {
        return instance;
    }
    
    /**
     * Gets the HUD display lines for the player
     */
    public List<String> getHUDLines() {
        List<String> lines = new ArrayList<>();
        
        // Dungeon name and difficulty
        String dungeonName = instance.getTemplate().getDisplayName();
        String difficulty = instance.getTemplate().getDifficulty();
        lines.add("§6§l" + dungeonName);
        lines.add("§7Difficulty: " + getDifficultyColor(difficulty) + difficulty);
        lines.add("");
        
        // Room progress
        int currentRoom = getCurrentRoomNumber();
        int totalRooms = getTotalRooms();
        int completedRooms = getCompletedRooms();
        
        lines.add("§eRoom Progress:");
        lines.add("  §7Current: §f" + currentRoom + "/" + totalRooms);
        lines.add("  §7Cleared: §a" + completedRooms + "/" + totalRooms);
        
        // Progress bar
        double progressPercent = getCompletionPercentage();
        lines.add("  " + getProgressBar(progressPercent));
        lines.add("");
        
        // Time
        long elapsed = getElapsedTime();
        long timeLimit = instance.getTemplate().getTimeLimit();
        
        if (timeLimit > 0) {
            long remaining = timeLimit - elapsed;
            lines.add("§eTime Remaining: " + formatTime(remaining));
        } else {
            lines.add("§eTime Elapsed: " + formatTime(elapsed));
        }
        lines.add("");
        
        // Party info
        int partySize = instance.getPlayers().size();
        int maxPlayers = instance.getTemplate().getMaxPlayers();
        lines.add("§eParty: §f" + partySize + "/" + maxPlayers);
        lines.add("");
        
        // Lives (if applicable)
        int maxLives = instance.getTemplate().getMaxLives();
        if (maxLives > 0) {
            int remainingLives = getRemainingLives();
            lines.add("§cLives: " + getHearts(remainingLives, maxLives));
            lines.add("");
        }
        
        // Current objective
        String objective = getCurrentObjective();
        if (objective != null && !objective.isEmpty()) {
            lines.add("§6§lObjective:");
            lines.add("§7" + objective);
        }
        
        return lines;
    }
    
    /**
     * Gets the current room number (1-indexed)
     */
    private int getCurrentRoomNumber() {
        // Placeholder - would track which room player is in
        return instance.getRoomManager().getCurrentRoomIndex() + 1;
    }
    
    /**
     * Gets total number of rooms
     */
    private int getTotalRooms() {
        List<DungeonRoom> rooms = instance.getRoomManager().getRooms();
        return rooms != null ? rooms.size() : 0;
    }
    
    /**
     * Gets number of completed rooms
     */
    private int getCompletedRooms() {
        return instance.getRoomManager().getCompletedRoomCount();
    }
    
    /**
     * Gets overall completion percentage
     */
    private double getCompletionPercentage() {
        int total = getTotalRooms();
        if (total == 0) return 0.0;
        
        int completed = getCompletedRooms();
        return (double) completed / total * 100.0;
    }
    
    /**
     * Gets elapsed time in seconds
     */
    private long getElapsedTime() {
        long startTime = instance.getStartTime();
        if (startTime == 0) return 0;
        
        return (System.currentTimeMillis() - startTime) / 1000;
    }
    
    /**
     * Gets remaining lives for the instance
     */
    private int getRemainingLives() {
        // Placeholder - would track deaths
        int maxLives = instance.getTemplate().getMaxLives();
        int deaths = instance.getDeathCount();
        return Math.max(0, maxLives - deaths);
    }
    
    /**
     * Gets the current objective text
     */
    private String getCurrentObjective() {
        switch (instance.getState()) {
            case ACTIVE:
            case IN_PROGRESS:
                int currentRoom = getCurrentRoomNumber();
                int totalRooms = getTotalRooms();
                
                if (currentRoom <= totalRooms) {
                    return "Clear Room " + currentRoom;
                } else {
                    return "Defeat the Boss";
                }
            case BOSS_FIGHT:
                return "§c§lDEFEAT THE BOSS!";
            case COMPLETING:
                return "§a§lDUNGEON COMPLETE!";
            case RESETTING:
                return "Returning to surface...";
            default:
                return "";
        }
    }
    
    /**
     * Formats time in MM:SS format
     */
    private String formatTime(long seconds) {
        long minutes = seconds / 60;
        long secs = seconds % 60;
        
        String color = "§f";
        if (seconds < 60) {
            color = "§c"; // Red for less than 1 minute
        } else if (seconds < 300) {
            color = "§e"; // Yellow for less than 5 minutes
        }
        
        return color + String.format("%02d:%02d", minutes, secs);
    }
    
    /**
     * Gets color code for difficulty
     */
    private String getDifficultyColor(String difficulty) {
        if (difficulty == null) return "§f";
        
        switch (difficulty.toUpperCase()) {
            case "EASY": return "§a";
            case "NORMAL": return "§e";
            case "HARD": return "§c";
            case "NIGHTMARE": return "§4";
            default: return "§f";
        }
    }
    
    /**
     * Creates a progress bar
     */
    private String getProgressBar(double percent) {
        int barLength = 20;
        int filled = (int) (barLength * percent / 100.0);
        
        StringBuilder bar = new StringBuilder("§7[");
        for (int i = 0; i < barLength; i++) {
            if (i < filled) {
                bar.append("§a█");
            } else {
                bar.append("§8█");
            }
        }
        bar.append("§7] ");
        bar.append(String.format("§f%.0f%%", percent));
        
        return bar.toString();
    }
    
    /**
     * Creates heart display for lives
     */
    private String getHearts(int current, int max) {
        StringBuilder hearts = new StringBuilder();
        
        for (int i = 0; i < max; i++) {
            if (i < current) {
                hearts.append("§c❤");
            } else {
                hearts.append("§8❤");
            }
        }
        
        hearts.append(" §f").append(current).append("/").append(max);
        
        return hearts.toString();
    }
    
    /**
     * Updates the last update timestamp
     */
    public void markUpdated() {
        this.lastUpdate = System.currentTimeMillis();
    }
    
    public long getLastUpdate() {
        return lastUpdate;
    }
}
