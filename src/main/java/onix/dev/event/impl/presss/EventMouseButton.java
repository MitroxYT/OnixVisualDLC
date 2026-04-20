package onix.dev.event.impl.presss;


import onix.dev.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventMouseButton extends Event {
    private final int button;
    private final int action;
}
