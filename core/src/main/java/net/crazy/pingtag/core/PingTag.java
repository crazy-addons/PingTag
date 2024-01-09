package net.crazy.pingtag.core;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.RenderableComponent;
import org.jetbrains.annotations.Nullable;

public class PingTag extends NameTag {
    private PingTagAddon addon;

    private PingTag(PingTagAddon addon) {
        this.addon = addon;
    }

    public static PingTag create(PingTagAddon addon) {
        return new PingTag(addon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        if (!addon.configuration().enabled().get())
            return null;

        if (!(this.entity instanceof Player))
            return null;

        Player player = (Player) this.entity;
        int ping = player.networkPlayerInfo().getCurrentPing();

        if (ping == 0) {
            return null;
        }

        String format = addon.configuration().getCustomFormat().getOrDefault("&6%sms").replace("&", "§");

        if (addon.configuration().getColoured().get()) {
            String color = getPingColor(ping);
            format = color + "%sms";
        }

        return RenderableComponent.of(Component.text(String.format(format, ping)));
    }

    @Override
    public boolean isVisible() {
        return super.isVisible() && addon.configuration().enabled().get();
    }

    private String getPingColor(int ping) {
        String color;

        if (ping < 150) {
            color = "§a";
        } else if (ping < 300) {
            color = "§c";
        } else {
            color = "§4";
        }

        return color;
    }
}
