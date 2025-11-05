package net.crazy.pingtag.core;

import java.util.Collections;
import java.util.List;
import net.crazy.pingtag.core.snapshot.PingTagExtraKeys;
import net.crazy.pingtag.core.snapshot.PingUserSnapshot;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.tags.ComponentNameTag;
import net.labymod.api.client.render.state.entity.AvatarSnapshot;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import org.jetbrains.annotations.NotNull;

public class PingTag extends ComponentNameTag {

  private final PingTagAddon addon;
  private final PositionType registeredPosition;
  private PositionType activePosition;

  public PingTag(PingTagAddon addon, PositionType registeredPosition) {
    this.addon = addon;
    this.registeredPosition = registeredPosition;
    this.activePosition = this.addon.configuration().getPosition().get().getTagPosition();
    this.addon.configuration().getPosition().addChangeListener(position ->
        this.activePosition = position.getTagPosition()
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

    Component formattedPing = pingUser.getFormattedPing();
    if (formattedPing == null) {
      return super.buildComponents(snapshot);
    }
    return Collections.singletonList(formattedPing);
  }

  @Override
  public float getScale() {
    return this.addon.configuration().getScale().get();
  }
}