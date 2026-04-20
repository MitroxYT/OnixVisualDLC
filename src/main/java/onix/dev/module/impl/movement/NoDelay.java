package onix.dev.module.impl.movement;

import com.google.common.eventbus.Subscribe;
import onix.dev.event.impl.game.EventUpdate;
import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.module.setting.impl.BooleanSetting;

@ModuleInfo(name = "NoDelay", category = Category.MOVEMENT, desc = "- Задержка")
public class NoDelay extends Function {
    public final BooleanSetting BreakCoolDown = new BooleanSetting("BreakCoolDown",  false);

    public final BooleanSetting RightClick = new BooleanSetting("RightClick",  false);

    public final BooleanSetting Jump = new BooleanSetting("Jump",  false);
    public NoDelay() {
    addSettings(BreakCoolDown, RightClick, Jump);
    }
    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (BreakCoolDown.getValue()) {
            assert mc.gameMode != null;
            mc.gameMode.destroyDelay = 0;
        }
        if (Jump.getValue()) {
            assert mc.player != null;
            mc.player.noJumpDelay= 0;
        }
        if (RightClick.getValue()) mc.rightClickDelay = 0;
    }
}