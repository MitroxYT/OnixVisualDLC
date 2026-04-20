package onix.dev.event.impl.game;

import net.minecraft.world.phys.Vec3;
import onix.dev.event.api.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FireworkEvent extends Event {
    public Vec3 vector;

}
