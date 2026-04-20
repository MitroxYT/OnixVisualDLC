package onix.dev.module.impl.render;

import com.google.common.eventbus.Subscribe;
import onix.dev.event.impl.render.RenderEvent;
import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.util.render.core.Renderer2D;

@ModuleInfo(name = "TestModule", category = Category.RENDER)
public class TestModule extends Function {
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Subscribe
    public void onRender(RenderEvent event) {
        // Получаем рендерер и размеры экрана прямо из вашего ивента
        Renderer2D renderer = event.renderer();
        int width = event.viewportWidth();
        int height = event.viewportHeight();

        // Передаем их в менеджер уведомлений

    }
}