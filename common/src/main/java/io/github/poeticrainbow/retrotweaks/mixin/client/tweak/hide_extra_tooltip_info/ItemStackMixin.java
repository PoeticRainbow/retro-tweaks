package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.hide_extra_tooltip_info;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Component getStyledHoverName();

    @ModifyReturnValue(method = "getTooltipLines", at = @At(value = "RETURN"))
    public List<Component> retrotweaks$get_tooltip_lines(List<Component> original) {
        if (Tweaks.HIDE_EXTRA_TOOLTIP_INFO.get()) {
            return List.of(Component.literal(this.getStyledHoverName().getString()));
        } else {
            return original;
        }
    }
}
