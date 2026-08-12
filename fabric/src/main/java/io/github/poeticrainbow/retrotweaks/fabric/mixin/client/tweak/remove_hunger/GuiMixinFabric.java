package io.github.poeticrainbow.retrotweaks.fabric.mixin.client.tweak.remove_hunger;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Hud;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Hud.class)
public class GuiMixinFabric {
    @Shadow
    private int lastHealth;

    @Redirect(
        method = "extractPlayerHealth",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/gui/Hud;lastHealth:I",
            ordinal = 1,
            opcode = Opcodes.GETFIELD
        )
    )
    private int retrotweaks$prevent_blinking(Hud instance) {
        if (Tweaks.REMOVE_HUNGER.get()) {
            return Integer.MAX_VALUE;
        }
        return this.lastHealth;
    }
}
