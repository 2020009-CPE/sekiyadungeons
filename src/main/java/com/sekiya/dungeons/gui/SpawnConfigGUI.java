package com.sekiya.dungeons.gui;

import com.sekiya.dungeons.wand.WandMode;
import com.sekiya.dungeons.wand.WandSession;

import java.util.HashMap;
import java.util.Map;

/**
 * GUI for configuring enemy spawns
 */
public class SpawnConfigGUI {
    private final String playerName;
    private final WandSession session;
    private final Map<Integer, GUIButton> buttons;
    private String title;
    private int size;
    
    // Enemy types
    private static final String[] ENEMY_TYPES = {
        "skeleton", "zombie", "spider", "witch", "creeper",
        "enderman", "blaze", "wither_skeleton", "piglin", "hoglin"
    };
    
    private String selectedEnemyType = "skeleton";
    private int selectedCount = 3;
    
    public SpawnConfigGUI(String playerName, WandSession session) {
        this.playerName = playerName;
        this.session = session;
        this.buttons = new HashMap<>();
        this.title = "§b§lSpawn Configuration";
        this.size = 54; // 6 rows
        
        setupButtons();
    }
    
    private void setupButtons() {
        // Enemy type selection (top rows)
        int slot = 10;
        for (String enemyType : ENEMY_TYPES) {
            final String type = enemyType;
            GUIButton enemyButton = new GUIButton(
                "enemy_" + enemyType,
                "§e§l" + formatName(enemyType),
                selectedEnemyType.equals(enemyType) ? 
                    "§a§l✓ Selected" : "§7Click to select",
                getEnemyIcon(enemyType),
                slot
            );
            enemyButton.setOnClick(() -> selectEnemyType(type));
            buttons.put(slot, enemyButton);
            slot++;
            
            if (slot == 17) slot = 19; // Skip to next row
            if (slot == 26) break; // Max 2 rows of enemies
        }
        
        // Count selection
        for (int i = 1; i <= 10; i++) {
            final int count = i;
            GUIButton countButton = new GUIButton(
                "count_" + i,
                "§f§l" + i + " Enemies",
                selectedCount == i ? "§a§l✓ Selected" : "§7Click to select",
                "egg",
                27 + i
            );
            countButton.setOnClick(() -> selectCount(count));
            buttons.put(27 + i, countButton);
        }
        
        // Set spawn point
        GUIButton setSpawn = new GUIButton(
            "set_spawn",
            "§b§lSet Spawn Point",
            "§7Switch to spawn mode\n§7Click to add spawn\n§e" + 
                selectedCount + "x " + formatName(selectedEnemyType),
            "spawner",
            40
        );
        setSpawn.setOnClick(() -> setSpawnMode());
        buttons.put(40, setSpawn);
        
        // Current selection info
        GUIButton info = new GUIButton(
            "info",
            "§6§lCurrent Selection",
            "§7Type: §f" + formatName(selectedEnemyType) + "\n" +
            "§7Count: §f" + selectedCount,
            "book",
            4
        );
        buttons.put(4, info);
        
        // Back
        GUIButton back = new GUIButton(
            "back",
            "§c§lBack",
            "§7Return to room config",
            "arrow",
            49
        );
        back.setOnClick(() -> goBack());
        buttons.put(49, back);
    }
    
    private void selectEnemyType(String enemyType) {
        this.selectedEnemyType = enemyType;
        sendMessage("§aSelected enemy type: §e" + formatName(enemyType));
        // Refresh GUI
        buttons.clear();
        setupButtons();
        open();
    }
    
    private void selectCount(int count) {
        this.selectedCount = count;
        sendMessage("§aSelected count: §e" + count);
        // Refresh GUI
        buttons.clear();
        setupButtons();
        open();
    }
    
    private void setSpawnMode() {
        session.setMode(WandMode.SET_SPAWN_POINT);
        session.setSessionData("spawnEnemyType", selectedEnemyType);
        session.setSessionData("spawnCount", selectedCount);
        
        sendMessage("§aWand mode: " + WandMode.SET_SPAWN_POINT.getFormattedName());
        sendMessage("§7Click to add spawn point");
        sendMessage("§e" + selectedCount + "x " + formatName(selectedEnemyType));
        close();
    }
    
    private void goBack() {
        close();
        RoomConfigGUI roomGUI = new RoomConfigGUI(playerName, session);
        roomGUI.open();
    }
    
    private void close() {
        System.out.println("[GUI] Closing spawn config GUI for " + playerName);
    }
    
    private void sendMessage(String message) {
        System.out.println("[GUI] Message to " + playerName + ": " + message);
    }
    
    private String formatName(String name) {
        return name.substring(0, 1).toUpperCase() + 
               name.substring(1).toLowerCase().replace("_", " ");
    }
    
    private String getEnemyIcon(String enemyType) {
        // Map enemy types to icons (item types)
        switch (enemyType) {
            case "skeleton": return "bow";
            case "zombie": return "rotten_flesh";
            case "spider": return "spider_eye";
            case "witch": return "potion";
            case "creeper": return "gunpowder";
            case "enderman": return "ender_pearl";
            case "blaze": return "blaze_rod";
            case "wither_skeleton": return "wither_skeleton_skull";
            case "piglin": return "gold_ingot";
            case "hoglin": return "porkchop";
            default: return "egg";
        }
    }
    
    public void open() {
        System.out.println("[GUI] Opening spawn config GUI for " + playerName);
        System.out.println("[GUI] Selected: " + selectedCount + "x " + selectedEnemyType);
        
        // In actual implementation, would create and show inventory GUI
    }
}
