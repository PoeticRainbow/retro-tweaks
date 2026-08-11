package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import org.jetbrains.annotations.NotNull;

public class TweakButtonList extends ContainerObjectSelectionList<@NotNull TweakButtonEntry> {
    public TweakButtonList(Minecraft minecraft, int width, int height, int y) {
        super(minecraft, width, height, y, 0);

        Tweaks.values().forEach(tweak -> addEntry(new TweakButtonEntry(tweak), 22));
    }

    @Override
    protected int scrollBarX() {
        return super.scrollBarX() - 5;
    }
}