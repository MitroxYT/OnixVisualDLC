package onix.dev.event.impl.player;


import onix.dev.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RotationUpdateEvent extends Event {
    byte type;
}
