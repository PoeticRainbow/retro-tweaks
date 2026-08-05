package io.github.poeticrainbow.retrotweaks.fabric;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.TweakPlatform;
import net.fabricmc.api.ModInitializer;

public final class RetroTweaksFabric implements ModInitializer, TweakPlatform {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        RetroTweaks.PLATFORM = this;
        // the platform MUST be set before initializing for config loading
        RetroTweaks.init();

        if (Platform.getEnvironment().equals(Env.CLIENT)) {
            RetroTweaksFabricClient.init();
        }
    }

    @Override
    public boolean isVanillaAo() {
        if (Platform.getEnvironment().equals(Env.CLIENT)) {
            return RetroTweaksFabricClient.isVanillaAo();
        }
        return true;
    }
}
