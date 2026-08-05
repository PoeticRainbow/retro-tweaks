package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_bossbar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BossEvent.class)
public class BossEventMixin {
    @WrapMethod(method = "getColor")
    public BossEvent.BossBarColor retrotweaks$get_color(Operation<BossEvent.BossBarColor> original) {
        if (Tweaks.OLD_BOSSBAR.get()) {
            return BossEvent.BossBarColor.PINK;
        } else {
            return original.call();
        }
    }
}
