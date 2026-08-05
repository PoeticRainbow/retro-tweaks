package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.hide_action_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixin {
    @WrapMethod(method = "renderSelectedItemName")
    private void renderOverlayMessage(GuiGraphics guiGraphics, Operation<Void> original) {
        if (!Tweaks.HIDE_ACTION_BAR.get()) {
            original.call(guiGraphics);
        }
    }
}
