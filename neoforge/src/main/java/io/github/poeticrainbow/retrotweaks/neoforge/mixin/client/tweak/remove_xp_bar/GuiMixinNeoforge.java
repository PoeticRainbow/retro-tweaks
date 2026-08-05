package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.remove_xp_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixinNeoforge {
    @WrapMethod(method = "renderHearts")
    private void retrotweaks$render_hearts(GuiGraphics arg, Player arg2, int m, int n, int o, int p, float f, int q, int r, int s, boolean bl, Operation<Void> original) {
        int j = n;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        original.call(arg, arg2, m, j, o, p, f, q, r, s, bl);
    }
    @WrapMethod(method = "renderFood")
    private void retrotweaks$render_hunger(GuiGraphics arg, Player arg2, int m, int n, Operation<Void> original) {
        int j = m;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        original.call(arg, arg2, j, n);
    }
    @WrapMethod(method = "renderAirBubbles")
    private void retrotweaks$render_bubbles(GuiGraphics arg, Player arg2, int m, int n, int o, Operation<Void> original) {
        int j = n;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        original.call(arg, arg2, m, j, o);
    }
    @WrapMethod(method = "renderArmor")
    private static void retrotweaks$render_armor(GuiGraphics arg, Player arg2, int m, int n, int o, int p, Operation<Void> original) {
        int j = m;
        if (Tweaks.HIDE_XP_BAR.get()) {
            j = j + 7;
        }
        original.call(arg, arg2, j, n, o, p);

    }
}
