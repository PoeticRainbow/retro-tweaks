package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.move_system_messages;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.time.Instant;

@Mixin(ChatListener.class)
public abstract class ChatListenerMixin {
    @Shadow protected abstract void logSystemMessage(Component component, Instant instant);
    @Shadow @Final private Minecraft minecraft;

    @WrapMethod(method = "handleSystemMessage")
    public void retrotweaks$move_system_messages_to_chat(Component component, boolean bl, Operation<Void> original) {
        if (Tweaks.MOVE_SYSTEM_MESSAGES.get()){
            this.minecraft.gui.getChat().addMessage(component);
            this.logSystemMessage(component, Instant.now());
            this.minecraft.getNarrator().saySystemChatQueued(component);
        } else {
            original.call(component, bl);
        }
    }
}
