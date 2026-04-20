package onix.dev.module.impl.misc;

import com.google.common.eventbus.Subscribe;
import onix.dev.event.impl.render.TextFactoryEvent;
import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.module.setting.impl.BooleanSetting;
import onix.dev.module.setting.impl.StringSetting;
import onix.dev.util.others.Friends;

@ModuleInfo(name = "NameProtect", category = Category.MISC, desc = "Защищает ваш ник и ники друзей от показа в чате")
public class NameProtect extends Function {



    StringSetting nameSetting = new StringSetting("Ник","efef");
    BooleanSetting friendsSetting = new BooleanSetting("Friends",false);

    public NameProtect(){
        addSettings(nameSetting, friendsSetting);
    }

    @Subscribe
    public void onTextFactory(TextFactoryEvent e) {
        e.replaceText(mc.getUser().getName(), nameSetting.getValue());
        if (friendsSetting.getValue()) Friends.getFriends().forEach(friend -> e.replaceText(friend.getName(), nameSetting.getValue()));
    }
}
