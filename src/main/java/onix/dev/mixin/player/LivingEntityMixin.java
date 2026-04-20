package onix.dev.mixin.player;


import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import onix.dev.Onixvisual;
import onix.dev.event.impl.game.JumpEvent;
import onix.dev.module.impl.combat.furry.RotationController;
import onix.dev.util.wrapper.Wrapper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Wrapper {

    @Unique
    private final Minecraft client = Minecraft.getInstance();
    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void jump(CallbackInfo info) {
        if ((Object) this instanceof LocalPlayer player) {
            JumpEvent event = new JumpEvent(player);
            Onixvisual.getInstance().getEventBus().post(event);
            if (event.isCanceled()) info.cancel();
        }
    }

//    @ModifyExpressionValue(method = "jump", at = @At(value = "NEW", target = "(DDD)Lnet/minecraft/util/math/Vec3d;"))
//    private Vec3d hookFixRotation(Vec3d original) {
//        if ((Object) this != client.player) {
//            return original;
//        }
//        float yaw = RotationController.INSTANCE.getMoveRotation().getYaw() * 0.017453292F;
//        return new Vec3d(-MathHelper.sin(yaw) * 0.2F, 0.0, MathHelper.cos(yaw) * 0.2F);
//    }


//    @ModifyExpressionValue(method = "calcGlidingVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getPitch()F"))
//    private float hookModifyFallFlyingPitch(float original) {
//        if ((Object) this != client.player) {
//            return original;
//        }
//        return RotationController.INSTANCE.getMoveRotation().getPitch();
//    }
//
//    @ModifyExpressionValue(method = "calcGlidingVelocity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;"))
//    private Vec3d hookModifyFallFlyingRotationVector(Vec3d original) {
//        if ((Object) this != client.player) {
//            return original;
//        }
//        return RotationController.INSTANCE.getMoveRotation().toVector();
//    }
//
//
//
//    @ModifyExpressionValue(method = "turnHead", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;wrapDegrees(F)F", ordinal = 1))
//    private float wrapDegreesHook(float original) {
//        if ((Object) this == client.player) {
//            return MathHelper.wrapDegrees(RotationController.INSTANCE.getRotation().getYaw() - bodyYaw);
//        }
//        return original;
//    }
}