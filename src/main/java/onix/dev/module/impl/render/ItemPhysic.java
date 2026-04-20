package onix.dev.module.impl.render;

import onix.dev.module.api.Category;
import onix.dev.module.api.Function;
import onix.dev.module.api.ModuleInfo;
import onix.dev.util.others.Instance;

@ModuleInfo(name = "ItemPhysic",category = Category.RENDER)
public class ItemPhysic extends Function {
    public static ItemPhysic getInstance() {
        return Instance.get(ItemPhysic.class);
    }
}
