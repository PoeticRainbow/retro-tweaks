package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.blue_void;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SkyRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin {
    @WrapMethod(method = "shouldRenderDarkDisc")
    private boolean retrotweaks$never_render_disc(float deltaPartialTick, ClientLevel level, Operation<Boolean> original) {
        if (Tweaks.BLUE_VOID.get()) {
            return false;
        } else {
            return original.call(deltaPartialTick, level);
        }
    }
}
