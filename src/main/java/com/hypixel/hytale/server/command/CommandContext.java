package com.hypixel.hytale.server.command;

import com.hypixel.hytale.server.entity.Player;

/**
 * Stub implementation of Hytale's CommandContext class
 * This is a placeholder until the actual Hytale API is available
 */
public class CommandContext {
    private final CommandSender sender;
    private final String[] args;
    
    public CommandContext(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }
    
    public CommandSender getSender() {
        return sender;
    }
    
    public <T> T senderAs(Class<T> clazz) {
        if (sender instanceof Player && clazz == Player.class) {
            return clazz.cast(sender);
        }
        return null;
    }
    
    public String[] getArgs() {
        return args;
    }
}
