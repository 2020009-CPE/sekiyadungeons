package com.hypixel.hytale.plugin;

import javax.annotation.Nonnull;
import java.util.logging.Logger;

/**
 * Stub implementation of Hytale's JavaPlugin base class
 * This is a placeholder until the actual Hytale API is available
 */
public abstract class JavaPlugin {
    private final Logger logger;
    
    public JavaPlugin(@Nonnull JavaPluginInit init) {
        this.logger = Logger.getLogger(getClass().getSimpleName());
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public abstract void onEnable();
    
    public abstract void onDisable();
    
    public <T> com.hypixel.hytale.server.config.Config<T> withConfig(String name, com.hypixel.hytale.server.codec.BuilderCodec<T> codec) {
        return new com.hypixel.hytale.server.config.Config<>(codec);
    }
    
    public com.hypixel.hytale.server.command.CommandRegistry getCommandRegistry() {
        return new com.hypixel.hytale.server.command.CommandRegistry();
    }
}
