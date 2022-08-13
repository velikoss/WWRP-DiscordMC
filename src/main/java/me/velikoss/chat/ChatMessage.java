// Decompiled with: CFR 0.152
// Class Version: 17
package me.velikoss.chat;

import java.time.Instant;
import org.bukkit.entity.Player;

public class ChatMessage {
    public String author;
    public String message;
    public String headURL;
    long index;
    long timestamp;

    public ChatMessage(String author, String message, long index) {
        this.author = author;
        this.message = message;
        this.headURL = "https://cravatar.eu/helmhead/" + this.author + "/256.png";
        this.index = index;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public ChatMessage(Player author, String message, long index) {
        this.author = author.getName();
        this.message = message;
        this.headURL = "https://cravatar.eu/helmhead/" + this.author + "/256.png";
        this.index = index;
        this.timestamp = Instant.now().getEpochSecond();
    }
}
