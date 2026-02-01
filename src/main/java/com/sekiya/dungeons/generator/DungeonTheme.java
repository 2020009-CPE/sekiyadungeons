package com.sekiya.dungeons.generator;

/**
 * Themes for dungeon world generation
 */
public enum DungeonTheme {
    STONE_CRYPT("Stone Crypt", new BlockType[]{
        BlockType.STONE_BRICKS, BlockType.CRACKED_STONE_BRICKS, BlockType.MOSSY_STONE_BRICKS
    }),
    
    CAVE("Cave", new BlockType[]{
        BlockType.STONE, BlockType.COBBLESTONE, BlockType.STONE
    }),
    
    DESERT_TOMB("Desert Tomb", new BlockType[]{
        BlockType.SANDSTONE, BlockType.SANDSTONE_BRICKS, BlockType.SANDSTONE
    }),
    
    ICE_CAVERN("Ice Cavern", new BlockType[]{
        BlockType.PACKED_ICE, BlockType.ICE, BlockType.STONE_BRICKS
    }),
    
    NETHER_FORTRESS("Nether Fortress", new BlockType[]{
        BlockType.NETHER_BRICKS, BlockType.NETHERRACK, BlockType.OBSIDIAN
    }),
    
    OCEAN_TEMPLE("Ocean Temple", new BlockType[]{
        BlockType.DARK_PRISMARINE, BlockType.PRISMARINE_BRICKS, BlockType.DARK_PRISMARINE
    });
    
    private final String displayName;
    private final BlockType[] wallMaterials;
    
    DungeonTheme(String displayName, BlockType[] wallMaterials) {
        this.displayName = displayName;
        this.wallMaterials = wallMaterials;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public BlockType[] getWallMaterials() {
        return wallMaterials;
    }
    
    public BlockType getRandomWallMaterial(java.util.Random random) {
        return wallMaterials[random.nextInt(wallMaterials.length)];
    }
}
