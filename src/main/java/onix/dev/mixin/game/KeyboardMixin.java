package onix.dev.mixin.game;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import onix.dev.Onixvisual;
import onix.dev.event.impl.game.KeyEvent;
import onix.dev.event.impl.presss.EventPress;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardMixin {
//    @Final
//    @Shadow
//    private Minecraft client;
//    @Inject(at = @At("HEAD"), method = "keyPress")
//    private void onKey(long window, int action, KeyInput input, CallbackInfo ci) {
//        EventPress event = new EventPress(input.key(), action);
//        event.call();
//        Onixvisual.getInstance().getEventBus().post(new KeyEvent(client.currentScreen, InputUtil.Type.KEYSYM, input.key(), action));
//    }

    @Unique
    @Final
    private Minecraft client = Minecraft.getInstance();
    @Inject(at = @At("HEAD"), method = "keyPress")
    private void onKey(long l, int i, net.minecraft.client.input.KeyEvent keyEvent, CallbackInfo ci) {
        EventPress event = new EventPress(keyEvent.key(), i);
        event.call();
        Onixvisual.getInstance().getEventBus().post(new KeyEvent(client.screen, InputConstants.Type.KEYSYM, keyEvent.key(), i));
    }


}
