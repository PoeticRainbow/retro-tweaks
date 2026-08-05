package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.remove_hunger;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerMixin {
    @WrapMethod(method = "canEat")
    private boolean retrotweaks$always_eat(boolean bl, Operation<Boolean> original)
    {
        if (Tweaks.REMOVE_HUNGER.get()) {
            return true;
        }
        return original.call(bl);
    }
}
