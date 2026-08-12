package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.chat_screen_style;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {
    protected ChatScreenMixin(Component component) {
        super(component);
    }

    @Shadow protected EditBox input;
    @Shadow CommandSuggestions commandSuggestions;
    @Shadow private ChatComponent.DisplayMode displayMode;

    @Shadow
    protected abstract boolean insertionClickMode();

    @Unique public int retrotweaks$updateCounter;

    @WrapMethod(method = "extractRenderState")
    public void retrotweaks$render(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, Operation<Void> original) {
        if (Tweaks.CHAT_SCREEN_STYLE.get().isEnabled()) {
            graphics.fill(2, this.height - 14, this.width - 2, this.height - 2, this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE));
            this.minecraft.gui.hud.getChat().extractRenderState(graphics, this.font, this.minecraft.gui.hud.getGuiTicks(), mouseX, mouseY, displayMode, insertionClickMode());
            graphics.text(this.font, "> " + this.input.getValue() + (this.retrotweaks$updateCounter / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 0xFFE0E0E0);

            if (Tweaks.CHAT_SCREEN_STYLE.get().showSuggestions()) {
                commandSuggestions.extractRenderState(graphics, mouseX, mouseY);
            }
        } else {
            original.call(graphics, mouseX, mouseY, a);
        }
    }

    public void tick() {
        this.retrotweaks$updateCounter++;
    }
}
