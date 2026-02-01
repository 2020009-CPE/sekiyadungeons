package com.sekiya.dungeons.generator;

/**
 * Block types for world generation
 */
public enum BlockType {
    // Basic blocks
    AIR("air"),
    STONE("stone"),
    COBBLESTONE("cobblestone"),
    STONE_BRICKS("stone_bricks"),
    CRACKED_STONE_BRICKS("cracked_stone_bricks"),
    MOSSY_STONE_BRICKS("mossy_stone_bricks"),
    
    // Decorative
    TORCH("torch"),
    WALL_TORCH("wall_torch"),
    GLOWSTONE("glowstone"),
    LANTERN("lantern"),
    
    // Dungeon specific
    IRON_BARS("iron_bars"),
    COBWEB("cobweb"),
    SPAWNER("spawner"),
    CHEST("chest"),
    
    // Door materials
    IRON_DOOR("iron_door"),
    BARRIER("barrier"),
    
    // Theme-specific blocks
    SANDSTONE("sandstone"),
    SANDSTONE_BRICKS("sandstone_bricks"),
    ICE("ice"),
    PACKED_ICE("packed_ice"),
    NETHERRACK("netherrack"),
    NETHER_BRICKS("nether_bricks"),
    OBSIDIAN("obsidian"),
    DARK_PRISMARINE("dark_prismarine"),
    PRISMARINE_BRICKS("prismarine_bricks"),
    
    // Hazards
    LAVA("lava"),
    MAGMA_BLOCK("magma_block"),
    SOUL_SAND("soul_sand");
    
    private final String id;
    
    BlockType(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
}
