package onix.dev.util.math;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import onix.dev.util.wrapper.Wrapper;

import org.joml.Matrix4f;
import org.joml.Vector4f;

/*
public class ProjectionUtil implements Wrapper {

    public static Vec3 interpolateEntity(Entity entity, float tickDelta) {
        double x = MathHelper.lerp(tickDelta, entity.xOld, entity.getX());
        double y = MathHelper.lerp(tickDelta, entity.lastRenderY, entity.getY());
        double z = MathHelper.lerp(tickDelta, entity.lastRenderZ, entity.getZ());
        return new Vec3(x, y, z);
    }

    public static Vec3 toScreen(Vec3 worldPos) {
        Camera camera = mc.gameRenderer.getMainCamera();
        if (camera == null) return null;

        Vec3d camPos = camera.getCameraPos();
        Vector4f pos = new Vector4f(
                (float)(worldPos.x - camPos.x),
                (float)(worldPos.y - camPos.y),
                (float)(worldPos.z - camPos.z),
                1.0f
        );

        Matrix4f view = MatrixCapture.viewMatrix;
        Matrix4f proj = MatrixCapture.projectionMatrix;

        pos.mul(view);
        pos.mul(proj);

        if (pos.w <= 0.0f) return null;

        pos.div(pos.w);
        float x = (pos.x * 0.5f + 0.5f) * mc.getWindow().getScaledWidth();
        float y = (1.0f - (pos.y * 0.5f + 0.5f)) * mc.getWindow().getScaledHeight();

        return new Vec3d(x, y, 0);
    }
}*/