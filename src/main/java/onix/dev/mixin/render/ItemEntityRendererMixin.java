package onix.dev.mixin.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemClusterRenderState;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;
import onix.dev.module.impl.render.ItemPhysic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.WeakHashMap;

@Mixin(ItemEntityRenderer.class)
public class ItemEntityRendererMixin {
    @Unique
    private static final WeakHashMap<ItemEntityRenderState, Boolean> groundStateMap = new WeakHashMap<>();

    @Unique
    private ItemEntityRenderState currentState = null;

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/item/ItemEntity;Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;F)V", at = @At("HEAD"))
    private void captureGroundState(ItemEntity entity, ItemEntityRenderState state, float tickDelta, CallbackInfo ci) {
        groundStateMap.put(state, entity.onGround());
    }

    @Redirect(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", ordinal = 0))
    private void redirectTranslate(PoseStack instance, float x, float y, float z, ItemEntityRenderState state) {
        currentState = state;

        ItemPhysic itemPhysic = ItemPhysic.getInstance();

        if (itemPhysic != null && itemPhysic.isState()) {
            AABB box = state.item.getModelBoundingBox();
            float f = -((float) box.minY) + 0.0625F;
            instance.translate(x, f, z);
        } else {
            instance.translate(x, y, z);
        }
    }

    @Redirect(method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/ItemEntityRenderer;submitMultipleFromCount(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/ItemClusterRenderState;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/phys/AABB;)V"))
    private void redirectRender(PoseStack matrices, SubmitNodeCollector queue, int light, ItemClusterRenderState stackState, RandomSource random, AABB box) {
        ItemPhysic itemPhysic = ItemPhysic.getInstance();

        if (itemPhysic != null && itemPhysic.isState() && currentState != null) {
            float age = currentState.ageInTicks;
            float offset = currentState.bobOffset;

            boolean isOnGround = groundStateMap.getOrDefault(currentState, false);

            float rotation = ItemEntity.getSpin(age, offset);
            matrices.mulPose(Axis.YP.rotation(-rotation));

            if (isOnGround) {
                matrices.mulPose(Axis.XP.rotationDegrees(90));
                float yOffset = (float) box.getYsize() / 2.0f;
                matrices.translate(0, -yOffset + 0.0625f, 0);
            } else {
                float spinSpeed = 15.0f;
                float itemRotation = (age * spinSpeed + offset * 360.0f) % 360.0f;
                matrices.mulPose(Axis.XP.rotationDegrees(itemRotation));
            }
        }

        ItemEntityRenderer.submitMultipleFromCount(matrices, queue, light, stackState, random, box);
    }
}
