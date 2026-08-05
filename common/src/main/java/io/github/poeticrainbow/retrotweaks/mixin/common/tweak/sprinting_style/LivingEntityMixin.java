package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.sprinting_style;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.enums.Sprint;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @WrapMethod(method = "setSprinting")
    public void setSprinting(boolean bl, Operation<Void> original) {
        boolean bl2 = bl;
        if (!Tweaks.SPRINTING_STYLE.get().equals(Sprint.MODERN)) {
            if (Tweaks.SPRINTING_STYLE.get().equals(Sprint.DISABLED)) {
                bl2 = false;
            }
        }
        original.call(bl2);
    }
}
