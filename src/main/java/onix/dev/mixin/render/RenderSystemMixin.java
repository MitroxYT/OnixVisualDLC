package onix.dev.mixin.render;

import com.mojang.blaze3d.TracyFrameCapture;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import onix.dev.Onixvisual;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    @Inject(method = "flipFrame", at = @At("HEAD"))
    private static void flipFrame(Window window, TracyFrameCapture tracyFrameCapture, CallbackInfo ci) {
       Onixvisual.onRender();
    }
//    @Inject(method = "flipFrame", at = @At("HEAD"))
//    private static void flipFrame(Window window, TracyFrameCapturer capturer, CallbackInfo ci) {
//       Onixvisual.onRender();
//    }
}

