package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.loading_screen;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.enums.Versions;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static io.github.poeticrainbow.retrotweaks.RetroTweaks.MOD_ID;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    @Shadow private long fadeOutStart;
    @Shadow private long fadeInStart;
    @Shadow private boolean fadeIn;

    @Unique
    private static final Identifier ALPHA_MOJANG_LOGO = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/mojang_alpha.png");
    @Unique
    private static final Identifier BETA_MOJANG_LOGO = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/mojang_beta.png");
    @Unique
    private static final Identifier RELEASE_MOJANG_LOGO = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/mojang_release.png");

    @Unique
    private static final Identifier DIRT_TEXTURE = Identifier.withDefaultNamespace("textures/block/dirt.png");

    /**
     * @author GMPDX
     * @reason Beta Mojang Logo
     */
    @WrapMethod(method = "render")
    public void retrotweaks$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        // only change when not modern logo
        if (!Tweaks.LOADING_SCREEN.get().equals(Versions.MODERN)) {
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            long now = Util.getMillis();
            Font font = Minecraft.getInstance().font;
            String loading = Component.translatable("retrotweaks.menu.loading").getString();

            if (this.fadeIn && this.fadeInStart == -1L) {
                this.fadeInStart = now - 5000L;
            }

            // use entirely seperate logic for infdev, as it has a wildly different loading screen
            if (!Tweaks.LOADING_SCREEN.get().equals(Versions.INFDEV)) {
                // hardcoded color, can we make the edges of the logo stretch to fill?
                guiGraphics.fill(RenderPipelines.GUI, 0, 0, width, height, Tweaks.LOADING_SCREEN.get().equals(Versions.ALPHA) ? 0xFF373363 : 0xFFFFFFFF);

                int x = (int) ((width / 4.0D) - (128 / 2.0D));
                int y = (int) ((height / 4.0D) - (128 / 2.0D));

                guiGraphics.pose().pushMatrix();
                guiGraphics.pose().scale(2F, 2F);

                var logo = switch (Tweaks.LOADING_SCREEN.get()) {
                    case Versions.ALPHA -> ALPHA_MOJANG_LOGO;
                    case Versions.BETA -> BETA_MOJANG_LOGO;
                    default -> RELEASE_MOJANG_LOGO;
                };
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, logo, x, y, 0, 0, 128, 128, 128, 128);

                guiGraphics.pose().popMatrix();
            } else {
                guiGraphics.blit(RenderPipelines.GUI_TEXTURED, DIRT_TEXTURE, 0, 0, 0.0F, 0.0F, width, height, 32, 32, 0xFF404040);
                guiGraphics.drawString(font, loading, 2 + 8, height - 18, 0xFFFFFFFF);
            }

            if (this.fadeOutStart != -1L) {
                float fadeOutPercentage = (float) (now - this.fadeOutStart) / 1000.0F;
                if (fadeOutPercentage >= 2.0F) {
                    Minecraft.getInstance().setOverlay(null);
                }
            }
        } else {
            original.call(guiGraphics, mouseX, mouseY, partialTick);
        }
    }
}