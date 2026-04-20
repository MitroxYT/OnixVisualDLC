package onix.dev.util.others;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import onix.dev.util.wrapper.Wrapper;
import lombok.experimental.UtilityClass;


@UtilityClass
public class ItemUtil implements Wrapper {

    public int maxUseTick(Item item) {
        return maxUseTick(item.getDefaultInstance());
    }

    public int maxUseTick(ItemStack stack) {
        return switch (stack.getUseAnimation()) {
            case EAT, DRINK -> 32;
            case CROSSBOW, SPEAR -> 10;
            case BOW -> 20;
            case BLOCK -> 0;
            default -> stack.getUseDuration(mc.player);
        };
    }

    public float getCooldownProgress(Item item) {
        ItemCooldowns cooldownManager = mc.player.getCooldowns();
        ItemCooldowns.CooldownInstance entry = cooldownManager.cooldowns.get(item);
        if (entry == null) return 0;
        return Math.max(0, (entry.endTime() - cooldownManager.tickCount) / 20F);
    }
}
