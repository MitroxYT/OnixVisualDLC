package onix.dev.module.impl.combat.furry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import onix.dev.util.wrapper.Wrapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RotationPlan implements Wrapper {
    Angle angle;
    Vec3 vec3d;
    Entity entity;
    AngleSmoothMode angleSmooth;
    int ticksUntilReset;
    float resetThreshold;
    boolean moveCorrection, freeCorrection;

    public Angle nextRotation(Angle fromAngle, boolean isResetting) {
        if (isResetting) {
            assert mc.player != null;
            return angleSmooth.limitAngleChange(fromAngle, AngleUtil.fromVec2f(mc.player.getRotationVector()));
        }
        return angleSmooth.limitAngleChange(fromAngle, angle, vec3d, entity);
    }
}