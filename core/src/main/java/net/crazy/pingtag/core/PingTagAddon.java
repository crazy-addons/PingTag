package net.crazy.pingtag.core;

import net.crazy.pingtag.core.PingTagConfiguration.Position;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class PingTagAddon extends LabyAddon<PingTagConfiguration> {

  private static PingTagAddon instance;

  @Override
  protected void enable() {
    this.registerSettingCategory();
    instance = this;

    registerTag();

    this.logger().info("PingTag | Addon enabled.");
  }

  public void registerTag() {
    if(this.configuration().getPosition().get() == Position.ABOVE) {
      labyAPI().tagRegistry().registerAfter("badge", "pingtag", PositionType.ABOVE_NAME, new PingTag(this));
    } else {
      labyAPI().tagRegistry().register("pingtag", PositionType.BELOW_NAME, new PingTag(this));
    }
    logger().info("Registered...");
  }

  @Override
  protected Class<PingTagConfiguration> configurationClass() {
    return PingTagConfiguration.class;
  }

  public static PingTagAddon getInstance() {
    return instance;
  }
}
