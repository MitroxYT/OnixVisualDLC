package onix.dev.event.impl.game;

import net.minecraft.world.phys.Vec3;
import onix.dev.event.api.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CameraPositionEvent extends Event {
    Vec3 pos;
}

