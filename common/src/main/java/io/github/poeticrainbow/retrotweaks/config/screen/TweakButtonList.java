package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import org.jetbrains.annotations.NotNull;

public class TweakButtonList extends ContainerObjectSelectionList<@NotNull TweakButtonEntry> {
    public TweakButtonList(Minecraft minecraft, int width, int height) {
        super(minecraft, width, height, 0, 0);

        for (Tweak<?> tweak : Tweaks.values()) {
            addEntry(new TweakButtonEntry(tweak), 22);
        }
    }

    @Override
    protected int scrollBarX() {
        return super.scrollBarX() - 5;
    }
}
