package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.sprinting_style;

import io.github.poeticrainbow.retrotweaks.enums.Sprint;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
    private int foodLevel;

    @Inject(method = "tick", at = @At("HEAD"))
    public void retrotweaks$infinite_sprint(ServerPlayer serverPlayer, CallbackInfo ci) {
        if (Tweaks.SPRINTING_STYLE.get().equals(Sprint.INFINITE)) {
            if (this.foodLevel < 8) {
                this.foodLevel = 8;
            }
        }
    }
}
