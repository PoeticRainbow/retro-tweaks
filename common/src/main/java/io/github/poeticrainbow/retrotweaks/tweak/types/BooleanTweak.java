package io.github.poeticrainbow.retrotweaks.tweak.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import dev.architectury.utils.Env;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class BooleanTweak extends Tweak<Boolean> {
    public BooleanTweak(String key, Env logicalSide, Boolean defaultValue, Boolean disabledValue) {
        super(key, logicalSide, defaultValue, disabledValue);
    }

    public BooleanTweak(String key, Env logicalSide, Boolean defaultValue, Boolean disabledValue, Supplier<Boolean> isFunctional) {
        super(key, logicalSide, defaultValue, disabledValue, isFunctional);
    }

    public void toggle() {
        this.set(!this.get());
    }

    @Override
    public Codec<Boolean> getCodec() {
        return PrimitiveCodec.BOOL;
    }

    @Override
    public StreamCodec<@NotNull ByteBuf, @NotNull Boolean> getStreamCodec() {
        return ByteBufCodecs.BOOL;
    }
}
