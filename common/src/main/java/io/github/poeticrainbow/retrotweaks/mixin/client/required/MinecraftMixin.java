package io.github.poeticrainbow.retrotweaks.mixin.client.required;

import io.github.poeticrainbow.retrotweaks.util.DimensionTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(method = "setLevel", at = @At("HEAD"))
    private void retrotweaks$update_dimension(ClientLevel level, CallbackInfo ci) {
        if (level != null) {
            DimensionTracker.updateDimension(level.dimension().identifier());
        } else {
            DimensionTracker.updateDimension(null);
        }
    }
}
