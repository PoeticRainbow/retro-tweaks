package io.github.poeticrainbow.retrotweaks.neoforge.mixin.client.tweak.beta_main_menu;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

/**
 * Replaces the custom NeoForge brand rendering with a Beta-styled one
 *
 * @author PoeticRainbow
 */
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixinNeoForge extends Screen {
    protected TitleScreenMixinNeoForge(Component arg) {
        super(arg);
    }

    @WrapOperation(method = "render", at = @At(target = "Lnet/neoforged/neoforge/internal/BrandingControl;forEachLine(ZZLjava/util/function/BiConsumer;)V", value = "INVOKE"))
    private void retrotweaks$override_neoforge_line_consumer(boolean includeMC, boolean reverse, BiConsumer<Integer, String> lineConsumer, Operation<Void> original, @Local(argsOnly = true) GuiGraphics graphics) {
        if (Tweaks.DIRT_GUI_BACKGROUND.get()) {
            original.call(includeMC, reverse, (BiConsumer<Integer, String>) (i, line) -> {
                // has support for multiline but will probably never have multiple lines due to mixing into BrandingControl
                graphics.drawString(this.font, line, 2, 2 + (i * 10), 0xFF505050);
            });
        } else {
            original.call(includeMC, reverse, lineConsumer);
        }
    }
}
