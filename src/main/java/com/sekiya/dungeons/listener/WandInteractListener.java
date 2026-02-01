package com.sekiya.dungeons.listener;

import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.wand.WandManager;
import com.sekiya.dungeons.wand.WandMode;
import com.sekiya.dungeons.wand.WandSession;

/**
 * Listens for wand interactions
 */
public class WandInteractListener {
    private final WandManager wandManager;
    
    public WandInteractListener(WandManager wandManager) {
        this.wandManager = wandManager;
    }
    
    /**
     * Handles left-click (interact) with wand
     */
    public void onLeftClick(String playerName, Location location, boolean isShiftClick) {
        if (!wandManager.hasSession(playerName)) {
            return;
        }
        
        WandSession session = wandManager.getSession(playerName);
        WandMode mode = session.getMode();
        
        if (isShiftClick) {
            // Shift+Click opens GUI
            sendMessage(playerName, "§7Opening wand menu...");
            // Would open GUI here
            return;
        }
        
        switch (mode) {
            case SET_ROOM_BOUNDS:
                handleSetBounds(session, location, false);
                break;
                
            case SET_PORTAL:
                handleSetPortal(session, location);
                break;
                
            case SET_ENTRY:
                handleSetEntry(session, location);
                break;
                
            case SET_EXIT:
                handleSetExit(session, location);
                break;
                
            case SET_SPAWN_POINT:
                handleSetSpawn(session, location);
                break;
                
            case SET_DOOR:
                handleSetDoor(session, location);
                break;
                
            case SET_BOSS_SPAWN:
                handleSetBossSpawn(session, location);
                break;
                
            case INSPECT:
                handleInspect(session, location);
                break;
        }
    }
    
    /**
     * Handles right-click (interact) with wand
     */
    public void onRightClick(String playerName, Location location, boolean isShiftClick) {
        if (!wandManager.hasSession(playerName)) {
            return;
        }
        
        WandSession session = wandManager.getSession(playerName);
        WandMode mode = session.getMode();
        
        if (isShiftClick) {
            // Shift+Right-Click opens GUI
            sendMessage(playerName, "§7Opening wand menu...");
            // Would open GUI here
            return;
        }
        
        switch (mode) {
            case SET_ROOM_BOUNDS:
                handleSetBounds(session, location, true);
                break;
                
            case SET_PORTAL:
                handleSetPortal(session, location);
                break;
                
            case SET_ENTRY:
                handleSetEntry(session, location);
                break;
                
            case SET_EXIT:
                handleSetExit(session, location);
                break;
                
            case SET_SPAWN_POINT:
                handleSetSpawn(session, location);
                break;
                
            case SET_DOOR:
                handleSetDoor(session, location);
                break;
                
            case SET_BOSS_SPAWN:
                handleSetBossSpawn(session, location);
                break;
                
            case INSPECT:
                handleInspect(session, location);
                break;
        }
    }
    
    private void handleSetBounds(WandSession session, Location location, boolean isPosition1) {
        if (isPosition1) {
            session.setPosition1(location);
            sendMessage(session.getPlayerName(), "§aPosition 1 set: §f" + formatLocation(location));
            spawnParticles(location, "green");
            
            if (session.hasBothPositions()) {
                wandManager.setRoomBounds(session);
                sendMessage(session.getPlayerName(), "§a§lRoom " + session.getCurrentRoom() + " bounds set!");
                sendMessage(session.getPlayerName(), "§7Volume: " + calculateVolume(session));
            }
        } else {
            session.setPosition2(location);
            sendMessage(session.getPlayerName(), "§aPosition 2 set: §f" + formatLocation(location));
            spawnParticles(location, "red");
            
            if (session.hasBothPositions()) {
                wandManager.setRoomBounds(session);
                sendMessage(session.getPlayerName(), "§a§lRoom " + session.getCurrentRoom() + " bounds set!");
                sendMessage(session.getPlayerName(), "§7Volume: " + calculateVolume(session));
            }
        }
    }
    
