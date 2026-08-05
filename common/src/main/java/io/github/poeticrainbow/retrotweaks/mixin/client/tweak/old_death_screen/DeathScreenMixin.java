package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_death_screen;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {

    protected DeathScreenMixin(Component component) {
        super(component);
    }

    @WrapMethod(method = "visitText")
    private void retrotweaks$get_rid_of_text(ActiveTextCollector activeTextCollector, Operation<Void> original) {
        if (Tweaks.OLD_DEATH_SCREEN.get()) {
            ActiveTextCollector.Parameters parameters = activeTextCollector.defaultParameters();
            activeTextCollector.defaultParameters(parameters.withScale(2.0F));
            activeTextCollector.accept(TextAlignment.CENTER, this.width / 2 / 2, 30, this.title);
            activeTextCollector.defaultParameters(parameters);
            activeTextCollector.accept(TextAlignment.CENTER, this.width / 2, 100, Component.translatable("retrotweaks.death.score"));
        } else {
            original.call(activeTextCollector);
        }
    }
}
