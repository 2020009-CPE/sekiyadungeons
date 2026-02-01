package com.sekiya.dungeons.gui;

import com.sekiya.dungeons.wand.WandSession;

import java.util.*;

/**
 * Main dungeon configuration GUI
 */
public class DungeonGUI {
    private final String playerName;
    private final WandSession session;
    private final Map<Integer, GUIButton> buttons;
    private String title;
    private int size; // Inventory size (rows * 9)
    
    public DungeonGUI(String playerName, WandSession session) {
        this.playerName = playerName;
        this.session = session;
        this.buttons = new HashMap<>();
        this.title = "§6§lDungeon Configuration";
        this.size = 54; // 6 rows
        
        setupButtons();
    }
    
    /**
     * Sets up all GUI buttons
     */
    private void setupButtons() {
        // Dungeon Info
        GUIButton info = new GUIButton(
            "dungeon_info",
            "§6§lDungeon Info",
            "§7Name: §f" + (session.getDungeonName() != null ? session.getDungeonName() : "Not Set"),
            "book",
            4
        );
        buttons.put(4, info);
        
        // Room Configuration
        GUIButton roomConfig = new GUIButton(
            "room_config",
            "§e§lRoom Configuration",
            "§7Configure rooms\n§7Current: Room " + session.getCurrentRoom(),
            "stone_bricks",
            10
        );
        roomConfig.setOnClick(() -> openRoomConfigGUI());
        buttons.put(10, roomConfig);
        
        // Spawn Configuration
        GUIButton spawnConfig = new GUIButton(
            "spawn_config",
            "§b§lSpawn Configuration",
            "§7Add and configure\n§7enemy spawn points",
            "spawner",
            12
        );
        spawnConfig.setOnClick(() -> openSpawnConfigGUI());
        buttons.put(12, spawnConfig);
        
        // Boss Configuration
        GUIButton bossConfig = new GUIButton(
            "boss_config",
            "§4§lBoss Configuration",
            "§7Configure boss\n§7spawn and type",
            "dragon_head",
            14
        );
        bossConfig.setOnClick(() -> openBossConfigGUI());
        buttons.put(14, bossConfig);
        
        // Dungeon Settings
        GUIButton settings = new GUIButton(
            "settings",
            "§a§lDungeon Settings",
            "§7Configure difficulty,\n§7player limits, etc.",
            "comparator",
            16
        );
        settings.setOnClick(() -> openSettingsGUI());
        buttons.put(16, settings);
        
        // Room Count
        GUIButton roomCount = new GUIButton(
            "room_count",
            "§e§lSet Room Count",
            "§7Click to change\n§7number of rooms",
            "chest",
            28
        );
        roomCount.setOnClick(() -> openRoomCountSelector());
        buttons.put(28, roomCount);
        
        // Preview
        GUIButton preview = new GUIButton(
            "preview",
            "§d§lPreview Dungeon",
            "§7View dungeon layout\n§7and statistics",
            "map",
            30
        );
        preview.setOnClick(() -> openPreviewGUI());
        buttons.put(30, preview);
        
        // Save
        GUIButton save = new GUIButton(
            "save",
            "§a§lSave Dungeon",
            "§7Save current\n§7configuration",
            "emerald",
            32
        );
        save.setOnClick(() -> saveDungeon());
        buttons.put(32, save);
        
        // Cancel
        GUIButton cancel = new GUIButton(
            "cancel",
            "§c§lDiscard Changes",
            "§7Close without saving",
            "barrier",
            49
        );
        cancel.setOnClick(() -> closeGUI());
        buttons.put(49, cancel);
    }
    
    /**
     * Opens the room configuration GUI
     */
    private void openRoomConfigGUI() {
        System.out.println("[GUI] Opening room config for " + playerName);
        RoomConfigGUI roomGUI = new RoomConfigGUI(playerName, session);
        roomGUI.open();
    }
    
    /**
     * Opens the spawn configuration GUI
     */
    private void openSpawnConfigGUI() {
        System.out.println("[GUI] Opening spawn config for " + playerName);
        SpawnConfigGUI spawnGUI = new SpawnConfigGUI(playerName, session);
        spawnGUI.open();
    }
    
    /**
     * Opens the boss configuration GUI
     */
    private void openBossConfigGUI() {
        System.out.println("[GUI] Opening boss config for " + playerName);
        // BossConfigGUI would be implemented similarly
    }
    
    /**
     * Opens the settings GUI
     */
    private void openSettingsGUI() {
        System.out.println("[GUI] Opening settings for " + playerName);
        DungeonSettingsGUI settingsGUI = new DungeonSettingsGUI(playerName, session);
        settingsGUI.open();
    }
    
    /**
     * Opens room count selector
     */
    private void openRoomCountSelector() {
        System.out.println("[GUI] Opening room count selector for " + playerName);
        // Show selector for 1-10 rooms
    }
    
    /**
     * Opens preview GUI
     */
    private void openPreviewGUI() {
        System.out.println("[GUI] Opening preview for " + playerName);
        // Show dungeon statistics and layout
    }
    
    /**
     * Saves the dungeon
     */
    private void saveDungeon() {
        System.out.println("[GUI] Saving dungeon for " + playerName);
        sendMessage("§a§lDungeon saved successfully!");
        closeGUI();
    }
    
    /**
     * Closes the GUI
     */
    private void closeGUI() {
        System.out.println("[GUI] Closing GUI for " + playerName);
        // Would close inventory in actual implementation
    }
    
    /**
     * Sends a message to the player
     */
    private void sendMessage(String message) {
        System.out.println("[GUI] Message to " + playerName + ": " + message);
    }
    
    /**
     * Opens the GUI for the player
     */
    public void open() {
        System.out.println("[GUI] Opening main dungeon GUI for " + playerName);
        System.out.println("[GUI] Title: " + title);
        System.out.println("[GUI] Size: " + size + " slots");
        System.out.println("[GUI] Buttons:");
        
        for (Map.Entry<Integer, GUIButton> entry : buttons.entrySet()) {
            GUIButton button = entry.getValue();
            System.out.println("[GUI]   Slot " + entry.getKey() + ": " + 
                             button.getDisplayName() + " (" + button.getIcon() + ")");
        }
        
        // In actual Hytale implementation:
        // Player player = getPlayer(playerName);
        // Inventory inv = createInventory(title, size);
        // for (Map.Entry<Integer, GUIButton> entry : buttons.entrySet()) {
        //     inv.setItem(entry.getKey(), createItemStack(entry.getValue()));
        // }
        // player.openInventory(inv);
    }
    
    /**
     * Handles button click
     */
    public void handleClick(int slot) {
        GUIButton button = buttons.get(slot);
        if (button != null) {
            button.click();
        }
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getSize() {
        return size;
    }
    
    public Map<Integer, GUIButton> getButtons() {
        return buttons;
    }
}
