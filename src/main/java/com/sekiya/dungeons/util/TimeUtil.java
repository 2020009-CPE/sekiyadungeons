package com.sekiya.dungeons.util;

import java.util.concurrent.TimeUnit;

/**
 * Utility class for time-related operations
 */
public class TimeUtil {
    
    /**
     * Formats seconds into a human-readable time string
     */
    public static String formatTime(long seconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long secs = seconds - TimeUnit.MINUTES.toSeconds(minutes);
        
        if (minutes > 0) {
            return String.format("%d:%02d", minutes, secs);
        }
        return String.format("%ds", secs);
    }
    
    /**
     * Formats milliseconds into a human-readable time string
     */
    public static String formatTimeMillis(long millis) {
        return formatTime(TimeUnit.MILLISECONDS.toSeconds(millis));
    }
    
    /**
     * Gets current time in milliseconds
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }
    
    /**
     * Gets current time in seconds
     */
    public static long currentTimeSeconds() {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }
}
