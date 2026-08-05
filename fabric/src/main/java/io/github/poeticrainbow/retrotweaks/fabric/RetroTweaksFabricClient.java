package io.github.poeticrainbow.retrotweaks.fabric;

import io.github.poeticrainbow.retrotweaks.ErrorCollector;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import net.fabricmc.fabric.impl.client.indigo.Indigo;
import net.fabricmc.fabric.impl.client.indigo.renderer.aocalc.AoConfig;

import java.util.Optional;

public class RetroTweaksFabricClient {
    public static void init() {
        ErrorCollector.addErrorCheck(() -> {
            if (Indigo.AMBIENT_OCCLUSION_MODE != AoConfig.VANILLA) {
                return Optional.of("ambient-occlusion-mode in fabric/indigo-renderer.properties is not set to vanilla");
            }
            return Optional.empty();
        });

        if (Indigo.AMBIENT_OCCLUSION_MODE != AoConfig.VANILLA) {
            RetroTweaks.LOGGER.error("ambient-occlusion-mode in config/fabric/indigo-renderer.properties is set to {}! It should be set to \"vanilla\" in order to have old-school lighting!", Indigo.AMBIENT_OCCLUSION_MODE);
        }
    }

    public static boolean isVanillaAo() {
        // return true when enhancedLighting is NOT enabled
        return Indigo.AMBIENT_OCCLUSION_MODE == AoConfig.VANILLA;
    }
}
