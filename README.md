# BossLand v2.3 - v1.16.1-v1.21.5

## This fork

BossLand 2.3 for Paper, Leaf and Spigot 26.3, built against Spigot 26.3 with JDK 25. Upstream's 2.3 covers 1.16.1 to 1.21.5; this build runs on 26.3 only. Changes from upstream:

- Shrines and fruits that need a biome summon again. Biomes are matched by key, so data pack biomes count: a `desert_canyon` is a desert.
- The Illager King's and Death's particles no longer throw. `config.yml` uses the current particle, potion effect and enchantment names, and still reads the old ones.
- The tornado attack works under the default 1024 health cap.
- The Illager King's potion lingers, the Aether God's arrow knocks back and the Devil's book hits with Sharpness.
- The Book of Knowledge adds and removes enchantments again, and Sweeping Edge can roll on loot.
- The Giant's third minion wave spawns; its mount was misspelt `SKELETONE_HORSE`.
- One crafter listener is registered, not one per crafting-grid change.
- The update check is off by default, because it follows upstream's releases.

On Paper and Leaf, bosses with more than 1024 health are capped at 1024 until `settings.attribute.maxHealth.max` in `spigot.yml` is raised to `2048.0`. A `config.yml` written by upstream's 2.3 keeps working.

Download `bossland-2.3-26.3.jar` from [Releases](https://github.com/lzlrd/BossLand/releases), or build with JDK 25: `mvn package`, then take `target/bossland-2.3-26.3.jar`.

## Overview

BossLand is a comprehensive Minecraft plugin that extends vanilla gameplay by adding **15 unique custom bosses** across multiple tiers. Originally created by **Eliminator** and discontinued after Minecraft 1.19, this project continues the development to bring enhanced boss combat experiences to modern Minecraft versions.

## Credits

**Original Author:** Eliminator  
**Original Repository:** https://bitbucket.org/Eliminator/bossland/  
**Original SpigotMC Resource:** https://www.spigotmc.org/resources/boss-land.68320/  

This continuation aims to maintain the original vision while updating compatibility and adding new features for current Minecraft versions.

## Downloads

| Minecraft Version | BossLand Version          | Download Link                                                                                                                  | Notes                                                      |
|-------------------|---------------------------|--------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------|
| 26.3              | 2.3                       | [Download 2.3-26.3](https://github.com/lzlrd/BossLand/releases/download/2.3-26.3/bossland-2.3-26.3.jar)                        | This fork: v2.3 ported to Minecraft 26.3                   |
| 1.16.1 - 1.21.5   | 2.3                       | [Download v2.3](https://github.com/TwistyLime/BossLand/releases/download/v2.3-v1.16.1-v1.21.5/bossland2.3-v1.16.1-v1.21.5.jar) | New Guide Book and Shard Effects added and bugs fixed      |
| 1.16.1 - 1.21.5   | 2.2                       | [Download v2.2](https://github.com/TwistyLime/BossLand/releases/download/v2.2-v1.16.1-v1.21.5/bossland2.2-v1.16.1-v1.21.5.jar) | Compatible throughout 1.16.1 uptill 1.21.5 with bugs fixed |
| 1.21.3            | 2.1                       | [Download v2.1](https://github.com/TwistyLime/BossLand/releases/download/v2.1-1.21.3/bossland2-0-2.1.jar)                      | Updated for 1.21.3 with new API and support                |
| 1.19.x            | 0.8 (Final by Eliminator) | [Download v0.8](https://www.spigotmc.org/resources/boss-land.68320/)                                                           | Original discontinued version                              |
| 1.18.x            | 0.7                       | [Download v0.7](https://www.spigotmc.org/resources/boss-land.68320/)                                                           | Last version before 1.19                                   |
| 1.16 – 1.17       | 0.5 – 0.6                 | [View Releases](https://www.spigotmc.org/resources/boss-land.68320/)                                                           | May have limited feature set                               |
| 1.13 – 1.15       | 0.1 – 0.4                 | [View Releases](https://www.spigotmc.org/resources/boss-land.68320/)                                                           | Legacy builds, minimal support                             |



## Features

### Core Concept
Defeated the Ender Dragon? Your journey has only begun! This plugin adds a progressive boss system with **four distinct tiers** of challenging encounters, each offering unique rewards and gameplay mechanics.

### Boss Progression System

#### **Tier 1 Bosses** (6 Bosses)
Entry-level bosses that can be summoned in specific biomes using vanilla materials:

- **Papa Panda** (Bamboo biome) - Drops brown shard
- **Slime King** (Swamp biome) - Drops green shard  
- **Killer Bunny** (Desert biome) - Drops grey shard
- **Zombie King** (Plains biome) - Drops red shard
- **Ghast Lord** (Nether) - Drops white shard
- **Wither Skeleton King** (Nether) - Drops black shard

#### **Tier 2 Bosses** (4 Bosses)
Mid-tier bosses requiring crafted shard items from Tier 1 defeats:

- **Giant** (Plains biome) - Drops emerald shard
- **Illager King** (Savana biome) - Drops gold shard
- **Evil Wizard** (Snow biome) - Drops blue shard
- **Demon** (Special conditions) - Drops demonic shard

#### **Tier 3 God Bosses** (3 Bosses)
High-tier bosses with unique summoning requirements:

- **Aether God** - Summoned at Y=200 with forbidden fruit
- **Pharaoh God** - Summoned in desert with forbidden fruit
- **Drowned God** - Summoned in ocean at Y=-40 with forbidden fruit

#### **Tier 4 Immortal Bosses** (2 Bosses)
Ultimate endgame encounters:

- **Devil** - Summoned in Nether with Abhorrent Fruit
- **Death** - Summoned with Death Note item

### Unique Mechanics

#### **Shard System**
- Tier 1 and 2 bosses drop magical shards
- Shards are used to craft summoning items for higher-tier bosses
- Physical crafting system - place items in the world, not crafting table
- On holding a shard, the player will get potion effects, some good, some bad.

#### **Biome-Specific Summoning**
- Each boss requires specific biome conditions
- Unique summoning rituals with vanilla block arrangements
- No traditional crafting recipes - all done through world interaction

#### **Progressive Difficulty**
- Each tier offers significantly increased challenge
- Better equipment and magical items obtained through progression
- Some bosses locked behind defeating others

## Commands

### Primary Command: `/bossland` or `/bl`

#### Available Sub-commands:
- `/bl guide` - Provides a guide book
- `/bl help` - Shows list of available commands
- `/bl info` - Shows information about plugin

### Admin Command: `/bosslandadmin` or `/bl-admin`

**Permission:** `bl-admin.cmds`

#### Available Sub-commands:
- `/bl-admin help` - Shows list of available commands
- `/bl-admin spawn <boss>` - Spawns a specific Boss.
- `/bl-admin cspawn <boss> [x y z] [world]` - Spawns a Boss at coords in a world.
- `/bl-admin loot <boss>` - Drops a boss's death loot
- `/bl-admin sloot <boss> <lootname>` - Drops specific loot
- `/bl-admin setLoot <boss> <id>` - Set loot for a boss
- `/bl-admin addLoot <boss>` - Add loot for a boss
- `/bl-admin killBosses <world>` - Remove all bosses in a world
- `/bl-admin reload` - Reload the plugin configuration

## Installation

1. Download the plugin JAR file
2. Place it in your server's `plugins` folder
3. Restart your server
4. Configuration files will be generated automatically

## Dependencies

- **Required:** Paper, Leaf or Spigot 26.3, on Java 25
- **Optional:** WorldGuard (for region protection)

## Technical Details

- **Main Class:** `com.twistylime.BossLand.BossLand`
- **API Version:** 26.3
- **Plugin Architecture:** Event-driven boss management system
- **Data Storage:** YAML configuration files

## Version History

### Original Development (by Eliminator)
- **v0.1-0.3:** Initial development for MC 1.13-1.15
- **v0.4-0.6:** Updated for MC 1.16-1.18  
- **v0.7-0.8:** Final updates for MC 1.19
- **Status:** Discontinued after 1.19

### Continued Development
- **Current:** Reviving and updating for modern Minecraft versions
- **Goal:** Maintain original gameplay while adding quality-of-life improvements
- **v2.3:** Update for MC v1.16.1 till v1.21.5

## Contributing

This is a continuation of Eliminator's original work. Contributions are welcome to help maintain and enhance the plugin for newer Minecraft versions.

## Support

For issues, suggestions, or questions about this continuation of the BossLand project, please use the repository's issue tracker.

## License

This project continues the work of the original [BossLand plugin](https://bitbucket.org/Eliminator/bossland/) by Eliminator, which appears to be discontinued.

Unless stated otherwise, this continuation is released under the MIT License. If you are the original author and wish for changes to be made regarding usage rights or attribution, please contact us via the repository’s issue tracker.


---

*"Your journey beyond the Ender Dragon starts here. Face the challenge, claim the rewards, and become the ultimate boss hunter!"*