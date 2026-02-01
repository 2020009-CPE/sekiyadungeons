package com.hypixel.hytale.server.util;

import com.hypixel.hytale.server.entity.PlayerRef;

/**
 * Stub implementation of Hytale's EventTitleUtil class
 * This is a placeholder until the actual Hytale API is available
 */
public class EventTitleUtil {
    public static void showEventTitleToPlayer(PlayerRef playerRef, Message title, Message subtitle, boolean isMajor) {
        String majorStr = isMajor ? " [MAJOR]" : "";
        System.out.println("[Title" + majorStr + "] " + title.getText() + " - " + subtitle.getText());
    }
}