    private void handleSetPortal(WandSession session, Location location) {
        wandManager.setPortal(session, location);
        sendMessage(session.getPlayerName(), "§a§lPortal location set!");
        sendMessage(session.getPlayerName(), "§7Location: " + formatLocation(location));
        spawnParticles(location, "purple");
        playSound(session.getPlayerName(), "entity.enderman.teleport");
    }
    
    private void handleSetEntry(WandSession session, Location location) {
        wandManager.setEntry(session, location);
        sendMessage(session.getPlayerName(), "§a§lEntry point set!");
        sendMessage(session.getPlayerName(), "§7Location: " + formatLocation(location));
        spawnParticles(location, "green");
        playSound(session.getPlayerName(), "entity.player.levelup");
    }
    
    private void handleSetExit(WandSession session, Location location) {
        wandManager.setExit(session, location);
        sendMessage(session.getPlayerName(), "§c§lExit point set!");
        sendMessage(session.getPlayerName(), "§7Location: " + formatLocation(location));
        spawnParticles(location, "red");
        playSound(session.getPlayerName(), "entity.player.levelup");
    }
    
    private void handleSetSpawn(WandSession session, Location location) {
        String enemyType = (String) session.getSessionData("spawnEnemyType");
        Integer count = (Integer) session.getSessionData("spawnCount");
        
        if (enemyType == null) enemyType = "skeleton";
        if (count == null) count = 3;
        
        wandManager.addSpawnPoint(session, location, enemyType, count);
        
        sendMessage(session.getPlayerName(), "§b§lSpawn point added!");
        sendMessage(session.getPlayerName(), "§7Type: §f" + enemyType);
        sendMessage(session.getPlayerName(), "§7Count: §f" + count);
        sendMessage(session.getPlayerName(), "§7Location: " + formatLocation(location));
        spawnParticles(location, "blue");
        playSound(session.getPlayerName(), "block.spawner.spawn");
    }
    
    private void handleSetDoor(WandSession session, Location location) {
        wandManager.setDoor(session, location);
        sendMessage(session.getPlayerName(), "§d§lDoor location set!");
        sendMessage(session.getPlayerName(), "§7Room: " + session.getCurrentRoom());
        sendMessage(session.getPlayerName(), "§7Location: " + formatLocation(location));
        spawnParticles(location, "magenta");
        playSound(session.getPlayerName(), "block.iron_door.open");
    }
    
    private void handleSetBossSpawn(WandSession session, Location location) {
        String bossType = (String) session.getSessionData("bossType");
        if (bossType == null) bossType = "ancient_guardian";
        
        wandManager.setBossSpawn(session, location, bossType);
        
        sendMessage(session.getPlayerName(), "§4§l§nBOSS SPAWN SET!");
        sendMessage(session.getPlayerName(), "§7Type: §c" + bossType);
        sendMessage(session.getPlayerName(), "§7Location: " + formatLocation(location));
        spawnParticles(location, "dark_red");
        playSound(session.getPlayerName(), "entity.wither.spawn");
    }
    
    private void handleInspect(WandSession session, Location location) {
        String info = wandManager.getInspectionInfo(location);
        sendMessage(session.getPlayerName(), info);
        spawnParticles(location, "white");
    }
    
    private String formatLocation(Location loc) {
        return String.format("%.0f, %.0f, %.0f", loc.getX(), loc.getY(), loc.getZ());
    }
    
    private int calculateVolume(WandSession session) {
        Location pos1 = session.getPosition1();
        Location pos2 = session.getPosition2();
        
        int dx = (int) Math.abs(pos2.getX() - pos1.getX()) + 1;
        int dy = (int) Math.abs(pos2.getY() - pos1.getY()) + 1;
        int dz = (int) Math.abs(pos2.getZ() - pos1.getZ()) + 1;
        
        return dx * dy * dz;
    }
    
    private void sendMessage(String playerName, String message) {
        System.out.println("[WAND] " + playerName + ": " + message);
    }
    
    private void spawnParticles(Location location, String color) {
        // PLACEHOLDER: Would spawn particles in Hytale
        System.out.println("[PARTICLES] " + color + " at " + formatLocation(location));
    }
    
    private void playSound(String playerName, String sound) {
        // PLACEHOLDER: Would play sound in Hytale
        System.out.println("[SOUND] " + sound + " for " + playerName);
    }
}
