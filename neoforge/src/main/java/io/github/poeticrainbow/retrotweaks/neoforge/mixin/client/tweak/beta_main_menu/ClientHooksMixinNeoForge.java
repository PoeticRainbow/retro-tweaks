package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.beta_main_menu;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.neoforge.client.ClientHooks;
import org.spongepowered.asm.mixin.Mixin;

/**
 * Removes the NeoForge warnings from the title screen
 *
 * @author PoeticRainbow
 */
@Mixin(ClientHooks.class)
public abstract class ClientHooksMixinNeoForge {
    @WrapMethod(method = "renderMainMenu")
    private static void retrotweaks$remove_neoforge_main_menu(TitleScreen gui, GuiGraphics graphics, Font font, int width, int height, int alpha, Operation<Void> original) {
        if (!Tweaks.BETA_MAIN_MENU.get()) {
            original.call(gui, graphics, font, width, height, alpha);
        }
    }
}
