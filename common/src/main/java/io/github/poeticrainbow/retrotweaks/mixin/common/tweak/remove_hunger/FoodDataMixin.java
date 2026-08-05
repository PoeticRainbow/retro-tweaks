package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.remove_hunger;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodData.class)
public class FoodDataMixin {
    @Shadow
    private int foodLevel;
    @Shadow
    private float exhaustionLevel;
    @Unique private int retrotweaks$healAmount;

    @Inject(method = "eat(Lnet/minecraft/world/food/FoodProperties;)V", at = @At("HEAD"))
    public void retrotweaks$eat(FoodProperties foodProperties, CallbackInfo ci) {
        if (Tweaks.REMOVE_HUNGER.get()) {
            this.retrotweaks$healAmount = foodProperties.nutrition();
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void retrotweaks$ticking(ServerPlayer serverPlayer, CallbackInfo ci) {
        if (Tweaks.REMOVE_HUNGER.get()) {
            serverPlayer.heal(this.retrotweaks$healAmount);
            this.retrotweaks$healAmount = 0;
            if (this.foodLevel < 1) {
                this.foodLevel = 1;
            }
        }
    }

    @ModifyVariable(method = "tick", at = @At("STORE"), ordinal = 0)
    private boolean retrotweaks$stop_regen(boolean bl) {
        if (Tweaks.REMOVE_HUNGER.get()) {
            return false;
        }
        return bl;
    }
}
