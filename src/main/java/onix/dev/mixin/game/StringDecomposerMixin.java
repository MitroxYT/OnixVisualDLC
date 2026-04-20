package onix.dev.mixin.game;

import net.minecraft.util.StringDecomposer;
import onix.dev.Onixvisual;
import onix.dev.event.impl.render.TextFactoryEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StringDecomposer.class)
public class StringDecomposerMixin {
    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/StringDecomposer;iterateFormatted(Ljava/lang/String;ILnet/minecraft/network/chat/Style;Lnet/minecraft/util/FormattedCharSink;)Z", ordinal = 0), method = {"iterateFormatted(Ljava/lang/String;Lnet/minecraft/network/chat/Style;Lnet/minecraft/util/FormattedCharSink;)Z" }, index = 0)
    private static String adjustText(String text) {
        TextFactoryEvent event = new TextFactoryEvent(text);
        Onixvisual.getInstance().getEventBus().post(event);
        return event.getText();
    }
}
