// Decompiled with: CFR 0.152
// Class Version: 17
package me.velikoss.sounds;

import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;

public enum joinSound {
    DOOR_OPEN("DOOR_OPEN", "BLOCK_WOODEN_DOOR_OPEN"),
    DOOR_CLOSE("DOOR_CLOSE", "BLOCK_WOODEN_DOOR_CLOSE"),
    AMBIENCE_CAVE("AMBIENCE_CAVE", "AMBIENT_CAVE"),
    ANVIL_BREAK("ANVIL_BREAK", "BLOCK_ANVIL_BREAK"),
    ANVIL_LAND("ANVIL_LAND", "BLOCK_ANVIL_LAND"),
    ANVIL_USE("ANVIL_USE", "BLOCK_ANVIL_USE"),
    ARROW_HIT("ARROW_HIT", "ENTITY_ARROW_HIT"),
    BURP("BURP", "ENTITY_PLAYER_BURP"),
    CHEST_CLOSE("CHEST_CLOSE", "ENTITY_CHEST_CLOSE", "BLOCK_CHEST_CLOSE"),
    CHEST_OPEN("CHEST_OPEN", "ENTITY_CHEST_OPEN", "BLOCK_CHEST_OPEN"),
    CLICK("CLICK", "UI_BUTTON_CLICK"),
    DRINK("DRINK", "ENTITY_GENERIC_DRINK"),
    EAT("EAT", "ENTITY_GENERIC_EAT"),
    EXPLODE("EXPLODE", "ENTITY_GENERIC_EXPLODE"),
    FUSE("FUSE", "ENTITY_TNT_PRIMED"),
    GLASS("GLASS", "BLOCK_GLASS_BREAK"),
    HURT_FLESH("HURT_FLESH", "ENTITY_PLAYER_HURT"),
    ITEM_BREAK("ITEM_BREAK", "ENTITY_ITEM_BREAK"),
    LAVA("LAVA", "BLOCK_LAVA_AMBIENT"),
    LEVEL_UP("LEVEL_UP", "ENTITY_PLAYER_LEVELUP"),
    NOTE_BASS("NOTE_BASS", "BLOCK_NOTE_BASS", "BLOCK_NOTE_BLOCK_BASS"),
    NOTE_PIANO("NOTE_PIANO", "BLOCK_NOTE_HARP", "BLOCK_NOTE_BLOCK_HARP"),
    NOTE_PLING("NOTE_PLING", "BLOCK_NOTE_PLING", "BLOCK_NOTE_BLOCK_PLING"),
    ORB_PICKUP("ORB_PICKUP", "ENTITY_EXPERIENCE_ORB_PICKUP"),
    PISTON_EXTEND("PISTON_EXTEND", "BLOCK_PISTON_EXTEND"),
    PISTON_RETRACT("PISTON_RETRACT", "BLOCK_PISTON_CONTRACT"),
    SHOOT_ARROW("SHOOT_ARROW", "ENTITY_ARROW_SHOOT"),
    SPLASH("SPLASH", "ENTITY_GENERIC_SPLASH"),
    WOOD_CLICK("WOOD_CLICK", "BLOCK_WOOD_BUTTON_CLICK_ON", "BLOCK_WOODEN_BUTTON_CLICK_ON"),
    BAT_HURT("BAT_HURT", "ENTITY_BAT_HURT"),
    BLAZE_HIT("BLAZE_HIT", "ENTITY_BLAZE_HURT"),
    CAT_MEOW("CAT_MEOW", "ENTITY_CAT_AMBIENT"),
    CHICKEN_IDLE("CHICKEN_IDLE", "ENTITY_CHICKEN_AMBIENT"),
    COW_IDLE("COW_IDLE", "ENTITY_COW_AMBIENT"),
    CREEPER_HISS("CREEPER_HISS", "ENTITY_CREEPER_PRIMED"),
    ENDERMAN_SCREAM("ENDERMAN_SCREAM", "ENTITY_ENDERMEN_SCREAM", "ENTITY_ENDERMAN_SCREAM"),
    GHAST_SCREAM("GHAST_SCREAM", "ENTITY_GHAST_SCREAM"),
    GHAST_MOAN("GHAST_MOAN", "ENTITY_GHAST_AMBIENT"),
    PIG_IDLE("PIG_IDLE", "ENTITY_PIG_AMBIENT"),
    SHEEP_IDLE("SHEEP_IDLE", "ENTITY_SHEEP_AMBIENT"),
    SKELETON_IDLE("SKELETON_IDLE", "ENTITY_SKELETON_AMBIENT"),
    SLIME_WALK("SLIME_WALK", "ENTITY_SLIME_JUMP"),
    SPIDER_IDLE("SPIDER_IDLE", "ENTITY_SPIDER_AMBIENT"),
    WOLF_BARK("WOLF_BARK", "ENTITY_WOLF_AMBIENT"),
    WOLF_PANT("WOLF_PANT", "ENTITY_WOLF_PANT"),
    ZOMBIE_WALK("ZOMBIE_WALK", "ENTITY_ZOMBIE_STEP"),
    ZOMBIE_PIG_IDLE("ZOMBIE_PIG_IDLE", "ENTITY_ZOMBIE_PIG_AMBIENT", "ENTITY_ZOMBIE_PIGMAN_AMBIENT"),
    FIREWORK_LARGE_BLAST("FIREWORK_LARGE_BLAST", "ENTITY_FIREWORK_LARGE_BLAST", "ENTITY_FIREWORK_ROCKET_LARGE_BLAST"),
    FIREWORK_LAUNCH("FIREWORK_LAUNCH", "ENTITY_FIREWORK_LAUNCH", "ENTITY_FIREWORK_ROCKET_LAUNCH"),
    SUCCESSFUL_HIT("SUCCESSFUL_HIT", "ENTITY_PLAYER_ATTACK_STRONG"),
    VILLAGER_TRADE("VILLAGER_HAGGLE", "ENTITY_VILLAGER_TRADING", "ENTITY_VILLAGER_TRADE"),
    VILLAGER_IDLE("VILLAGER_IDLE", "ENTITY_VILLAGER_AMBIENT"),
    VILLAGER_NO("VILLAGER_NO", "ENTITY_VILLAGER_NO"),
    VILLAGER_YES("VILLAGER_YES", "ENTITY_VILLAGER_YES");

    private final String[] versionDependentNames;
    private Sound cached = null;

    private joinSound(String ... versionDependentNames) {
        this.versionDependentNames = versionDependentNames;
    }

    public static Sound byName(@NotNull String name) {
        for (joinSound value : joinSound.values()) {
            for (String versionDependentName : value.versionDependentNames) {
                if (!versionDependentName.equalsIgnoreCase(name)) continue;
                return value.bukkitSound();
            }
        }
        throw new IllegalArgumentException("Found no valid sound name for " + name);
    }

    public Sound bukkitSound() {
        if (this.cached != null) {
            return this.cached;
        }
        for (String name : this.versionDependentNames) {
            try {
                this.cached = Sound.valueOf((String)name);
                return this.cached;
            }
            catch (IllegalArgumentException var6) {
            }
        }
        throw new IllegalArgumentException("Found no valid sound name for " + this.name());
    }
}
