package com.hypixel.hytale.server.config;

import com.hypixel.hytale.server.codec.BuilderCodec;

/**
 * Stub implementation of Hytale's Config class
 * This is a placeholder until the actual Hytale API is available
 */
public class Config<T> {
    private final T instance;
    
    public Config(BuilderCodec<T> codec) {
        this.instance = codec.createInstance();
    }
    
    public T get() {
        return instance;
    }
}
