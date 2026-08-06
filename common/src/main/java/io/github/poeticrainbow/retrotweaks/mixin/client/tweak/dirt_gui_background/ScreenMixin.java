package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.dirt_gui_background;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Screen.class)
public abstract class ScreenMixin {
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Shadow
    @Final
    protected Minecraft minecraft;
    @Unique
    private static final Identifier DIRT_TEXTURE = Identifier.withDefaultNamespace("textures/block/dirt.png");

    @WrapMethod(method = "extractPanorama")
    private void retrotweaks$render_dirt_for_panorama(GuiGraphicsExtractor guiGraphics, float a, Operation<Void> original) {
        if (Tweaks.DIRT_GUI_BACKGROUND.get()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, DIRT_TEXTURE, 0, 0, 0.0F, 0.0F, width, height, 32, 32, 0xFF404040);
        } else {
            original.call(guiGraphics, a);
        }
    }

    @WrapMethod(method = "extractMenuBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIII)V")
    private void retrotweaks$remove_menu_background(GuiGraphicsExtractor graphics, int x, int y, int width, int height, Operation<Void> original) {
        if (Tweaks.DIRT_GUI_BACKGROUND.get() && this.minecraft.level == null) {

        } else {
            original.call(graphics, x, y, width, height);
        }
    }
}
