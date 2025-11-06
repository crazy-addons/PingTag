package net.crazy.pingtag.core.snapshot;

import net.labymod.api.laby3d.renderer.snapshot.ExtraKey;

public class PingTagExtraKeys {

  public static final ExtraKey<PingUserSnapshot> PING_USER = ExtraKey.of(
      "ping_user",
      PingUserSnapshot.class
  );

}
