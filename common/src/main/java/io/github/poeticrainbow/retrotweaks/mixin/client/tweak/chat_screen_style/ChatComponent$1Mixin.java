package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.chat_screen_style;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.GuiMessage;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.client.gui.components.ChatComponent$1")
public abstract class ChatComponent$1Mixin {
    @WrapMethod(method = "accept")
    private void retrotweaks$accept(GuiMessage.Line line, int i, float f, Operation<Void> original) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            original.call(line, i, 1f);
        } else {
            original.call(line, i, f);
        }
    }
}
