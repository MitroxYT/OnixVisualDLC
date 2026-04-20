package onix.dev.mixin.player;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import onix.dev.module.impl.combat.furry.RotationController;
import onix.dev.util.wrapper.Wrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(Player.class)
public class PlayerEntityMixin implements Wrapper {


    //        @ModifyExpressionValue(
//                method = {
//                        "causeExtraKnockback",
//                        "doSweepAttack"
//                },
//                at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getYaw()F")
//        )
    @ModifyExpressionValue(
            method = {
                    "causeExtraKnockback",
                    "doSweepAttack"
            },
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getYRot()F")
    )
    private float hookAttackRotation(float original) {

        return RotationController.INSTANCE.getMoveRotation().getYaw();
    }

}
