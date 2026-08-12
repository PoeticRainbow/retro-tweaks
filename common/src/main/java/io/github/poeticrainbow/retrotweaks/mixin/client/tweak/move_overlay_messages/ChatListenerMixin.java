package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.move_overlay_messages;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChatListener.class)
public abstract class ChatListenerMixin {
    @Shadow @Final private Minecraft minecraft;

    @WrapMethod(method = "handleOverlay")
    public void retrotweaks$move_overlay_messages_to_chat(Component message, Operation<Void> original) {
        if (Tweaks.MOVE_OVERLAY_MESSAGES.get()){
            this.minecraft.gui.hud.getChat().addClientSystemMessage(message);
            this.minecraft.getNarrator().saySystemChatQueued(message);
        } else {
            original.call(message);
        }
    }
}
