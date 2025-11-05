package net.crazy.pingtag.core.snapshot;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.laby3d.renderer.snapshot.AbstractLabySnapshot;
import net.labymod.api.laby3d.renderer.snapshot.Extras;
import org.jetbrains.annotations.Nullable;

public class PingUserSnapshot extends AbstractLabySnapshot {

  private final Component formattedPing;

  public PingUserSnapshot(Player player, Extras extras) {
    super(extras);
    NetworkPlayerInfo info = player.getNetworkPlayerInfo();
    if (info == null) {
      this.formattedPing = null;
      return;
    }
    this.formattedPing = Component.text(info.getCurrentPing());
  }

  @Nullable
  public Component getFormattedPing() {
    return this.formattedPing;
  }
}
