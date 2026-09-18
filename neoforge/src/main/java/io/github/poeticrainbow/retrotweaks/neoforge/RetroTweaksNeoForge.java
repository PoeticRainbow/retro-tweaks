package io.github.poeticrainbow.retrotweaks.neoforge;

import dev.architectury.platform.Platform;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.TweakPlatform;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(RetroTweaks.MOD_ID)
public final class RetroTweaksNeoForge implements TweakPlatform {
    public RetroTweaksNeoForge(ModContainer container) {
        // Run our common setup.
        RetroTweaks.PLATFORM = this;
        // the platform MUST be set before initializing for config loading
        RetroTweaks.init();

        if (Platform.getEnv().isClient()) {
            RetroTweaksNeoForgeClient.init(container);
        }
    }

    @Override
    public boolean isVanillaAo() {
        if (Platform.getEnv().isClient()) {
            return RetroTweaksNeoForgeClient.isVanillaAo();
        }
        return true;
    }
}
