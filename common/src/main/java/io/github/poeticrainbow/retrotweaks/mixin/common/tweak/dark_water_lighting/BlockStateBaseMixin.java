package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.dark_water_lighting;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {
    @Shadow
    protected abstract BlockState asState();

    @ModifyReturnValue(method = "getLightBlock", at = @At("RETURN"))
    private int retrotweaks$modify_light_blocking(int original) {
        try {
            if (Tweaks.DARK_WATER_LIGHTING.get() && this.asState().getFluidState().is(FluidTags.WATER)) {
                return 3;
            }
        } catch (Exception ignored) {
        }

        return original;
    }
}
