package io.github.poeticrainbow.retrotweaks.mixin.client.required;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SkyRenderer.class)
public interface SkyRendererAccessor {
    @Accessor("renderTarget")
    RenderTarget retrotweaks$getRenderTarget();

    @Accessor("bottomSkyBuffer")
    GpuBuffer retrotweaks$getBottomSkyBuffer();
}
