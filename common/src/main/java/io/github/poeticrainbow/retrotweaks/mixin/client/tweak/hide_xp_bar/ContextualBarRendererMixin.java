package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.hide_xp_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContextualBar.class)
public interface ContextualBarRendererMixin {
    @WrapMethod(method = "extractExperienceLevel")
    private static void renderExperienceLevel(GuiGraphicsExtractor graphics, Font font, int experienceLevel, Operation<Void> original) {
        if (!Tweaks.HIDE_XP_BAR.get()) {
            original.call(graphics, font, experienceLevel);
        }
    }
}
