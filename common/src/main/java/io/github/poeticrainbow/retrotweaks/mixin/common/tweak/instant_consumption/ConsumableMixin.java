package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.instant_consumption;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Consumable.class)
public abstract class ConsumableMixin {
    @Mutable @Shadow @Final private Holder<SoundEvent> sound;

    @WrapMethod(method = "consumeTicks")
    private int retrotweaks$override_consume_ticks(Operation<Integer> original) {
        if (Tweaks.INSTANT_CONSUMPTION.get()) {
            return 0;
        } else {
            return original.call();
        }
    }

    @WrapMethod(method = "emitParticlesAndSounds")
    public void retrotweaks$dont_emit_particles_and_sounds(RandomSource randomSource, LivingEntity livingEntity, ItemStack itemStack, int i, Operation<Void> original) {
        if (!Tweaks.INSTANT_CONSUMPTION.get()) {
            original.call(randomSource, livingEntity, itemStack, i);
        }
        //do nothing
    }

    @Redirect(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V"))
    public void retrotweaks$use_no_anim(LivingEntity instance, Holder<GameEvent> holder) {
        if (!Tweaks.INSTANT_CONSUMPTION.get()) {
            instance.gameEvent(holder);
        }
        // do nothing i think
    }

    @WrapMethod(method = "sound")
    public Holder<SoundEvent> sound(Operation<Holder<SoundEvent>> original) {
        if (Tweaks.INSTANT_CONSUMPTION.get()) {
            this.sound = Holder.direct(SoundEvents.EMPTY);
            return this.sound;
        } else {
            return original.call();
        }
    }
}
