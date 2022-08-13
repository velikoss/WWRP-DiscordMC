// Decompiled with: CFR 0.152
// Class Version: 17
package me.velikoss.sounds;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum joinSoundT {
    DOOR_OPEN(new ItemStack(Material.OAK_DOOR), "Открытие двери (дефолт)"),
    DOOR_CLOSE(new ItemStack(Material.OAK_DOOR), "Закрытие двери"),
    AMBIENCE_CAVE(new ItemStack(Material.STONE), "Жуткий шум"),
    ANVIL_BREAK(new ItemStack(Material.ANVIL), "Наковальня сломалась"),
    ANVIL_LAND(new ItemStack(Material.ANVIL, 2), "Наковальня упала"),
    ANVIL_USE(new ItemStack(Material.ANVIL, 3), "Наковальня использовалась"),
    ARROW_HIT(new ItemStack(Material.ARROW), "Попадание стрелой"),
    BURP(new ItemStack(Material.PLAYER_HEAD), "Отрыжка"),
    CHEST_CLOSE(new ItemStack(Material.CHEST), "Сундук закрывается"),
    CHEST_OPEN(new ItemStack(Material.CHEST), "Сундук открывается"),
    CLICK(new ItemStack(Material.OAK_BUTTON), "Щелчок"),
    DRINK(new ItemStack(Material.POTION), "Пьёт"),
    EAT(new ItemStack(Material.COOKED_BEEF), "Ест"),
    EXPLODE(new ItemStack(Material.TNT), "Взрыв"),
    FUSE(new ItemStack(Material.TNT), "Динамит поджигается (АХАХАХАХХА)"),
    GLASS(new ItemStack(Material.GLASS), "Стекло ломается"),
    HURT_FLESH(new ItemStack(Material.STICK), "Игрок получает урон"),
    ITEM_BREAK(new ItemStack(Material.DIAMOND_PICKAXE), "Предмет ломается"),
    LAVA(new ItemStack(Material.LAVA_BUCKET), "Лава"),
    LEVEL_UP(new ItemStack(Material.EXPERIENCE_BOTTLE), "Левелап"),
    NOTE_BASS(new ItemStack(Material.NOTE_BLOCK), "Басс"),
    NOTE_PIANO(new ItemStack(Material.NOTE_BLOCK, 2), "Пианино"),
    NOTE_PLING(new ItemStack(Material.NOTE_BLOCK, 3), "Плинг"),
    ORB_PICKUP(new ItemStack(Material.EXPERIENCE_BOTTLE), "Подобрал опыт"),
    PISTON_EXTEND(new ItemStack(Material.PISTON), "Поршень выдвинулся"),
    PISTON_RETRACT(new ItemStack(Material.PISTON), "Поршень вдвинулся"),
    SHOOT_ARROW(new ItemStack(Material.BOW), "Стрельнули"),
    SPLASH(new ItemStack(Material.WATER_BUCKET), "чё"),
    WOOD_CLICK(new ItemStack(Material.OAK_BUTTON), "Щелчок кнопкой"),
    BAT_HURT(new ItemStack(Material.BAT_SPAWN_EGG), "Летучая мышь получила урон"),
    BLAZE_HIT(new ItemStack(Material.BLAZE_SPAWN_EGG), "Блейз получил урон"),
    CAT_MEOW(new ItemStack(Material.CAT_SPAWN_EGG), "Чебупеля"),
    CHICKEN_IDLE(new ItemStack(Material.CHICKEN), "Курица"),
    COW_IDLE(new ItemStack(Material.BEEF), "Корова"),
    CREEPER_HISS(new ItemStack(Material.CREEPER_HEAD), "Крипер поджигается (АХАХАХАХААХАХХА)"),
    ENDERMAN_SCREAM(new ItemStack(Material.ENDERMAN_SPAWN_EGG), "эндермен орёт"),
    GHAST_SCREAM(new ItemStack(Material.GHAST_SPAWN_EGG), "гаст орёт"),
    GHAST_MOAN(new ItemStack(Material.GHAST_SPAWN_EGG), "гаст"),
    PIG_IDLE(new ItemStack(Material.PORKCHOP), "Свинья"),
    SHEEP_IDLE(new ItemStack(Material.WHITE_WOOL), "Овца"),
    SKELETON_IDLE(new ItemStack(Material.BONE), "Скелет"),
    SLIME_WALK(new ItemStack(Material.SLIME_BLOCK), "Слайм прыгнул"),
    SPIDER_IDLE(new ItemStack(Material.STRING), "Паук"),
    WOLF_BARK(new ItemStack(Material.WOLF_SPAWN_EGG), "Волк гав"),
    WOLF_PANT(new ItemStack(Material.WOLF_SPAWN_EGG), "Волк дышит"),
    ZOMBIE_WALK(new ItemStack(Material.ZOMBIE_HEAD), "Зомби хз"),
    ZOMBIE_PIG_IDLE(new ItemStack(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG), "Зомби пиглин"),
    FIREWORK_LARGE_BLAST(new ItemStack(Material.FIREWORK_ROCKET), "Феерверк взрыв"),
    FIREWORK_LAUNCH(new ItemStack(Material.FIREWORK_ROCKET), "Феерверк вылетел"),
    SUCCESSFUL_HIT(new ItemStack(Material.SHIELD), "Сильная атака"),
    VILLAGER_TRADE(new ItemStack(Material.VILLAGER_SPAWN_EGG), "Трейд с жителем"),
    VILLAGER_IDLE(new ItemStack(Material.VILLAGER_SPAWN_EGG, 2), "Житель"),
    VILLAGER_NO(new ItemStack(Material.VILLAGER_SPAWN_EGG, 3), "Житель нет"),
    VILLAGER_YES(new ItemStack(Material.VILLAGER_SPAWN_EGG, 4), "Житель да");

    public final ItemStack item;
    public final String tr;

    private joinSoundT(ItemStack item, String name) {
        this.item = item;
        this.tr = name;
    }
}
