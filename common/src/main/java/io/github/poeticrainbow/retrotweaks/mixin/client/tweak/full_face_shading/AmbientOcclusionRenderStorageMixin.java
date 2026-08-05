package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.full_face_shading;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.renderer.block.ModelBlockRenderer$AmbientOcclusionRenderStorage")
public abstract class AmbientOcclusionRenderStorageMixin  {
    /*
    * Faces should never be partial and should always use lighting as if they are full faces
    */
    @Redirect(method = "calculate", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer$AmbientOcclusionRenderStorage;facePartial:Z", opcode = Opcodes.GETFIELD))
    private boolean retrotweaks$disable_partial_face_shading(ModelBlockRenderer.AmbientOcclusionRenderStorage instance) {
        if (Tweaks.FULL_FACE_SHADING.get()) return false;
        return instance.facePartial;
    }
}
