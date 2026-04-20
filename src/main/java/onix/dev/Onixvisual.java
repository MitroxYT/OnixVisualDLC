package onix.dev;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.Minecraft;
import onix.dev.event.impl.presss.EventPress;
import onix.dev.event.impl.render.RenderEvent;
import onix.dev.module.FunctionManager;
import onix.dev.module.api.Function;
import onix.dev.module.setting.api.SettingManager;
import onix.dev.module.setting.impl.BooleanSetting;
import onix.dev.util.Player.PlayerServis;
import onix.dev.util.config.ConfigSystem;
import onix.dev.util.math.MathUtil;
import onix.dev.util.others.ItemUtil;
import onix.dev.util.others.Lisener.ListenerRepository;
import onix.dev.util.payload.OnixVisualPayload;
import onix.dev.util.render.animation.AnimationSystem;
import onix.dev.util.render.backends.gl.GlBackend;
import onix.dev.util.render.backends.gl.GlState;
import onix.dev.util.render.core.Renderer2D;
import onix.dev.util.render.text.FontObject;
import onix.dev.util.render.text.FontRegistry;

@Getter
public class Onixvisual implements ModInitializer {
    @Getter
    public static Onixvisual instance;
    public EventBus eventBus;
    public SettingManager settingManager;
    public FunctionManager functionManager;
    public static GlBackend backend;
    public static Renderer2D renderer;
    public static FontObject uiFont;
    private PlayerServis playerServis;
    public static boolean initialized = false;
    ListenerRepository listenerRepository;
    private static synchronized void onInit() {
        if (initialized) {
            return;
        }
        backend = new GlBackend();
        renderer = new Renderer2D(backend);

        FontRegistry.initialize(backend, renderer);
        uiFont = FontRegistry.INTER_MEDIUM;
        initialized = true;
    }

    @Setter
    public boolean panic;

    public Onixvisual() {
        instance = this;
    }
//    float cooldownProgress = ItemUtil.getCooldownProgress(item);
//
//        if (cooldownProgress > 0) {
//        String time = MathUtil.round(cooldownProgress, 0.1) + "с";
//
//
//        return;
//    }
    @Override
    public void onInitialize() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ConfigSystem.saveConfig("autoConfig");
        }));
        eventBus = new EventBus();
        PayloadTypeRegistry.playC2S().register(OnixVisualPayload.ID,OnixVisualPayload.CODEC);
        settingManager = new SettingManager();
        functionManager = new FunctionManager();
        playerServis = new PlayerServis();
        initListeners();
        eventBus.register(this);
        ConfigSystem.loadConfig("autoConfig");
    }

    @Subscribe
    public void onPresss(EventPress event) {
        if (event.getAction() == 1) {

            for (Function module : functionManager.getModules()) {
                if (module.getKey() == event.getKey()) {
                    module.toggle();
                }


                for (var setting : module.getSettings()) {
                    if (setting instanceof BooleanSetting boolSetting) {
                        if (boolSetting.getKey() == event.getKey()) {
                            boolSetting.toggle();
                        }
                    }
                }
            }
        }
    }




    private void initListeners() {
        listenerRepository = new ListenerRepository();
        listenerRepository.setup();
    }

    public static void onRender() {
        GlState.Snapshot snapshot = GlState.push();
        try {
            if (!initialized) {
                onInit();
            }

            Minecraft client = Minecraft.getInstance();
            if (client == null || client.getWindow() == null || client.player == null || client.level == null) {
                return;
            }

            int width = client.getWindow().getWidth();
            int height = client.getWindow().getHeight();
            if (width <= 0 || height <= 0) {
                return;
            }

            AnimationSystem.getInstance().tick();

//            if (!Events.RENDER.hasSubscribers()) {
//                return;
//            }

            try {
                renderer.begin(width, height);
                try {
                    RenderEvent renderEvent = new RenderEvent(client, renderer, uiFont, width, height);
                    renderEvent.call();
                } finally {
                    renderer.end();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } finally {
            GlState.pop(snapshot);
        }
    }
}
