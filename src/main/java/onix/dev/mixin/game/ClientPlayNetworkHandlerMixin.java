package onix.dev.mixin.game;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelParticlesPacket;
import onix.dev.util.others.ServerUtil;
import onix.dev.util.render.utils.ChatUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(ClientPacketListener.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Unique
    private static final Set<String> problem = Set.of(
            "hub", "lobby", "рги", "дщиин", "дуфм", "дуфму", "leave", "leav", "logout"
    );

    @Unique
    private String last= null;
    @Unique
    private long time = 0L;


    @Inject(method = "sendCommand", at = @At("HEAD"), cancellable = true)
    public void onSendChatCommand(String command, CallbackInfo ci) {
        String fullCommand = command.trim();
        if (fullCommand.isEmpty()) return;
        String baseCommand = fullCommand.split(" ")[0].toLowerCase();

//        if (problem.contains(baseCommand) && ServerUtil.inPvp()) {
//            long now = System.currentTimeMillis();
//            if (!fullCommand.equalsIgnoreCase(last) || (now - time) > 3000) {
//                last = fullCommand;
//                time = now;
//              //  ChatUtils.sendMessage(Formatting.RED + "Вы в PvP! " + Formatting.GRAY + "Введите команду ещё раз для подтверждения: " + Formatting.YELLOW + "/" + fullCommand);
//                ci.cancel();
//            } else {
//                last = null;
//                time = 0L;
//            }
//        }
    }
    @Inject(method = "sendChat",at = @At("HEAD"), cancellable = true)
    public void handleSendChatMessage(String Message,CallbackInfo ci){

    }
    @Inject(method = "handleParticleEvent",at = @At("HEAD"),cancellable = true)
    public void onPart(ClientboundLevelParticlesPacket clientboundLevelParticlesPacket, CallbackInfo ci){
        if (clientboundLevelParticlesPacket.getCount() > 1000) ci.cancel();
    }
}
