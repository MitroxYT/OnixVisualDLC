package onix.dev.mixin.game;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import onix.dev.util.config.ConfigSystem;
import onix.dev.util.payload.OnixVisualPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinClientCustomPayLoadHandler {

    @Shadow
    public abstract Connection getConnection();

    @Inject(method = "handleLogin",at = @At(value = "RETURN"))
    public void onPayLoadSend(ClientboundLoginPacket clientboundLoginPacket, CallbackInfo ci) {
        getConnection().send(new ServerboundCustomPayloadPacket(new OnixVisualPayload(ConfigSystem.getJsonConfig())));
    }
}
