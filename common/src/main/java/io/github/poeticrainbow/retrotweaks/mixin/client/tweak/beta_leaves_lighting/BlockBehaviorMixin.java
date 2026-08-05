package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.beta_leaves_lighting;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.tags.BlockTags;
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
    private float retrotweaks$beta_leaves_lighting(float original, BlockState state) {
        if (Tweaks.BETA_LEAVES_LIGHTING.get() && state.is(BlockTags.LEAVES)) {
            return 1f;
        }
        return original;
    }
}
