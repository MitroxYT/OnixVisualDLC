package onix.dev.event.api;

import lombok.Getter;
import lombok.Setter;
import onix.dev.Onixvisual;

@Getter
public class Event {
    private boolean canceled;
    @Setter
    private boolean pre;

    public void cancel() {canceled = true;}

    public void resume() {canceled = false;}

    public void call() {
        if (!Onixvisual.instance.isPanic()) {
            Onixvisual.getInstance().getEventBus().post(this);
        }
    }
}
