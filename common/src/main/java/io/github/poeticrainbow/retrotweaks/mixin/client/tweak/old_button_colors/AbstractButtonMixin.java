package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_button_colors;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(AbstractButton.class)
public class AbstractButtonMixin extends AbstractWidgetMixin {
    @ModifyArg(
            method = "renderDefaultLabel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/AbstractButton;renderScrollingStringOverContents(Lnet/minecraft/client/gui/ActiveTextCollector;Lnet/minecraft/network/chat/Component;I)V"),
            index = 1
            )
    private Component retrotweaks$modify_text_colors(Component text) {
        if (Tweaks.OLD_BUTTON_COLORS.get()) {
            int baseColor = this.active ? (this.isHovered ? 0xFFFFA0 : 0xE0E0E0) : 0x5F5F60;

            return ComponentUtils.mergeStyles(text, Style.EMPTY.withColor(baseColor));
        } else {
            return text;
        }
    }
}
