package io.github.poeticrainbow.retrotweaks.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DimensionTracker {
    @Nullable
    private static Identifier previousDimension;
    @Nullable
    private static Identifier currentDimension;

    public static Identifier previousDimension() {
        return Objects.requireNonNullElse(previousDimension, BuiltinDimensionTypes.OVERWORLD.identifier());
    }

    public static Identifier currentDimension() {
        return Objects.requireNonNullElse(currentDimension, BuiltinDimensionTypes.OVERWORLD.identifier());
    }

    public static MutableComponent previousDimensionTranslation() {
        return Component.translatable(dimensionTranslationKey(previousDimension()));
    }

    public static MutableComponent currentDimensionTranslation() {
        return Component.translatable(dimensionTranslationKey(currentDimension()));
    }

    public static String dimensionTranslationKey(Identifier id) {
        return "dimension." + id.getNamespace() + "." + id.getPath();
    }

    public static void updateDimension(@Nullable Identifier newDimension) {
        // prev <- current <- new
        DimensionTracker.previousDimension = currentDimension;
        DimensionTracker.currentDimension = newDimension;
    }
}
