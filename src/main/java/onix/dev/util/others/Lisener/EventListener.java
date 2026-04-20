package onix.dev.util.others.Lisener;

import com.google.common.eventbus.Subscribe;
import onix.dev.Onixvisual;
import onix.dev.event.impl.game.EventUpdate;
import onix.dev.util.others.ServerUtil;

public class EventListener implements Listener {
    public static boolean serverSprint;
    public static int selectedSlot;

    @Subscribe
    public void onTick(EventUpdate e) {
        ServerUtil.tick();
    }

//    @Subscribe
//    public void onPacket(PacketEvent e) {
//        switch (e.getPacket()) {
//            case ClientCommandC2SPacket command -> serverSprint = switch (command.getMode()) {
//                case ClientCommandC2SPacket.Mode.START_SPRINTING -> true;
//                case ClientCommandC2SPacket.Mode.STOP_SPRINTING -> false;
//                default -> serverSprint;
//            };
//            case UpdateSelectedSlotC2SPacket slot -> selectedSlot = slot.getSelectedSlot();
//            default -> {}
//        }
//
//    }
}
