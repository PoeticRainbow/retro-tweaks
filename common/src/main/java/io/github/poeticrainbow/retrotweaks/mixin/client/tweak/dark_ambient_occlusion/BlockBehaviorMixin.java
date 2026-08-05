package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.dark_ambient_occlusion;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviorMixin {
    /*
     * Handles the old Ambient Occlusion and old Leaves lighting
     */
    @ModifyReturnValue(method = "getShadeBrightness", at = @At("RETURN"))
    private float retrotweaks$old_ambient_occlusion(float original, BlockState state) {
        if (Tweaks.DARK_AMBIENT_OCCLUSION.get()) {
            return original == 0.2f ? 0f : original;
        }
        return original;
    }
}
