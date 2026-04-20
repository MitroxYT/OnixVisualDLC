package onix.dev.module.impl.combat.furry;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import onix.dev.util.wrapper.Wrapper;
import lombok.experimental.UtilityClass;


import static java.lang.Math.hypot;
import static java.lang.Math.toDegrees;
import static net.minecraft.util.Mth.wrapDegrees;

@UtilityClass
public class AngleUtil implements Wrapper {
    public Angle fromVec2f(Vec2 vector2f) {
        return new Angle(vector2f.y, vector2f.x);
    }

    public Angle fromVec3d(Vec3 vector) {
        return new Angle((float) wrapDegrees(toDegrees(Math.atan2(vector.z, vector.x)) - 90), (float) wrapDegrees(toDegrees(-Math.atan2(vector.y, hypot(vector.x, vector.z)))));
    }

    public Angle calculateDelta(Angle start, Angle end) {
        float deltaYaw = Mth.wrapDegrees(end.getYaw() - start.getYaw());
        float deltaPitch = Mth.wrapDegrees(end.getPitch() - start.getPitch());
        return new Angle(deltaYaw, deltaPitch);
    }

    public Angle calculateAngle(Vec3 to) {
        return fromVec3d(to.subtract(mc.player.getEyePosition()));
    }

    public Angle pitch(float pitch) {
        return new Angle(mc.player.getYRot(), pitch);
    }

    public Angle cameraAngle() {
        assert mc.player != null;

        return new Angle(mc.player.getYRot(), mc.player.getXRot());}

}
