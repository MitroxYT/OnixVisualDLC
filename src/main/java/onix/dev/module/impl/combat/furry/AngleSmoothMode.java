package onix.dev.module.impl.combat.furry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import onix.dev.util.wrapper.Wrapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AngleSmoothMode implements Wrapper {
    String name;

    public Angle limitAngleChange(Angle currentAngle, Angle targetAngle) {
        return limitAngleChange(currentAngle, targetAngle, null, null);
    }

    public Angle limitAngleChange(Angle currentAngle, Angle targetAngle, Vec3 vec3d) {
        return limitAngleChange(currentAngle, targetAngle, vec3d, null);
    }

    public abstract Angle limitAngleChange(Angle currentAngle, Angle targetAngle, Vec3 vec3d, Entity entity);

    public abstract Vec3 randomValue();
}