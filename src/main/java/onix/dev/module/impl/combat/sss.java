package onix.dev.module.impl.combat;

import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.module.setting.impl.BooleanSetting;
import onix.dev.module.setting.impl.ModeSetting;
import onix.dev.module.setting.impl.NumberSetting;

@ModuleInfo(name = "sss", category = Category.COMBAT, desc = "бежать бежать бежать бежать бежать бежать сасать бежать бежать бежать бежать бежать бежать бежать бежать бежать бежать")
public class sss  extends Function {

    private final BooleanSetting rotate = new BooleanSetting("Rotate", this, true);
    private final NumberSetting range = new NumberSetting("Range", null, 3.0, 1.0, 6.0, 0.1);
    private final ModeSetting mode = new ModeSetting("Mode", this, "Switch", "Switch", "Single", "Multi");

    public sss() {


        addSettings(rotate, range, mode);
    }
}