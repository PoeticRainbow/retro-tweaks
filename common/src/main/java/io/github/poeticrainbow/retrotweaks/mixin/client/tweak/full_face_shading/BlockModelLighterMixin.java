package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.full_face_shading;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.renderer.block.BlockModelLighter;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockModelLighter.class)
public abstract class BlockModelLighterMixin {
    /*
    * Faces should never be partial and should always use lighting as if they are full faces
    */
    @Redirect(method = "prepareQuadAmbientOcclusion", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/block/BlockModelLighter;facePartial:Z", opcode = Opcodes.GETFIELD))
    private boolean retrotweaks$disable_partial_face_shading(BlockModelLighter instance) {
        if (Tweaks.FULL_FACE_SHADING.get()) return false;
        return instance.facePartial;
    }
}
