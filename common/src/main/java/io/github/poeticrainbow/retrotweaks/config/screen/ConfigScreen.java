package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.ErrorCollector;
import io.github.poeticrainbow.retrotweaks.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ConfigScreen extends Screen {
    public static final Component TITLE = Component.translatable("gui.retrotweaks.config");
    private final Screen parent;

    private List<String> errors;

    public ConfigScreen(Screen parent) {
        super(TITLE);
        this.parent = parent;
    }

    @Override
    protected void init() {
        var borderHeight = 24;
        var buttonHeight = 20;

        var title = new StringWidget(width / 2 - font.width(TITLE) / 2, borderHeight / 2, font.width(TITLE), 0, TITLE, font);
        addRenderableOnly(title);

        var list = new TweakButtonList(minecraft, width, height - borderHeight * 2, borderHeight);
        addRenderableWidget(list);

        addRenderableWidget(
            new Button.Builder(CommonComponents.GUI_DONE, o -> this.onClose())
                .bounds((width - 220) / 2, height - buttonHeight - ((borderHeight - buttonHeight) / 2), 220, buttonHeight)
                .build()
        );

        errors = ErrorCollector.checkForErrors();
    }

    @Override
    public void onClose() {
        Config.saveAll();
        if (parent != null) {
            minecraft.setScreen(parent);
        } else {
            super.onClose();
        }
    }

    public void renderErrors(@NotNull GuiGraphics graphics) {
        if (!errors.isEmpty()) {
            for (String error : errors) {
                graphics.drawWordWrap(getFont(), FormattedText.of(error), 0, 0, width, 0xFFFF0000);
            }
        }
    }

    @Override
    public void render(@NonNull GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        renderErrors(guiGraphics);
    }
}