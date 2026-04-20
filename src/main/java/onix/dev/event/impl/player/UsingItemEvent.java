package onix.dev.event.impl.player;

import onix.dev.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsingItemEvent extends Event {
    byte type;
}
