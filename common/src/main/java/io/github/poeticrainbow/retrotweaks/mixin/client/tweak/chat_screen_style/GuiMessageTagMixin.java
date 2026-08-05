package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.chat_screen_style;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GuiMessageTag.class)
public abstract class GuiMessageTagMixin {
    @Shadow @Final private static GuiMessageTag SYSTEM;
    @Shadow @Final private static GuiMessageTag SYSTEM_SINGLE_PLAYER;
    @Shadow @Final private static GuiMessageTag CHAT_NOT_SECURE;
    @Shadow @Final private static GuiMessageTag CHAT_ERROR;
    @Shadow @Final private static Component CHAT_MODIFIED_TEXT;

    @WrapMethod(method = "system")
    private static GuiMessageTag retrotweaks$system(Operation<GuiMessageTag> original) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            return null;
        } else {
            return SYSTEM;
        }
    }

    @WrapMethod(method = "systemSinglePlayer")
    private static GuiMessageTag retrotweaks$system_singleplayer(Operation<GuiMessageTag> original) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            return null;
        } else {
            return SYSTEM_SINGLE_PLAYER;
        }
    }

    @WrapMethod(method = "chatNotSecure")
    private static GuiMessageTag retrotweaks$chat_not_secure(Operation<GuiMessageTag> original) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            return null;
        } else {
            return CHAT_NOT_SECURE;
        }
    }

    @WrapMethod(method = "chatModified")
    private static GuiMessageTag retrotweaks$chat_modified(String string, Operation<GuiMessageTag> original) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            return null;
        } else {
            Component component = Component.literal(string).withStyle(ChatFormatting.GRAY);
            Component component2 = Component.empty()
                                            .append(CHAT_MODIFIED_TEXT)
                                            .append(CommonComponents.NEW_LINE)
                                            .append(component);
            return new GuiMessageTag(6316128, GuiMessageTag.Icon.CHAT_MODIFIED, component2, "Modified");
        }
    }

    @WrapMethod(method = "chatError")
    private static GuiMessageTag retrotweaks$chat_error(Operation<GuiMessageTag> original) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            return null;
        } else {
            return CHAT_ERROR;
        }
    }
}
