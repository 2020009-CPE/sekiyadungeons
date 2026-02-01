package com.sekiya.dungeons.api.events;

import com.sekiya.dungeons.party.Party;

/**
 * Event fired when a player joins a party
 */
public class PartyJoinEvent {
    private final Party party;
    private final String playerName;
    private boolean cancelled;
    
    public PartyJoinEvent(Party party, String playerName) {
        this.party = party;
        this.playerName = playerName;
        this.cancelled = false;
    }
    
    public Party getParty() {
        return party;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
