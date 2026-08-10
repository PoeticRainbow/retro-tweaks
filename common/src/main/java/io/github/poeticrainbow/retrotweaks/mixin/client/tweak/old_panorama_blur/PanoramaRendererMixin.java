package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_panorama_blur;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.Panorama;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Panorama.class)
public class PanoramaRendererMixin {
    /**
     * @author PoeticRainbow
     * replace the panorama overlay with the old gradient one
     */
    @WrapOperation(method = "extractRenderState", at = @At(target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V", value = "INVOKE"))
    private void retrotweaks$replace_panorama_overlay(GuiGraphicsExtractor graphics, RenderPipeline renderPipeline, Identifier texture, int x, int y, float u, float v, int width, int height, int srcWidth, int srcHeight, int textureWidth, int textureHeight, Operation<Void> original) {
        if (Tweaks.OLD_PANORAMA_BLUR.get()) {
            // we cannot use GuiGraphics.blurBeforeThisStratum() as it can only happen once per frame

            // for accuracy, we will most likely have to implement a custom cubemap renderer that
            // renders to a256x256 target and then renders that smoothly over the screen
            graphics.fillGradient(0, 0, width, height, 0x80FFFFFF, 0x00FFFFFF);
            graphics.fillGradient(0, 0, width, height, 0, 0x80000000);
        } else {
            original.call(graphics, renderPipeline, texture, x, y, u, v, width, height, srcWidth, srcHeight, textureWidth, textureHeight);
        }
    }
}
