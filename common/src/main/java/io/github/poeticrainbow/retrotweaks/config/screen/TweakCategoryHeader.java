package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class TweakCategoryHeader extends TweakButtonList.AbstractEntry {
    public final StringWidget child;

    public TweakCategoryHeader(Tweaks.TweakCategory category) {
        this.child = new StringWidget(getX(), getY(), getWidth(), getHeight(), category.translation(), Minecraft.getInstance().font);
    }

    @Override
    public @NonNull List<? extends NarratableEntry> narratables() {
        return List.of(child);
    }

    @Override
    public void extractContent(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
        child.setMaxWidth(getWidth());
        child.setX(getContentXMiddle() - Minecraft.getInstance().font.width(child.getMessage()) / 2);
        child.setY(getContentYMiddle());

        child.extractRenderState(graphics, mouseX, mouseY, a);
    }

    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return List.of(child);
    }
}
