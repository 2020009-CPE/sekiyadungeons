package com.sekiya.dungeons.util;

import com.hypixel.hytale.server.entity.Player;
import com.hypixel.hytale.server.util.Message;
import com.hypixel.hytale.server.util.EventTitleUtil;

/**
 * Utility class for formatting and sending messages using Hytale's Message API
 */
public class MessageUtil {
    
    private static final String PREFIX = "&8[&6SekiyaDungeons&8]&r ";
    
    /**
     * Formats a message with color codes
     */
    public static String format(String message) {
        return message.replace('&', '§');
    }
    
    /**
     * Formats a message with the plugin prefix
     */
    public static String formatWithPrefix(String message) {
        return format(PREFIX + message);
    }
    
    /**
     * Sends a formatted message to a player using Hytale's Message API
     */
    public static void sendMessage(Object player, String message) {
        if (player instanceof Player) {
            ((Player) player).sendMessage(Message.raw(message));
        } else {
            // Fallback for non-player senders
            System.out.println("[Message] " + format(message));
        }
    }
    
    /**
     * Broadcasts a message to all players in a dungeon
     */
    public static void broadcastToDungeon(java.util.Collection<?> players, String message) {
        String formatted = format(message);
        for (Object player : players) {
            if (player instanceof Player) {
                ((Player) player).sendMessage(Message.raw(formatted));
            }
        }
    }
    
    /**
     * Sends an action bar message to a player
     */
    public static void sendActionBar(Object player, String message) {
        if (player instanceof Player) {
            // TODO: Use Hytale's action bar API when available
            ((Player) player).sendMessage(Message.raw(message));
        } else {
            System.out.println("[Action Bar] " + format(message));
        }
    }
    
    /**
     * Sends a title to a player using EventTitleUtil
     */
    public static void sendTitle(Object player, String title, String subtitle, boolean major) {
        if (player instanceof Player) {
            Player p = (Player) player;
            EventTitleUtil.showEventTitleToPlayer(
                p.getPlayerRef(),
                Message.raw(title),
                Message.raw(subtitle),
                major
            );
        } else {
            System.out.println(String.format("[Title] %s - %s (major: %s)", 
                format(title), format(subtitle), major));
        }
    }
    
    /**
     * Sends a title to a player with fade timing (legacy support)
     */
    public static void sendTitle(Object player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        // Convert to new API - use major=false for standard titles
        sendTitle(player, title, subtitle, false);
    }
}
