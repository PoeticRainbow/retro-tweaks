package io.github.poeticrainbow.retrotweaks.network;

import dev.architectury.networking.NetworkManager;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public record ConfigSyncS2C(Map<String, Object> values) implements CustomPacketPayload {
    public static final Type<@NotNull ConfigSyncS2C> ID = new CustomPacketPayload.Type<>(Packets.CONFIG_SYNC_S2C);

    // {tweak_key: value, ...}
    public static final StreamCodec<@NotNull RegistryFriendlyByteBuf, @NotNull ConfigSyncS2C> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public ConfigSyncS2C decode(RegistryFriendlyByteBuf buf) {
            // on the client, receiving
            int size = buf.readVarInt();
            Map<String, Object> values = new HashMap<>();

            for (int i = 0; i < size; i++) {
                String key = buf.readUtf();
                Tweak<?> tweak = Tweaks.get(key);

                values.put(key, tweak.getStreamCodec().decode(buf));
            }

            return new ConfigSyncS2C(values);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void encode(RegistryFriendlyByteBuf buf, ConfigSyncS2C packet) {
            // server to client, sending
            buf.writeVarInt(packet.values().size());

            for (var entry : packet.values().entrySet()) {
                buf.writeUtf(entry.getKey());

                Tweak<?> tweak = Tweaks.get(entry.getKey());
                ((Tweak<Object>) tweak).getStreamCodec().encode(buf, entry.getValue());
            }
        }
    };

    public static void updateTweaksS2C(ServerPlayer player) {
        if (NetworkManager.canPlayerReceive(player, ID)) {
            NetworkManager.sendToPlayer(player, build());
        }
    }

    @Override
    public @NotNull Type<? extends @NotNull CustomPacketPayload> type() {
        return ID;
    }

    public static ConfigSyncS2C build() {
        var map = new HashMap<String, Object>();
        Tweaks.values()
              .stream()
              .filter(tweak -> tweak.logicalSide().equals(Env.SERVER))
              .map(tweak -> Pair.of(tweak.key(), tweak.get()))
              .forEach(pair -> map.put(pair.getLeft(), pair.getRight()));
        return new ConfigSyncS2C(map);
    }
}
