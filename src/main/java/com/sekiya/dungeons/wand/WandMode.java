package com.sekiya.dungeons.wand;

/**
 * Different modes the dungeon wand can operate in
 */
public enum WandMode {
    SET_PORTAL("Set Portal", "Click to set portal location", "§6"),
    SET_ENTRY("Set Entry", "Click to set dungeon entry point", "§a"),
    SET_EXIT("Set Exit", "Click to set dungeon exit point", "§c"),
    SET_ROOM_BOUNDS("Set Room Bounds", "Right-click: pos1, Left-click: pos2", "§e"),
    SET_SPAWN_POINT("Set Spawn Point", "Click to add enemy spawn location", "§b"),
    SET_DOOR("Set Door", "Click to set room door location", "§d"),
    SET_BOSS_SPAWN("Set Boss Spawn", "Click to set boss spawn location", "§4"),
    INSPECT("Inspect", "Click to view region information", "§7");
    
    private final String displayName;
    private final String description;
    private final String colorCode;
    
    WandMode(String displayName, String description, String colorCode) {
        this.displayName = displayName;
        this.description = description;
        this.colorCode = colorCode;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getColorCode() {
        return colorCode;
    }
    
    public String getFormattedName() {
        return colorCode + "§l" + displayName;
    }
    
    public String getFormattedDescription() {
        return "§7" + description;
    }
}
