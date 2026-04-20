package onix.dev.module;

import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.impl.misc.DebugPanelModule;
import onix.dev.module.impl.misc.NameProtect;
import onix.dev.module.impl.movement.AutoSprint;
import onix.dev.module.impl.movement.NoDelay;
import onix.dev.module.impl.render.NotificationModule;
import onix.dev.module.impl.render.RenderTest;
import onix.dev.module.impl.render.SwingAnimation;
import onix.dev.module.impl.render.TestModule;
import onix.dev.ui.clickgui.ClickGuiScreen;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class FunctionManager {
    private final List<Function> modules = new ArrayList<>();

    public FunctionManager() {
        modules.addAll(Arrays.asList(
                new AutoSprint(),
                new RenderTest(),
                new TestModule(),
                new DebugPanelModule(),
                new NoDelay(),
                new NotificationModule(),

                new NameProtect(),
                new SwingAnimation(),
                new ClickGuiScreen()
        ));
    }

    public List<Function> getModules(Category category) {
        return modules.stream()
                .filter(module -> module.getCategory() == category)
                .toList();
    }

    @SuppressWarnings("unchecked")
    public <T extends Function> T getModule(Class<T> tClass) {
        return (T) modules.stream()
                .filter(module -> module.getClass() == tClass)
                .findFirst()
                .orElse(null);
    }
}
