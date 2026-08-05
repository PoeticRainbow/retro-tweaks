package io.github.poeticrainbow.retrotweaks.command;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.datafixers.util.Pair;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static java.util.Objects.requireNonNullElse;

public class CommandHelper {
    public static List<String> CLIENT_TWEAK_KEYS;
    public static List<String> COMMON_TWEAK_KEYS;

    public static final Component NEW_LINE = Component.literal("\n");

    public static CompletableFuture<Suggestions> suggest(List<String> list, SuggestionsBuilder builder) {
        list.stream().filter(key -> key.startsWith(builder.getRemainingLowerCase())).forEach(builder::suggest);
        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggestCommonTweaks(SuggestionsBuilder builder) {
        return suggest(getCommonTweakKeys(), builder);
    }

    public static CompletableFuture<Suggestions> suggestClientTweaks(SuggestionsBuilder builder) {
        return suggest(getClientTweakKeys(), builder);
    }

    public static List<String> getTweakKeys(Env side) {
        return Tweaks.values().stream().filter(tweak -> tweak.logicalSide().equals(side)).map(Tweak::key).toList();
    }

    public static List<String> getCommonTweakKeys() {
        if (COMMON_TWEAK_KEYS == null) {
            COMMON_TWEAK_KEYS = getTweakKeys(Env.SERVER);
        }
        return COMMON_TWEAK_KEYS;
    }

    public static List<String> getClientTweakKeys() {
        if (CLIENT_TWEAK_KEYS == null) {
            CLIENT_TWEAK_KEYS = getTweakKeys(Env.CLIENT);
        }
        return CLIENT_TWEAK_KEYS;
    }

    public static List<String> getTweakList(Env side) {
        return Tweaks.values()
                     .stream()
                     .filter(tweak -> tweak.logicalSide().equals(side))
                     .map(tweak -> Pair.of(tweak.key(), tweak.get()))
                     .map(Pair::toString)
                     .toList();
    }

    public static List<String> getCommonTweakList() {
        return getTweakList(Env.SERVER);
    }

    public static List<String> getClientTweakList() {
        return getTweakList(Env.CLIENT);
    }

    public static MutableComponent color(String message, ChatFormatting color) {
        return Component.literal(message)
                        .withColor(0xFF000000 | Objects.requireNonNullElse(color.getColor(), 0xFFFFFF));
    }

    public static Component tweakValuesLine(String key, String currentValue, String defaultValue, String serverValue, String actual) {
        var separator = color(", ", ChatFormatting.WHITE);
        // tweak_key: current, default, server (actual)
        return color(key, ChatFormatting.GOLD).append(color(": ", ChatFormatting.GRAY))
                                              .append(color(currentValue, ChatFormatting.AQUA))
                                              .append(separator)
                                              .append(color(defaultValue, ChatFormatting.GRAY))
                                              .append(separator)
                                              .append(color(serverValue, ChatFormatting.GREEN))
                                              .append(separator)
                                              .append(color("(" + actual + ")", ChatFormatting.LIGHT_PURPLE));
    }

    public static Component prettyTweakValueList(Env side) {
        var component = Component.empty();
        component.append(color("--------------------\n", ChatFormatting.GRAY));
        component.append(tweakValuesLine("tweak_key", "current", "default", "server", "actual"));
        component.append(NEW_LINE);
        var tweaks = Tweaks.values().stream().filter(tweak -> tweak.logicalSide().equals(side)).toList();
        for (int i = 0; i < tweaks.size(); i++) {
            var tweak = tweaks.get(i);
            component.append(tweakValuesLine(
                tweak.key(),
                tweak.currentValue().toString(),
                tweak.defaultValue().toString(),
                requireNonNullElse(tweak.serverSideValue(), "none").toString(),
                tweak.get().toString()
            ));
            if (i + 1 < tweaks.size()) {
                component.append(NEW_LINE);
            }
        }
        component.append(color("\n--------------------", ChatFormatting.GRAY));
        return component;
    }
}
