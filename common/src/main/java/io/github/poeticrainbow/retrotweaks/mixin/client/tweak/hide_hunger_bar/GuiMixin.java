package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.hide_hunger_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Final @Shadow private static Identifier ARMOR_EMPTY_SPRITE;
    @Unique private static final Identifier ARMOR_HALF_SPRITE = Identifier.fromNamespaceAndPath(RetroTweaks.MOD_ID, "hud/armor_half_flipped");
    @Final @Shadow private static Identifier ARMOR_FULL_SPRITE;
    @Final @Shadow private static Identifier AIR_SPRITE;
    @Final @Shadow private static Identifier AIR_POPPING_SPRITE;

    @WrapMethod(method = "renderArmor")
    private static void retrotweaks$render_armor(GuiGraphics guiGraphics, Player player, int i, int j, int k, int l, Operation<Void> original) {
        if (Tweaks.HIDE_HUNGER_BAR.get()) {
            int m = player.getArmorValue();
            int var6 = guiGraphics.guiWidth();
            int var7 = guiGraphics.guiHeight();
            int yOffset = var7 - 32;
            if (!Tweaks.HIDE_XP_BAR.get()) {
                yOffset = yOffset - 7;
            }
            if (m > 0) {
                for(int o = 0; o < 10; ++o) {
                    int xOffset = var6 / 2 + 91 - o * 8 - 9;
                    if (o * 2 + 1 < m) {

                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_FULL_SPRITE, xOffset, yOffset, 9, 9);
                    }

                    if (o * 2 + 1 == m) {
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_HALF_SPRITE, xOffset, yOffset, 9, 9);
                    }

                    if (o * 2 + 1 > m) {
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, ARMOR_EMPTY_SPRITE, xOffset, yOffset, 9, 9);
                    }
                }
            }
        } else {
            original.call(guiGraphics, player, i, j, k, l);
        }
    }

    @WrapMethod(method = "renderFood")
    private void retrotweaks$render_food(GuiGraphics guiGraphics, Player player, int i, int j, Operation<Void> original) {
        if (!Tweaks.HIDE_HUNGER_BAR.get()) {
            original.call(guiGraphics, player, i, j);
        }
        // do nothing
    }

    @WrapMethod(method = "renderAirBubbles")
    private void retrotweaks$render_air_bubble(GuiGraphics guiGraphics, Player player, int i, int j, int k, Operation<Void> original) {
        if (Tweaks.HIDE_HUNGER_BAR.get()) {
            int l = player.getMaxAirSupply();
            int m = Math.clamp(player.getAirSupply(), 0, l);
            boolean bl = player.isEyeInFluid(FluidTags.WATER);
            int var6 = guiGraphics.guiWidth();
            int var7 = guiGraphics.guiHeight();

            if (bl || m < l) {
                int n = (int) Math.ceil((double) (m - 2) * 10.0D / (double) l);
                int totalBubbles = (int) Math.ceil((double) m * 10.0D / (double) l);
                int o = totalBubbles - n;
                float maxHealth = (float) player.getAttributeValue(Attributes.MAX_HEALTH);
                float absorption = player.getAbsorptionAmount();
                int healthRows = Mth.ceil((maxHealth + absorption) / 2.0F / 10.0F);

                int yOffset = var7 - 32 - 9;
                yOffset -= (healthRows - 1) * 10;

                if (!Tweaks.HIDE_XP_BAR.get()) {
                    yOffset = yOffset - 7;
                }

                for (int q = 0; q < (n + o); ++q) {
                    if (q < n) {
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, AIR_SPRITE, var6 / 2 - 91 + q * 8, yOffset, 9, 9);
                    } else {
                        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, AIR_POPPING_SPRITE, var6 / 2 - 91 + q * 8, yOffset, 9, 9);
                    }
                }
            }
        } else {
            original.call(guiGraphics, player, i, j, k);
        }
    }
}
