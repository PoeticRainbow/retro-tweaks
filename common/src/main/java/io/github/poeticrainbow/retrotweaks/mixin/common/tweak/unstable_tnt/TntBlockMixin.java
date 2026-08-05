package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.unstable_tnt;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TntBlock.class)
public abstract class TntBlockMixin extends Block {
    public TntBlockMixin(Properties properties) {
        super(properties);
    }

    @WrapMethod(method = "playerWillDestroy(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/world/level/block/state/BlockState;")
    public BlockState retrotweaks$explode_unstable_tnt(Level level, BlockPos pos, BlockState state, Player player, Operation<BlockState> original) {
        if (Tweaks.UNSTABLE_TNT.get()) {
            if (!level.isClientSide() && !player.getAbilities().instabuild) {
                TntBlock.prime(level, pos);
            }
            return super.playerWillDestroy(level, pos, state, player);
        } else {
            return original.call(level, pos, state, player);
        }
    }
}
