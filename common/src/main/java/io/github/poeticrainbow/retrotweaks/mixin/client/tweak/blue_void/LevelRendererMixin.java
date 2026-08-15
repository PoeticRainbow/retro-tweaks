package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.blue_void;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import io.github.poeticrainbow.retrotweaks.util.BlueVoidRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "lambda$addSkyPass$0", at = @At(target = "Lnet/minecraft/client/renderer/SkyRenderer;renderSunMoonAndStars(Lcom/mojang/blaze3d/vertex/PoseStack;FFFLnet/minecraft/world/level/MoonPhase;FF)V", value = "INVOKE", shift = At.Shift.AFTER))
    private static void retrotweaks$render_blue_void(GpuBufferSlice skyFog, SkyRenderState state, SkyRenderer skyRenderer, CallbackInfo ci) {
        if (Tweaks.BLUE_VOID.get()) {
            BlueVoidRenderer.renderBlueVoid(state.skyColor);
        }
    }
}
