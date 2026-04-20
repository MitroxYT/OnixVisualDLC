package onix.dev.module.impl.render;

import com.google.common.eventbus.Subscribe;
import onix.dev.event.impl.render.RenderEvent;
import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.ui.notification.NotificationManager;


@ModuleInfo(
        name = "Notifications",
        category = Category.RENDER,
        visual = true
)
public class NotificationModule extends Function {

    public NotificationModule() {

    }

    @Subscribe
    public void onRender(RenderEvent e) {
        NotificationManager.render(e.renderer());
    }
}