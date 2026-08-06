package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_bossbar;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyArg(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"), index = 1)
    private Component retrotweaks$modify_boss_health_text(Component component) {
        if (Tweaks.OLD_BOSSBAR.get()) {
            return Component.translatable("retrotweaks.bosshealth.name");
        } else {
            return component;
        }
    }
    @ModifyArg(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"), index = 4)
    private int retrotweaks$modify_boss_health_text_color(int i) {
        if (Tweaks.OLD_BOSSBAR.get()) {
            return 0xFFFF00FF;
        } else {
            return i;
        }
    }
    @ModifyArg(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"), index = 2)
    private int retrotweaks$modify_boss_health_text_x(int i) {
        if (Tweaks.OLD_BOSSBAR.get()) {
            int var4 = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            return var4 / 2 - minecraft.font.width(Component.translatable("retrotweaks.bosshealth.name")) / 2;
        } else {
            return i;
        }
    }
    @ModifyArg(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"), index = 3)
    private int retrotweaks$modify_boss_health_text_y(int i) {
        if (Tweaks.OLD_BOSSBAR.get()) {
            return 2;
        } else {
            return i;
        }
    }

    // TODO: Was this method doing anything before? Cannot figure out the purpose
    //@WrapMethod(method = "drawBar(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/BossEvent;I[Lnet/minecraft/resources/Identifier;[Lnet/minecraft/resources/Identifier;)V")
    //private void retrotweaks$modify_boss_health_bar(GuiGraphics guiGraphics, int i, int j, BossEvent bossEvent, int k, Identifier[] identifiers, Identifier[] identifiers2, Operation<Void> original) {
    //    if (Tweaks.OLD_BOSSBAR.get()) {
    //        int var4 = guiGraphics.guiWidth();
    //        short var5 = 182;
    //        int var6 = var4 / 2 - var5 / 2;
    //        byte var8 = 12;
    //        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifiers[bossEvent.getColor().ordinal()], 182, 5, 0, 0, var6, var8,  k, 5);
    //        if (bossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
    //            guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, identifiers2[bossEvent.getOverlay().ordinal() - 1], 182, 5, 0, 0, var6, var8, var5, 5);
    //        }
    //    } else {
    //        original.call(guiGraphics, i, j, bossEvent, k, identifiers, identifiers2);
    //    }
    //}
}
