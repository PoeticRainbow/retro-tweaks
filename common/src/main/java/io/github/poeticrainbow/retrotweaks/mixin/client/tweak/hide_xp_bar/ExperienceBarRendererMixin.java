package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.hide_xp_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ExperienceBarRenderer.class)
public class ExperienceBarRendererMixin {
    @WrapMethod(method = "renderBackground")
    public void retrotweaks$render_background(GuiGraphics arg, DeltaTracker arg2, Operation<Void> original) {
        if (!Tweaks.HIDE_XP_BAR.get()) {
            original.call(arg, arg2);
        }
    }
}
