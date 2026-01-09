package net.cosette.columbina.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.CommandDispatcher;

public class ColumbinaCommands {

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        // /columbina team create <name>
        dispatcher.register(
                Commands.literal("columbina")
                        .then(
                                Commands.literal("team")
                                .then(
                                        Commands.literal("create")
                                        .then(
                                                Commands.argument("name", StringArgumentType.word())
                                                        .executes(ctx -> {
                                                            String teamName = StringArgumentType.getString(ctx, "name");
                                                            ctx.getSource().sendSuccess(() -> Component.literal("Équipe créée : " + teamName), false);
                                                            return 1;
                                                        })
                                        )
                                )
                        )
        );
    }
}