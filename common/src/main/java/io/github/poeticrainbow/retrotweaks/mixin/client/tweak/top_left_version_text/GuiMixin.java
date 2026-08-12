package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.top_left_version_text;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Hud.class)
public class GuiMixin {
    @Shadow @Final private Minecraft minecraft;

    @Inject(method = "extractRenderState", at = @At("HEAD"))
    private void retrotweaks$skip_fade_in(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if (Tweaks.TOP_LEFT_VERSION_TEXT.get()){
            if (!this.minecraft.debugEntries.isOverlayVisible()) {
                String version = Component.translatable("retrotweaks.menu.version").getString();
                graphics.text(this.minecraft.font, version, 2, 2, 0xFFFFFFFF);
            }
        }
    }
}
