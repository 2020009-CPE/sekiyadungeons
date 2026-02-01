package com.hypixel.hytale.server.util;

/**
 * Stub implementation of Hytale's Message class
 * This is a placeholder until the actual Hytale API is available
 */
public class Message {
    private final String text;
    
    private Message(String text) {
        this.text = text;
    }
    
    public static Message raw(String text) {
        return new Message(text);
    }
    
    public String getText() {
        return text.replace('&', '§');
    }
}
