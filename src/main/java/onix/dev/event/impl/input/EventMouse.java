package onix.dev.event.impl.input;

import onix.dev.event.api.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventMouse extends Event {
    private final double mouseX;
    private final double mouseY;
    private final int button;
    private final int action;
}