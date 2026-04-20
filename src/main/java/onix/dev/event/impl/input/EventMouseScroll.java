package onix.dev.event.impl.input;

import onix.dev.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventMouseScroll extends Event {
    private final double delta;
}