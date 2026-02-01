package com.sekiya.dungeons.party;

import com.sekiya.dungeons.util.MessageUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all parties in the system
 */
public class PartyManager {
    private final Map<String, Party> parties; // partyId -> Party
    private final Map<String, String> playerToParty; // playerName -> partyId
    private int partyCounter;
    
    private static final long INVITATION_EXPIRY_MS = 60000; // 1 minute
    
    public PartyManager() {
        this.parties = new ConcurrentHashMap<>();
        this.playerToParty = new ConcurrentHashMap<>();
        this.partyCounter = 0;
    }
    
    /**
     * Creates a new party with the specified leader
     */
    public Party createParty(String leaderName) {
        // Check if player is already in a party
        if (isInParty(leaderName)) {
            return null;
        }
        
        String partyId = "party_" + (++partyCounter);
        Party party = new Party(partyId, leaderName);
        
        parties.put(partyId, party);
        playerToParty.put(leaderName, partyId);
        
        return party;
    }
    
    /**
     * Disbands a party
     */
    public boolean disbandParty(String partyId) {
        Party party = parties.remove(partyId);
        if (party == null) {
            return false;
        }
        
        // Remove all members from tracking
        for (String member : party.getMembers()) {
            playerToParty.remove(member);
        }
        
        return true;
    }
    
    /**
     * Gets a party by ID
     */
    public Party getParty(String partyId) {
        return parties.get(partyId);
    }
    
    /**
     * Gets the party a player is in
     */
    public Party getPlayerParty(String playerName) {
        String partyId = playerToParty.get(playerName);
        return partyId != null ? parties.get(partyId) : null;
    }
    
    /**
     * Checks if a player is in a party
     */
    public boolean isInParty(String playerName) {
        return playerToParty.containsKey(playerName);
    }
    
    /**
     * Invites a player to a party
     */
    public boolean inviteToParty(String partyId, String playerName) {
        Party party = parties.get(partyId);
        if (party == null || party.isFull()) {
            return false;
        }
        
        // Can't invite if already in a party
        if (isInParty(playerName)) {
            return false;
        }
        
        party.invitePlayer(playerName, INVITATION_EXPIRY_MS);
        
        // Notify player (placeholder)
        MessageUtil.sendMessage(playerName, 
            String.format("&aYou've been invited to join %s's party! Use /party join %s to accept.", 
                party.getLeaderName(), party.getLeaderName()));
        
        return true;
    }
    
    /**
     * Player joins a party via invitation
     */
    public boolean joinParty(String playerName, String partyId) {
        Party party = parties.get(partyId);
        if (party == null) {
            return false;
        }
        
        // Check invitation
        if (!party.hasActiveInvitation(playerName)) {
            return false;
        }
        
        // Check if already in a party
        if (isInParty(playerName)) {
            return false;
        }
        
        // Add to party
        if (party.addMember(playerName)) {
            playerToParty.put(playerName, partyId);
            party.removeInvitation(playerName);
            
            // Notify party
            party.broadcastMessage(String.format("&a%s has joined the party!", playerName));
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Player leaves their party
     */
    public boolean leaveParty(String playerName) {
        Party party = getPlayerParty(playerName);
        if (party == null) {
            return false;
        }
        
        // If leader leaves, disband the party
        if (party.isLeader(playerName)) {
            party.broadcastMessage("&cThe party has been disbanded by the leader.");
            return disbandParty(party.getPartyId());
        }
        
        // Remove member
        if (party.removeMember(playerName)) {
            playerToParty.remove(playerName);
            party.broadcastMessage(String.format("&e%s has left the party.", playerName));
            return true;
        }
        
        return false;
    }
    
    /**
     * Kicks a player from a party (leader only)
     */
    public boolean kickFromParty(String leaderName, String targetPlayer) {
        Party party = getPlayerParty(leaderName);
        if (party == null || !party.isLeader(leaderName)) {
            return false;
        }
        
        if (!party.hasMember(targetPlayer)) {
            return false;
        }
        
        if (party.removeMember(targetPlayer)) {
            playerToParty.remove(targetPlayer);
            party.broadcastMessage(String.format("&e%s has been kicked from the party.", targetPlayer));
            MessageUtil.sendMessage(targetPlayer, "&cYou have been kicked from the party.");
            return true;
        }
        
        return false;
    }
    
    /**
     * Gets all active parties
     */
    public Collection<Party> getAllParties() {
        return new ArrayList<>(parties.values());
    }
    
    /**
     * Clears all parties
     */
    public void clearAll() {
        parties.clear();
        playerToParty.clear();
        partyCounter = 0;
    }
}
