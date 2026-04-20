package onix.dev.mixin.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import onix.dev.Onixvisual;
import onix.dev.event.api.EventType;
import onix.dev.event.impl.game.*;
import onix.dev.event.impl.player.UsingItemEvent;
import onix.dev.module.impl.combat.furry.RotationController;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static onix.dev.util.wrapper.Wrapper.mc;

@Mixin(LocalPlayer.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayer {



    @Shadow protected abstract void updateAutoJump(float dx, float dz);
    @Shadow public abstract boolean isShiftKeyDown();

    public MixinClientPlayerEntity(ClientLevel world, GameProfile profile) {
        super(world, profile);
    }


//    @ModifyExpressionValue(
//            method = {
//                    "isBlockedFromSprinting",
//                    "applyMovementSpeedFactors"
//            },
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z")
//    )
    @ModifyExpressionValue(method = {"isSlowDueToUsingItem","modifyInput"},at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
    private boolean usingItemHook(boolean original) {

        if (original) {
            UsingItemEvent event = new UsingItemEvent(EventType.ON);
            Onixvisual.getInstance().getEventBus().post(event);


            if (event.isCanceled()) {
                return false;
            }
        }
        return original;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        EventPlayerTick event = new EventPlayerTick();
        Onixvisual.getInstance().getEventBus().post(event);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void onTickEnd(CallbackInfo ci) {

        Onixvisual.getInstance().getEventBus().post(new PostUpdateEvent());
    }


    @Inject(method = "closeContainer", at = @At(value = "HEAD"), cancellable = true)
    private void closeHandledScreenHook(CallbackInfo info) {
        CloseScreenEvent event = new CloseScreenEvent(mc.screen);
        Onixvisual.getInstance().getEventBus().post(event);
        if (event.isCanceled()) info.cancel();
    }



    //@ModifyExpressionValue(method = {"sendPosition", "tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    @ModifyExpressionValue(method = {"sendPosition","tick"},at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getYRot()F"))
    private float hookSilentRotationYaw(float original) {
        return RotationController.INSTANCE.getRotation().getYaw();
    }

   // @ModifyExpressionValue(method = {"sendMovementPackets", "tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    @ModifyExpressionValue(method = {"sendPosition","tick"},at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getXRot()F"))
    private float hookSilentRotationPitch(float original) {
        return RotationController.INSTANCE.getRotation().getPitch();
    }


    @Inject(method = "sendPosition", at = @At(value = "HEAD"), cancellable = true)
    private void preMotion(CallbackInfo ci) {
        MotionEvent event = new MotionEvent(getX(), getY(), getZ(), getYRot(1), getXRot(1), onGround());
        Onixvisual.getInstance().getEventBus().post(event);
        if (event.isCanceled()) ci.cancel();
    }

    //@Inject(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"), cancellable = true)
    @Inject(method = "move",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V"), cancellable = true)
    public void onMoveHook(MoverType movementType, Vec3 movement, CallbackInfo ci) {
            MoveEvent event = new MoveEvent(movement);
            Onixvisual.getInstance().getEventBus().post(event);
            double d = this.getX();
            double e = this.getZ();
            super.move(movementType, event.getMovement());
            this.updateAutoJump((float) (this.getX() - d), (float) (this.getZ() - e));
            ci.cancel();

        }
    }



