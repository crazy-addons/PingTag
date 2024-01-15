package net.crazy.pingtag.core;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
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


    private final String defaultFormat = "%sms";
    public String format = "%sms";

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

        Component pingTag = Component.text(String.format(format, ping));

        if (addon.configuration().getColoured().get()) {
            pingTag.color(getPingColor(ping));
        }

        return RenderableComponent.of(pingTag);
    }

    private TextColor getPingColor(int ping) {
        TextColor color;

        if (ping < 150) {
            color = NamedTextColor.GREEN;
        } else if (ping < 300) {
            color = NamedTextColor.RED;
        } else {
            color = NamedTextColor.DARK_RED;
        }

        return color;
    }

    public void updateFormat() {
        this.format = addon.configuration().getColoured().get() ? this.defaultFormat
                : addon.configuration().getCustomFormat().getOrDefault("%sms").replace('&', '§');
    }
}
