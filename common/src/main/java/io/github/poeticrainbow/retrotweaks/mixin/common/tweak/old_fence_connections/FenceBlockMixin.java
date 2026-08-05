package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.old_fence_connections;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FenceBlock.class)
public abstract class FenceBlockMixin extends CrossCollisionBlock {
    protected FenceBlockMixin(float f, float g, float h, float i, float j, Properties properties) {
        super(f, g, h, i, j, properties);
    }

    @Unique
    private boolean retrotweaks$should_connect(BlockState state, Direction direction) {
        if (state.is(BlockTags.FENCE_GATES) && state.hasProperty(HorizontalDirectionalBlock.FACING)) {
            var facing = state.getValue(HorizontalDirectionalBlock.FACING);
            return switch (facing) {
                case NORTH, SOUTH -> direction.getAxis().equals(Direction.Axis.X);
                case EAST, WEST -> direction.getAxis().equals(Direction.Axis.Z);
                default -> false;
            };
        }
        return state.is(BlockTags.FENCES);
    }

    @Unique
    private BlockState retrotweaks$connect_fence_state(BlockState state, BlockState north, BlockState east, BlockState south, BlockState west) {
        return state.setValue(NORTH, retrotweaks$should_connect(north, Direction.NORTH))
                    .setValue(EAST, retrotweaks$should_connect(east, Direction.EAST))
                    .setValue(SOUTH, retrotweaks$should_connect(south, Direction.SOUTH))
                    .setValue(WEST, retrotweaks$should_connect(west, Direction.WEST));
    }

    @WrapMethod(method = "getStateForPlacement")
    private BlockState retrotweaks$fences_connect_to_fences(BlockPlaceContext blockPlaceContext, Operation<BlockState> original) {
        if (Tweaks.OLD_FENCE_CONNECTIONS.get()) {
            var state = defaultBlockState();
            var pos = blockPlaceContext.getClickedPos();
            var level = blockPlaceContext.getLevel();

            var north = level.getBlockState(pos.north());
            var east = level.getBlockState(pos.east());
            var south = level.getBlockState(pos.south());
            var west = level.getBlockState(pos.west());

            return retrotweaks$connect_fence_state(state, north, east, south, west);
        } else {
            return original.call(blockPlaceContext);
        }
    }

    @WrapMethod(method = "updateShape")
    private BlockState retrotweaks$update_shape(BlockState state, LevelReader reader, ScheduledTickAccess tickAccess, BlockPos pos, Direction direction, BlockPos pos1, BlockState state1, RandomSource random, Operation<BlockState> original) {
        if (Tweaks.OLD_FENCE_CONNECTIONS.get()) {
            var north = reader.getBlockState(pos.north());
            var east = reader.getBlockState(pos.east());
            var south = reader.getBlockState(pos.south());
            var west = reader.getBlockState(pos.west());

            return retrotweaks$connect_fence_state(state, north, east, south, west);
        } else {
            return original.call(state, reader, tickAccess, pos, direction, pos1, state1, random);
        }
    }
}
