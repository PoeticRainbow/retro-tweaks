package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.hide_action_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Hud.class)
public class GuiMixin {
    @WrapMethod(method = "extractSelectedItemName")
    private void renderOverlayMessage(GuiGraphicsExtractor graphics, Operation<Void> original) {
        if (!Tweaks.HIDE_ACTION_BAR.get()) {
            original.call(graphics);
        }
    }
}
