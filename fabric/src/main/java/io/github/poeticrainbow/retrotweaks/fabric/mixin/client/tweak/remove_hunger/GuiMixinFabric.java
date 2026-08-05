package io.github.poeticrainbow.retrotweaks.fabric.mixin.client.tweak.remove_hunger;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class GuiMixinFabric {
    @Shadow
    private int lastHealth;

    @Redirect(
            method = "renderPlayerHealth",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/Gui;lastHealth:I",
                    ordinal = 1
            )
    )
    private int retrotweaks$prevent_blinking(Gui instance) {
        if (Tweaks.REMOVE_HUNGER.get()) {
            return Integer.MAX_VALUE;
        }
        return this.lastHealth;
    }
}
