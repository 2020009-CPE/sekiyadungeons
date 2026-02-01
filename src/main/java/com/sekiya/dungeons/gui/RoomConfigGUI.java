package com.sekiya.dungeons.gui;

import com.sekiya.dungeons.wand.WandMode;
import com.sekiya.dungeons.wand.WandSession;

import java.util.HashMap;
import java.util.Map;

/**
 * GUI for configuring room settings
 */
public class RoomConfigGUI {
    private final String playerName;
    private final WandSession session;
    private final Map<Integer, GUIButton> buttons;
    private String title;
    private int size;
    
    public RoomConfigGUI(String playerName, WandSession session) {
        this.playerName = playerName;
        this.session = session;
        this.buttons = new HashMap<>();
        this.title = "§e§lRoom " + session.getCurrentRoom() + " Configuration";
        this.size = 45; // 5 rows
        
        setupButtons();
    }
    
    private void setupButtons() {
        // Select Room Number
        for (int i = 1; i <= 10; i++) {
            final int roomNum = i;
            GUIButton roomButton = new GUIButton(
                "room_" + i,
                "§e§lRoom " + i,
                session.getCurrentRoom() == i ? "§a§l✓ Currently Selected" : "§7Click to select",
                "stone_bricks",
                i - 1 + 10 // Slots 10-19
            );
            roomButton.setOnClick(() -> selectRoom(roomNum));
            buttons.put(i - 1 + 10, roomButton);
        }
        
        // Set Bounds
        GUIButton setBounds = new GUIButton(
            "set_bounds",
            "§e§lSet Room Bounds",
            "§7Switch to bounds mode\n§7Right-click: pos1\n§7Left-click: pos2",
            "golden_pickaxe",
            28
        );
        setBounds.setOnClick(() -> switchMode(WandMode.SET_ROOM_BOUNDS));
        buttons.put(28, setBounds);
        
        // Set Door
        GUIButton setDoor = new GUIButton(
            "set_door",
            "§d§lSet Room Door",
            "§7Switch to door mode\n§7Click to set door location",
            "iron_door",
            30
        );
        setDoor.setOnClick(() -> switchMode(WandMode.SET_DOOR));
        buttons.put(30, setDoor);
        
        // Add Spawns
        GUIButton addSpawns = new GUIButton(
            "add_spawns",
            "§b§lAdd Spawn Points",
            "§7Open spawn configuration",
            "spawner",
            32
        );
        addSpawns.setOnClick(() -> openSpawnConfig());
        buttons.put(32, addSpawns);
        
        // Room Info
        GUIButton info = new GUIButton(
            "room_info",
            "§6§lRoom Info",
            "§7View room details\n§7and statistics",
            "book",
            34
        );
        info.setOnClick(() -> showRoomInfo());
        buttons.put(34, info);
        
        // Back
        GUIButton back = new GUIButton(
            "back",
            "§c§lBack",
            "§7Return to main menu",
            "arrow",
            40
        );
        back.setOnClick(() -> goBack());
        buttons.put(40, back);
    }
    
    private void selectRoom(int roomNum) {
        session.setCurrentRoom(roomNum);
        sendMessage("§aSelected Room " + roomNum);
        // Refresh GUI to show new selection
        open();
    }
    
    private void switchMode(WandMode mode) {
        session.setMode(mode);
        sendMessage("§aWand mode: " + mode.getFormattedName());
        sendMessage("§7" + mode.getDescription());
        close();
    }
    
    private void openSpawnConfig() {
        close();
        SpawnConfigGUI spawnGUI = new SpawnConfigGUI(playerName, session);
        spawnGUI.open();
    }
    
    private void showRoomInfo() {
        sendMessage("§6§lRoom " + session.getCurrentRoom() + " Information");
        sendMessage("§7Bounds: " + (session.hasBothPositions() ? "§a✓ Set" : "§c✗ Not Set"));
        // Would show more detailed info from draft dungeon
    }
    
    private void goBack() {
        close();
        DungeonGUI mainGUI = new DungeonGUI(playerName, session);
        mainGUI.open();
    }
    
    private void close() {
        System.out.println("[GUI] Closing room config GUI for " + playerName);
    }
    
    private void sendMessage(String message) {
        System.out.println("[GUI] Message to " + playerName + ": " + message);
    }
    
    public void open() {
        System.out.println("[GUI] Opening room config GUI for " + playerName);
        System.out.println("[GUI] Current room: " + session.getCurrentRoom());
        
        // In actual implementation, would create and show inventory GUI
    }
}
