# SekiyaDungeons

A comprehensive dungeon system plugin for Hytale featuring portals, shards, room-based progression, boss encounters, automatic reset mechanics, **party system**, and **auto-generated dungeons**.

## Features

### Core Systems
- **Portal System** - Interactive portals for dungeon access with shard validation
- **Shard System** - Consumable/reusable items for dungeon entry control
- **Instance Management** - Multiple concurrent dungeon runs with player tracking
- **Room Progression** - Sequential room unlocking based on enemy defeats
- **Enemy Management** - Spawn points, tracking, and death detection
- **Boss Fights** - Special boss rooms with unique encounters
- **Completion System** - 30-second countdown with rewards distribution
- **Auto-Reset** - Automatic dungeon restoration after completion
- **Configuration** - JSON-based dungeon templates with easy editing

### 🎉 NEW: Party System
- **Party Creation** - Create and manage parties of up to 8 players
- **Party Invitations** - Invite players with time-limited invitations
- **Party-Based Entry** - Enter dungeons together as a party
- **Party Commands** - Full command suite for party management
- **Party Requirements** - Dungeons can require specific party sizes

### 🎉 NEW: Auto-Generated Dungeons
- **Procedural Generation** - Automatically generate complete dungeons
- **Seed-Based** - Reproducible dungeons with custom seeds
- **Difficulty Scaling** - Generate dungeons at Easy, Normal, Hard, or Nightmare difficulty
- **Random Layouts** - Variable room count, sizes, and connections
- **Random Enemies** - Procedurally placed spawn points with varied enemy types
- **Random Loot** - Automatically generated reward tables

### 🎉 NEW: Enhanced Features
- **Difficulty Tiers** - Four difficulty levels with different requirements
- **Party Requirements** - Require minimum party size for entry
- **Level Requirements** - Set minimum player levels
- **Cooldown System** - Per-player dungeon cooldowns
- **Scaling System** - Enemy health/damage scales with party size
- **Lives System** - Optional limited lives per run
- **Checkpoints** - Save progress at key points
- **Bonus Objectives** - Optional challenges for extra rewards
- **Time Attack Mode** - Complete dungeons faster for bonuses

### Admin Tools
- Full command system for dungeon creation and management
- In-game configuration of portals, rooms, spawns, and bosses
- Live reload support
- Detailed dungeon information display

### API
- Public API for other plugins to interact with dungeons
- Custom events for dungeon lifecycle (start, complete, room clear, boss defeat)
- Party events (create, join, leave, disband)
- Generation events for procedural dungeons
- Instance and player tracking methods

## Installation

1. Download the latest release JAR file
2. Place it in your Hytale server's `plugins` folder
3. Restart your server
4. Configure dungeons using commands or edit JSON files directly

## Quick Start

### Option 1: Auto-Generate a Dungeon (NEW!)

The fastest way to create a dungeon:

```bash
# Generate a dungeon with default settings
/dungeon generate my_dungeon

# Generate with specific difficulty
/dungeon generate hard_dungeon HARD

# Generate with a custom seed for reproducibility
/dungeon generate seeded_dungeon NORMAL 12345
```

This creates a complete dungeon with:
- 3-6 random rooms (varies by difficulty)
- Random enemy spawns
- Random boss selection
- Random loot rewards
- Proper room connections and doors

Then just set the portal location:
```bash
/dungeon setportal my_dungeon
/dungeon setentry my_dungeon
/dungeon setexit my_dungeon
```

### Option 2: Manual Creation

1. **Create the dungeon template:**
   ```
   /dungeon create my_first_dungeon
   ```

2. **Set portal location** (stand where you want the portal):
   ```
   /dungeon setportal my_first_dungeon
   ```

3. **Set entry point** (stand where players should spawn):
   ```
   /dungeon setentry my_first_dungeon
   ```

4. **Set exit point** (stand where players should return):
   ```
   /dungeon setexit my_first_dungeon
   ```

5. **Add a room:**
   ```
   /dungeon room add my_first_dungeon room_1 1
   ```

