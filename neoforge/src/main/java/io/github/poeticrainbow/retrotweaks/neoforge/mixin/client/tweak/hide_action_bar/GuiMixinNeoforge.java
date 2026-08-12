package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.hide_action_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Hud.class)
public class GuiMixinNeoforge {
    @WrapMethod(method = "extractSelectedItemName(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V")
    private void renderOverlayMessage(GuiGraphicsExtractor graphics, Operation<Void> original) {
        if (!Tweaks.HIDE_ACTION_BAR.get()) {
            original.call(graphics);
        }
    }
}
