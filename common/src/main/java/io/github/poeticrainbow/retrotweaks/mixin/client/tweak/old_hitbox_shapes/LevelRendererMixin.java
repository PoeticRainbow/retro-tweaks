package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_hitbox_shapes;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.poeticrainbow.retrotweaks.util.OldBlockShapesHelper;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Overrides the visual outline shape of blocks, without
 * affecting crosshair targeting, collision, or culling
 *
 * @author PoeticRainbow
 */
@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @WrapOperation(method = "extractBlockOutline", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
    private VoxelShape retrotweaks$override_block_outline(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context, Operation<VoxelShape> original) {
        return OldBlockShapesHelper.getOutlineShapeForState(state).orElseGet(() -> original.call(state, getter, pos, context));
    }
}