6. **Set room bounds** (stand at one corner, run command, move to opposite corner, run again):
   ```
   /dungeon room setbounds my_first_dungeon room_1
   ```

7. **Add enemy spawns** (stand where enemies should spawn):
   ```
   /dungeon spawn add my_first_dungeon room_1 skeleton 3
   ```

8. **Set boss** (stand where boss should spawn):
   ```
   /dungeon boss set my_first_dungeon ancient_guardian
   ```

9. **Give yourself a shard:**
   ```
   /dungeon give shard <your_name> my_first_dungeon 1
   ```

### Using the Party System

```bash
# Create a party
/party create

# Invite players
/party invite PlayerName

# Player accepts invitation
/party join LeaderName

# View party info
/party info

# Leave party
/party leave

# Kick a member (leader only)
/party kick PlayerName

# Disband party (leader only)
/party disband

# List all active parties
/party list
```

When entering a dungeon portal, **all party members near the portal will enter together** automatically!

## Commands

### Dungeon Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/dungeon create <name>` | Create new dungeon template | `sekiyadungeons.command.create` |
| `/dungeon generate <name> [difficulty] [seed]` | **NEW** Generate random dungeon | `sekiyadungeons.command.generate` |
| `/dungeon delete <name>` | Delete dungeon template | `sekiyadungeons.command.delete` |
| `/dungeon list` | List all dungeons | `sekiyadungeons.command.list` |
| `/dungeon info <name>` | Show dungeon details | `sekiyadungeons.command.info` |
| `/dungeon reload` | Reload configurations | `sekiyadungeons.command.reload` |

### Party Commands (NEW!)

| Command | Description | Permission |
|---------|-------------|------------|
| `/party create` | Create a new party | `sekiyadungeons.party.create` |
| `/party invite <player>` | Invite a player to your party | `sekiyadungeons.party.invite` |
| `/party join <leader>` | Join a party you were invited to | `sekiyadungeons.party.join` |
| `/party leave` | Leave your current party | `sekiyadungeons.party.leave` |
| `/party kick <player>` | Kick a player (leader only) | `sekiyadungeons.party.kick` |
| `/party disband` | Disband your party (leader only) | `sekiyadungeons.party.disband` |
| `/party info` | Show your party information | `sekiyadungeons.party.info` |
| `/party list` | List all active parties | `sekiyadungeons.party.list` |

### Configuration Commands

| Command | Description |
|---------|-------------|
| `/dungeon setportal <name>` | Set portal location at your position |
| `/dungeon setentry <name>` | Set dungeon entry point at your position |
| `/dungeon setexit <name>` | Set dungeon exit point at your position |

### Room Management

| Command | Description |
|---------|-------------|
| `/dungeon room add <dungeon> <roomId> [order]` | Add a room to dungeon |
| `/dungeon room setbounds <dungeon> <roomId>` | Set room boundaries (run twice for two corners) |

### Spawn Management

| Command | Description |
|---------|-------------|
| `/dungeon spawn add <dungeon> <roomId> <enemyType> <count>` | Add enemy spawn point |
| `/dungeon spawn remove <dungeon> <roomId> <spawnId>` | Remove spawn point |

### Boss Management

| Command | Description |
|---------|-------------|
| `/dungeon boss set <dungeon> <bossType>` | Set boss type and spawn location |

### Instance Control

| Command | Description |
|---------|-------------|
| `/dungeon start <name>` | Manually start instance |
| `/dungeon stop <instanceId>` | Force stop instance |

### Item Commands

| Command | Description |
|---------|-------------|
| `/dungeon give shard <player> <dungeonName> [amount]` | Give dungeon shards |

## Configuration

### Plugin Configuration

The main plugin configuration is stored in `plugins/SekiyaDungeons/config.json`:

```json
{
  "debugMode": false,
  "maxConcurrentInstances": 10,
  "autoReset": true,
  "resetDelaySeconds": 5,
  "messages": {
    "portal_activated": "Portal activated! Step through to enter.",
    "dungeon_started": "The dungeon has begun!",
    "room_cleared": "Room cleared! The door has opened.",
    "boss_spawned": "BOSS FIGHT! Defeat the guardian!",
    "dungeon_complete": "DUNGEON COMPLETED! Well done!",
    "countdown": "Returning to surface in {time} seconds..."
  }
}
```

