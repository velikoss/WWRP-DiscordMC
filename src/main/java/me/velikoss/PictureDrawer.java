// Decompiled with: CFR 0.152
// Class Version: 17
package me.velikoss;

import java.awt.Image;
import java.awt.image.BufferedImage;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

public class PictureDrawer
        extends MapRenderer {
    BufferedImage frame;
    MapView.Scale scale;
    int x;
    int y;

    public PictureDrawer(BufferedImage picture, MapView.Scale scale, int x, int y) {
        this.frame = picture;
        this.scale = scale;
        this.x = x;
        this.y = y;
    }

    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player sp) {
        map.setScale(this.scale);
        canvas.drawImage(this.x, this.y, (Image)this.frame);
    }
}
