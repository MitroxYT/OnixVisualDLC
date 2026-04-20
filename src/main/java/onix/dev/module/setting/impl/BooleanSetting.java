package onix.dev.module.setting.impl;

import onix.dev.module.api.Function;
import onix.dev.module.setting.api.Setting;
import lombok.Getter;
import lombok.Setter;

@Getter
public class BooleanSetting extends Setting<Boolean> {
    @Getter @Setter
    private int key = -1;

    public BooleanSetting(String name, Function parent, boolean value) {
        super(name, parent, value);
    }

    public BooleanSetting(String name, boolean value) {
        super(name, null, value);
    }

    public void toggle() {
        setValue(!getValue());
    }
}