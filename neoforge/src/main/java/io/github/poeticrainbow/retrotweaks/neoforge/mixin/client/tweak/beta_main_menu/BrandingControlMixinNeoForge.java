package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.beta_main_menu;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.internal.BrandingControl;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.BiConsumer;

/**
 * Removes the NeoForge branding from the version text on the main menu
 *
 * @author PoeticRainbow
 */
@Mixin(BrandingControl.class)
public abstract class BrandingControlMixinNeoForge {
    @WrapMethod(method = "forEachLine")
    private static void retrotweaks$replace_version_branding(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, Operation<Void> original) {
        if (Tweaks.BETA_MAIN_MENU.get()) {
            String version = Component.translatable("retrotweaks.menu.version").getString();

            lineConsumer.accept(0, version);
        } else {
            original.call(includeMC, reverse, lineConsumer);
        }
    }
}
