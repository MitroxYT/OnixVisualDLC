package onix.dev.util.Player;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.prediction.PredictiveAction;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import onix.dev.Onixvisual;
import onix.dev.module.impl.combat.furry.Angle;
import onix.dev.module.impl.combat.furry.AngleUtil;
import onix.dev.util.render.utils.ColorUtils;
import onix.dev.util.wrapper.Wrapper;
import lombok.experimental.UtilityClass;


import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class PlayerIntersectionUtil implements Wrapper {


    public void sendSequencedPacket(PredictiveAction packetCreator) {
        mc.gameMode.startPrediction(mc.level, packetCreator);
    }

    public void interactItem(InteractionHand hand) {
        interactItem(hand, AngleUtil.cameraAngle());
    }

    public void interactItem(InteractionHand hand, Angle angle) {
        sendSequencedPacket(i -> new ServerboundUseItemPacket(hand, i, angle.getYaw(), angle.getPitch()));
    }


    public void interactEntity(Entity entity) {
        mc.player.connection.send(ServerboundInteractPacket.createInteractionPacket(entity, false, InteractionHand.MAIN_HAND, entity.getBoundingBox().getCenter()));
        mc.player.connection.send(ServerboundInteractPacket.createInteractionPacket(entity, false, InteractionHand.MAIN_HAND));
    }


    public void sendPacketWithOutEvent(Packet<?> packet) {
        mc.player.connection.getConnection().send(packet, null);
    }

    public String getHealthString(LivingEntity entity) {
        return getHealthString(getHealth(entity));
    }

    public String getHealthString(float hp) {
        return String.format("%.1f", hp).replace(",",".").replace(".0","");
    }

    public float getHealth(LivingEntity entity) {
        float hp = entity.getHealth() + entity.getAbsorptionAmount();
        //if (entity instanceof Player player) {
//            if (Onixvisual.getInstance().getFunctionManager().getModule(NameTags.class).hp.getValue()) {
//                Objective scoreBoard = player.level().getScoreboard().getObjective(String.valueOf(DisplaySlot.BELOW_NAME));
//                if (scoreBoard != null) {
//                    MutableComponent text2 = ReadableScoreboardScore.getFormattedScore(player.level().getScoreboard().get(player,scoreBoard), scoreBoard.getNumberFormatOr(StyledNumberFormat.EMPTY));
//                    try {
//                        hp = Float.parseFloat(ColorUtils.removeFormatting(text2.getString()));
//                    } catch (NumberFormatException ignored) {}
//                }
//            }
//        }
        return Mth.clamp(hp, 0, entity.getMaxHealth());
    }



    public InputConstants.Type getKeyType(int key) {
        return key < 8 ? InputConstants.Type.MOUSE : InputConstants.Type.KEYSYM;
    }




    public boolean isKey(KeyMapping key) {
        return isKey(key.getDefaultKey().getType(), key.getDefaultKey().getValue());
    }

    public boolean isKey(InputConstants.Type type, int keyCode) {
        if (keyCode != -1) switch (type) {
            case InputConstants.Type.KEYSYM: return GLFW.glfwGetKey(mc.getWindow().handle(), keyCode) == 1;
            case InputConstants.Type.MOUSE: return GLFW.glfwGetMouseButton(mc.getWindow().handle(), keyCode) == 1;
        }
        return false;
    }


    public boolean isChat(Screen screen) {return screen instanceof ChatScreen;}
    public boolean nullCheck() {return mc.player == null || mc.level == null;}
}
