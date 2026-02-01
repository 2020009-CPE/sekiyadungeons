package com.sekiya.dungeons.util;

/**
 * Utility class for formatting and sending messages
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
     * Sends a formatted message to a player
     * Note: This is a placeholder - actual implementation depends on Hytale API
     */
    public static void sendMessage(Object player, String message) {
        // Placeholder for Hytale's player.sendMessage() equivalent
        System.out.println("[Message to player] " + format(message));
    }
    
    /**
     * Broadcasts a message to all players in a dungeon
     */
    public static void broadcastToDungeon(java.util.Collection<?> players, String message) {
        String formatted = format(message);
        for (Object player : players) {
            sendMessage(player, formatted);
        }
    }
    
    /**
     * Sends an action bar message to a player
     */
    public static void sendActionBar(Object player, String message) {
        // Placeholder for Hytale's action bar API
        System.out.println("[Action Bar] " + format(message));
    }
    
    /**
     * Sends a title to a player
     */
    public static void sendTitle(Object player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        // Placeholder for Hytale's title API
        System.out.println(String.format("[Title] %s - %s", format(title), format(subtitle)));
    }
}
