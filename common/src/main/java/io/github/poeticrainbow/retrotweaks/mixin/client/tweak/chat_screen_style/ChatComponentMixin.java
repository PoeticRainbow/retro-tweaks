package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.chat_screen_style;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChatComponent.class)
public abstract class ChatComponentMixin {
    @Redirect(method = "lambda$extractRenderState$1", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;fill(IIIII)V"))
    private static void retrotweaks$chat_fill(ChatComponent.ChatGraphicsAccess instance, int i, int j, int g, int k, int l) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            instance.fill(-2, j, g, k, l);
        } else {
            instance.fill(i, j, g, k, l);
        }
    }
    @Redirect(method = "extractRenderState(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;fill(IIIII)V", ordinal = 2))
    private void retrotweaks$remove_scrollbar1(ChatComponent.ChatGraphicsAccess instance, int i, int j, int g, int k, int l) {
        if (!Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            instance.fill(i, j, g, k, l);
        }
    }
    @Redirect(method = "extractRenderState(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;fill(IIIII)V", ordinal = 3))
    private void retrotweaks$remove_scrollbar2(ChatComponent.ChatGraphicsAccess instance, int i, int j, int g, int k, int l) {
        if (!Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            instance.fill(i, j, g, k, l);
        }
    }
}


