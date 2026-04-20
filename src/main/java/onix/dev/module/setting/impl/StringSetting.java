package onix.dev.module.setting.impl;

import onix.dev.module.api.Function;
import onix.dev.module.setting.api.Setting;
import lombok.Getter;

@Getter
public class StringSetting extends Setting<String> {

    public StringSetting(String name, Function parent, String defaultValue) {
        super(name, parent, defaultValue);
    }

    public StringSetting(String name, String defaultValue) {
        super(name, null, defaultValue);
    }
}