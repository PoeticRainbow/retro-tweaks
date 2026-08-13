package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;

public class TweakButtonList extends ContainerObjectSelectionList<TweakButtonList.AbstractEntry> {
    public TweakButtonList(Minecraft minecraft, int width, int height, int y) {
        super(minecraft, width, height, y, 0);

        Tweaks.CATEGORIES.forEach((category, tweaks) -> {
            addEntry(new TweakCategoryHeader(category), 16);
            for (Tweak<?> tweak : tweaks) {
                addEntry(new TweakButtonEntry(tweak), 22);
            }
        });
    }

    @Override
    protected int scrollBarX() {
        return super.scrollBarX() - 5;
    }


    protected abstract static class AbstractEntry extends ContainerObjectSelectionList.Entry<TweakButtonList.AbstractEntry> {
        protected AbstractEntry() {
        }
    }
}
