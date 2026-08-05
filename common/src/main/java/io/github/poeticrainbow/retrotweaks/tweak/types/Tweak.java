package io.github.poeticrainbow.retrotweaks.tweak.types;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class Tweak<T> {
    private final String key;
    private final T defaultValue;
    private final T disabledValue;
    private T currentValue;
    @Nullable private T serverSideValue = null;
    private final Supplier<Boolean> isFunctional;
    private final Env logicalSide;

    public static final String ERROR_TOOLTIP = "retrotweaks.error_tooltip";

    public Tweak(String key, Env logicalSide, T defaultValue, T disabledValue) {
        this(key, logicalSide, defaultValue, disabledValue, () -> Boolean.TRUE); // default true for all tweaks, functional
    }

    public Tweak(String key, Env logicalSide, T defaultValue, T disabledValue, Supplier<Boolean> isFunctional) {
        this.key = key;
        this.logicalSide = logicalSide;
        this.defaultValue = defaultValue;
        this.disabledValue = disabledValue;
        this.currentValue = defaultValue;
        this.isFunctional = isFunctional;
    }

    public T get() {
        return switch (logicalSide) {
            // clientside tweaks are always controlled by the client
            case CLIENT -> this.currentValue;
            // singleplayer or dedicated server (tweak host) : get the value from the current dedicated server (tweak client/guest)
            case SERVER -> RetroTweaks.isLogicalSide() ? this.currentValue : Objects.requireNonNullElse(this.serverSideValue, this.disabledValue);
        };
    }

    public void set(T value) {
        switch (logicalSide) {
            // clientside tweaks are always controlled by the client
            case CLIENT -> this.currentValue = value;
            case SERVER -> {
                // singleplayer or dedicated server (tweak host)
                if (RetroTweaks.isLogicalSide()) {
                    this.currentValue = value;
                }
            }
        }
    }

    public void setServerSideValue(@Nullable T serverSideValue) {
        this.serverSideValue = serverSideValue;
    }

    public String key() {
        return this.key;
    }

    public T defaultValue() {
        return this.defaultValue;
    }

    public T disabledValue() {
        return this.disabledValue;
    }

    public T currentValue() {
        return currentValue;
    }

    public @Nullable T serverSideValue() {
        return serverSideValue;
    }

    public String translationKey() {
        return "retrotweaks.tweak." + this.key();
    }

    public String tooltip() {
        return isFunctional() ? this.translationKey() + ".tooltip" : ERROR_TOOLTIP;
    }

    public boolean isFunctional() {
        return isFunctional.get();
    }

    public Env logicalSide() {
        return logicalSide;
    }

    public abstract Codec<T> getCodec();

    public abstract StreamCodec<@NotNull ByteBuf, @NotNull T> getStreamCodec();

    public DataResult<JsonElement> encode() {
        // do not use get() as we do not want to override with the current server's value
        return this.getCodec().encodeStart(JsonOps.INSTANCE, currentValue);
    }

    public void decode(JsonElement element) {
        var value = this.getCodec().decode(JsonOps.INSTANCE, element);
        value.ifSuccess(pair -> {
            this.currentValue = pair.getFirst();
        });
    }
}
