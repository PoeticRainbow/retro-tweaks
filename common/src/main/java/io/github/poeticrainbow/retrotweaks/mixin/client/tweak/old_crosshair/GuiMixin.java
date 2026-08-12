package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.old_crosshair;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Hud.class)
public abstract class GuiMixin{
    @Shadow @Final private static Identifier CROSSHAIR_SPRITE;
    @Shadow protected abstract boolean canRenderCrosshairForSpectator(@Nullable HitResult arg);
    @Shadow @Final private Minecraft minecraft;

    @WrapMethod(method = "extractCrosshair")
    private void retrotweaks$render_crosshair(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker, Operation<Void> original) {
        if (Tweaks.OLD_CROSSHAIR.get()) {
            if ((this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR || this.canRenderCrosshairForSpectator(this.minecraft.hitResult)) && !this.minecraft.debugEntries.isCurrentlyEnabled(DebugScreenEntries.THREE_DIMENSIONAL_CROSSHAIR)) {
                graphics.nextStratum();
                graphics.blitSprite(RenderPipelines.CROSSHAIR, CROSSHAIR_SPRITE, graphics.guiWidth() / 2 - 7, graphics.guiHeight() / 2 - 7, 15, 15);
            }
        } else {
            original.call(graphics, deltaTracker);
        }
    }
}
