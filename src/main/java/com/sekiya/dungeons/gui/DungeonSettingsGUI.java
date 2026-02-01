package com.sekiya.dungeons.gui;

import com.sekiya.dungeons.wand.WandSession;

import java.util.HashMap;
import java.util.Map;

/**
 * GUI for dungeon settings (difficulty, player limits, etc.)
 */
public class DungeonSettingsGUI {
    private final String playerName;
    private final WandSession session;
    private final Map<Integer, GUIButton> buttons;
    private String title;
    private int size;
    
    public DungeonSettingsGUI(String playerName, WandSession session) {
        this.playerName = playerName;
        this.session = session;
        this.buttons = new HashMap<>();
        this.title = "§a§lDungeon Settings";
        this.size = 54; // 6 rows
        
        setupButtons();
    }
    
    private void setupButtons() {
        // Difficulty selection
        String[] difficulties = {"EASY", "NORMAL", "HARD", "NIGHTMARE"};
        String[] diffColors = {"§a", "§e", "§c", "§4"};
        
        for (int i = 0; i < difficulties.length; i++) {
            final String diff = difficulties[i];
            GUIButton diffButton = new GUIButton(
                "diff_" + diff,
                diffColors[i] + "§l" + diff,
                "§7Click to set difficulty",
                getDifficultyIcon(diff),
                10 + i * 2
            );
            diffButton.setOnClick(() -> setDifficulty(diff));
            buttons.put(10 + i * 2, diffButton);
        }
        
        // Min/Max players
        GUIButton minPlayers = new GUIButton(
            "min_players",
            "§e§lMin Players",
            "§7Click to set minimum\n§7Current: §f1",
            "player_head",
            19
        );
        minPlayers.setOnClick(() -> adjustMinPlayers());
        buttons.put(19, minPlayers);
        
        GUIButton maxPlayers = new GUIButton(
            "max_players",
            "§e§lMax Players",
            "§7Click to set maximum\n§7Current: §f4",
            "player_head",
            21
        );
        maxPlayers.setOnClick(() -> adjustMaxPlayers());
        buttons.put(21, maxPlayers);
        
        // Time limit
        GUIButton timeLimit = new GUIButton(
            "time_limit",
            "§6§lTime Limit",
            "§7Click to set time limit\n§7Current: §f30 minutes",
            "clock",
            23
        );
        timeLimit.setOnClick(() -> adjustTimeLimit());
        buttons.put(23, timeLimit);
        
        // Party requirement
        GUIButton partyReq = new GUIButton(
            "party_req",
            "§d§lRequire Party",
            "§7Toggle party requirement\n§7Current: §cDisabled",
            "lead",
            25
        );
        partyReq.setOnClick(() -> togglePartyRequirement());
        buttons.put(25, partyReq);
        
        // Scaling
        GUIButton scaling = new GUIButton(
            "scaling",
            "§b§lEnemy Scaling",
            "§7Toggle enemy scaling\n§7Current: §aEnabled",
            "diamond_sword",
            28
        );
        scaling.setOnClick(() -> toggleScaling());
        buttons.put(28, scaling);
        
        // Lives system
        GUIButton lives = new GUIButton(
            "lives",
            "§c§lMax Lives",
            "§7Click to set max lives\n§7Current: §fUnlimited",
            "totem_of_undying",
            30
        );
        lives.setOnClick(() -> adjustMaxLives());
        buttons.put(30, lives);
        
        // Checkpoints
        GUIButton checkpoints = new GUIButton(
            "checkpoints",
            "§a§lCheckpoints",
            "§7Toggle checkpoints\n§7Current: §cDisabled",
            "beacon",
            32
        );
        checkpoints.setOnClick(() -> toggleCheckpoints());
        buttons.put(32, checkpoints);
        
        // Time attack
        GUIButton timeAttack = new GUIButton(
            "time_attack",
            "§e§lTime Attack",
            "§7Toggle time attack mode\n§7Current: §cDisabled",
            "diamond",
            34
        );
        timeAttack.setOnClick(() -> toggleTimeAttack());
        buttons.put(34, timeAttack);
        
        // Back
        GUIButton back = new GUIButton(
            "back",
            "§c§lBack",
            "§7Return to main menu",
            "arrow",
            49
        );
        back.setOnClick(() -> goBack());
        buttons.put(49, back);
    }
    
    private void setDifficulty(String difficulty) {
        sendMessage("§aSet difficulty: " + difficulty);
        // Would update draft dungeon
    }
    
    private void adjustMinPlayers() {
        sendMessage("§aAdjusting minimum players...");
        // Would show number selector
    }
    
    private void adjustMaxPlayers() {
        sendMessage("§aAdjusting maximum players...");
        // Would show number selector
    }
    
    private void adjustTimeLimit() {
        sendMessage("§aAdjusting time limit...");
        // Would show time selector
    }
    
    private void togglePartyRequirement() {
        sendMessage("§aToggled party requirement");
        // Would toggle in draft dungeon
    }
    
    private void toggleScaling() {
        sendMessage("§aToggled enemy scaling");
        // Would toggle in draft dungeon
    }
    
    private void adjustMaxLives() {
        sendMessage("§aAdjusting max lives...");
        // Would show number selector
    }
    
    private void toggleCheckpoints() {
        sendMessage("§aToggled checkpoints");
        // Would toggle in draft dungeon
    }
    
    private void toggleTimeAttack() {
        sendMessage("§aToggled time attack mode");
        // Would toggle in draft dungeon
    }
    
    private void goBack() {
        close();
        DungeonGUI mainGUI = new DungeonGUI(playerName, session);
        mainGUI.open();
    }
    
    private void close() {
        System.out.println("[GUI] Closing settings GUI for " + playerName);
    }
    
    private void sendMessage(String message) {
        System.out.println("[GUI] Message to " + playerName + ": " + message);
    }
    
    private String getDifficultyIcon(String difficulty) {
        switch (difficulty) {
            case "EASY": return "lime_dye";
            case "NORMAL": return "yellow_dye";
            case "HARD": return "red_dye";
            case "NIGHTMARE": return "wither_rose";
            default: return "paper";
        }
    }
    
    public void open() {
        System.out.println("[GUI] Opening settings GUI for " + playerName);
        
        // In actual implementation, would create and show inventory GUI
    }
}
