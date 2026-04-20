package onix.dev.module.impl.movement;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import onix.dev.event.impl.game.EventUpdate;
import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.module.setting.impl.BooleanSetting;
import onix.dev.module.setting.impl.ColorSetting;
import onix.dev.module.setting.impl.MultiBoxSetting;
import onix.dev.module.setting.impl.NumberSetting;
import onix.dev.util.payload.OnixVisualPayload;

import java.awt.*;
import java.util.Locale;

@ModuleInfo(name = "AutoSprint", category = Category.MOVEMENT, desc = "бежать бежать бежать бежать бежать бежать сасать бежать бежать бежать бежать бежать бежать бежать бежать бежать бежать")
public class AutoSprint extends Function {
    private MultiBoxSetting settins = new MultiBoxSetting("aa",new BooleanSetting("ssdwd",false));
    private NumberSetting numberSetting = new NumberSetting("aa",this,10,10,15,0.5);
    private ColorSetting colorSetting = new ColorSetting("A",this, Color.BLACK);

    public AutoSprint() {
        addSettings(settins,numberSetting,colorSetting);
    }

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (fullNullCheck()) return;
        mc.options.keySprint.setDown(true);
        mc.getConnection().send(
       new ServerboundCustomPayloadPacket(new OnixVisualPayload("dakla".toLowerCase(Locale.ROOT))));
    }
}
