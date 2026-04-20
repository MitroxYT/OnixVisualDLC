package onix.dev.event.impl.render;

import net.minecraft.client.Minecraft;
import onix.dev.event.api.Event;
import onix.dev.util.render.core.Renderer2D;
import onix.dev.util.render.text.FontObject;

import java.util.Objects;

public final class RenderEvent extends Event {

    private final Minecraft client;
    private final Renderer2D renderer;
    private final FontObject defaultFont;
    private final int viewportWidth;
    private final int viewportHeight;

    public RenderEvent(
            Minecraft client,
            Renderer2D renderer,
            FontObject defaultFont,
            int viewportWidth,
            int viewportHeight
    ) {
        this.client = Objects.requireNonNull(client, "client");
        this.renderer = Objects.requireNonNull(renderer, "renderer");
        this.defaultFont = Objects.requireNonNull(defaultFont, "defaultFont");
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
    }

    public Minecraft client() {
        return client;
    }

    public Renderer2D renderer() {
        return renderer;
    }

    public FontObject defaultFont() {
        return defaultFont;
    }

    public int viewportWidth() {
        return viewportWidth;
    }

    public int viewportHeight() {
        return viewportHeight;
    }
}
