package io.github.poeticrainbow.retrotweaks;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import io.github.poeticrainbow.retrotweaks.command.RetroTweaksClientCommand;
import io.github.poeticrainbow.retrotweaks.config.screen.ConfigScreen;
import io.github.poeticrainbow.retrotweaks.network.ConfigSyncS2C;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import org.lwjgl.glfw.GLFW;

public class RetroTweaksClient {
    public static final KeyMapping RETRO_TWEAKS_BUTTON = new KeyMapping("key.retrotweaks", GLFW.GLFW_KEY_O, KeyMapping.Category.MISC);

    public static void init() {
        // networking
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ConfigSyncS2C.ID, ConfigSyncS2C.STREAM_CODEC, (packet, context) -> {
            packet.values().forEach((key, value) -> {
                //noinspection unchecked
                ((Tweak<Object>) Tweaks.get(key)).setServerSideValue(value);
            });
        });

        // keybind
        KeyMappingRegistry.register(RETRO_TWEAKS_BUTTON);

        ClientRawInputEvent.KEY_PRESSED.register((client, action, keyEvent) -> {
            if (RETRO_TWEAKS_BUTTON.consumeClick()) {
                RetroTweaksClient.openConfigScreen();
                return EventResult.interruptTrue();
            }
            return EventResult.pass();
        });

        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> {
            dispatcher.register(RetroTweaksClientCommand.build());
        });

        ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(player -> Tweaks.resetServerValues());
    }

    public static void openConfigScreen() {
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new ConfigScreen(Minecraft.getInstance().screen)));
    }

    @SuppressWarnings("ConstantValue")
    public static boolean isLogicalSide() {
        // if client side, this is the controller of logic
        var minecraft = Minecraft.getInstance();
        if (minecraft == null) {
            // minecraft is null during neoforge loading
            return true;
        }
        var level = minecraft.level;
        // if there is no level, we have full control
        // if there is a level, we check if we have logical control over the server (singleplayer)
        return level == null || minecraft.hasSingleplayerServer();
    }

    public static MinecraftServer getSingleplayerServer() {
        try {
            if (Minecraft.getInstance().hasSingleplayerServer()) {
                return Minecraft.getInstance().getSingleplayerServer();
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
