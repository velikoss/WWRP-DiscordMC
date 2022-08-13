// Decompiled with: CFR 0.152
// Class Version: 17
package me.velikoss;

package me.velikoss;

import com.destroystokyo.paper.event.server.ServerTickEndEvent;
import com.google.gson.GsonBuilder;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import me.clip.placeholderapi.PlaceholderAPI;
import me.velikoss.PictureDrawer;
import me.velikoss.chat.ChatLogger;
import me.velikoss.lang.lang;
import me.velikoss.sounds.joinSound;
import me.velikoss.sounds.joinSoundT;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutSetSlot;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.MessageAttachment;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import ru.mrbrikster.chatty.api.events.ChattyMessageEvent;
import ru.mrbrikster.chatty.util.Sound;

public class WWRPDiscord extends JavaPlugin implements CommandExecutor, Listener {

    public static WWRPDiscord instance;
    String lastMessage;
    public DiscordApi api;
    public ConsoleCommandSender consoleCommandSender = Bukkit.getConsoleSender();
    protected ArrayList<ItemStack> maps = new ArrayList();
    public String SERVER;
    public String CONSOLE;
    static long uptime;

    public void update() {
        this.api.getServerTextChannelById(this.SERVER).get().updateTopic("Сервер: <:on1:1008012700875489420><:on2:1008012699382317056><:on3:1008012698056933406> | Онлайн: " + Bukkit.getOnlinePlayers().size() + " | ТПС: " + Math.min(Math.ceil(Bukkit.getTPS()[2] * 1.25), 20.0) + " | Аптайм: <t:" + uptime + ":R> | Последнее обновление: <t:" + Instant.now().getEpochSecond() + ":R>").join();
    }

    public void onEnable() {
        saveDefaultConfig();
        SERVER = getConfig().getString("SERVER");
        CONSOLE = getConfig().getString("CONSOLE");
        uptime = ManagementFactory.getRuntimeMXBean().getStartTime() / 1000L;
        instance = this;
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
        ((CompletableFuture)new DiscordApiBuilder().setToken(getConfig().getString("token")).setWaitForServersOnStartup(false).login().thenAccept(this::onConnectToDiscord)).exceptionally(error -> {
            this.getLogger().warning("Failed to connect to Discord! Disabling plugin!");
            this.getPluginLoader().disablePlugin((Plugin)this);
            return null;
        });
        BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.scheduleAsyncRepeatingTask((Plugin)this, (Runnable)new BukkitRunnable(){
            public void run() {
                if (api == null) {return;}
                update();
            }
        }, 200L, 6000L);
        Objects.requireNonNull(this.getCommand("dcdebug")).setExecutor((CommandExecutor)this);
    }

