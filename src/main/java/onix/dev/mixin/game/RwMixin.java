package onix.dev.mixin.game;

import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Scoreboard.class)
public abstract class RwMixin {
    @Shadow
    public abstract @Nullable PlayerTeam getPlayersTeam(String scoreHolderName);

    @Inject(method = "removePlayerFromTeam(Ljava/lang/String;Lnet/minecraft/world/scores/PlayerTeam;)V",at = @At("HEAD"),cancellable = true)
    public void fuckRw(String string, PlayerTeam playerTeam, CallbackInfo ci){
        if (this.getPlayersTeam(string) != playerTeam) {
            ci.cancel();
        }
    }


}
