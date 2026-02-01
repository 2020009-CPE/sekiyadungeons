package com.hypixel.hytale.server.entity;

import com.hypixel.hytale.server.util.Message;
import com.hypixel.hytale.server.command.CommandSender;

/**
 * Stub implementation of Hytale's Player class
 * This is a placeholder until the actual Hytale API is available
 */
public class Player implements CommandSender {
    private final String name;
    private final World world;
    private final PlayerRef playerRef;
    
    public Player(String name) {
        this.name = name;
        this.world = new World();
        this.playerRef = new PlayerRef(this);
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public void sendMessage(Message message) {
        System.out.println("[Player " + name + "] " + message.getText());
    }
    
    public World getWorld() {
        return world;
    }
    
    public PlayerRef getPlayerRef() {
        return playerRef;
    }
}
