package io.github.poeticrainbow.retrotweaks.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public enum Chat implements StringRepresentable {
    BETA,
    BETA_PLUS,
    MODERN;

    public static final List<Chat> VALUES = List.of(BETA, BETA_PLUS, MODERN);

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.toString();
    }

    public boolean isEnabled() {
        return !this.equals(MODERN);
    }

    public boolean showSuggestions() {
        return this.equals(BETA_PLUS);
    }
}
