package onix.dev.mixin.game;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import onix.dev.event.impl.presss.EventMouseButton;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseButtonMixin {

    @Inject(method = "onButton", at = @At("HEAD"))
    private void onMouseButton(long l, MouseButtonInfo mouseButtonInfo, int i, CallbackInfo ci) {
        new EventMouseButton(i, mouseButtonInfo.input()).call();
    }
//    @Inject(method = "onButton", at = @At("HEAD"))
//    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
//        new EventMouseButton(button, action).call();
//    }
}