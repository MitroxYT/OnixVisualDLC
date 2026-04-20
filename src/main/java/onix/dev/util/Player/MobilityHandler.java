package onix.dev.util.Player;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import onix.dev.util.wrapper.Wrapper;
import lombok.experimental.UtilityClass;


import java.util.Objects;

@UtilityClass
public class MobilityHandler implements Wrapper {
    public static boolean isMoving() {
        Vec2 inputVector =  mc.player.input.getMoveVector();

        float forward = inputVector.y;
        float strafe = inputVector.x;
        return forward != 0.0 || strafe != 0.0;
    }



    public static Vec3 getRotationVector(float pitch, float yaw) {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = Mth.cos(g);
        float i = Mth.sin(g);
        float j = Mth.cos(f);
        float k = Mth.sin(f);
        return new Vec3(i * j, -k, h * j);
    }



}
