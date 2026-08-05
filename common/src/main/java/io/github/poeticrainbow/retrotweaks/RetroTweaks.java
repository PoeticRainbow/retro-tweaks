package io.github.poeticrainbow.retrotweaks;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.architectury.utils.GameInstance;
import io.github.poeticrainbow.retrotweaks.command.RetroTweaksServerCommand;
import io.github.poeticrainbow.retrotweaks.config.Config;
import io.github.poeticrainbow.retrotweaks.network.ConfigSyncS2C;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RetroTweaks {
    public static final String MOD_ID = "retrotweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static TweakPlatform PLATFORM;

    static {
        Config.init();
    }

    public static void init() {
        // Write common init code here.
        if (isServer()) {
            NetworkManager.registerS2CPayloadType(ConfigSyncS2C.ID, ConfigSyncS2C.STREAM_CODEC);
        }

        PlayerEvent.PLAYER_JOIN.register(ConfigSyncS2C::updateTweaksS2C);

        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> {
            dispatcher.register(RetroTweaksServerCommand.build());
        });

        if (isClient()) {
            RetroTweaksClient.init();
        }
    }

    public static boolean isClient() {
        return Platform.getEnvironment().equals(Env.CLIENT);
    }

    public static boolean isServer() {
        return Platform.getEnvironment().equals(Env.SERVER);
    }

    @Nullable
    public static MinecraftServer getServer() {
        if (isServer()) {
            return GameInstance.getServer();
        } else if (isClient()) {
            return RetroTweaksClient.getSingleplayerServer();
        }
        return null;
    }

    public static boolean isVanillaAo() {
        try {
            return RetroTweaks.PLATFORM.isVanillaAo();
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean isLogicalSide() {
        return switch (Platform.getEnvironment()) {
            // clients are only in control of logic when in singleplayer
            case CLIENT -> RetroTweaksClient.isLogicalSide();
            // dedicated servers are always in control of the logic
            case SERVER -> true;
        };
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
