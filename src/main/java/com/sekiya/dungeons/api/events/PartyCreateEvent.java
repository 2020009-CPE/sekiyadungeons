package com.sekiya.dungeons.api.events;

import com.sekiya.dungeons.party.Party;

/**
 * Event fired when a party is created
 */
public class PartyCreateEvent {
    private final Party party;
    private boolean cancelled;
    
    public PartyCreateEvent(Party party) {
        this.party = party;
        this.cancelled = false;
    }
    
    public Party getParty() {
        return party;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
