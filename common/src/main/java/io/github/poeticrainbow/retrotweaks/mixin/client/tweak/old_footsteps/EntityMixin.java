package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_footsteps;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow
    public abstract void playSound(SoundEvent arg, float f, float g);

    @WrapOperation(
        method = "walkingStepSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;playStepSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"
        )
    )
    private void retrotweaks$old_footsteps(Entity instance, BlockPos pos, BlockState state, Operation<Void> original) {
        if (Tweaks.OLD_FOOTSTEPS.get()) {
            retrotweaks$play_block_step(state);
        } else {
            original.call(instance, pos, state);
        }
    }

    @Unique
    private void retrotweaks$play_block_step(BlockState state) {
        var soundType = state.getSoundType();
        playSound(soundType.getStepSound(), soundType.getPitch() * 0.15F, soundType.getPitch());
    }
}
