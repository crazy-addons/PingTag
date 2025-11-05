package net.crazy.pingtag.core.snapshot;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import net.labymod.api.laby3d.renderer.snapshot.LabySnapshotFactory;
import net.labymod.api.service.annotation.AutoService;

@AutoService(LabySnapshotFactory.class)
public class PingUserSnapshotFactory extends LabySnapshotFactory<Player, PingUserSnapshot> {

  public PingUserSnapshotFactory() {
    super(PingTagExtraKeys.PING_USER);
  }

  @Override
  protected PingUserSnapshot create(Player player, Extras extras) {
    return new PingUserSnapshot(player, extras);
  }
}
