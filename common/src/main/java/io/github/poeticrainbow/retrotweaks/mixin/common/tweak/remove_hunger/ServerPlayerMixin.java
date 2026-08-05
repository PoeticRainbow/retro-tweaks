package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.remove_hunger;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Shadow
    public abstract ServerLevel level();

    @WrapMethod(method = "tickRegeneration")
    public void retrotweaks$stop_regen(Operation<Void> original) {
        if (Tweaks.REMOVE_HUNGER.get() && this.level().getDifficulty() != Difficulty.PEACEFUL) {
            //do nothing
        } else {
            original.call();
        }
    }
}
