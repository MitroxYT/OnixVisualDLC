package onix.dev.event.impl.game;


import net.minecraft.client.gui.screens.Screen;
import onix.dev.event.api.Event;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CloseScreenEvent extends Event {
    Screen screen;

}
