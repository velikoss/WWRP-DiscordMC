// Decompiled with: CFR 0.152
// Class Version: 17
package me.velikoss.chat;

import java.net.MalformedURLException;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import me.velikoss.EmojiManager;
import me.velikoss.WWRPDiscord;
import me.velikoss.chat.ChatMessage;
import org.bukkit.entity.Player;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ChatLogger {
    static newChat = false;
    private static final Vector<ChatMessage> queue = new Vector();
    static long index = 0L;
    static short MAXSIZE = (short)20;
    public static String messageID;
    public static boolean newChat;

    public ChatLogger(String messageID) {
        WWRPDiscord.instance.getLogger().log(Level.WARNING, "NEW LOGGER");
        if (messageID != null && !messageID.isEmpty()) {
            return;
        }
        ChatLogger.messageID = messageID;
    }

    public void add(String author, String message) throws MalformedURLException {
        ChatMessage cmessage = new ChatMessage(author, message, ++index);
        EmojiManager.add(cmessage.headURL, author);
        queue.add(cmessage);
    }

    public void add(Player author, String message) throws MalformedURLException {
        ChatMessage cmessage = new ChatMessage(author, message, ++index);
        EmojiManager.add(cmessage.headURL, author.getName());
        queue.add(cmessage);
    }

    public void delete() {
        if (index == 0L && messageID != null && !messageID.equals("1") && !newChat) {
            try {
                Message message = WWRPDiscord.instance.api.getMessageById(messageID, (TextChannel)WWRPDiscord.instance.api.getChannelById(WWRPDiscord.instance.SERVER).get()).join();
                message.delete().join();
            }
            catch (CompletionException e) {
                newChat = true;
                queue.clear();
                index = 0L;
                EmojiManager.clear();
            }
        }
        newChat = true;
        queue.clear();
        index = 0L;
        EmojiManager.clear();
    }

    public void clear() throws ExecutionException, InterruptedException {
        DiscordEntity message = null;
        try {
            message = ((TextChannel)WWRPDiscord.instance.api.getChannelById(WWRPDiscord.instance.SERVER).get()).sendMessage(new EmbedBuilder().setFooter("velikoss (C) 2022").setDescription("Чат появится здесь позже, мб").setTitle("Чат")).get();
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert (message != null);
        messageID = String.valueOf(message.getId());
    }

    public String format() {
        StringBuilder format = new StringBuilder();
        queue.forEach(e -> format.append("||<t:").append(e.timestamp).append(":t>|| ").append(EmojiManager.get(e.author)).append(" **").append(e.author).append(e.message.startsWith("🟩") || e.message.startsWith("🟥") || e.message.startsWith("💀") ? "** " : ":** ").append(e.message).append('\n'));
        if (format.toString().length() >= 4096) {
            return "!!!CLEAR!!!&";
        }
        return format.toString();
    }

    public void update() {
        String format = this.format();
        if (newChat) {
            newChat = false;
            try {
                this.clear();
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (Objects.equals(format, "!!!CLEAR!!!&")) {
            this.delete();
            WWRPDiscord.instance.getLogger().log(Level.INFO, "CLEAR!!!!!");
            return;
        }
        WWRPDiscord.instance.api.getMessageById(messageID, (TextChannel)WWRPDiscord.instance.api.getChannelById(WWRPDiscord.instance.SERVER).get()).join().edit("", new EmbedBuilder().setFooter("velikoss (C) 2022").setDescription(format).setTitle("Чат"));
    }

}
