package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.star_style;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import io.github.poeticrainbow.retrotweaks.enums.Versions;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.renderer.DynamicUniforms;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.client.renderer.state.SkyRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.EnvironmentAttributeProbe;
import net.minecraft.world.attribute.EnvironmentAttributes;
import org.joml.Matrix4fc;
import org.joml.Vector3fc;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SkyRenderer.class)
public abstract class SkyRendererMixin {
    // After Blending
    // Classic -> Beta 1.4
    // 0xFF808080
    // Beta 1.5 -> Now
    // 0xFF242424

    // k for fabric
    // f4 for neoforge
    //@ModifyVariable(method = "buildStars", at = @At(value = "STORE"), name = "k")
    @ModifyVariable(method = "buildStars", at = @At(value = "STORE"), ordinal = 4)
    private static float retrotweaks$modify_star_size(float value) {
        if (Tweaks.STAR_STYLE.get().ordinal() < Versions.RELEASE.ordinal()) {
            // modern 0.15F + randomSource.nextFloat() * 0.1F
            // [0.15, 0.25]
            // beta 0.25f + source.nextFloat() * 0.25f
            // [0.25, 0.5]

            // we do NOT want to roll the RandomSource again, or it will shift the random values
            var random = (value - 0.15f) * 10;
            return 0.25f + random * 0.25f;
        }
        return value;
    }

    @WrapOperation(method = "renderStars", at = @At(target = "Lnet/minecraft/client/renderer/DynamicUniforms;writeTransform(Lorg/joml/Matrix4fc;Lorg/joml/Vector4fc;Lorg/joml/Vector3fc;Lorg/joml/Matrix4fc;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;", value = "INVOKE"))
    private static GpuBufferSlice retrotweaks$opaque_stars(DynamicUniforms instance, Matrix4fc modelView, Vector4fc colorModulator, Vector3fc modelOffset, Matrix4fc textureMatrix, Operation<GpuBufferSlice> original) {
        if (Tweaks.STAR_STYLE.get().isOlderThanOrEqualTo(Versions.ALPHA)) {
            colorModulator = new Vector4f(colorModulator.x(), colorModulator.y(), colorModulator.z(), 1.0F);
        }

        return original.call(instance, modelView, colorModulator, modelOffset, textureMatrix);
    }

    @WrapOperation(method = "extractRenderState", at = @At(target = "Lnet/minecraft/client/renderer/state/SkyRenderState;starBrightness:F", value = "FIELD", opcode = Opcodes.PUTFIELD))
    private static void retrotweaks$override_star_brightness(SkyRenderState instance, float value, Operation<Void> original, @Local(argsOnly = true, name = "f") float f, @Local(name = "environmentAttributeProbe") EnvironmentAttributeProbe environmentAttributeProbe) {
        if (!Tweaks.STAR_STYLE.get().isOlderThan(Versions.MODERN)) {
            instance.starBrightness = retrotweaks$beta_star_brightness(environmentAttributeProbe.getValue(EnvironmentAttributes.SUN_ANGLE, f) / 360F);
        } else {
            original.call(instance, value);
        }
    }

    @Unique
    private static float retrotweaks$beta_star_brightness(float dayTime) {
        // modern uses timeline, beta uses clamped function

        // dayTime: range [0, 1), aka [sunrise, sunrise), ~[0.5, 1.0) is nighttime, ticks: [0, 24000)
        // starBrightness: range [0, 0.5]
        float starBrightness = Mth.clamp(1.0F - (Mth.cos(dayTime * (float) Math.PI * 2.0F) * 2.0F + 0.75F), 0.0F, 1.0F);

        return starBrightness * starBrightness * 0.5F;
    }
}