### Dungeon Templates

Dungeon templates are stored in `plugins/SekiyaDungeons/dungeons/`. See `examples/ancient_crypt.json` for a complete example.

**Key Configuration Fields:**

**Basic Settings:**
- `name` - Unique dungeon identifier
- `displayName` - Friendly display name
- `portalLocation` - Where the portal spawns in the overworld
- `entryPoint` - Where players spawn when entering
- `exitPoint` - Where players return after completion
- `shardType` - Item ID for access shard
- `shardConsumed` - Whether shard is used up on entry
- `minPlayers` / `maxPlayers` - Player count limits
- `timeLimit` - Maximum dungeon duration in seconds
- `completionCountdown` - Seconds before teleport after boss defeat

**🎉 NEW Enhanced Settings:**
- `difficulty` - Difficulty level: "EASY", "NORMAL", "HARD", or "NIGHTMARE"
- `requiresParty` - Whether dungeon requires players to be in a party
- `requiredPartySize` - Minimum party size to enter (0 = any size)
- `minLevel` - Minimum player level required
- `requiredItems` - Array of item IDs required in inventory
- `cooldownMillis` - Per-player cooldown between runs (in milliseconds)
- `scalingEnabled` - Whether enemy stats scale with party size
- `healthScalingPerPlayer` - Health multiplier per additional player (e.g., 0.3 = +30%)
- `damageScalingPerPlayer` - Damage multiplier per additional player (e.g., 0.2 = +20%)
- `maxLives` - Maximum deaths allowed (0 = unlimited)
- `hasCheckpoints` - Whether dungeon has checkpoint save points
- `bonusObjectives` - Array of optional bonus challenge IDs
- `timeAttackMode` - Whether dungeon has time attack bonuses
- `timeAttackBonusSeconds` - Time limit for bonus rewards

**Room and Boss Configuration:**
- `rooms[]` - Array of dungeon rooms with spawn points
- `bossRoom` - Boss configuration
- `rewards[]` - Items/XP given on completion

### Example Enhanced Configuration

```json
{
  "name": "nightmare_fortress",
  "displayName": "Nightmare Fortress",
  "difficulty": "NIGHTMARE",
  "requiresParty": true,
  "requiredPartySize": 3,
  "minLevel": 50,
  "cooldownMillis": 3600000,
  "scalingEnabled": true,
  "healthScalingPerPlayer": 0.4,
  "damageScalingPerPlayer": 0.25,
  "maxLives": 3,
  "timeAttackMode": true,
  "timeAttackBonusSeconds": 600,
  "minPlayers": 3,
  "maxPlayers": 4,
  "timeLimit": 900
}
```

This creates a challenging dungeon that:
- Requires a party of 3+ players
- Requires player level 50+
- Has a 1-hour cooldown per player
- Scales enemy stats by 40% health and 25% damage per player
- Allows only 3 deaths before failure
- Grants bonus rewards if completed in under 10 minutes

## Dungeon Flow

### 1. Portal Activation
- Player interacts with inactive portal
- System checks for valid shard in inventory
- If valid: consume shard (if consumable), activate portal
- Portal shows visual effects

### 2. Entry
- Player enters active portal
- System creates or joins existing instance
- Player teleported to entry point
- First room's enemies spawn on player entry

### 3. Room Progression
- Enemies spawn when player enters room
- System tracks enemy deaths
- When all enemies defeated → door opens, next room unlocks
- Visual/audio effects play

### 4. Boss Fight
- All rooms cleared → boss room unlocked
- Boss spawns when player enters (or immediately if configured)
- System tracks boss health/death

### 5. Completion
- Boss defeated → 30-second countdown starts
- Countdown displayed to all players via titles/action bar
- Portal enters CLOSING state (no new entries)
- After countdown: teleport all players to exit point
- Distribute rewards

