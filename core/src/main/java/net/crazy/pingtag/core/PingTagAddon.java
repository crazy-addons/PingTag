package net.crazy.pingtag.core;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class PingTagAddon extends LabyAddon<PingTagConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    PingTag tag = PingTag.create(this);
    labyAPI().tagRegistry().registerAfter("pingtag", "badge", PositionType.ABOVE_NAME, tag);

    configuration().getCustomFormat().addChangeListener(string -> tag.updateFormat());
    configuration().getColoured().addChangeListener(bool -> tag.updateFormat());

    this.logger().info("PingTag | Addon enabled.");
  }

  @Override
  protected Class<PingTagConfiguration> configurationClass() {
    return PingTagConfiguration.class;
  }
}
