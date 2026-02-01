package com.sekiya.dungeons.completion;

import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.util.MessageUtil;

/**
 * Task that runs a countdown after dungeon completion
 */
public class CountdownTask implements Runnable {
    private final DungeonInstance instance;
    private final CompletionHandler handler;
    private int remainingSeconds;
    
    public CountdownTask(DungeonInstance instance, CompletionHandler handler, int countdownSeconds) {
        this.instance = instance;
        this.handler = handler;
        this.remainingSeconds = countdownSeconds;
    }
    
    @Override
    public void run() {
        if (remainingSeconds > 0) {
            // Send countdown message to all players
            String message = String.format("&eReturning to surface in &c%d&e seconds...", remainingSeconds);
            
            for (String playerName : instance.getPlayers()) {
                // Placeholder: Get actual player object and send message
                MessageUtil.sendMessage(playerName, message);
                
                // Send title for dramatic effect at certain intervals
                if (remainingSeconds <= 10 || remainingSeconds % 10 == 0) {
                    MessageUtil.sendTitle(playerName, 
                        "&6&lDUNGEON COMPLETE!", 
                        String.format("&eReturning in %d seconds", remainingSeconds),
                        10, 20, 10);
                }
            }
            
            remainingSeconds--;
            
            // Schedule next tick (this would use actual scheduler in real implementation)
            // For now, this is a placeholder
        } else {
            // Countdown finished, trigger teleport
            handler.onCountdownComplete(instance);
        }
    }
    
    public int getRemainingSeconds() {
        return remainingSeconds;
    }
}
