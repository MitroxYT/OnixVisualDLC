package onix.dev.module.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import onix.dev.event.impl.render.HandAnimationEvent;
import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.module.setting.impl.BooleanSetting;
import onix.dev.module.setting.impl.ModeSetting;
import onix.dev.module.setting.impl.NumberSetting;

@ModuleInfo(name = "SwingAnimation",category = Category.RENDER)
public class SwingAnimation extends Function {
    public ModeSetting swingType = new ModeSetting("Тип",this,"","Chop", "Swipe", "Down", "Smooth", "Smooth 2", "Power", "Feast", "Twist", "Default");
    public NumberSetting hitStrengthSetting = new NumberSetting("Сила Взмаха",this,1.0F,0.5F,3.0F,0.5F);
    public NumberSetting swingSpeedSetting = new NumberSetting("Скорость Взмаха",this,1.0F,0.5F,4.0F,0.5F);
    public BooleanSetting onlySwing = new BooleanSetting("Ток При Взмахе",false);
    public SwingAnimation() {
        addSettings(swingType, hitStrengthSetting, swingSpeedSetting, onlySwing);
    }
    @Subscribe
    public void onHandAnimation(HandAnimationEvent e) {
        boolean isMainHand = e.getHand().equals(InteractionHand.MAIN_HAND);
        if (isMainHand) {
            PoseStack matrix = e.getMatrices();
            float swingProgress = e.getSwingProgress();
            int i = mc.player.getMainArm().equals(HumanoidArm.RIGHT) ? 1 : -1;
            float sin1 = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
            float sin2 = Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI);
            float sinSmooth = (float) (Math.sin(swingProgress * Math.PI) * 0.5F);
            float strength = hitStrengthSetting.getValue().floatValue();

            if (true) {
                if (onlySwing.getValue() ? mc.player.swingTime != 0 : true) {
                    switch (swingType.getSelected()) {
                        case "Chop" -> {
                            matrix.translate(0.56F * i, -0.44F, -0.72F);
                            matrix.translate(0.0F, 0.33F * -0.6F, 0.0F);
                            matrix.mulPose(Axis.YP.rotationDegrees(45.0F * i));
                            float f = Mth.sin(swingProgress * swingProgress * (float) Math.PI);
                            float f2 = Mth.sin(Mth.sqrt(swingProgress) * (float) Math.PI);
                            matrix.mulPose(Axis.ZP.rotationDegrees(f2 * -20.0F * i * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(f2 * -80.0F * strength));
                            matrix.translate(0.4F, 0.2F, 0.2F);
                            matrix.translate(-0.5F, 0.08F, 0.0F);
                            matrix.mulPose(Axis.YP.rotationDegrees(20.0F));
                            matrix.mulPose(Axis.XP.rotationDegrees(-80.0F));
                            matrix.mulPose(Axis.YP.rotationDegrees(20.0F));
                        }
                        case "Twist" -> {
                            matrix.translate(i * 0.56F, -0.36F, -0.72F);
                            matrix.mulPose(Axis.YP.rotationDegrees(80 * i));
                            matrix.mulPose(Axis.XP.rotationDegrees(sin2 * -90 * strength));
                            matrix.mulPose(Axis.ZP.rotationDegrees((sin1 - sin2) * 60 * i * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(-30));
                            matrix.translate(0, -0.1F, 0.05F);
                        }
                        case "Swipe" -> {
                            matrix.translate(0.56F * i, -0.32F, -0.72F);
                            matrix.mulPose(Axis.YP.rotationDegrees(70 * i));
                            matrix.mulPose(Axis.ZP.rotationDegrees(-20 * i));
                            matrix.mulPose(Axis.YP.rotationDegrees((sin2 * sin1) * -5 * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees((sin2 * sin1) * -120 * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(-70));
                        }
                        case "Default" -> {
                            matrix.translate(i * 0.56F, -0.52F - (sin2 * 0.5F * strength), -0.72F);
                            matrix.mulPose(Axis.YP.rotationDegrees(45 * i));
                            matrix.mulPose(Axis.YP.rotationDegrees(-45 * i));
                        }
                        case "Down" -> {
                            matrix.translate(i * 0.56F, -0.32F, -0.72F);
                            matrix.mulPose(Axis.YP.rotationDegrees(76 * i));
                            matrix.mulPose(Axis.YP.rotationDegrees(sin2 * -5 * strength));
                            matrix.mulPose(Axis.XN.rotationDegrees(sin2 * -100 * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(sin2 * -155 * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(-100));
                        }
                        case "Smooth" -> {
                            matrix.translate(i * 0.56F, -0.42F, -0.72F);
                            matrix.mulPose(Axis.YP.rotationDegrees((float) i * (45.0F + sin1 * -20.0F * strength)));
                            matrix.mulPose(Axis.ZP.rotationDegrees((float) i * sin2 * -20.0F * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(sin2 * -80.0F * strength));
                            matrix.mulPose(Axis.YP.rotationDegrees((float) i * -45.0F));
                            matrix.translate(0, -0.1, 0);
                        }
                        case "Smooth 2" -> {
                            matrix.translate(i * 0.56F, -0.42F, -0.72F);
                            matrix.mulPose(Axis.XP.rotationDegrees(sin2 * -80.0F * strength));
                            matrix.translate(0, -0.1, 0);
                        }
                        case "Power" -> {
                            matrix.translate(i * 0.56F, -0.32F, -0.72F);
                            matrix.translate((-sinSmooth * sinSmooth * sin1) * i * strength, 0, 0);
                            matrix.mulPose(Axis.YP.rotationDegrees(61 * i));
                            matrix.mulPose(Axis.ZP.rotationDegrees(sin2 * strength));
                            matrix.mulPose(Axis.YP.rotationDegrees((sin2 * sin1) * -5 * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees((sin2 * sin1) * -30 * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(-60));
                            matrix.mulPose(Axis.XP.rotationDegrees(sinSmooth * -60 * strength));
                        }
                        case "Feast" -> {
                            matrix.translate(i * 0.56F, -0.32F, -0.72F);
                            matrix.mulPose(Axis.YP.rotationDegrees(30 * i));
                            matrix.mulPose(Axis.YP.rotationDegrees(sin2 * 75 * i * strength));
                            matrix.mulPose(Axis.XP.rotationDegrees(sin2 * -45 * strength));
                            matrix.mulPose(Axis.YP.rotationDegrees(30 * i));
                            matrix.mulPose(Axis.XP.rotationDegrees(-80));
                            matrix.mulPose(Axis.YP.rotationDegrees(35 * i));
                        }
                    }
                } else {
                    matrix.translate(i * 0.56F, -0.52F, -0.72F);
                }
            } else {
                return;
            }
            e.cancel();
        }
    }
}
