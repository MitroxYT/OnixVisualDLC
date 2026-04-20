package onix.dev.mixin.game;

import net.minecraft.client.Minecraft;
import onix.dev.Onixvisual;
import onix.dev.event.impl.game.EventUpdate;
import onix.dev.event.impl.game.HotBarUpdateEvent;
import onix.dev.ui.clickgui.ClickGuiScreen;
import onix.dev.util.input.MouseHandler;
import onix.dev.util.others.Lisener.Counter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        EventUpdate eventUpdate = new EventUpdate();
        eventUpdate.call();
        MouseHandler.handleMouse();
        Counter.updateFPS();
        if (eventUpdate.isCanceled()) {
            ci.cancel();
        }
    }




   


    @Inject(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getInventory()Lnet/minecraft/world/entity/player/Inventory;"), cancellable = true)
    //@Inject(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getInventory()Lnet/minecraft/entity/player/PlayerInventory;"), cancellable = true)
    public void handleInputEventsHook(CallbackInfo ci) {
        HotBarUpdateEvent event = new HotBarUpdateEvent();
        Onixvisual.getInstance().getEventBus().post(event);
        if (event.isCanceled()) ci.cancel();
    }

    @Inject(method = "handleKeybinds", at = @At("HEAD"), cancellable = true)
    public void handleInputEvents(CallbackInfo ci) {
        ClickGuiScreen clickGui = Onixvisual.getInstance().getFunctionManager().getModule(ClickGuiScreen.class);
        if (clickGui != null && clickGui.isOpen()) {
            ci.cancel();
        }
    }

    @Inject(method = "createTitle", at = @At("HEAD"), cancellable = true)
    public void updateWindowTitle(CallbackInfoReturnable<String> cir) {
         cir.setReturnValue("Onix Visuals 1.21.11");
    }
}
