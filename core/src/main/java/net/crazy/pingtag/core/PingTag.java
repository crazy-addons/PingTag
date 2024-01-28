package net.crazy.pingtag.core;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import org.jetbrains.annotations.Nullable;

public class PingTag extends NameTag {

    private final PingTagAddon addon;

    private String prePingFormat;
    private String postPingFormat;

    private PingTag(PingTagAddon addon) {
        this.addon = addon;

        ConfigProperty<String> customFormat = addon.configuration().getCustomFormat();
        this.updateCustomFormat(customFormat);
        customFormat.addChangeListener(ignored -> this.updateCustomFormat(customFormat));
    }

    public static PingTag create(PingTagAddon addon) {
        return new PingTag(addon);
    }

    @Override
    protected @Nullable RenderableComponent getRenderableComponent() {
        if (!(this.entity instanceof Player player)) {
            return null;
        }

        PingTagConfiguration configuration = this.addon.configuration();
        if (!configuration.enabled().get()) {
            return null;
        }

        int ping = player.networkPlayerInfo().getCurrentPing();
        if(ping == 0) {
            return null;
        }

        String format = this.prePingFormat;
        if (configuration.getColoured().get()) {
            String color;
            if (ping < 150) {
                color = "§a";
            } else if (ping < 300) {
                color = "§c";
            } else {
                color = "§4";
            }

            format += color;
        }

        format += ping;
        if (this.postPingFormat != null) {
            format += this.postPingFormat;
        }

        return RenderableComponent.of(Component.text(format));
    }

    private void updateCustomFormat(ConfigProperty<String> formatProperty) {
        String format = formatProperty.get();
        if (format == null || format.trim().length() == 0) {
            format = formatProperty.defaultValue();
        }

        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        String[] parts = componentMapper.translateColorCodes(format).split("%ping%", 2);
        this.prePingFormat = parts[0];
        this.postPingFormat = parts.length == 2 ? parts[1] : null;
    }
}