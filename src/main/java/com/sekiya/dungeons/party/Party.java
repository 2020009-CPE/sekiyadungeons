package com.sekiya.dungeons.party;

import java.util.*;

/**
 * Represents a party of players who can enter dungeons together
 */
public class Party {
    private final String partyId;
    private final String leaderName;
    private final Set<String> members;
    private final Map<String, Long> invitations; // playerName -> expiry time
    private final long creationTime;
    private int maxSize;
    
    public Party(String partyId, String leaderName) {
        this.partyId = partyId;
        this.leaderName = leaderName;
        this.members = new HashSet<>();
        this.invitations = new HashMap<>();
        this.creationTime = System.currentTimeMillis();
        this.maxSize = 8; // Default max party size
        
        // Leader is automatically a member
        this.members.add(leaderName);
    }
    
    public String getPartyId() {
        return partyId;
    }
    
    public String getLeaderName() {
        return leaderName;
    }
    
    public Set<String> getMembers() {
        return new HashSet<>(members);
    }
    
    public int getSize() {
        return members.size();
    }
    
    public int getMaxSize() {
        return maxSize;
    }
    
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
    
    public boolean isFull() {
        return members.size() >= maxSize;
    }
    
    public boolean isLeader(String playerName) {
        return leaderName.equals(playerName);
    }
    
    public boolean hasMember(String playerName) {
        return members.contains(playerName);
    }
    
    public boolean addMember(String playerName) {
        if (isFull()) {
            return false;
        }
        return members.add(playerName);
    }
    
    public boolean removeMember(String playerName) {
        // Can't remove leader
        if (isLeader(playerName)) {
            return false;
        }
        return members.remove(playerName);
    }
    
    public void invitePlayer(String playerName, long durationMillis) {
        long expiryTime = System.currentTimeMillis() + durationMillis;
        invitations.put(playerName, expiryTime);
    }
    
    public boolean hasActiveInvitation(String playerName) {
        Long expiryTime = invitations.get(playerName);
        if (expiryTime == null) {
            return false;
        }
        
        if (System.currentTimeMillis() > expiryTime) {
            invitations.remove(playerName);
            return false;
        }
        
        return true;
    }
    
    public void removeInvitation(String playerName) {
        invitations.remove(playerName);
    }
    
    public long getCreationTime() {
        return creationTime;
    }
    
    /**
     * Broadcasts a message to all party members
     */
    public void broadcastMessage(String message) {
        for (String member : members) {
            // Placeholder for actual message sending
            System.out.println(String.format("[Party:%s] %s -> %s", partyId, message, member));
        }
    }
    
    @Override
    public String toString() {
        return String.format("Party{id=%s, leader=%s, members=%d/%d}", 
            partyId, leaderName, members.size(), maxSize);
    }
}
