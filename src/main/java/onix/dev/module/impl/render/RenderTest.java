package onix.dev.module.impl.render;

import com.google.common.eventbus.Subscribe;
import onix.dev.event.impl.render.RenderEvent;
import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.util.render.core.Renderer2D;
import onix.dev.util.render.text.FontRegistry;

import java.awt.*;

@ModuleInfo(name = "test", category = Category.RENDER, desc = "Рендер Ватермарки",visual = true )
public class RenderTest extends Function {

    public RenderTest() {
//        setKey(GLFW.GLFW_KEY_RIGHT_ALT);
    }

    @Subscribe
    public void onRender(RenderEvent event) {
        render(event.renderer());
    }

    public void render(Renderer2D renderer) {

            float widths = renderer.measureText(FontRegistry.SF_REGULAR, "Onix Visuals", 10).width() * 1.4f+0.9f;
            //renderer.shadow(5, 5, 60, 22, 4, 1, 1, new Color(25, 23, 28, 80).getRGB());
            renderer.rect(5, 5, widths, 22, 4, new Color(25, 23, 28).getRGB());
            renderer.text(FontRegistry.SF_REGULAR, 16, 20f, 11, "Onix Visuals", Color.WHITE.getRGB());

            float width = renderer.measureText(FontRegistry.SF_REGULAR, mc.getFps() + "fps", 10).width() + renderer.measureText(FontRegistry.SF_REGULAR, "fps", 10).width() + 11 * 2;
  //          renderer.shadow(90, 5, width, 22, 4, 1, 1, new Color(25, 23, 28, 80).getRGB());
       // System.out.println(width);
            renderer.rect(90, 5, width, 22, 4, new Color(25, 23, 28).getRGB());
            renderer.text(FontRegistry.SF_REGULAR, 100, 20f, 10, mc.getFps() + " fps", Color.WHITE.getRGB());

            String role = "Dev";
            float aaa = role.equals("Premium") ?  75.01758F : 58.865234F;
            float widtha = renderer.measureText(FontRegistry.SF_REGULAR, "Role: " + role, 10).width() * 1.26f;
          //  System.out.println(widtha);
            renderer.rect(5,30, aaa, 22, 4, new Color(25, 23, 28).getRGB());
            renderer.text(FontRegistry.SF_REGULAR, 15, 45f, 10, "Role: " + role, Color.WHITE.getRGB());

    }

    @Subscribe
    public void onRenders(RenderEvent e) {

    }
}
