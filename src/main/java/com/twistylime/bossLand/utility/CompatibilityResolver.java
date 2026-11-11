package com.twistylime.bossLand.utility;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class CompatibilityResolver {

    private static final Logger LOGGER = Logger.getLogger("CompatResolver");

    // Particle names the default config used before 1.20.5 renamed them.
    private static final Map<String, String> LEGACY_PARTICLES = Map.of(
            "SMOKE_NORMAL", "SMOKE",
            "SMOKE_LARGE", "LARGE_SMOKE",
            "SLIME", "ITEM_SLIME",
            "CRIT_MAGIC", "ENCHANTED_HIT",
            "SPELL_MOB", "ENTITY_EFFECT",
            "EXPLOSION_LARGE", "EXPLOSION",
            "WATER_SPLASH", "SPLASH");
    private static final Map<String, Optional<Particle>> CONFIG_PARTICLES = new HashMap<>();
    // Potion effect names the default config used before 1.20.5 renamed them.
    private static final Map<String, String> LEGACY_POTION_EFFECTS = Map.of(
            "SLOW", "SLOWNESS",
            "JUMP", "JUMP_BOOST",
            "CONFUSION", "NAUSEA",
            "HARM", "INSTANT_DAMAGE");

    private static Method zombieSetBabyBoolean = null;
    private static Method zombieSetBabyVoid = null;
    private static boolean zombieSetBabyChecked = false;

    public static Attribute resolveAttribute(String... fieldNames) {
        return (Attribute) resolveField(Attribute.class, fieldNames);
    }

    /**
     * Resolves the first available Enchantment from given field names.
     */
    public static Enchantment resolveEnchantment(String... fieldNames) {
        return (Enchantment) resolveField(Enchantment.class, fieldNames);
    }

    /**
     * Resolves the first available PotionEffectType from given field names.
     */
    public static PotionEffectType resolvePotionEffect(String... fieldNames) {
        return (PotionEffectType) resolveField(PotionEffectType.class, fieldNames);
    }

    /**
     * Resolves a potion effect name from the config, including names from configs written before 1.20.5.
     * Returns null for a name that is not a potion effect, such as the config's FIRE.
     */
    public static PotionEffectType resolveConfigPotionEffect(String name) {
        String key = name.toUpperCase(Locale.ROOT);
        return PotionEffectType.getByKey(NamespacedKey.fromString(LEGACY_POTION_EFFECTS.getOrDefault(key, key).toLowerCase(Locale.ROOT)));
    }

    public static PotionType resolvePotionTypeEffect(String... fieldNames) {
        return (PotionType) resolveField(PotionType.class, fieldNames);
    }

    public static EntityType resolveEntityType(String... fieldNames) {
        return (EntityType) resolveField(EntityType.class, fieldNames);
    }

    public static Particle resolveParticle(String... fieldNames) {
        return (Particle) resolveField(Particle.class, fieldNames);
    }

    /**
     * Resolves a particle name from the config, including names from configs written before 1.20.5.
     * Returns null, after a single warning, for a name that is unknown or needs data BossLand cannot supply.
     */
    public static Particle resolveConfigParticle(String name) {
        return CONFIG_PARTICLES.computeIfAbsent(name, n -> {
            String key = n.toUpperCase(Locale.ROOT);
            Particle particle = resolveParticle(LEGACY_PARTICLES.getOrDefault(key, key));
            if (particle != null && particle.getDataType() != Void.class && particleData(particle) == null) {
                LOGGER.warning("Particle '" + n + "' needs " + particle.getDataType().getSimpleName() + " data and cannot be used");
                particle = null;
            }
            return Optional.ofNullable(particle);
        }).orElse(null);
    }

    /**
     * Default data for the particles that cannot be spawned without any, or null if there is none.
     */
    public static Object particleData(Particle particle) {
        Class<?> type = particle.getDataType();
        if (type == Color.class) return Color.BLACK; // ENTITY_EFFECT, black as SPELL_MOB was at speed 0
        if (type == Float.class) return 1.0f; // DRAGON_BREATH power
        return null;
    }

    public static Material resolveMaterial(String... fieldNames) {
        return (Material) resolveField(Material.class, fieldNames);
    }

    /**
     * Generic field resolver from a class and list of possible static field names.
     */
    private static Object resolveField(Class<?> clazz, String... fieldNames) {
        for (String name : fieldNames) {
            try {
                Field field = clazz.getField(name.toUpperCase(Locale.ROOT));
                return field.get(null); // static field
            } catch (NoSuchFieldException ignored) {
            } catch (IllegalAccessException e) {
                LOGGER.warning("Cannot access field '" + name + "' in " + clazz.getSimpleName());
            }
        }
        LOGGER.warning("None of the fields were found in " + clazz.getSimpleName() + ": " + String.join(", ", fieldNames));
        return null;
    }

    /**
     * Sets the base potion type on a PotionMeta.
     */
    public static void setBasePotion(PotionMeta meta, PotionType type) {
        meta.setBasePotionType(type);
    }

    public static void setArrowBasePotionType(Arrow arrow, PotionType type) {
        arrow.setBasePotionType(type);
    }

    /**
     * Makes a zombie a baby, compatible with both old (boolean arg) and new (no arg) versions.
     */
    public static void resolveSetBaby(Zombie zombie) {
        if (!zombieSetBabyChecked) {
            try {
                // Try old method with boolean parameter
                zombieSetBabyBoolean = Zombie.class.getMethod("setBaby", boolean.class);
            } catch (NoSuchMethodException e1) {
                try {
                    // Try new method with no parameters (only supports making baby)
                    zombieSetBabyVoid = Zombie.class.getMethod("setBaby");
                } catch (NoSuchMethodException e2) {
                    LOGGER.warning("Zombie.setBaby() method not found in any known form.");
                }
            }
            zombieSetBabyChecked = true;
        }

        try {
            if (zombieSetBabyBoolean != null) {
                zombieSetBabyBoolean.invoke(zombie, true);
            } else if (zombieSetBabyVoid != null) {
                zombieSetBabyVoid.invoke(zombie); // no way to set false in new API
            }
        } catch (Exception e) {
            LOGGER.warning("Failed to invoke Zombie#setBaby: " + e.getMessage());
        }
    }
}
