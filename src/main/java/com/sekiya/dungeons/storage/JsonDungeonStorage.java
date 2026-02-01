package com.sekiya.dungeons.storage;

import com.google.gson.*;
import com.sekiya.dungeons.config.DungeonTemplate;
import com.sekiya.dungeons.config.PluginConfig;
import com.sekiya.dungeons.util.Location;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * JSON-based storage for dungeon data
 */
public class JsonDungeonStorage implements DungeonStorage {
    private final Path dataFolder;
    private final Gson gson;
    
    public JsonDungeonStorage(Path dataFolder) {
        this.dataFolder = dataFolder;
        this.gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Location.class, new LocationAdapter())
            .create();
        
        try {
            Files.createDirectories(dataFolder);
            Files.createDirectories(dataFolder.resolve("dungeons"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void saveDungeon(DungeonTemplate template) {
        Path file = dataFolder.resolve("dungeons").resolve(template.getName() + ".json");
        try (Writer writer = new FileWriter(file.toFile())) {
            gson.toJson(template, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public DungeonTemplate loadDungeon(String name) {
        Path file = dataFolder.resolve("dungeons").resolve(name + ".json");
        if (!Files.exists(file)) {
            return null;
        }
        
        try (Reader reader = new FileReader(file.toFile())) {
            return gson.fromJson(reader, DungeonTemplate.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void deleteDungeon(String name) {
        Path file = dataFolder.resolve("dungeons").resolve(name + ".json");
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<DungeonTemplate> loadAllDungeons() {
        List<DungeonTemplate> dungeons = new ArrayList<>();
        Path dungeonsDir = dataFolder.resolve("dungeons");
        
        if (!Files.exists(dungeonsDir)) {
            return dungeons;
        }
        
        try {
            Files.list(dungeonsDir)
                .filter(p -> p.toString().endsWith(".json"))
                .forEach(p -> {
                    try (Reader reader = new FileReader(p.toFile())) {
                        DungeonTemplate template = gson.fromJson(reader, DungeonTemplate.class);
                        if (template != null) {
                            dungeons.add(template);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return dungeons;
    }
    
    @Override
    public void saveConfig(PluginConfig config) {
        Path file = dataFolder.resolve("config.json");
        try (Writer writer = new FileWriter(file.toFile())) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public PluginConfig loadConfig() {
        Path file = dataFolder.resolve("config.json");
        if (!Files.exists(file)) {
            PluginConfig config = new PluginConfig();
            saveConfig(config);
            return config;
        }
        
        try (Reader reader = new FileReader(file.toFile())) {
            return gson.fromJson(reader, PluginConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new PluginConfig();
        }
    }
    
    /**
     * Custom adapter for Location serialization
     */
    private static class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
        @Override
        public JsonElement serialize(Location loc, java.lang.reflect.Type type, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("world", loc.getWorld());
            obj.addProperty("x", loc.getX());
            obj.addProperty("y", loc.getY());
            obj.addProperty("z", loc.getZ());
            if (loc.getYaw() != 0 || loc.getPitch() != 0) {
                obj.addProperty("yaw", loc.getYaw());
                obj.addProperty("pitch", loc.getPitch());
            }
            return obj;
        }
        
        @Override
        public Location deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context) {
            JsonObject obj = json.getAsJsonObject();
            String world = obj.get("world").getAsString();
            double x = obj.get("x").getAsDouble();
            double y = obj.get("y").getAsDouble();
            double z = obj.get("z").getAsDouble();
            
            float yaw = obj.has("yaw") ? obj.get("yaw").getAsFloat() : 0f;
            float pitch = obj.has("pitch") ? obj.get("pitch").getAsFloat() : 0f;
            
            return new Location(world, x, y, z, yaw, pitch);
        }
    }
}
