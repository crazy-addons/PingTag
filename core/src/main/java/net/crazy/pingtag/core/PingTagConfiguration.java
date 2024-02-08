package net.crazy.pingtag.core;

import net.labymod.api.Laby;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownEntryTranslationPrefix;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget.DropdownSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;

@ConfigName("settings")
public class PingTagConfiguration extends AddonConfig {

  public PingTagConfiguration() {
    this.position.addChangeListener(tagPosition -> {
      if(!enabled().get()) return;
      if(PingTagAddon.getInstance() != null) {
        Laby.labyAPI().tagRegistry().unregister("pingtag");
        PingTagAddon.getInstance().logger().info("Unregistered...");
        PingTagAddon.getInstance().registerTag();
      }
    });
    this.customFormat.addChangeListener(ignored -> PingTag.updateCustomFormat(this.customFormat));
  }

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> coloured = new ConfigProperty<>(false);

  @TextFieldWidget.TextFieldSetting
  @SettingRequires(value = "coloured", invert = true)
  private final ConfigProperty<String> customFormat = new ConfigProperty<>("%ping%ms");

  @DropdownSetting
  @DropdownEntryTranslationPrefix("pingtag.settings.position.type")
  private final ConfigProperty<Position> position = new ConfigProperty<>(Position.ABOVE);

  @SliderSetting(min = 0.4F, max = 1.5F, steps = 0.1F)
  private final ConfigProperty<Float> scale = new ConfigProperty<>(1.0F);

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

  public ConfigProperty<Position> getPosition() {
    return position;
  }

  public ConfigProperty<Float> getScale() {
    return scale;
  }

  public enum Position {
    ABOVE, BELOW
  }

}
