package onix.dev.event.impl.render;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import net.minecraft.world.InteractionHand;
import onix.dev.event.api.Event;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
public class HandAnimationEvent extends Event {
    PoseStack matrices;
    InteractionHand hand;
    float swingProgress;
}
