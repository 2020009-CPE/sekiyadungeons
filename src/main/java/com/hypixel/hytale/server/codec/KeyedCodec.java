package com.hypixel.hytale.server.codec;

/**
 * Stub implementation of Hytale's KeyedCodec class
 * This is a placeholder until the actual Hytale API is available
 */
public class KeyedCodec<T> {
    private final String key;
    private final Codec<T> codec;
    
    public KeyedCodec(String key, Codec<T> codec) {
        this.key = key;
        this.codec = codec;
    }
    
    public String getKey() {
        return key;
    }
    
    public Codec<T> getCodec() {
        return codec;
    }
}
