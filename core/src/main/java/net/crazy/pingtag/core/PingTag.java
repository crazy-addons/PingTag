package net.crazy.pingtag.core;

import java.util.Collections;
import java.util.List;
import net.crazy.pingtag.core.snapshot.PingTagExtraKeys;
import net.crazy.pingtag.core.snapshot.PingUserSnapshot;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.tags.ComponentNameTag;
import net.labymod.api.client.render.state.entity.AvatarSnapshot;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import org.jetbrains.annotations.NotNull;

public class PingTag extends ComponentNameTag {

  private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();

  private final PingTagAddon addon;
  private final PositionType registeredPosition;
  private PositionType activePosition;
  private boolean coloured;
  private String customFormat;

  public PingTag(PingTagAddon addon, PositionType registeredPosition) {
    this.addon = addon;
    this.registeredPosition = registeredPosition;
    this.activePosition = this.addon.configuration().getPosition().get().getTagPosition();
    this.addon.configuration().getPosition().addChangeListener(position ->
        this.activePosition = position.getTagPosition()
    );
    this.coloured = this.addon.configuration().getColoured().get();
    this.addon.configuration().getColoured().addChangeListener(coloured ->
        this.coloured = coloured
    );
    this.customFormat = this.addon.configuration().getCustomFormat().get();
    this.addon.configuration().getCustomFormat().addChangeListener(customFormat ->
        this.customFormat = customFormat
    );
  }

  @Override
  protected @NotNull List<Component> buildComponents(EntitySnapshot snapshot) {
    if (this.registeredPosition != this.activePosition) {
      return super.buildComponents(snapshot);
    }
    if (!(snapshot instanceof AvatarSnapshot player) || player.isDiscrete()
        || player.isInvisible()) {
      return super.buildComponents(snapshot);
    }
    if (!player.has(PingTagExtraKeys.PING_USER)) {
      return super.buildComponents(snapshot);
    }
    PingUserSnapshot pingUser = player.get(PingTagExtraKeys.PING_USER);

    Integer ping = pingUser.getPing();
    if (ping == null) {
      return super.buildComponents(snapshot);
    }
    Component formattedPing = serializer.deserialize(
        this.customFormat.replace("%ping%", ping.toString()));
    if (this.coloured) {
      TextColor color;
      if (ping < 150) {
        color = NamedTextColor.GREEN;
      } else if (ping < 300) {
        color = NamedTextColor.RED;
      } else {
        color = NamedTextColor.DARK_RED;
      }

      formattedPing.color(color);
    }
    return Collections.singletonList(formattedPing);
  }

  @Override
  public float getScale() {
    return this.addon.configuration().getScale().get();
  }
}