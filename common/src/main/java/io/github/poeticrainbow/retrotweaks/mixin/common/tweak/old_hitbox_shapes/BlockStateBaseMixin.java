package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.old_hitbox_shapes;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.util.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Overrides the physical collision shape of blocks, does not affect
 * crosshair targeting, culling, or the visual outline shape
 *
 * @author PoeticRainbow
 */
@Mixin(BlockBehaviour.BlockStateBase.class)
public class BlockStateBaseMixin {
    @WrapMethod(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/phys/shapes/VoxelShape;")
    private VoxelShape retrotweaks$override_collision_shape(BlockGetter getter, BlockPos pos, Operation<VoxelShape> original) {
        return OldBlockShapesHelper.getCollisionShapeForState(((BlockBehaviour.BlockStateBase)(Object) this))
                                   .orElse(original.call(getter, pos));
    }

    @WrapMethod(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;")
    private VoxelShape retrotweaks$override_collision_shape_with_context(BlockGetter getter, BlockPos pos, CollisionContext context, Operation<VoxelShape> original) {
        return OldBlockShapesHelper.getCollisionShapeForState(((BlockBehaviour.BlockStateBase)(Object) this))
                                   .orElse(original.call(getter, pos, context));
    }

    @WrapMethod(method = "isCollisionShapeFullBlock")
    private boolean retrotweaks$override_is_full_block(BlockGetter getter, BlockPos pos, Operation<Boolean> original) {
        var shape = OldBlockShapesHelper.getCollisionShapeForState(((BlockBehaviour.BlockStateBase)(Object) this));
        return shape.map(Block::isShapeFullBlock).orElseGet(() -> original.call(getter, pos));
    }

    @WrapMethod(method = "hasLargeCollisionShape")
    private boolean retrotweaks$override_has_large_collision_shape(Operation<Boolean> original) {
        var shape = OldBlockShapesHelper.getCollisionShapeForState(((BlockBehaviour.BlockStateBase)(Object) this));
        return shape.map(OldBlockShapesHelper::hasLargeCollisionShape).orElseGet(original::call);
    }
}
