package com.sekiya.dungeons.gui;

/**
 * Represents a button/option in a GUI menu
 */
public class GUIButton {
    private final String id;
    private final String displayName;
    private final String description;
    private final String icon;
    private final int slot;
    private Runnable onClick;
    
    public GUIButton(String id, String displayName, String description, String icon, int slot) {
        this.id = id;
        this.displayName = displayName;
        this.description = description;
        this.icon = icon;
        this.slot = slot;
    }
    
    public String getId() {
        return id;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public int getSlot() {
        return slot;
    }
    
    public Runnable getOnClick() {
        return onClick;
    }
    
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }
    
    public void click() {
        if (onClick != null) {
            onClick.run();
        }
    }
}
