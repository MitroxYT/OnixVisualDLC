package onix.dev.event.impl.presss;

import onix.dev.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventPress extends Event {
    int key, action;
}
