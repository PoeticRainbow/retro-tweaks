package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.hide_extra_tooltip_info;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(CreativeModeInventoryScreen.class)
public class CreativeModeInventoryScreenMixin {
    @WrapMethod(method = "getTooltipFromContainerItem")
    public List<Component> retrotweaks$get_tooltip(ItemStack itemStack, Operation<List<Component>> original) {
        if (Tweaks.HIDE_EXTRA_TOOLTIP_INFO.get()) {
            return List.of(Component.literal(itemStack.getStyledHoverName().getString()));
        } else {
            return original.call(itemStack);
        }
    }
}
