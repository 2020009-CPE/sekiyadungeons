# Hytale API Stub Implementation

This directory contains stub implementations of the Hytale modding API classes. These are placeholder implementations that allow the plugin to compile and run standalone for testing purposes.

## Purpose

Since the official Hytale modding API is not yet publicly available, these stubs provide:
1. **Compilation Support**: Allow the project to compile without the actual Hytale API JARs
2. **API Compliance**: Follow the correct Hytale API structure and patterns
3. **Testing**: Enable standalone testing of plugin logic

## Structure

The stub API follows the expected Hytale package structure:

```
com.hypixel.hytale.
├── plugin/                # Plugin base classes
│   ├── JavaPlugin        # Base plugin class
│   └── JavaPluginInit    # Plugin initialization context
├── server/
│   ├── codec/            # Configuration codec system
│   │   ├── BuilderCodec  # Builder pattern for config serialization
│   │   ├── Codec         # Base codec class
│   │   └── KeyedCodec    # Key-value codec wrapper
│   ├── command/          # Command system
│   │   ├── CommandBase   # Base command class
│   │   ├── CommandContext# Command execution context
│   │   ├── CommandRegistry# Command registration
│   │   └── CommandSender # Command sender interface
│   ├── config/           # Configuration management
│   │   └── Config        # Configuration wrapper
│   ├── entity/           # Entity API
│   │   ├── Player        # Player class
│   │   ├── PlayerRef     # Player reference
│   │   └── World         # World class
│   └── util/             # Utilities
│       ├── Message       # Message formatting
│       └── EventTitleUtil# Title display utility
```

## Replacing with Real API

When the actual Hytale API becomes available:

1. **Remove stub implementations**:
   ```bash
   rm -rf src/main/java/com/hypixel/hytale/
   ```

2. **Add real Hytale API JARs** to `libs/` directory or as Maven/Gradle dependencies

3. **Update build.gradle.kts** to reference the official Hytale API

4. **Test thoroughly** as real API may have different behaviors than stubs

## Stub Limitations

The stub implementations:
- ✅ Provide correct method signatures matching expected Hytale API
- ✅ Allow code to compile and run
- ✅ Follow proper API patterns (BuilderCodec, CommandBase, etc.)
- ❌ Don't perform actual game operations
- ❌ Print to console instead of interacting with game
- ❌ May not match all nuances of final Hytale API

## Notes

These stubs were created based on:
- Hytale modding API documentation examples
- Common Minecraft plugin API patterns
- Problem statement requirements from the sekiyadungeons project

All stub classes are marked with comments indicating they are placeholders.
