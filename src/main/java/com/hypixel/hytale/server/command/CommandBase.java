package com.hypixel.hytale.server.command;

import javax.annotation.Nonnull;

/**
 * Stub implementation of Hytale's CommandBase class
 * This is a placeholder until the actual Hytale API is available
 */
public abstract class CommandBase {
    private final String name;
    private final String description;
    private final boolean requiresOp;
    
    protected CommandBase(String name, String description, boolean requiresOp) {
        this.name = name;
        this.description = description;
        this.requiresOp = requiresOp;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean requiresOp() {
        return requiresOp;
    }
    
    protected abstract void executeSync(@Nonnull CommandContext ctx);
}
