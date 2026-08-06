package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.remove_xp_bar;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Gui.class)
public class GuiMixinNeoforge {
    @Redirect(method = "extractRenderState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;leftHeight:I", opcode = Opcodes.PUTFIELD))
    private void retrotweaks$shift_left_height(Gui instance, int value) {
        if (Tweaks.HIDE_XP_BAR.get()) {
            instance.leftHeight = value + 7;
        }
    }

    @Redirect(method = "extractRenderState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Gui;rightHeight:I", opcode = Opcodes.PUTFIELD))
    private void retrotweaks$shift_right_height(Gui instance, int value) {
        if (Tweaks.HIDE_XP_BAR.get()) {
            instance.rightHeight = value + 7;
        }
    }
}
