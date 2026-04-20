package onix.dev.module.impl.combat.furry;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import onix.dev.util.math.MathUtil;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Angle {
    public static Angle DEFAULT = new Angle(0, 0);
    float yaw, pitch;

    public Angle adjustSensitivity() {
        double gcd = MathUtil.computeGcd();

        Angle previousAngle = RotationController.INSTANCE.getServerAngle();

        float adjustedYaw = adjustAxis(yaw, previousAngle.yaw, gcd);
        float adjustedPitch = adjustAxis(pitch, previousAngle.pitch, gcd);

        return new Angle(adjustedYaw, Mth.clamp(adjustedPitch, -90f, 90f));
    }

    public Angle random(float f) {
        return new Angle(yaw + MathUtil.getRandom(-f, f), pitch + MathUtil.getRandom(-f, f));
    }

    private float adjustAxis(float axisValue, float previousValue, double gcd) {
        float delta = axisValue - previousValue;
        return previousValue + Math.round(delta / gcd) * (float) gcd;
    }

    public final Vec3 toVector() {
        float f = pitch * 0.017453292F;
        float g = -yaw * 0.017453292F;
        float h = Mth.cos(g);
        float i = Mth.sin(g);
        float j = Mth.cos(f);
        float k = Mth.sin(f);
        return new Vec3(i * j, -k, h * j);
    }

    public Angle addYaw(float yaw) {
        return new Angle(this.yaw + yaw, this.pitch);
    }

    public Angle addPitch(float pitch) {
        this.pitch = Mth.clamp(this.pitch + pitch, -90, 90);
        return this;
    }

    public Angle of(Angle angle) {
        return new Angle(angle.getYaw(), angle.getPitch());
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public static class VecRotation {
        final Angle angle;
        final Vec3 vec;
    }
}
