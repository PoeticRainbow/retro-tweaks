package io.github.poeticrainbow.retrotweaks.mixin.client.required;

import io.github.poeticrainbow.retrotweaks.RetroTweaksClient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Override
    public boolean keyPressed(@NotNull KeyEvent keyEvent) {
        if (RetroTweaksClient.RETRO_TWEAKS_BUTTON.matches(keyEvent)) {
            RetroTweaksClient.openConfigScreen();
            return true;
        }
        return super.keyPressed(keyEvent);
    }
}
