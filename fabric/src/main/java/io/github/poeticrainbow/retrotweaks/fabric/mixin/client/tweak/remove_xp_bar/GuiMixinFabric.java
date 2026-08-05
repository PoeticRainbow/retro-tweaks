package io.github.poeticrainbow.retrotweaks.fabric.mixin.client.tweak.remove_xp_bar;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Gui.class)
public class GuiMixinFabric {
    @ModifyArg(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHearts(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIIIFIIIZ)V"), index = 3)
    private int retrotweaks$render_hearts(int i) {
        int j = i;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        return j;
    }
    @ModifyArg(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderFood(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;II)V"), index = 2)
    private int retrotweaks$render_hunger(int i) {
        int j = i;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        return j;
    }
    @ModifyArg(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderAirBubbles(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;III)V"), index = 3)
    private int retrotweaks$render_bubbles(int i) {
        int j = i;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        return j;
    }
    @ModifyArg(method = "renderPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderArmor(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/entity/player/Player;IIII)V"), index = 2)
    private int retrotweaks$render_armor(int i) {
        int j = i;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        return j;
    }
}
