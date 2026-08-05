package io.github.poeticrainbow.retrotweaks.neoforge;

import io.github.poeticrainbow.retrotweaks.ErrorCollector;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import net.neoforged.neoforge.client.config.NeoForgeClientConfig;
import net.neoforged.neoforge.client.event.ClientResourceLoadFinishedEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Optional;

public class RetroTweaksNeoForgeClient {
    public static void init() {
        ErrorCollector.addErrorCheck(() -> {
            if (NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean()) {
                return Optional.of("NeoForge's enhancedLighting is enabled, breaking Per Face Lighting");
            }
            return Optional.empty();
        });

        NeoForge.EVENT_BUS.addListener(RetroTweaksNeoForgeClient::onResourceReload);
    }

    static void onResourceReload(ClientResourceLoadFinishedEvent event) {
        if (NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean()) {
            RetroTweaks.LOGGER.error("NeoForge's enhancedLighting is enabled in your neoforge-client.toml config file. The old lighting calculations will not work!");
        }
    }

    public static boolean isVanillaAo() {
        // return true when enhancedLighting is NOT enabled
        return !NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean();
    }
}
