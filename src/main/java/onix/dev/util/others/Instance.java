package onix.dev.util.others;

import lombok.experimental.UtilityClass;
import onix.dev.Onixvisual;
import onix.dev.module.api.Function;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@UtilityClass
public class Instance {
    private final ConcurrentMap<Class<? extends Function>, Function> instanceModules = new ConcurrentHashMap<>();

    public <T extends Function> T get(Class<T> clazz) {
        return clazz.cast(instanceModules.computeIfAbsent(clazz, instance -> Onixvisual.getInstance().getFunctionManager().getModule(clazz)));
    }

}
