package net.crazy.pingtag.core;

import net.crazy.pingtag.core.PingTagConfiguration.Position;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class PingTagAddon extends LabyAddon<PingTagConfiguration> {

  private PingTag pingTag;

  @Override
  protected void enable() {
    this.registerSettingCategory();

    pingTag = new PingTag(this);

    registerTag();

    configuration().getPosition().addChangeListener(tagPosition -> {
      Laby.labyAPI().tagRegistry().unregister("pingtag");
      registerTag();
    });

    this.logger().info("PingTag | Addon enabled.");
  }

  public void registerTag() {
    if(this.configuration().getPosition().get() == Position.ABOVE) {
      labyAPI().tagRegistry().registerAfter("badge", "pingtag", PositionType.ABOVE_NAME, this.pingTag);
    } else {
      labyAPI().tagRegistry().register("pingtag", PositionType.BELOW_NAME, this.pingTag);
    }
  }

  @Override
  protected Class<PingTagConfiguration> configurationClass() {
    return PingTagConfiguration.class;
  }
}
