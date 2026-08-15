package io.github.poeticrainbow.retrotweaks.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import io.github.poeticrainbow.retrotweaks.mixin.client.required.LevelRendererAccessor;
import io.github.poeticrainbow.retrotweaks.mixin.client.required.SkyRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class BlueVoidRenderer {
    public static void renderBlueVoid(int skyColor) {
        var skyRendererAccessor = (SkyRendererAccessor) ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).retrotweaks$getSkyRenderer();

        if (skyRendererAccessor != null) {
            var modelViewStack = RenderSystem.getModelViewStack();
            modelViewStack.pushMatrix();
            modelViewStack.translate(0.0F, -12.0F, 0.0F);

            // transformation to sky color from b1.7.3
            var voidColor = ARGB.vector4fFromARGB32(skyColor)
                                .mul(0.2F, 0.2F, 0.6F, 1.0F)
                                .add(0.04F, 0.04F, 0.1F, 0.0F);

            var dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(modelViewStack, voidColor, new Vector3f(), new Matrix4f());

            GpuTextureView colorTexture = Minecraft.getInstance().getMainRenderTarget().getColorTextureView();
            GpuTextureView depthTexture = Minecraft.getInstance().getMainRenderTarget().getDepthTextureView();

            try (var renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "RetroTweaks Blue Void", colorTexture, OptionalInt.empty(), depthTexture, OptionalDouble.empty())) {
                renderPass.setPipeline(RenderPipelines.SKY);
                RenderSystem.bindDefaultUniforms(renderPass);
                renderPass.setUniform("DynamicTransforms", dynamicTransforms);
                renderPass.setVertexBuffer(0, skyRendererAccessor.retrotweaks$getBottomSkyBuffer());
                renderPass.draw(0, 10);
            }

            modelViewStack.popMatrix();
        }
    }
}
