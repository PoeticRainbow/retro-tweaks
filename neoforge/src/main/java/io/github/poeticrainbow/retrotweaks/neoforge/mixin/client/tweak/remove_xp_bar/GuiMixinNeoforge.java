package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.remove_xp_bar;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Hud;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Hud.class)
public class GuiMixinNeoforge {
    @Redirect(method = "extractRenderState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Hud;leftHeight:I", opcode = Opcodes.PUTFIELD))
    private void retrotweaks$shift_left_height(Hud instance, int value) {
        if (Tweaks.HIDE_XP_BAR.get()) {
            instance.leftHeight = value + 7;
        }
    }

    @Redirect(method = "extractRenderState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/Hud;rightHeight:I", opcode = Opcodes.PUTFIELD))
    private void retrotweaks$shift_right_height(Hud instance, int value) {
        if (Tweaks.HIDE_XP_BAR.get()) {
            instance.rightHeight = value + 7;
        }
    }
}
