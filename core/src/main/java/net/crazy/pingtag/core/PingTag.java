package net.crazy.pingtag.core;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.tags.NameTag;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import org.jetbrains.annotations.Nullable;

public class PingTag extends NameTag {

    private final PingTagAddon addon;

    private static String prePingFormat;
    private static String postPingFormat;

    public PingTag(PingTagAddon addon) {
        this.addon = addon;
        ConfigProperty<String> customFormat = addon.configuration().getCustomFormat();
        updateCustomFormat(customFormat);
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

        NetworkPlayerInfo networkPlayerInfo = player.networkPlayerInfo();
        if (networkPlayerInfo == null) {
            return null;
        }

        int ping = networkPlayerInfo.getCurrentPing();
        if(ping == 0) {
            return null;
        }

        String format = prePingFormat;
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
        if (postPingFormat != null) {
            format += postPingFormat;
        }

        return RenderableComponent.of(Component.text(format));
    }

  @Override
  public boolean isVisible() {
    return this.addon.configuration().enabled().get() && entity instanceof Player;
  }

  @Override
  public float getScale() {
    return this.addon.configuration().getScale().get();
  }

  public static void updateCustomFormat(ConfigProperty<String> formatProperty) {
        String format = formatProperty.get();
        if (format == null || format.trim().isEmpty()) {
            format = formatProperty.defaultValue();
        }

        ComponentMapper componentMapper = Laby.labyAPI().minecraft().componentMapper();
        String[] parts = componentMapper.translateColorCodes(format).split("%ping%", 2);
        prePingFormat = parts[0];
        postPingFormat = parts.length == 2 ? parts[1] : null;
    }
}