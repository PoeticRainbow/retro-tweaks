package io.github.poeticrainbow.retrotweaks.config.screen;

import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.tweak.types.BooleanTweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.EnumTweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;
import io.github.poeticrainbow.retrotweaks.util.ColorHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweakButtonEntry extends ContainerObjectSelectionList.Entry<@NotNull TweakButtonEntry> implements Button.OnPress {
    public final Tweak<?> tweak;

    public static final int BUTTON_WIDTH = 80;

    private final Component label;
    private final Button child;

    public TweakButtonEntry(Tweak<?> tweak) {
        this.tweak = tweak;

        var tooltip = Component.translatable(tweak.tooltip());
        if (!tweak.isFunctional()) {
            // if the tweak is not functional, an error occurred. mark it with red
            tooltip.setStyle(Style.EMPTY.withColor(0xFFFF0000));
            tooltip.append("\n\n")
                   .append(Component.translatable("retrotweaks.tooltip.error"));
        }

        var isInControl = tweak.logicalSide().equals(Env.CLIENT) || RetroTweaks.isLogicalSide();
        if (!isInControl) {
            tooltip.append("\n\n")
                   .append(Component.translatable("retrotweaks.tooltip.server"));
        }

        this.label = Component.translatable(tweak.translationKey());
        this.child = Button.builder(getButtonLabel(), this)
                           .tooltip(Tooltip.create(tooltip))
                           .build();
        this.child.active = tweak.isFunctional() && isInControl;
    }

    @Override
    public void onPress(@NotNull Button button) {
        if (this.tweak instanceof BooleanTweak booleanTweak) booleanTweak.toggle();
        if (this.tweak instanceof EnumTweak<?> enumTweak) enumTweak.next();
        updateMessage();
        Minecraft.getInstance().levelRenderer.allChanged();
    }

    @Override
    public @NotNull List<? extends GuiEventListener> children() {
        return List.of(child);
    }

    @Override
    public boolean mouseClicked(@NotNull MouseButtonEvent event, boolean bl) {
        return child.mouseClicked(event, bl);
    }

    @Override
    public @NotNull List<? extends NarratableEntry> narratables() {
        return List.of(child);
    }

    public Component getButtonLabel() {
        var value = tweak.get();
        var message = Component.literal(value.toString());
        if (value instanceof StringRepresentable stringRepresentable) {
            // key is retrotweaks.enum.class_name.value_name
            var className = ((Enum<?>) value).getDeclaringClass().getSimpleName().toLowerCase();
            var valueName = stringRepresentable.getSerializedName();
            message = Component.translatable("retrotweaks.enum." + className + "." + valueName);
        }
        if (value instanceof Boolean bool) {
            message.withColor(ColorHelper.getColor(bool ? ChatFormatting.GREEN : ChatFormatting.RED));
        }
        return message;
    }

    public void updateMessage() {
        child.setMessage(getButtonLabel());
    }

    @Override
    public void renderContent(@NotNull GuiGraphics graphics, int i, int j, boolean bl, float f) {
        // i and j are mouse position
        var textRenderer = graphics.textRenderer();
        var font = Minecraft.getInstance().font;
        textRenderer.accept(getX() + 2, getY() + (getHeight() - font.lineHeight) / 2, label);
        child.setPosition(getX() + getWidth() - BUTTON_WIDTH, getY() + 1);
        child.setWidth(BUTTON_WIDTH);
        child.render(graphics, i, j, f);
    }
}
