package net.crazy.pingtag.core;

import net.crazy.pingtag.core.PingTagConfiguration.Position;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class PingTagAddon extends LabyAddon<PingTagConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    for (Position position : Position.values()) {
      PositionType positionType = position.getTagPosition();
      Laby.labyAPI().tagRegistry()
          .register("ping_display", positionType, new PingTag(this, positionType));
    }

    this.logger().info("PingTag | Addon enabled.");
  }

  @Override
  protected Class<PingTagConfiguration> configurationClass() {
    return PingTagConfiguration.class;
  }
}
