package com.sekiya.dungeons.generator;

import com.sekiya.dungeons.config.RoomConfig;
import com.sekiya.dungeons.util.Location;

import java.util.*;

/**
 * Generates actual world structures for dungeons
 */
public class WorldGenerator {
    
    /**
     * Generates block placements for a complete dungeon
     */
    public List<BlockPlacement> generateDungeonWorld(List<RoomConfig> rooms, DungeonTheme theme, long seed) {
        List<BlockPlacement> placements = new ArrayList<>();
        Random random = new Random(seed);
        
        for (RoomConfig room : rooms) {
            placements.addAll(generateRoom(room, theme, random));
        }
        
        // Generate corridors between rooms
        for (int i = 0; i < rooms.size() - 1; i++) {
            placements.addAll(generateCorridor(rooms.get(i), rooms.get(i + 1), theme, random));
        }
        
        return placements;
    }
    
    /**
     * Generates a single room structure
     */
    public List<BlockPlacement> generateRoom(RoomConfig room, DungeonTheme theme, Random random) {
        List<BlockPlacement> placements = new ArrayList<>();
        
        if (room.getMinBounds() == null || room.getMaxBounds() == null) {
            return placements;
        }
        
        Location min = room.getMinBounds();
        Location max = room.getMaxBounds();
        
        int minX = (int) min.getX();
        int minY = (int) min.getY();
        int minZ = (int) min.getZ();
        int maxX = (int) max.getX();
        int maxY = (int) max.getY();
        int maxZ = (int) max.getZ();
        
        String world = min.getWorld();
        
        // Generate floor
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                BlockType floorMaterial = theme.getRandomWallMaterial(random);
                placements.add(new BlockPlacement(
                    new Location(world, x, minY, z),
                    floorMaterial
                ));
            }
        }
        
        // Generate ceiling
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                BlockType ceilingMaterial = theme.getRandomWallMaterial(random);
                placements.add(new BlockPlacement(
                    new Location(world, x, maxY, z),
                    ceilingMaterial
                ));
            }
        }
        
        // Generate walls
        // North and South walls
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY + 1; y < maxY; y++) {
                // North wall
                BlockType wallMaterial = theme.getRandomWallMaterial(random);
                placements.add(new BlockPlacement(
                    new Location(world, x, y, minZ),
                    wallMaterial
                ));
                
                // South wall
                wallMaterial = theme.getRandomWallMaterial(random);
                placements.add(new BlockPlacement(
                    new Location(world, x, y, maxZ),
                    wallMaterial
                ));
            }
        }
        
        // East and West walls
        for (int z = minZ + 1; z < maxZ; z++) {
            for (int y = minY + 1; y < maxY; y++) {
                // West wall
                BlockType wallMaterial = theme.getRandomWallMaterial(random);
                placements.add(new BlockPlacement(
                    new Location(world, minX, y, z),
                    wallMaterial
                ));
                
                // East wall
                wallMaterial = theme.getRandomWallMaterial(random);
                placements.add(new BlockPlacement(
                    new Location(world, maxX, y, z),
                    wallMaterial
                ));
            }
        }
        
        // Clear interior (air)
        for (int x = minX + 1; x < maxX; x++) {
            for (int z = minZ + 1; z < maxZ; z++) {
                for (int y = minY + 1; y < maxY; y++) {
                    placements.add(new BlockPlacement(
                        new Location(world, x, y, z),
                        BlockType.AIR
                    ));
                }
            }
        }
        
        // Add decorations
        placements.addAll(generateDecorations(min, max, world, theme, random));
        
        return placements;
    }
    
    /**
     * Generates corridor between two rooms
     */
    private List<BlockPlacement> generateCorridor(RoomConfig room1, RoomConfig room2, 
                                                  DungeonTheme theme, Random random) {
        List<BlockPlacement> placements = new ArrayList<>();
        
        if (room1.getMaxBounds() == null || room2.getMinBounds() == null) {
            return placements;
        }
        
        Location start = room1.getMaxBounds();
        Location end = room2.getMinBounds();
        
        int startX = (int) start.getX();
        int startY = (int) start.getY();
        int startZ = (int) (start.getZ() + (room1.getMinBounds().getZ() - start.getZ()) / 2);
        
        int endX = (int) end.getX();
        int endY = (int) end.getY();
        int endZ = (int) (end.getZ() + (room2.getMaxBounds().getZ() - end.getZ()) / 2);
        
        String world = start.getWorld();
        
        // Simple corridor - just connect the centers
        int corridorWidth = 3;
        int corridorHeight = 4;
        
        for (int x = startX; x <= endX; x++) {
            for (int offsetZ = -corridorWidth/2; offsetZ <= corridorWidth/2; offsetZ++) {
                // Floor
                placements.add(new BlockPlacement(
                    new Location(world, x, startY, startZ + offsetZ),
                    theme.getRandomWallMaterial(random)
                ));
                
                // Ceiling
                placements.add(new BlockPlacement(
                    new Location(world, x, startY + corridorHeight, startZ + offsetZ),
                    theme.getRandomWallMaterial(random)
                ));
                
                // Clear interior
                for (int y = startY + 1; y < startY + corridorHeight; y++) {
                    placements.add(new BlockPlacement(
                        new Location(world, x, y, startZ + offsetZ),
                        BlockType.AIR
                    ));
                }
                
                // Side walls
                if (Math.abs(offsetZ) == corridorWidth/2) {
                    for (int y = startY + 1; y < startY + corridorHeight; y++) {
                        placements.add(new BlockPlacement(
                            new Location(world, x, y, startZ + offsetZ),
                            theme.getRandomWallMaterial(random)
                        ));
                    }
                }
            }
        }
        
        return placements;
    }
    
    /**
     * Generates decorative elements for a room
     */
    private List<BlockPlacement> generateDecorations(Location min, Location max, String world,
                                                     DungeonTheme theme, Random random) {
        List<BlockPlacement> placements = new ArrayList<>();
        
        int minX = (int) min.getX();
        int minY = (int) min.getY();
        int minZ = (int) min.getZ();
        int maxX = (int) max.getX();
        int maxZ = (int) max.getZ();
        
        // Add torches on walls
        int torchSpacing = 5;
        
        // North wall torches
        for (int x = minX + 2; x < maxX; x += torchSpacing) {
            placements.add(new BlockPlacement(
                new Location(world, x, minY + 2, minZ + 1),
                BlockType.WALL_TORCH,
                "facing=south"
            ));
        }
        
        // South wall torches
        for (int x = minX + 2; x < maxX; x += torchSpacing) {
            placements.add(new BlockPlacement(
                new Location(world, x, minY + 2, maxZ - 1),
                BlockType.WALL_TORCH,
                "facing=north"
            ));
        }
        
        // Add some cobwebs for atmosphere (random)
        int cobwebCount = random.nextInt(5) + 2;
        for (int i = 0; i < cobwebCount; i++) {
            int x = random.nextInt(maxX - minX - 2) + minX + 1;
            int y = random.nextInt(3) + minY + 3; // Upper portion of room
            int z = random.nextInt(maxZ - minZ - 2) + minZ + 1;
            
            placements.add(new BlockPlacement(
                new Location(world, x, y, z),
                BlockType.COBWEB
            ));
        }
        
        // Add pillars in corners (for larger rooms)
        int width = maxX - minX;
        int length = maxZ - minZ;
        
        if (width > 15 && length > 15) {
            // Place pillars
            int pillarHeight = (int) (max.getY() - min.getY() - 1);
            
            for (int y = minY + 1; y < minY + pillarHeight; y++) {
                // Corner pillars
                placements.add(new BlockPlacement(
                    new Location(world, minX + 2, y, minZ + 2),
                    BlockType.STONE_BRICKS
                ));
                placements.add(new BlockPlacement(
                    new Location(world, maxX - 2, y, minZ + 2),
                    BlockType.STONE_BRICKS
                ));
                placements.add(new BlockPlacement(
                    new Location(world, minX + 2, y, maxZ - 2),
                    BlockType.STONE_BRICKS
                ));
                placements.add(new BlockPlacement(
                    new Location(world, maxX - 2, y, maxZ - 2),
                    BlockType.STONE_BRICKS
                ));
            }
        }
        
        return placements;
    }
    
    /**
     * Generates a treasure room
     */
    public List<BlockPlacement> generateTreasureRoom(Location center, DungeonTheme theme, Random random) {
        List<BlockPlacement> placements = new ArrayList<>();
        
        String world = center.getWorld();
        int cx = (int) center.getX();
        int cy = (int) center.getY();
        int cz = (int) center.getZ();
        
        int size = 7;
        
        // Small square room
        for (int x = cx - size/2; x <= cx + size/2; x++) {
            for (int z = cz - size/2; z <= cz + size/2; z++) {
                // Floor
                placements.add(new BlockPlacement(
                    new Location(world, x, cy, z),
                    BlockType.STONE_BRICKS
                ));
                
                // Ceiling
                placements.add(new BlockPlacement(
                    new Location(world, x, cy + 4, z),
                    BlockType.STONE_BRICKS
                ));
            }
        }
        
        // Walls
        for (int i = 1; i < 4; i++) {
            // North/South
            for (int x = cx - size/2; x <= cx + size/2; x++) {
                placements.add(new BlockPlacement(
                    new Location(world, x, cy + i, cz - size/2),
                    BlockType.STONE_BRICKS
                ));
                placements.add(new BlockPlacement(
                    new Location(world, x, cy + i, cz + size/2),
                    BlockType.STONE_BRICKS
                ));
            }
            
            // East/West
            for (int z = cz - size/2; z <= cz + size/2; z++) {
                placements.add(new BlockPlacement(
                    new Location(world, cx - size/2, cy + i, z),
                    BlockType.STONE_BRICKS
                ));
                placements.add(new BlockPlacement(
                    new Location(world, cx + size/2, cy + i, z),
                    BlockType.STONE_BRICKS
                ));
            }
        }
        
        // Place chest in center
        placements.add(new BlockPlacement(
            new Location(world, cx, cy + 1, cz),
            BlockType.CHEST
        ));
        
        // Glowstone lighting
        placements.add(new BlockPlacement(
            new Location(world, cx - 2, cy + 3, cz),
            BlockType.GLOWSTONE
        ));
        placements.add(new BlockPlacement(
            new Location(world, cx + 2, cy + 3, cz),
            BlockType.GLOWSTONE
        ));
        placements.add(new BlockPlacement(
            new Location(world, cx, cy + 3, cz - 2),
            BlockType.GLOWSTONE
        ));
        placements.add(new BlockPlacement(
            new Location(world, cx, cy + 3, cz + 2),
            BlockType.GLOWSTONE
        ));
        
        return placements;
    }
}
