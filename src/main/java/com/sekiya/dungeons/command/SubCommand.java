package com.sekiya.dungeons.command;

/**
 * Base interface for subcommands
 */
public interface SubCommand {
    
    /**
     * Gets the name of the subcommand
     */
    String getName();
    
    /**
     * Gets the usage string
     */
    String getUsage();
    
    /**
     * Gets the description
     */
    String getDescription();
    
    /**
     * Executes the subcommand
     * @return true if successful, false to show usage
     */
    boolean execute(Object sender, String[] args);
}
