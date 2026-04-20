package onix.dev.event.impl.player;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import onix.dev.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class EventAttackEntity extends Event {
    private final Player player;
    private final Entity target;
}
