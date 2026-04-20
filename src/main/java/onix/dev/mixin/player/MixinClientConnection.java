package onix.dev.mixin.player;

import net.minecraft.network.Connection;
import net.minecraft.network.PacketListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import onix.dev.Onixvisual;
import onix.dev.event.impl.game.PacketEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static onix.dev.util.wrapper.Wrapper.mc;

@Mixin(Connection.class)
public class MixinClientConnection {

    @Inject(method = "genericsFtw", at = @At("HEAD"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacket(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        if(mc.player == null || mc.level == null) return;
        if (packet instanceof ClientboundBundlePacket packs) {
            packs.subPackets().forEach(p -> {
                PacketEvent.Receive event = new PacketEvent.Receive(p);
                Onixvisual.getInstance().getEventBus().post(event);
                if (event.isCanceled()) {
                    info.cancel();
                }
            });
        } else {
            PacketEvent.Receive event = new PacketEvent.Receive(packet);
            Onixvisual.getInstance().getEventBus().post(event);
            if (event.isCanceled()) {
                info.cancel();
            }
        }
    }





    @Inject(method = "genericsFtw", at = @At("TAIL"), cancellable = true)
    private static <T extends PacketListener> void onHandlePacketPost(Packet<T> packet, PacketListener listener, CallbackInfo info) {
        if(mc.player == null || mc.level == null) return;
        if (packet instanceof ClientboundBundlePacket packs) {
            packs.subPackets().forEach(p -> {
                PacketEvent.ReceivePost event = new PacketEvent.ReceivePost(p);
                Onixvisual.getInstance().getEventBus().post(event);
                if (event.isCanceled()) {
                    info.cancel();
                }
            });
        } else {
            PacketEvent.ReceivePost event = new PacketEvent.ReceivePost(packet);
            Onixvisual.getInstance().getEventBus().post(event);
            if (event.isCanceled()) {
                info.cancel();
            }
        }
    }

   // @Inject(method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At("HEAD"),cancellable = true)
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V",at = @At("HEAD"),cancellable = true)
    private void onSendPacketPre(Packet<?> packet, CallbackInfo info) {
        if(mc.player == null || mc.level == null) return;
        PacketEvent.Send event = new PacketEvent.Send(packet);
        Onixvisual.getInstance().getEventBus().post(event);
        if (event.isCanceled()) info.cancel();
    }

}