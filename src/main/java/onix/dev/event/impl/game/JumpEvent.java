package onix.dev.event.impl.game;

import net.minecraft.client.player.LocalPlayer;
import onix.dev.event.api.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JumpEvent extends Event {
    LocalPlayer player;

}
