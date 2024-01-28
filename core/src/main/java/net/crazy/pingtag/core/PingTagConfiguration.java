package net.crazy.pingtag.core;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

@ConfigName("settings")
public class PingTagConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> coloured = new ConfigProperty<>(false);

  @TextFieldWidget.TextFieldSetting
  @SettingRequires(value = "coloured", invert = true)
  private final ConfigProperty<String> customFormat = new ConfigProperty<>("%ping%ms");

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> getColoured() {
    return coloured;
  }

  public ConfigProperty<String> getCustomFormat() {
    return customFormat;
  }
}
