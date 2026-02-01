package com.sekiya.dungeons.generator;

/**
 * Configuration for dungeon generation
 */
public class GenerationConfig {
    private int minRooms = 3;
    private int maxRooms = 6;
    private int minEnemiesPerRoom = 2;
    private int maxEnemiesPerRoom = 8;
    private boolean includeBoss = true;
    private String difficulty = "NORMAL";
    private long seed = 0;
    
    // Room size parameters
    private int minRoomWidth = 15;
    private int maxRoomWidth = 30;
    private int minRoomHeight = 10;
    private int maxRoomHeight = 20;
    private int minRoomLength = 15;
    private int maxRoomLength = 30;
    
    // Enemy types pool
    private String[] enemyTypes = {"skeleton", "zombie", "spider", "witch", "creeper"};
    
    // Boss types pool
    private String[] bossTypes = {"ancient_guardian", "crypt_lord", "shadow_beast", "flame_titan"};
    
    // Loot configuration
    private boolean randomLoot = true;
    private int minRewards = 2;
    private int maxRewards = 5;
    
    public GenerationConfig() {}
    
    public int getMinRooms() { return minRooms; }
    public void setMinRooms(int minRooms) { this.minRooms = minRooms; }
    
    public int getMaxRooms() { return maxRooms; }
    public void setMaxRooms(int maxRooms) { this.maxRooms = maxRooms; }
    
    public int getMinEnemiesPerRoom() { return minEnemiesPerRoom; }
    public void setMinEnemiesPerRoom(int minEnemiesPerRoom) { this.minEnemiesPerRoom = minEnemiesPerRoom; }
    
    public int getMaxEnemiesPerRoom() { return maxEnemiesPerRoom; }
    public void setMaxEnemiesPerRoom(int maxEnemiesPerRoom) { this.maxEnemiesPerRoom = maxEnemiesPerRoom; }
    
    public boolean isIncludeBoss() { return includeBoss; }
    public void setIncludeBoss(boolean includeBoss) { this.includeBoss = includeBoss; }
    
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    
    public long getSeed() { return seed; }
    public void setSeed(long seed) { this.seed = seed; }
    
    public int getMinRoomWidth() { return minRoomWidth; }
    public void setMinRoomWidth(int minRoomWidth) { this.minRoomWidth = minRoomWidth; }
    
    public int getMaxRoomWidth() { return maxRoomWidth; }
    public void setMaxRoomWidth(int maxRoomWidth) { this.maxRoomWidth = maxRoomWidth; }
    
    public int getMinRoomHeight() { return minRoomHeight; }
    public void setMinRoomHeight(int minRoomHeight) { this.minRoomHeight = minRoomHeight; }
    
    public int getMaxRoomHeight() { return maxRoomHeight; }
    public void setMaxRoomHeight(int maxRoomHeight) { this.maxRoomHeight = maxRoomHeight; }
    
    public int getMinRoomLength() { return minRoomLength; }
    public void setMinRoomLength(int minRoomLength) { this.minRoomLength = minRoomLength; }
    
    public int getMaxRoomLength() { return maxRoomLength; }
    public void setMaxRoomLength(int maxRoomLength) { this.maxRoomLength = maxRoomLength; }
    
    public String[] getEnemyTypes() { return enemyTypes; }
    public void setEnemyTypes(String[] enemyTypes) { this.enemyTypes = enemyTypes; }
    
    public String[] getBossTypes() { return bossTypes; }
    public void setBossTypes(String[] bossTypes) { this.bossTypes = bossTypes; }
    
    public boolean isRandomLoot() { return randomLoot; }
    public void setRandomLoot(boolean randomLoot) { this.randomLoot = randomLoot; }
    
    public int getMinRewards() { return minRewards; }
    public void setMinRewards(int minRewards) { this.minRewards = minRewards; }
    
    public int getMaxRewards() { return maxRewards; }
    public void setMaxRewards(int maxRewards) { this.maxRewards = maxRewards; }
}
