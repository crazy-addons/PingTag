package net.crazy.pingtag.core.snapshot;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.laby3d.renderer.snapshot.AbstractLabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import org.jetbrains.annotations.Nullable;

public class PingUserSnapshot extends AbstractLabySnapshot {

  private final Integer ping;

  public PingUserSnapshot(Player player, Extras extras) {
    super(extras);
    NetworkPlayerInfo info = player.getNetworkPlayerInfo();
    if (info == null) {
      this.ping = null;
      return;
    }
    this.ping = info.getCurrentPing();
  }

  @Nullable
  public Integer getPing() {
    return this.ping;
  }
}
