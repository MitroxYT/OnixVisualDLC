package onix.dev.mixin.player;


/*
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin implements Wrapper {


    @Shadow
    @Nullable
    protected abstract RenderLayer getRenderLayer(LivingEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline);

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;lerpAngleDegrees(FFF)F"))
    private float lerpAngleDegreesHook(float original, @Local(ordinal = 0, argsOnly = true) LivingEntity entity, @Local(ordinal = 0, argsOnly = true) float delta) {
        if (entity.equals(mc.player)) {
            if (Onixvisual.getInstance().getFunctionManager().getModule(Killaura.class).isToggled()) {
                RotationController controller = RotationController.INSTANCE;
                return MathHelper.lerpAngleDegrees(delta, controller.getPreviousRotation().getYaw(), controller.getRotation().getYaw());
            }
        }
        return original;
    }

    @ModifyExpressionValue(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getLerpedPitch(F)F"))
    private float getLerpedPitchHook(float original, @Local(ordinal = 0, argsOnly = true) LivingEntity entity, @Local(ordinal = 0, argsOnly = true) float delta) {
        if (entity.equals(mc.player)) {
            if (Onixvisual.getInstance().getFunctionManager().getModule(Killaura.class).isToggled()) {
                RotationController controller = RotationController.INSTANCE;
                return MathHelper.lerp(delta, controller.getPreviousRotation().getPitch(), controller.getRotation().getPitch());
            }
        }
        return original;
    }



}*/