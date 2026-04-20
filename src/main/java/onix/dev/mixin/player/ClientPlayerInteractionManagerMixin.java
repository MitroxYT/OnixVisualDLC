package onix.dev.mixin.player;

import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import onix.dev.Onixvisual;
import onix.dev.event.api.EventType;
import onix.dev.event.impl.input.ClickSlotEvent;
import onix.dev.event.impl.player.EventAttackEntity;
import onix.dev.event.impl.player.UsingItemEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiPlayerGameMode.class)
public class ClientPlayerInteractionManagerMixin {

    @Inject(method = "attack", at = @At("HEAD"))
    public void attackEntity(Player player, Entity entity, CallbackInfo ci) {
        EventAttackEntity event = new EventAttackEntity(player, entity);
        Onixvisual.getInstance().getEventBus().post(event);

    }

    @Inject(method = "useItem", at = @At(value = "RETURN"))
    public void interactItemHook(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
        if (cir.getReturnValue() instanceof InteractionResult.Success success && !success.swingSource().equals(InteractionResult.SwingSource.CLIENT)) {
            UsingItemEvent event = new UsingItemEvent(EventType.PRE);
            Onixvisual.getInstance().getEventBus().post(event);
        }
    }
//    @Inject(method = "handleInventoryMouseClick", at = @At("HEAD"), cancellable = true)
//    public void clickSlotHook(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo info) {
//        ClickSlotEvent event = new ClickSlotEvent(syncId,slotId,button,actionType);
//        Onixvisual.getInstance().getEventBus().post(event);
//        if (event.isCanceled()) info.cancel();
//    }
    @Inject(method = "handleInventoryMouseClick", at = @At("HEAD"), cancellable = true)
    public void clickSlotHook(int i, int j, int k, ClickType clickType, Player player, CallbackInfo ci) {
        ClickSlotEvent event = new ClickSlotEvent(i,j,k,clickType);
        Onixvisual.getInstance().getEventBus().post(event);
        if (event.isCanceled())ci.cancel();
    }


}
