package onix.dev.event.impl.game;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.screens.Screen;
import onix.dev.event.api.Event;
import onix.dev.util.wrapper.Wrapper;
import lombok.Getter;


@Getter
public class KeyEvent extends Event implements Wrapper {

    private final Screen screen;
    private final InputConstants.Type type;
    private final int key;
    private final int action;

    public KeyEvent(Screen screen, InputConstants.Type type, int key, int action) {
        this.screen = screen;
        this.type = type;
        this.key = key;
        this.action = action;
    }

    public boolean isKeyDown(int key) {
        return isKeyDown(key, mc.screen == null);
    }

    public boolean isKeyDown(int key, boolean screen) {
        return this.key == key && action == 1 && screen;
    }

    public boolean isKeyReleased(int key) {
        return isKeyReleased(key, mc.screen == null);
    }

    public boolean isKeyReleased(int key, boolean screen) {
        return this.key == key && action == 0 && screen;
    }

}