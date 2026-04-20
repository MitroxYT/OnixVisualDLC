package onix.dev.util.wrapper;


import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;

public interface Wrapper {
    Minecraft mc = Minecraft.getInstance();
    DeltaTracker tickCounter = mc.getDeltaTracker();
    Window window = mc.getWindow();
}

