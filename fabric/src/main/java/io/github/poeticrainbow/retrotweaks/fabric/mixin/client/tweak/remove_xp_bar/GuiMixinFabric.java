package io.github.poeticrainbow.retrotweaks.fabric.mixin.client.tweak.remove_xp_bar;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Gui.class)
public class GuiMixinFabric {
    @ModifyVariable(method = "extractPlayerHealth", at = @At(value = "STORE", target = "Lnet/minecraft/client/gui/Gui;extractPlayerHealth(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V"), name = "yLineBase")
    private int retrotweaks$render_hearts(int yLineBase) {
        if (Tweaks.HIDE_XP_BAR.get()) {
            return yLineBase + 7;
        }
        return yLineBase;
    }
}
