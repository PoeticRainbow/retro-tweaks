package io.github.poeticrainbow.retrotweaks.enums;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public enum Sprint implements StringRepresentable {
    DISABLED,
    MODERN,
    INFINITE;

    public static final List<Sprint> VALUES = List.of(DISABLED, MODERN, INFINITE);

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
}