package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.remove_hunger;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixinNeoforge {
    @WrapMethod(method = "extractHeart")
    private void retrotweaks$prevent_blinking(GuiGraphicsExtractor graphics, Gui.HeartType type, int xo, int yo, boolean isHardcore, boolean blinks, boolean half, Operation<Void> original) {
        if (Tweaks.REMOVE_HUNGER.get()) {
            original.call(graphics, type, xo, yo, isHardcore, false, half);
        }
        original.call(graphics, type, xo, yo, isHardcore, blinks, half);
    }
}
