package onix.dev.event.impl.input;


import net.minecraft.world.inventory.ClickType;
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
public class ClickSlotEvent extends Event {
    int windowId, slotId, button;
    ClickType actionType;
}

