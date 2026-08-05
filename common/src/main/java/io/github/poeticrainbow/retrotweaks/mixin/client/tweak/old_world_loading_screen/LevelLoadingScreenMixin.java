package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_world_loading_screen;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import io.github.poeticrainbow.retrotweaks.util.DimensionTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.LevelLoadTracker;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin extends Screen {
    protected LevelLoadingScreenMixin(Component component) {
        super(component);
    }

    @Shadow private LevelLoadTracker loadTracker;
    @Shadow private float smoothedProgress;

    @Unique private Component retrotweaks$previousDimension;
    @Unique private Component retrotweaks$currentDimension;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void retrotweaks$set_previous_and_current_dimensions(LevelLoadTracker levelLoadTracker, LevelLoadingScreen.Reason reason, CallbackInfo ci) {
        retrotweaks$previousDimension = DimensionTracker.previousDimensionTranslation();
        retrotweaks$currentDimension = DimensionTracker.currentDimensionTranslation();
    }

    @Unique
    void retrotweaks$draw_progress_bar(GuiGraphics guiGraphics, int xStart, int yStart, int length, int height, float progress) {
        guiGraphics.fill(xStart, yStart, xStart + length, yStart + height, 0xFF808080);
        guiGraphics.fill(xStart, yStart, xStart + Math.round(progress * length), yStart + height, 0xFF80FF80);
    }

    @WrapMethod(method = "render")
    public void retrotweaks$render(GuiGraphics guiGraphics, int m, int n, float f, Operation<Void> original) {
        if (Tweaks.OLD_WORLD_LOADING_SCREEN.get()) {
            int width = this.width / 2;
            int height = this.height / 2;
            int l = height - 10;
            int barLength = 100;
            int barHeight = 2;
            int xStart = width - barLength / 2;
            int yStart = height + 16;

            Component header = Component.translatable("retrotweaks.loading.loading");
            Component stage = Component.translatable("retrotweaks.loading.building");

            // do not show text when going between identical dimensions
            if (this.minecraft.level != null && !retrotweaks$previousDimension.equals(retrotweaks$currentDimension)) {
                if (retrotweaks$previousDimension.equals(Component.translatable("dimension.minecraft.overworld"))) {
                    stage = Component.translatable("retrotweaks.loading.enter", retrotweaks$currentDimension);
                }
                if (retrotweaks$currentDimension.equals(Component.translatable("dimension.minecraft.overworld"))) {
                    stage = Component.translatable("retrotweaks.loading.leave", retrotweaks$previousDimension);
                }
            }

            guiGraphics.drawCenteredString(this.font, stage, width, l - 4 + 8, -1);
            guiGraphics.drawCenteredString(this.font, header, width, l - 4 - 16, -1);
            if (loadTracker.hasProgress()) {
                this.retrotweaks$draw_progress_bar(guiGraphics, xStart, yStart, barLength, barHeight, smoothedProgress);
            }
        } else {
            original.call(guiGraphics, m, n, f);
        }
    }

    @WrapMethod(method = "renderBackground")
    private void retrotweaks$render_background(GuiGraphics guiGraphics, int i, int j, float f, Operation<Void> original) {
        if (Tweaks.OLD_WORLD_LOADING_SCREEN.get()) {
            this.renderPanorama(guiGraphics, f);
            this.renderBlurredBackground(guiGraphics);
            if (!Tweaks.DIRT_GUI_BACKGROUND.get()) {
                this.renderMenuBackground(guiGraphics);
            }
        } else {
            original.call(guiGraphics, i, j, f);
        }
    }
}
