package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.dirt_gui_background;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelLoadingScreen.class)
public class LevelLoadingScreenMixin extends Screen {
    protected LevelLoadingScreenMixin(Component component) {
        super(component);
    }

    @Redirect(method = "renderBackground", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/LevelLoadingScreen;renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    public void retrotweaks$render_background(LevelLoadingScreen instance, GuiGraphics guiGraphics) {
        if (!Tweaks.DIRT_GUI_BACKGROUND.get()) {
            this.renderMenuBackground(guiGraphics);
        }
    }
}
