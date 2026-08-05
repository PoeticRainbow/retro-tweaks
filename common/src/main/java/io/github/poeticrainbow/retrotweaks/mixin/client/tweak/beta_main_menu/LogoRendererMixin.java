package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.beta_main_menu;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static io.github.poeticrainbow.retrotweaks.RetroTweaks.MOD_ID;

@Mixin(LogoRenderer.class)
public abstract class LogoRendererMixin {
    @Unique private static final Identifier BETA_MINECRAFT_LOGO = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/minecraft.png");

    @WrapMethod(method = "renderLogo")
    public void retrotweaks$renderLogo(GuiGraphics guiGraphics, int i, float f, Operation<Void> original) {
        if (Tweaks.BETA_MAIN_MENU.get()) {
            int k = i / 2 - 274 / 2;
            float g = 1f;
            int l = ARGB.white(g);
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BETA_MINECRAFT_LOGO, k, 30, 0.0F, 0.0F, 274, 44, 274, 44, l);
        } else {
            original.call(guiGraphics, i, f);
        }
    }
}
