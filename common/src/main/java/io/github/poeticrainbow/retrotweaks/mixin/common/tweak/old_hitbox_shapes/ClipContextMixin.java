package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.old_hitbox_shapes;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.util.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Overrides the shape used for crosshair targeting without
 * affecting collision, culling, or the visual outline shape
 *
 * @author PoeticRainbow
 */
@Mixin(ClipContext.class)
public abstract class ClipContextMixin {
    @Shadow @Final private ClipContext.Block block;

    @WrapMethod(method = "getBlockShape")
    private VoxelShape retrotweaks$override_shape(BlockState state, BlockGetter getter, BlockPos pos, Operation<VoxelShape> original) {
        if (block.equals(ClipContext.Block.OUTLINE)) {
            var shape = OldBlockShapesHelper.getOutlineShapeForState(state);
            if (shape.isPresent()) return shape.get();
        }
        return original.call(state, getter, pos);
    }
}
