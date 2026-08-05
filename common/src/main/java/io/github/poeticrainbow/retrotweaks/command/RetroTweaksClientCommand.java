package io.github.poeticrainbow.retrotweaks.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.RetroTweaksClient;

import static dev.architectury.event.events.client.ClientCommandRegistrationEvent.literal;

public class RetroTweaksClientCommand {
    public static LiteralArgumentBuilder<ClientCommandRegistrationEvent.ClientCommandSourceStack> build() {
        return literal(RetroTweaks.MOD_ID + "client")
            .then(literal("config")
                      .executes(ctx -> {
                          RetroTweaksClient.openConfigScreen();
                          return 1;
                      }))
            .then(literal("tweaks").executes(context -> {
                context.getSource()
                       .arch$getPlayer()
                       .displayClientMessage(CommandHelper.prettyTweakValueList(Env.CLIENT), false);
                context.getSource()
                       .arch$getPlayer()
                       .displayClientMessage(CommandHelper.prettyTweakValueList(Env.SERVER), false);
                return 1;
            }));
    }
}
