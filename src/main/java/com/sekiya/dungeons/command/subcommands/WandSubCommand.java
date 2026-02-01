package com.sekiya.dungeons.command.subcommands;

import com.sekiya.dungeons.SekiyaDungeons;
import com.sekiya.dungeons.command.SubCommand;
import com.sekiya.dungeons.gui.DungeonGUI;
import com.sekiya.dungeons.wand.WandManager;
import com.sekiya.dungeons.wand.WandMode;
import com.sekiya.dungeons.wand.WandSession;

/**
 * /dungeon wand [action] [params]
 * Manages the dungeon wand for manual creation
 */
public class WandSubCommand implements SubCommand {
    private final SekiyaDungeons plugin;
    private final WandManager wandManager;
    
    public WandSubCommand(SekiyaDungeons plugin, WandManager wandManager) {
        this.plugin = plugin;
        this.wandManager = wandManager;
    }
    
    @Override
    public String getName() {
        return "wand";
    }
    
    @Override
    public String getDescription() {
        return "Get and manage the dungeon creation wand";
    }
    
    @Override
    public String getUsage() {
        return "wand [get|mode|menu|room|info|clear|save|discard] [args]";
    }
    
    @Override
    public boolean execute(Object sender, String[] args) {
        String playerName = sender.toString(); // Simplified for placeholder
        
        if (args.length == 0) {
            // Give wand with default settings
            giveWand(playerName);
            return true;
        }
        
        String action = args[0].toLowerCase();
        
        switch (action) {
            case "get":
                giveWand(playerName);
                return true;
                
            case "mode":
                return setMode(playerName, args);
                
            case "menu":
            case "gui":
                return openGUI(playerName, args);
                
            case "room":
                return setRoom(playerName, args);
                
            case "info":
                return showInfo(playerName);
                
            case "clear":
                return clearSession(playerName);
                
            case "save":
                return saveDungeon(playerName, args);
                
            case "discard":
                return discardDungeon(playerName, args);
                
            default:
                sendMessage(sender, "§cUnknown action: " + action);
                sendMessage(sender, "§7Usage: /dungeon " + getUsage());
                return false;
        }
    }
    
    private void giveWand(String playerName) {
        // In actual implementation, would give wand item
        sendMessage(playerName, "§a§lDungeon Wand§r §agiven!");
        sendMessage(playerName, "§7Right-click: Set position 1");
        sendMessage(playerName, "§7Left-click: Set position 2");
        sendMessage(playerName, "§7Shift+Right-click: Open menu");
        sendMessage(playerName, "§7Use §e/dungeon wand mode§7 to change modes");
        
        // Create session
        WandSession session = wandManager.getSession(playerName);
        sendMessage(playerName, "§7Current mode: " + session.getMode().getFormattedName());
    }
    
    private boolean setMode(String playerName, String[] args) {
        if (args.length < 2) {
            sendMessage(playerName, "§cUsage: /dungeon wand mode <mode>");
            sendMessage(playerName, "§7Modes: SET_PORTAL, SET_ENTRY, SET_EXIT, SET_ROOM_BOUNDS,");
            sendMessage(playerName, "        SET_SPAWN_POINT, SET_DOOR, SET_BOSS_SPAWN, INSPECT");
            return false;
        }
        
        String modeName = args[1].toUpperCase();
        
        try {
            WandMode mode = WandMode.valueOf(modeName);
            WandSession session = wandManager.getSession(playerName);
            session.setMode(mode);
            
            sendMessage(playerName, "§aWand mode: " + mode.getFormattedName());
            sendMessage(playerName, "§7" + mode.getDescription());
            return true;
        } catch (IllegalArgumentException e) {
            sendMessage(playerName, "§cInvalid mode: " + modeName);
            return false;
        }
    }
    
    private boolean openGUI(String playerName, String[] args) {
        WandSession session = wandManager.getSession(playerName);
        
        // Check if dungeon name is set
        if (session.getDungeonName() == null) {
            if (args.length < 2) {
                sendMessage(playerName, "§cUsage: /dungeon wand menu <dungeonName>");
                sendMessage(playerName, "§7Or set dungeon name first with: /dungeon wand menu <name>");
                return false;
            }
            session.setDungeonName(args[1]);
        }
        
        DungeonGUI gui = new DungeonGUI(playerName, session);
        gui.open();
        
        sendMessage(playerName, "§aOpening dungeon configuration menu...");
        return true;
    }
    
    private boolean setRoom(String playerName, String[] args) {
        if (args.length < 2) {
            sendMessage(playerName, "§cUsage: /dungeon wand room <number>");
            return false;
        }
        
        try {
            int roomNumber = Integer.parseInt(args[1]);
            
            if (roomNumber < 1 || roomNumber > 20) {
                sendMessage(playerName, "§cRoom number must be between 1 and 20");
                return false;
            }
            
            WandSession session = wandManager.getSession(playerName);
            session.setCurrentRoom(roomNumber);
            
            sendMessage(playerName, "§aSelected room: §e" + roomNumber);
            sendMessage(playerName, "§7Now editing Room " + roomNumber);
            return true;
        } catch (NumberFormatException e) {
            sendMessage(playerName, "§cInvalid room number: " + args[1]);
            return false;
        }
    }
    
    private boolean showInfo(String playerName) {
        WandSession session = wandManager.getSession(playerName);
        String summary = session.getSummary();
        
        sendMessage(playerName, summary);
        return true;
    }
    
    private boolean clearSession(String playerName) {
        WandSession session = wandManager.getSession(playerName);
        session.clearPositions();
        
        sendMessage(playerName, "§aCleared position selections");
        return true;
    }
    
    private boolean saveDungeon(String playerName, String[] args) {
        WandSession session = wandManager.getSession(playerName);
        
        if (session.getDungeonName() == null) {
            if (args.length < 2) {
                sendMessage(playerName, "§cUsage: /dungeon wand save <dungeonName>");
                return false;
            }
            session.setDungeonName(args[1]);
        }
        
        String dungeonName = session.getDungeonName();
        
        try {
            wandManager.saveDraftDungeon(playerName, dungeonName);
            sendMessage(playerName, "§a§lDungeon saved: §e" + dungeonName);
            sendMessage(playerName, "§7You can now use this dungeon!");
            return true;
        } catch (Exception e) {
            sendMessage(playerName, "§cError saving dungeon: " + e.getMessage());
            return false;
        }
    }
    
    private boolean discardDungeon(String playerName, String[] args) {
        WandSession session = wandManager.getSession(playerName);
        
        if (session.getDungeonName() == null) {
            sendMessage(playerName, "§cNo dungeon being edited");
            return false;
        }
        
        String dungeonName = session.getDungeonName();
        wandManager.discardDraft(playerName, dungeonName);
        session.setDungeonName(null);
        
        sendMessage(playerName, "§cDiscarded changes to: " + dungeonName);
        return true;
    }
    
    private void sendMessage(Object sender, String message) {
        System.out.println("[" + sender + "] " + message);
    }
}
