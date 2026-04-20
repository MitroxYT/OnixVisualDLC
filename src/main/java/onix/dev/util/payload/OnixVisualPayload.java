package onix.dev.util.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record OnixVisualPayload(String brand) implements CustomPacketPayload {
    public static final Type<OnixVisualPayload> ID =
            new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("onixvisuals", "load"));
    public static final StreamCodec<FriendlyByteBuf, OnixVisualPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,       OnixVisualPayload::brand,
            OnixVisualPayload::new
    );

    public CustomPacketPayload.Type<OnixVisualPayload> type() {
        return ID;
    }
}

