package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_panorama_blur;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PanoramaRenderer.class)
public class PanoramaRendererMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    /**
     * @author PoeticRainbow
     * @reason replace the panorama overlay with the old gradient one
     */
    @WrapOperation(method = "render", at = @At(target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V", value = "INVOKE"))
    private void retrotweaks$replace_panorama_overlay(GuiGraphics instance, RenderPipeline renderPipeline, Identifier arg, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p, Operation<Void> original) {
        if (Tweaks.OLD_PANORAMA_BLUR.get()) {
            if (minecraft != null && minecraft.screen != null) {
                // we cannot use GuiGraphics.blurBeforeThisStratum() as it can only happen once per frame

                // for accuracy, we will most likely have to implement a custom cubemap renderer that
                // renders to a256x256 target and then renders that smoothly over the screen
                instance.fillGradient(0, 0, minecraft.screen.width, minecraft.screen.height, 0x80FFFFFF, 0x00FFFFFF);
                instance.fillGradient(0, 0, minecraft.screen.width, minecraft.screen.height, 0, 0x80000000);
            }
        } else {
            original.call(instance, renderPipeline, arg, i, j, f, g, k, l, m, n, o, p);
        }
    }
}
