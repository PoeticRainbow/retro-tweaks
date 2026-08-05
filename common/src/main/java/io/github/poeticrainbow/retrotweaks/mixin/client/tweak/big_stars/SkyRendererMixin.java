package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.big_stars;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin {
    // k for fabric
    // f4 for neoforge
    //@ModifyVariable(method = "buildStars", at = @At(value = "STORE"), name = "k")
    @ModifyVariable(method = "buildStars", at = @At(value = "STORE"), ordinal = 4)
    private static float retrotweaks$modify_star_size(float value) {
        if (Tweaks.BIG_STARS.get()) {
            // modern 0.15F + randomSource.nextFloat() * 0.1F
            // [0.15, 0.25]
            // beta 0.25f + source.nextFloat() * 0.25f
            // [0.25, 0.5]

            // we do NOT want to roll the RandomSource again, or it will shift the random values
            var random = (value - 0.15f) * 10;
            return 0.25f + random * 0.25f;
        }
        return value;
    }
}