### 6. Reset
- Clear all entities in dungeon
- Restore all doors/barriers
- Reset room states
- Set portal to INACTIVE
- Instance ready for next run

## API Usage

### For Other Plugins

```java
// Get the API
SekiyaDungeons plugin = (SekiyaDungeons) server.getPluginManager().getPlugin("SekiyaDungeons");
DungeonAPI api = plugin.getAPI();

// Check if player is in a dungeon
boolean inDungeon = api.isPlayerInDungeon(playerName);

// Get player's current instance
DungeonInstance instance = api.getPlayerInstance(playerName);

// Get dungeon template
DungeonTemplate template = api.getDungeonTemplate("ancient_crypt");

// Get all active instances
Collection<DungeonInstance> instances = api.getAllInstances();
```

### Custom Events

Listen to dungeon events in your plugin:

```java
// DungeonStartEvent - fired when dungeon starts
// DungeonCompleteEvent - fired when dungeon completes  
// RoomClearEvent - fired when room is cleared
// BossDefeatEvent - fired when boss is defeated

// 🎉 NEW Party Events
// PartyCreateEvent - fired when party is created
// PartyJoinEvent - fired when player joins party

// 🎉 NEW Generation Event
// DungeonGenerateEvent - fired when dungeon is auto-generated
```

### Party API

```java
// Get the party manager
PartyManager partyManager = plugin.getPartyManager();

// Check if player is in a party
boolean inParty = partyManager.isInParty(playerName);

// Get player's party
Party party = partyManager.getPlayerParty(playerName);

// Get party details
if (party != null) {
    String leader = party.getLeaderName();
    Set<String> members = party.getMembers();
    int size = party.getSize();
    boolean isFull = party.isFull();
}
```

### Generator API

```java
// Generate a dungeon programmatically
DungeonGenerator generator = new DungeonGenerator();
GenerationConfig config = new GenerationConfig();
config.setDifficulty("HARD");
config.setSeed(12345);
config.setMinRooms(5);
config.setMaxRooms(8);

DungeonTemplate template = generator.generate("my_dungeon", config);
configManager.saveDungeonTemplate(template);
```
// BossDefeatEvent - fired when boss is defeated
```

## Architecture

### Package Structure

```
com.sekiya.dungeons/
├── SekiyaDungeons.java - Main plugin class
├── config/ - Configuration models and management
├── portal/ - Portal system
├── shard/ - Shard item system  
├── instance/ - Dungeon instance management
├── room/ - Room and door system
├── enemy/ - Enemy spawning and tracking
├── boss/ - Boss fight system
├── completion/ - Completion handler and countdown
├── reset/ - Dungeon reset system
├── command/ - Command handling
├── listener/ - Event listeners
├── storage/ - JSON persistence
├── util/ - Utility classes
└── api/ - Public API and events
```

## Building

```bash
./gradlew build
```

Output JAR will be in `build/libs/SekiyaDungeons-1.0.0.jar`

## Requirements

- Hytale Server (when available)
- Java 25+

## Notes

**Important:** This plugin is built as a template for the upcoming Hytale modding API. Since Hytale is not yet released, some implementations use placeholders for actual API calls. When Hytale's official API is available, these placeholders will need to be updated with actual API methods.

### Placeholder Systems

The following systems currently use placeholder implementations:
- Entity spawning/despawning
- Block placement/removal
- Player inventory management
- Teleportation
- Particle effects
- Event registration
- Permission system

## Troubleshooting

### Dungeon won't start
- Verify portal, entry, and exit points are set
- Check that at least one room is configured
- Ensure spawn points are set in rooms

### Enemies not spawning
- Verify enemy types are valid
- Check spawn point configurations
- Ensure room boundaries are set correctly

### Portal not activating
- Verify shard type is configured
- Check player has the correct shard
- Ensure portal location is set

## License

This project is provided as-is for use with Hytale servers.

## Credits

**Author:** Sekiya  
**Repository:** https://github.com/2020009-CPE/sekiyadungeons

## Support

For issues, feature requests, or contributions, please visit the GitHub repository.