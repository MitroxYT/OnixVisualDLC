package onix.dev.module.impl.combat.furry;


import com.google.common.eventbus.Subscribe;
import net.minecraft.util.Mth;
import onix.dev.Onixvisual;
import onix.dev.event.api.EventType;
import onix.dev.event.impl.game.EventUpdate;
import onix.dev.event.impl.player.RotationUpdateEvent;
import onix.dev.module.api.Function;
import onix.dev.util.Script.TaskPriority;
import onix.dev.util.Script.TaskProcessor;
import onix.dev.util.wrapper.Wrapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RotationController implements Wrapper {
    public static RotationController INSTANCE = new RotationController();

    RotationPlan lastRotationPlan;
    final TaskProcessor<RotationPlan> rotationPlanTaskProcessor = new TaskProcessor<>();
    Angle currentAngle, previousAngle, serverAngle = Angle.DEFAULT;

    public RotationController() {
        Onixvisual.getInstance().getEventBus().register(this);
    }

    public void setRotation(Angle value) {
        if (value == null) {
            this.previousAngle = this.currentAngle != null ? this.currentAngle : AngleUtil.cameraAngle();
        } else {
            this.previousAngle = this.currentAngle;
        }
        this.currentAngle = value;
    }

    public Angle getRotation() {
        return currentAngle != null ? currentAngle : AngleUtil.cameraAngle();
    }

    public Angle getPreviousRotation() {
        return currentAngle != null && previousAngle != null ? previousAngle : new Angle(mc.player.yRotLast, mc.player.xRotLast);
    }

    public Angle getMoveRotation() {
        RotationPlan rotationPlan = getCurrentRotationPlan();
        return currentAngle != null && rotationPlan != null && rotationPlan.isMoveCorrection() ? currentAngle : AngleUtil.cameraAngle();
    }

    public RotationPlan getCurrentRotationPlan() {
        return rotationPlanTaskProcessor.fetchActiveTaskValue() != null ? rotationPlanTaskProcessor.fetchActiveTaskValue() : lastRotationPlan;
    }



    public void rotateTo(RotationPlan plan, TaskPriority taskPriority, Function provider) {
        rotationPlanTaskProcessor.addTask(new TaskProcessor.Task<>(1, taskPriority.getPriority(), provider, plan));
    }

    public void update() {
        RotationPlan activePlan = getCurrentRotationPlan();
        if (activePlan == null) return;
        if (mc.player == null || mc.level == null) return;
        Angle clientAngle = AngleUtil.cameraAngle();
        if (lastRotationPlan != null) {
            double differenceFromCurrentToPlayer = computeRotationDifference(serverAngle, clientAngle);
            if (activePlan.getTicksUntilReset() <= rotationPlanTaskProcessor.tickCounter && differenceFromCurrentToPlayer < activePlan.getResetThreshold()) {
                setRotation(null);
                lastRotationPlan = null;
                rotationPlanTaskProcessor.tickCounter = 0;
                return;
            }
        }

        Angle newAngle = activePlan.nextRotation(currentAngle != null ? currentAngle : clientAngle, rotationPlanTaskProcessor.fetchActiveTaskValue() == null).adjustSensitivity();
        setRotation(newAngle);
        lastRotationPlan = activePlan;
        rotationPlanTaskProcessor.tick(1);
    }

    public static double computeRotationDifference(Angle a, Angle b) {
        return Math.hypot(Math.abs(computeAngleDifference(a.getYaw(), b.getYaw())), Math.abs(a.getPitch() - b.getPitch()));
    }

    public static float computeAngleDifference(float a, float b) {
        return Mth.wrapDegrees(a - b);
    }


    @Subscribe
    public void onTick(EventUpdate e) {


        Onixvisual.getInstance().getEventBus().post(new RotationUpdateEvent(EventType.PRE));
        update();
        Onixvisual.getInstance().getEventBus().post(new RotationUpdateEvent(EventType.POST));
    }

//    @Subscribe
//    public void onPacket(PacketEvent event) {
//        if (!event.isCanceled()) switch (event.getPacket()) {
//            case PlayerMoveC2SPacket player when player.changesLook() -> serverAngle = new Angle(player.getYaw(1), player.getPitch(1));
//            case PlayerPositionLookS2CPacket player -> serverAngle = new Angle(player.change().yaw(), player.change().pitch());
//            default -> {}
//        }
//    }
}

