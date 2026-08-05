package io.github.poeticrainbow.retrotweaks.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.config.Config;
import joptsimple.internal.Strings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permission;
import net.minecraft.server.permissions.PermissionLevel;

import static net.minecraft.commands.Commands.literal;

public class RetroTweaksServerCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return literal(RetroTweaks.MOD_ID + "server")
            .requires(source -> source.permissions().hasPermission(new Permission.HasCommandLevel(PermissionLevel.ADMINS)))
            .then(literal("reload")
                      .executes(context -> {
                          Config.init();
                          context.getSource().sendSystemMessage(Component.literal("Reloaded config"));

                          RetroTweaks.LOGGER.info("New config values: {}", Strings.join(CommandHelper.getCommonTweakList(), ", "));

                          return 1;
                      }))
            .then(literal("tweaks").executes(context -> {
                context.getSource().sendSystemMessage(CommandHelper.prettyTweakValueList(Env.SERVER));
                return 1;
            }));
    }

}
