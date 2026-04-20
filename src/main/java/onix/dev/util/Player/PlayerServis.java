package onix.dev.util.Player;

import com.google.common.eventbus.Subscribe;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import onix.dev.Onixvisual;
import onix.dev.event.impl.game.PacketEvent;
import lombok.Getter;

import static net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action.START_SPRINTING;
import static net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action.STOP_SPRINTING;
import static onix.dev.module.api.Function.fullNullCheck;
import static onix.dev.util.wrapper.Wrapper.mc;

@Getter
public class PlayerServis {


    private int serverSlot;
    private float serverYaw, serverPitch, fallDistance;
    private double serverX, serverY, serverZ;
    private boolean serverOnGround, serverSprinting, serverSneaking, serverHorizontalCollision;

    public PlayerServis() {

        Onixvisual.getInstance().getEventBus().register(this);
    }


    @Subscribe
    public void onPacketSend(PacketEvent.Send e) {
        if (fullNullCheck()) return;

        if (e.getPacket() instanceof ServerboundMovePlayerPacket packet) {
            if (packet.hasPosition()) {
                serverX = packet.getX(mc.player.getX());
                serverY = packet.getY(mc.player.getY());
                serverZ = packet.getZ(mc.player.getZ());
            }

            if (packet.hasRotation()) {
                serverYaw = packet.getYRot(mc.player.getYRot());
                serverPitch = packet.getXRot(mc.player.getXRot());
            }

            serverOnGround = packet.isOnGround();
            serverHorizontalCollision = packet.horizontalCollision();
        }

        if (e.getPacket() instanceof ServerboundSetCarriedItemPacket packet) serverSlot = packet.getSlot();

        if (e.getPacket() instanceof ServerboundPlayerCommandPacket packet) {
            switch (packet.getAction()) {
                case START_SPRINTING -> serverSprinting = true;
                case STOP_SPRINTING -> serverSprinting = false;

            }
        }
    }
}