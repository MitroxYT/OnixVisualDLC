package onix.dev.module.setting.api;

import onix.dev.util.render.core.Renderer2D;
import onix.dev.util.render.text.FontObject;

public interface SettingElement {
    void render(Renderer2D renderer, FontObject font, float x, float y, float width, float alpha);

    float getHeight();

    boolean mouseClicked(double mouseX, double mouseY, float x, float y, float width);

    default boolean mouseClicked(double mouseX, double mouseY, int button, float x, float y, float width) {
        return mouseClicked(mouseX, mouseY, x, y, width);
    }

    void updateHover(double mouseX, double mouseY, float x, float y, float width);


    void mouseReleased(double mouseX, double mouseY, int button);

    void mouseDragged(double mouseX, double mouseY, int button, float x, float y, float width);

    Setting<?> getSetting();
}