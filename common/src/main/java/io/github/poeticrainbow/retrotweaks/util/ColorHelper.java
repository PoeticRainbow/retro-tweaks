package io.github.poeticrainbow.retrotweaks.util;

import net.minecraft.ChatFormatting;

public class ColorHelper {
    public static int getColor(ChatFormatting formatting) {
        return formatting.getColor() != null ? formatting.getColor() : 0xFFFF0000;
    }
}