    @EventHandler
    public void guiClick(InventoryClickEvent e) {
        if (e.getView().title().equals(Component.text((String)"Звуки входа"))) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(((TextComponent)Component.text((String)"Вы поставили звук входа: ").append((Component)Component.text((String)joinSoundT.values()[e.getRawSlot()].tr))).append(Component.text((String)" [Клик]").clickEvent(ClickEvent.runCommand((String)("/sound id" + e.getRawSlot())))));
            e.getWhoClicked().getPersistentDataContainer().set(new NamespacedKey((Plugin)this, "soundIDJ"), PersistentDataType.INTEGER, (Object)e.getRawSlot());
            e.getWhoClicked().closeInventory();
        }
        if (e.getView().title().equals(Component.text((String)"Звуки выхода"))) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(((TextComponent)Component.text((String)"Вы поставили звук выхода: ").append((Component)Component.text((String)joinSoundT.values()[e.getRawSlot()].tr))).append(Component.text((String)" [Клик]").clickEvent(ClickEvent.runCommand((String)("/sound id" + e.getRawSlot())))));
            e.getWhoClicked().getPersistentDataContainer().set(new NamespacedKey((Plugin)this, "soundIDQ"), PersistentDataType.INTEGER, (Object)e.getRawSlot());
            e.getWhoClicked().closeInventory();
        }
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player p = (Player)sender;
        if (command.getName().equals("sound")) {
            if (args.length > 0) {
                if (args[0].startsWith("id")) {
                    int id = Integer.parseInt(args[0].substring(2));
                    p.playSound(p.getEyeLocation(), joinSound.values()[id].bukkitSound(), 1.0f, 0.75f);
                    return false;
                }
                switch (args[0]) {
                    case "1": {
                        p.sendMessage(Component.text((String)"Данные звуки официально зарегистрированны как произведение искусства. Вы точно хотите продолжить? ").append(((TextComponent)Component.text((String)"[Я ПЛОХОЙ ЧЕЛОВЕК, ПРОДОЛЖИТЬ]").color(TextColor.color((int)Color.RED.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound 2"))));
                        break;
                    }
                    case "2": {
                        p.sendMessage(Component.text((String)"ВЫ ПОНИМАЕТЕ, ЧТО ЭТО НАВСЕГДА???? ").append(((TextComponent)Component.text((String)"[ДА]").color(TextColor.color((int)Color.RED.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound 3"))));
                        break;
                    }
                    case "3": {
                        p.sendMessage(Component.text((String)"Я прошу тебя, ненадо....").append(((TextComponent)Component.text((String)"[ПРОДОЛЖИТЬ]").color(TextColor.color((int)Color.RED.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound 4"))));
                        break;
                    }
                    case "4": {
                        p.sendMessage(Component.text((String)"п-прошу.....").append(((TextComponent)Component.text((String)"[ПРОДОЛЖИТЬ]").color(TextColor.color((int)Color.RED.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound 5"))));
                        break;
                    }
                    case "5": {
                        p.sendMessage(((TextComponent)Component.text((String)"т-точно?").color(TextColor.fromHexString((String)"#a9a9a9"))).append(((TextComponent)Component.text((String)"[ПРОДОЛЖИТЬ]").color(TextColor.color((int)Color.RED.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound 6"))));
                        break;
                    }
                    case "6": {
                        p.sendMessage((Component)Component.text((String)"Вы отключили звук входа/выхода, стоило ли оно того?"));
                        Bukkit.getScoreboardManager().getMainScoreboard().getObjective("sounds").getScore((OfflinePlayer)p).setScore(1);
                        p.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement((NamespacedKey)NamespacedKey.fromString((String)"fratch:fratch/soundoff")))).awardCriteria("impossible");
                        break;
                    }
                    case "new": {
                        p.sendMessage((Component)Component.text((String)"Вы заного включили звуки!"));
                        Bukkit.getScoreboardManager().getMainScoreboard().getObjective("sounds").getScore((OfflinePlayer)p).setScore(0);
                        p.getAdvancementProgress(Objects.requireNonNull(Bukkit.getAdvancement((NamespacedKey)NamespacedKey.fromString((String)"fratch:fratch/soundon")))).awardCriteria("impossible");
                        break;
                    }
                    default: {
                        p.sendMessage(Component.text((String)"Отключая звуки входа и выхода, вы лишаетесь лучшего опыта в своей жизни. Подумайте ещё раз ").append(((TextComponent)Component.text((String)"[Всё равно продолжить :( ]").color(TextColor.color((int)Color.RED.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound 1"))));
                        break;
                    }
                }
            } else if (Objects.requireNonNull(Bukkit.getScoreboardManager().getMainScoreboard().getObjective("sounds")).getScore((OfflinePlayer)p).getScore() == 0) {
                p.sendMessage(Component.text((String)"Отключая звуки входа и выхода, вы лишаетесь лучшего опыта в своей жизни. Подумайте ещё раз ").append(((TextComponent)Component.text((String)"[Всё равно продолжить :( ]").color(TextColor.color((int)Color.RED.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound 1"))));
            } else {
                p.sendMessage(Component.text((String)"ХОЧЕШЬ ВЕРНУТЬ ЗВУКИ ОПЯТЬ???? :)))))").append(((TextComponent)Component.text((String)"[ДАААА]").color(TextColor.color((int)Color.GREEN.getRGB()))).clickEvent(ClickEvent.runCommand((String)"/sound new"))));
            }
        } else if (command.getName().equals("dcdebug")) {
            ((CraftPlayer)p).getHandle().b.a((Packet)new PacketPlayOutSetSlot(0, 0, 36 + p.getInventory().getHeldItemSlot(), CraftItemStack.asNMSCopy((ItemStack)this.maps.get(Integer.parseInt(args[0])))));
            p.sendMap(Objects.requireNonNull(((MapMeta)this.maps.get(Integer.parseInt(args[0])).getItemMeta()).getMapView()));
        } else if (command.getName().equals("join")) {
            if (!p.hasPermission("fratch.sponsor")) {
                p.sendMessage(Component.text((String)"Только для спонсоров!").color(TextColor.color((int)205, (int)92, (int)92)));
            }
            Inventory a = Bukkit.createInventory((InventoryHolder)p, (int)54, (Component)Component.text((String)"Звуки входа"));
            for (int i = 0; i < joinSound.values().length; ++i) {
                ItemStack stack = joinSoundT.values()[i].item;
                ItemMeta meta = stack.getItemMeta();
                meta.displayName((Component)Component.text((String)joinSoundT.values()[i].tr));
                if (p.getPersistentDataContainer().has(new NamespacedKey((Plugin)this, "soundIDJ"), PersistentDataType.INTEGER)) {
                    if ((Integer)p.getPersistentDataContainer().get(new NamespacedKey((Plugin)this, "soundIDJ"), PersistentDataType.INTEGER) == i) {
                        meta.addEnchant(Enchantment.MENDING, 1, true);
                    } else {
                        meta.removeEnchant(Enchantment.MENDING);
                    }
                } else if (i == 0) {
                    meta.addEnchant(Enchantment.MENDING, 1, true);
                }
                stack.setItemMeta(meta);
                a.addItem(new ItemStack[]{stack});
            }
            p.openInventory(a);
        } else if (command.getName().equals("quit")) {
            if (!p.hasPermission("fratch.sponsor")) {
                p.sendMessage(Component.text((String)"Только для спонсоров!").color(TextColor.color((int)205, (int)92, (int)92)));
            }
            Inventory a = Bukkit.createInventory((InventoryHolder)p, (int)54, (Component)Component.text((String)"Звуки выхода"));
            for (int i = 0; i < joinSound.values().length; ++i) {
                ItemStack stack = joinSoundT.values()[i].item;
                ItemMeta meta = stack.getItemMeta();
                meta.displayName((Component)Component.text((String)joinSoundT.values()[i].tr));
                if (p.getPersistentDataContainer().has(new NamespacedKey((Plugin)this, "soundIDQ"), PersistentDataType.INTEGER)) {
                    if ((Integer)p.getPersistentDataContainer().get(new NamespacedKey((Plugin)this, "soundIDQ"), PersistentDataType.INTEGER) == i) {
                        meta.addEnchant(Enchantment.MENDING, 1, true);
                    } else {
                        meta.removeEnchant(Enchantment.MENDING);
                    }
                } else if (i == 1) {
                    meta.addEnchant(Enchantment.MENDING, 1, true);
                }
                stack.setItemMeta(meta);
                a.addItem(new ItemStack[]{stack});
            }
            p.openInventory(a);
        }
        return false;
    }

    @EventHandler
    public void join(PlayerJoinEvent event) throws MalformedURLException {
        currentLogger.add(event.getPlayer().getName(), "🟩 **Зашел на сервер**");
        currentLogger.update();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Objects.requireNonNull(Bukkit.getScoreboardManager().getMainScoreboard().getObjective("sounds")).getScore((OfflinePlayer)p).getScore() != 0) continue;
            if (event.getPlayer().getPersistentDataContainer().has(new NamespacedKey((Plugin)this, "soundIDJ"), PersistentDataType.INTEGER)) {
                int id = (Integer)event.getPlayer().getPersistentDataContainer().get(new NamespacedKey((Plugin)this, "soundIDJ"), PersistentDataType.INTEGER);
                p.playSound(p.getEyeLocation(), joinSound.values()[id].bukkitSound(), 1.0f, 0.75f);
                continue;
            }
            p.playSound(p.getEyeLocation(), Sound.DOOR_OPEN.bukkitSound(), 1.0f, 0.75f);
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) throws MalformedURLException {
        currentLogger.add(event.getPlayer().getName(), "🟥 **Вышел из игры**");
        currentLogger.update();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (Objects.requireNonNull(Bukkit.getScoreboardManager().getMainScoreboard().getObjective("sounds")).getScore((OfflinePlayer)p).getScore() != 0) continue;
            if (event.getPlayer().getPersistentDataContainer().has(new NamespacedKey((Plugin)this, "soundIDQ"), PersistentDataType.INTEGER)) {
                int id = (Integer)event.getPlayer().getPersistentDataContainer().get(new NamespacedKey((Plugin)this, "soundIDQ"), PersistentDataType.INTEGER);
                p.playSound(p.getEyeLocation(), joinSound.values()[id].bukkitSound(), 1.0f, 0.75f);
                continue;
            }
            p.playSound(p.getEyeLocation(), Sound.DOOR_CLOSE.bukkitSound(), 1.0f, 0.75f);
        }
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            BufferedImage buff = (BufferedImage)img;
            return buff;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), 2);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void chat(ChattyMessageEvent e) throws MalformedURLException, ExecutionException, InterruptedException {
        if (e.getChat().getRange() != -2) {
            return;
        }
        currentLogger.add(e.getPlayer(), e.getMessage());
        currentLogger.update();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) throws MalformedURLException, ParseException {
        String replace = "???";
        String comp = PlainTextComponentSerializer.plainText().serialize(Objects.requireNonNull(e.deathMessage()));
        if (comp.contains("by ")) {
            replace = comp.substring(comp.indexOf("by ") + 3);
        }
        String or = Objects.requireNonNull(e.deathMessage()).toString();
        String a = or.substring(or.indexOf("key=\""));
        a = a.substring(5, a.indexOf("\", "));
        a = ((JSONObject)new GsonBuilder().create().fromJson(lang.ru_ru, JSONObject.class)).get((Object)a).toString();
        if (a.contains("%2$s")) {
            a = a.replace("%2$s", replace);
        }
        currentLogger.add(e.getPlayer(), a.replace("%1$s", "💀 **") + "**");
        currentLogger.update();
    }

    private void onConnectToDiscord(DiscordApi api) {
        this.api = api;
        this.getLogger().info("Connected to Discord as " + api.getYourself().getDiscriminatedName());
        this.getLogger().info("Open the following url to invite the bot: " + api.createBotInvite());
        currentLogger = new ChatLogger("1");
        try {
            currentLogger.clear();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        api.setMessageCacheSize(10, 3600);
        SlashCommand command = (SlashCommand)SlashCommand.with("online", "Текущий онлайн").createGlobal(this.api).join();
        SlashCommand tps = (SlashCommand)SlashCommand.with("tps", "Текущий ТПС").createGlobal(this.api).join();
        api.addSlashCommandCreateListener(event -> {
            currentLogger.delete();
            SlashCommandInteraction slashCommandInteraction = event.getSlashCommandInteraction();
            slashCommandInteraction.respondLater().thenAccept(interactionOriginalResponseUpdater -> {
                if (event.getSlashCommandInteraction().getChannel().get().getId() == SERVER && Objects.equals(event.getSlashCommandInteraction().getCommandName(), "online")) {
                    StringBuilder a = new StringBuilder();
                    a.append("Текущий онлайн на сервере: ").append("**").append(Bukkit.getOnlinePlayers().size()).append("**").append(" \n`");
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        a.append(player.getName()).append(", ");
                    }
                    ((InteractionOriginalResponseUpdater)interactionOriginalResponseUpdater.setContent(a.substring(0, a.length() - 2) + "`")).update();
                } else if (event.getSlashCommandInteraction().getChannel().get().getId() == SERVER) {
                    try {
                        String joinText = "%spark_tps_5m%";
                        joinText = PlaceholderAPI.setPlaceholders((OfflinePlayer)Bukkit.getOfflinePlayer((String)"velikoss"), (String)joinText);
                        ((InteractionOriginalResponseUpdater)interactionOriginalResponseUpdater.setContent("Текущий ТПС: **" + Double.parseDouble(joinText.replaceAll("[a-zA-Z]", "").replace("§", "").replace("*", "")) + "**")).update();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        api.addMessageCreateListener(event -> {
            if (String.valueOf(event.getChannel().getId()) == this.CONSOLE) {
                new BukkitRunnable(){

                    public void run() {
                        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), (String)event.getMessageContent());
                    }
                }.runTask((Plugin)this);
            }
            if (event.getMessageAuthor().isWebhook() || event.getMessageAuthor().isBotUser()) {
                return;
            }
            if (String.valueOf(event.getChannel().getId()) == this.SERVER) {
                currentLogger.delete();
                ComponentBuilder cb = Component.empty().toBuilder();
                ComponentBuilder cbP = Component.empty().toBuilder();
                int indx = 0;
                for (MessageAttachment e : event.getMessageAttachments()) {
                    if (!e.isImage() && !e.getMessage().getContent().endsWith(".gif")) continue;
                    try {
                        cb.append(((TextComponent)Component.text((String)" ").color(TextColor.color((int)Color.DARK_GRAY.getRGB()))).clickEvent(ClickEvent.runCommand((String)("/dcdebug " + this.maps.size()))));
                        cbP.append(((TextComponent)Component.text((String)"[картинка] ").color(TextColor.color((int)Color.DARK_GRAY.getRGB()))).clickEvent(ClickEvent.runCommand((String)("/dcdebug " + this.maps.size()))));
                        MapView map = Bukkit.createMap((World)Objects.requireNonNull(Bukkit.getWorld((String)"world")));
                        for (MapRenderer mapRenderer : map.getRenderers()) {
                            map.removeRenderer(mapRenderer);
                        }
                        BufferedImage bufferedImage;
                        try {
                            double width = e.getWidth().get().intValue();
                            height = e.getHeight().get().intValue();
                            bufferedImage = ImageIO.read(new URL(e.getUrl().toString() + "?width=" + Math.max(width / Math.floor(width / 128.0), 128.0) + "&height=" + Math.max(height / Math.floor(height / 128.0), 128.0)));
                        }
                        catch (IOException ex) {
                            this.getLogger().log(Level.WARNING, "Using alternative download method (SLOWER)");
                            bufferedImage = e.downloadAsImage().get();
                        }
                        Image image = bufferedImage.getScaledInstance(128, 128, 2);
                        map.addRenderer((MapRenderer)new PictureDrawer(WWRPDiscord.toBufferedImage(image), MapView.Scale.NORMAL, 0, 0));
                        ItemStack item = new ItemStack(Material.FILLED_MAP, 1);
                        MapMeta meta = (MapMeta)item.getItemMeta();
                        meta.setMapView(map);
                        item.setItemMeta((ItemMeta)meta);
                        this.maps.add(item);
                        ++indx;
                    }
                    catch (InterruptedException | ExecutionException ex) {
                        ex.printStackTrace();
                    }
                }
                TextComponent aaa = Component.text((String)(event.getMessageContent() + " "));
                TextComponent aaaa = Component.text((String)("[DC] " + event.getMessageAuthor().getDisplayName() + " ▹ "));
                aaaa = aaaa.color(TextColor.fromHexString((String)"#6469f5"));
                aaa = aaa.color(TextColor.fromHexString((String)"#d2d3f8"));
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.sendMessage(aaaa.append((Component)aaa).append((Component)cbP.build()));
                }
                this.getLogger().log(Level.INFO, PlainTextComponentSerializer.plainText().serialize(aaaa.append((Component)aaa).append((Component)cbP.build())));
            }
            System.gc();
        });
    }

    public void onDisable() {
        if (this.api != null) {
            ((CompletableFuture)((CompletableFuture)this.api.getServerTextChannelById(this.SERVER).get().updateTopic("Сервер: <:off1:1008012696622485544><:off2:1008012695037022229><:off3:1008012693686468703>").thenRun(() -> this.api.disconnect())).thenRun(() -> {
                this.api = null;
            })).join();
        }
    }
}
