package onix.dev.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import onix.dev.Onixvisual;
import onix.dev.event.impl.render.HandAnimationEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
    @Unique
    private boolean onixCustomAnimation = false;

    @WrapOperation(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V"))
    private void wrapApplyEquipOffset(ItemInHandRenderer instance, PoseStack matrices, HumanoidArm arm, float equipProgress, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) AbstractClientPlayer player, @Local(ordinal = 0, argsOnly = true) InteractionHand hand, @Local(ordinal = 2, argsOnly = true) float swingProgress, @Local(ordinal = 0, argsOnly = true) ItemStack stack) {
        boolean isUsingItem = player.isUsingItem() && player.getUsedItemHand() == hand;

        if (isUsingItem) {
            onixCustomAnimation = false;
            original.call(instance, matrices, arm, equipProgress);
            return;
        }

        HandAnimationEvent event = new HandAnimationEvent(matrices, hand, swingProgress);
        Onixvisual.getInstance().getEventBus().post(event);

        if (event.isCanceled()) {
            onixCustomAnimation = true;
            return;
        }

        onixCustomAnimation = false;
        original.call(instance, matrices, arm, equipProgress);
    }
    @WrapOperation(
            method = "renderArmWithItem",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;swingArm(FLcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/entity/HumanoidArm;)V")
    )
    private void wrapSwingArm(
            ItemInHandRenderer instance, float f, PoseStack poseStack, int i, HumanoidArm humanoidArm, Operation<Void> original
    ) {
        if (onixCustomAnimation) {
            return;
        }
        original.call(instance, f, poseStack, i, humanoidArm);
    }
}
