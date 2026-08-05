package io.github.poeticrainbow.retrotweaks.tweak;

import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.enums.Chat;
import io.github.poeticrainbow.retrotweaks.enums.Sprint;
import io.github.poeticrainbow.retrotweaks.enums.Versions;
import io.github.poeticrainbow.retrotweaks.tweak.types.BooleanTweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.EnumTweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tweaks {
    public static final Map<String, Tweak<?>> REGISTRY = new LinkedHashMap<>(); // order of inserted elements maintained

    // CLIENT SIDE
    public static final Tweak<Boolean> BETA_LEAVES_LIGHTING = register(new BooleanTweak("beta_leaves_lighting", Env.CLIENT, true, false));
    public static final Tweak<Boolean> DARK_AMBIENT_OCCLUSION = register(new BooleanTweak("dark_ambient_occlusion", Env.CLIENT, true, false));
    public static final Tweak<Boolean> FULL_FACE_SHADING = register(new BooleanTweak("full_face_shading", Env.CLIENT, true, false, RetroTweaks::isVanillaAo));
    public static final Tweak<Boolean> BIG_STARS = register(new BooleanTweak("big_stars", Env.CLIENT, true, false));
    public static final Tweak<Boolean> FLAT_ITEMS = register(new BooleanTweak("flat_items", Env.CLIENT, true, false));
    public static final Tweak<Boolean> OLD_FOOTSTEPS = register(new BooleanTweak("old_footsteps", Env.CLIENT, true, false));
    public static final Tweak<Boolean> BETA_MAIN_MENU = register(new BooleanTweak("beta_main_menu", Env.CLIENT, true, false));
    public static final Tweak<Boolean> BETA_PAUSE_MENU = register(new BooleanTweak("beta_pause_menu", Env.CLIENT, true, false));
    public static final Tweak<Versions> LOADING_SCREEN = registerEnum(new EnumTweak<>("loading_screen", Env.CLIENT, Versions.BETA, Versions.MODERN, Versions.MAIN_VERSIONSINFDEV));
    public static final Tweak<Boolean> DIRT_GUI_BACKGROUND = register(new BooleanTweak("dirt_gui_background", Env.CLIENT, true, false));
    public static final Tweak<Boolean> OLD_PANORAMA_BLUR = register(new BooleanTweak("old_panorama_blur", Env.CLIENT, true, false));
    public static final Tweak<Boolean> OLD_BUTTON_COLORS = register(new BooleanTweak("old_button_colors", Env.CLIENT, true, false));
    public static final Tweak<Boolean> OLD_WORLD_LOADING_SCREEN = register(new BooleanTweak("old_world_loading_screen", Env.CLIENT, true, false));
    public static final Tweak<Boolean> HIDE_HUNGER_BAR = register(new BooleanTweak("hide_hunger_bar", Env.CLIENT, true, false));
    public static final Tweak<Boolean> HIDE_XP_BAR = register(new BooleanTweak("hide_xp_bar", Env.CLIENT, true, false));
    public static final Tweak<Boolean> OLD_DEATH_SCREEN = register(new BooleanTweak("old_death_screen", Env.CLIENT, true, false));
    public static final Tweak<Boolean> OLD_CROSSHAIR = register(new BooleanTweak("old_crosshair", Env.CLIENT, true, false));
    public static final Tweak<Boolean> MOVE_SYSTEM_MESSAGES = register(new BooleanTweak("move_system_messages", Env.CLIENT, true, false));
    public static final Tweak<Chat> CHAT_SCREEN_STYLE = register(new EnumTweak<>("chat_screen_style", Env.CLIENT, Chat.BETA, Chat.MODERN, Chat.VALUES));
    public static final Tweak<Boolean> TOP_LEFT_VERSION_TEXT = register(new BooleanTweak("top_left_version_text", Env.CLIENT, false, false));
    public static final Tweak<Boolean> HIDE_ACTION_BAR = register(new BooleanTweak("hide_action_bar", Env.CLIENT, true, false));
    public static final Tweak<Boolean> OLD_BOSSBAR = register(new BooleanTweak("old_bossbar", Env.CLIENT, true, false));
    public static final Tweak<Boolean> HIDE_EXTRA_TOOLTIP_INFO = register(new BooleanTweak("hide_extra_tooltip_info", Env.CLIENT, true, false));

    // todo: eventually split tweaks into tweaks for clientside, and gamerules/similar for common
    // SERVER SIDE
    public static final Tweak<Boolean> DARK_WATER_LIGHTING = register(new BooleanTweak("dark_water_lighting", Env.SERVER, true, false));
    public static final Tweak<Boolean> OLD_HITBOX_SHAPES = register(new BooleanTweak("old_hitbox_shapes", Env.SERVER, true, false));
    public static final Tweak<Boolean> OLD_FENCE_CONNECTIONS = register(new BooleanTweak("old_fence_connections", Env.SERVER, true, false));
    public static final Tweak<Boolean> UNSTABLE_TNT = register(new BooleanTweak("unstable_tnt", Env.SERVER, false, false));
    public static final Tweak<Boolean> INSTANT_CONSUMPTION = register(new BooleanTweak("instant_consumption", Env.SERVER, true, false));
    public static final Tweak<Boolean> REMOVE_HUNGER = register(new BooleanTweak("remove_hunger", Env.SERVER, true, false));
    public static final Tweak<Sprint> SPRINTING_STYLE = register(new EnumTweak<>("sprinting_style", Env.SERVER, Sprint.DISABLED, Sprint.MODERN, Sprint.VALUES));

    //public static final Tweak<Enum<Versions>> TEST = registerEnum(new EnumTweak<>("test", Versions.BETA));

    private static <V extends Enum<V>> Tweak<V> registerEnum(Tweak<V> tweak) {
        REGISTRY.put(tweak.key(), tweak);
        return tweak;
    }

    public static <V> Tweak<V> register(Tweak<V> tweak) {
        REGISTRY.put(tweak.key(), tweak);
        return tweak;
    }

    public static Collection<Tweak<?>> values() {
        return REGISTRY.values();
    }

    public static void resetServerValues() {
        values().forEach(tweak -> tweak.setServerSideValue(null));
    }

    public static Tweak<?> get(String key) {
        return REGISTRY.get(key);
    }
}
