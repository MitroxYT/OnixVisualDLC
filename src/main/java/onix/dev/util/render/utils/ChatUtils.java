package onix.dev.util.render.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import onix.dev.util.wrapper.Wrapper;
import lombok.experimental.UtilityClass;


import java.awt.Color;

@UtilityClass
public class ChatUtils implements Wrapper {

    public void sendMessage(String message) {
        if (mc.player == null || mc.level == null ) return;
        MutableComponent text = Component.literal("");
        for (int i = 0; i < "Ficality".length(); i++) {
            text.append(Component.literal("Ficality".charAt(i) + "")
                    .setStyle(Style.EMPTY
                            .withBold(true)
                            .withColor(TextColor.fromRgb(ColorUtils.gradient(ColorUtils.getGlobalColor(), Color.WHITE, (float) i / "Ficality".length()).getRGB()))
                    )
            );
        }

        text.append(Component.literal(" -> ")
                .setStyle(Style.EMPTY
                        .withBold(false)
                        .withColor(TextColor.fromRgb(new Color(200, 200, 200).getRGB()))
                )
        );

        text.append(Component.literal(message)
                .setStyle(Style.EMPTY
                        .withBold(false)
                        .withColor(TextColor.fromRgb(new Color(200, 200, 200).getRGB()))
                )
        );

        mc.player.displayClientMessage(text, false);
    }
}