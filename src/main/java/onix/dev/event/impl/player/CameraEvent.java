package onix.dev.event.impl.player;

import onix.dev.event.api.Event;
import onix.dev.module.impl.combat.furry.Angle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CameraEvent extends Event {
    boolean cameraClip;
    float distance;
    Angle angle;
}
