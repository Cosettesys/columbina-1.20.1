package net.cosette.columbina.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.cosette.columbina.team.TeamManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.CommandDispatcher;

public class ColumbinaCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("columbina")
                        .then(Commands.literal("team")
                                .then(Commands.literal("create")
                                        .then(Commands.argument("name", StringArgumentType.word())
                                                .executes(context -> {
                                                    String teamName = StringArgumentType.getString(context, "name");

                                                    boolean success = TeamManager.getInstance().createTeam(teamName);

                                                    if (success) {
                                                        context.getSource().sendSuccess(
                                                                () -> Component.literal("Équipe créée : " + teamName),
                                                                false
                                                        );
                                                    } else {
                                                        context.getSource().sendSuccess(
                                                                () -> Component.literal("L'équipe existe déjà : " + teamName),
                                                                false
                                                        );
                                                    }
                                                    return 1;
                                                })
                                        )
                                )
                        )
        );
    }
}