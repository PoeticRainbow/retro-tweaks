package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.instant_consumption;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoodProperties.class)
public class FoodPropertiesMixin {
    @WrapOperation(
        method = "onConsume",
        at = @At(
            target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/Entity;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
            value = "INVOKE",
            ordinal = 1
        )
    )
    private void retrotweaks$remove_burp_sound(Level level, Entity arg, double d, double e, double f, SoundEvent arg2, SoundSource arg3, float g, float h, Operation<Void> original) {
        if (!Tweaks.INSTANT_CONSUMPTION.get()) {
            original.call(level, arg, d, e, f, arg2, arg3, g, h);
        }
        // do nothing
    }
}
