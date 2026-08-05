package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_button_colors;

import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractWidget.class)
public class AbstractWidgetMixin {
    @Shadow protected boolean isHovered;
    @Shadow protected float alpha;
    @Shadow public boolean active;
}
