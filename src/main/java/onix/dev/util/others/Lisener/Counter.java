package onix.dev.util.others.Lisener;

import net.minecraft.util.Mth;
import onix.dev.util.wrapper.Wrapper;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Counter implements Wrapper {

    @Getter
    private int currentFPS;

    public void updateFPS() {
        int prevFPS = mc.getFps();
        currentFPS = (int) Mth.lerp(0.8f, prevFPS, currentFPS);
    }


}