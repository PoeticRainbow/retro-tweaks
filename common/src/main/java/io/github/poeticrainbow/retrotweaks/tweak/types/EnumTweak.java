package io.github.poeticrainbow.retrotweaks.tweak.types;

import com.mojang.serialization.Codec;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EnumTweak<T extends Enum<T> & StringRepresentable> extends Tweak<T> {
    public final Codec<T> CODEC = StringRepresentable.fromEnum(() -> defaultValue().getDeclaringClass().getEnumConstants());

    private final List<T> values;

    public EnumTweak(String key, Env logicalSide, T defaultValue, T disabledValue) {
        this(key, logicalSide, defaultValue, disabledValue, defaultValue.getDeclaringClass().getEnumConstants());
    }

    public EnumTweak(String key, Env logicalSide, T defaultValue, T disabledValue, T[] allowedValues) {
        this(key, logicalSide, defaultValue, disabledValue, List.of(allowedValues));
    }

    public EnumTweak(String key, Env logicalSide, T defaultValue, T disabledValue, List<T> allowedValues) {
        super(key, logicalSide, defaultValue, disabledValue);
        this.values = allowedValues;
    }

    @Override
    public void set(T value) {
        if (values.contains(value)) {
            super.set(value);
        } else {
            RetroTweaks.LOGGER.error(
                "Tried to set tweak to value {} which is not allowed (allowed: {})",
                value,
                StringUtils.join(values, ", ")
            );
        }
    }

    @Override
    public Codec<T> getCodec() {
        return CODEC;
    }

    @Override
    public StreamCodec<@NotNull ByteBuf, @NotNull T> getStreamCodec() {
        return ByteBufCodecs.idMapper(value -> defaultValue().getDeclaringClass().getEnumConstants()[value], Enum::ordinal);
    }

    public void next() {
        var index = values.indexOf(this.get());
        var newValue = values.get((index + 1) % values.size());
        this.set(newValue);
    }
}
