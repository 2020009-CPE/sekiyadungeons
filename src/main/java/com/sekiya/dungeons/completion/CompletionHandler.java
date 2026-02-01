package com.sekiya.dungeons.completion;

import com.sekiya.dungeons.instance.DungeonInstance;
import com.sekiya.dungeons.instance.DungeonManager;
import com.sekiya.dungeons.portal.PortalManager;
import com.sekiya.dungeons.reset.DungeonResetter;
import com.sekiya.dungeons.util.Location;
import com.sekiya.dungeons.util.MessageUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles dungeon completion and rewards
 */
public class CompletionHandler {
    private final DungeonManager dungeonManager;
    private final PortalManager portalManager;
    private final DungeonResetter resetter;
    private final Map<String, CountdownTask> activeCountdowns;
    
    public CompletionHandler(DungeonManager dungeonManager, PortalManager portalManager, DungeonResetter resetter) {
        this.dungeonManager = dungeonManager;
        this.portalManager = portalManager;
        this.resetter = resetter;
        this.activeCountdowns = new HashMap<>();
    }
    
    /**
     * Handles dungeon completion
     */
    public void handleCompletion(DungeonInstance instance) {
        if (instance.getState().ordinal() >= instance.getState().COMPLETING.ordinal()) {
            return; // Already completing
        }
        
        instance.complete();
        
        // Set portal to closing state
        portalManager.closePortal(instance.getDungeonName());
        
        // Broadcast completion message
        String message = MessageUtil.formatWithPrefix("&6&lDUNGEON COMPLETED! Well done!");
        for (String playerName : instance.getPlayers()) {
            MessageUtil.sendMessage(playerName, message);
        }
        
        // Distribute rewards
        distributeRewards(instance);
        
        // Start countdown
        int countdownSeconds = instance.getTemplate().getCompletionCountdown();
        startCountdown(instance, countdownSeconds);
    }
    
    /**
     * Distributes rewards to players
     */
    private void distributeRewards(DungeonInstance instance) {
        if (instance.getTemplate().getRewards() == null) {
            return;
        }
        
        for (String playerName : instance.getPlayers()) {
            for (var reward : instance.getTemplate().getRewards()) {
                giveReward(playerName, reward);
            }
        }
    }
    
    /**
     * Gives a reward to a player
     * Placeholder for actual Hytale API implementation
     */
    private void giveReward(String playerName, com.sekiya.dungeons.config.RewardConfig reward) {
        // Placeholder: Use Hytale's item/experience APIs
        String message = String.format("&aReceived: %s x%d", reward.getItemId(), reward.getAmount());
        MessageUtil.sendMessage(playerName, message);
    }
    
    /**
     * Starts the completion countdown
     */
    private void startCountdown(DungeonInstance instance, int seconds) {
        CountdownTask task = new CountdownTask(instance, this, seconds);
        activeCountdowns.put(instance.getInstanceId(), task);
        
        // In real implementation, this would use a scheduler
        // For now, simulate countdown
        simulateCountdown(task, seconds);
    }
    
    /**
     * Simulates countdown (placeholder for actual scheduler)
     */
    private void simulateCountdown(CountdownTask task, int seconds) {
        // This would use Hytale's scheduler in real implementation
        // Example: plugin.getScheduler().runTaskTimer(task, 0, 20)
        System.out.println("Starting countdown: " + seconds + " seconds");
    }
    
    /**
     * Called when countdown completes
     */
    public void onCountdownComplete(DungeonInstance instance) {
        activeCountdowns.remove(instance.getInstanceId());
        
        // Teleport all players to exit
        Location exitPoint = instance.getExitPoint();
        for (String playerName : instance.getPlayers()) {
            teleportPlayer(playerName, exitPoint);
            MessageUtil.sendMessage(playerName, MessageUtil.formatWithPrefix("&aTeleporting you back..."));
        }
        
        // Reset the dungeon
        resetter.resetDungeon(instance);
        
        // Deactivate portal
        portalManager.deactivatePortal(instance.getDungeonName());
        
        // Close instance
        dungeonManager.closeInstance(instance.getInstanceId());
    }
    
    /**
     * Teleports a player to a location
     * Placeholder for actual Hytale API implementation
     */
    private void teleportPlayer(String playerName, Location location) {
        // Placeholder: Use Hytale's teleport API
        System.out.println(String.format("Teleporting %s to %s", playerName, location));
    }
    
    /**
     * Cancels active countdown for an instance
     */
    public void cancelCountdown(String instanceId) {
        activeCountdowns.remove(instanceId);
    }
}
