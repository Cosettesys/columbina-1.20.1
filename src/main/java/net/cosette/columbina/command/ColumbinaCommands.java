package net.cosette.columbina.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.cosette.columbina.team.TeamManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class ColumbinaCommands {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("columbina")
                        .then(Commands.literal("team")
                                .then(Commands.literal("create")
                                        .then(Commands.argument("name", StringArgumentType.word())
                                                .executes(context -> {
                                                    String teamName = StringArgumentType.getString(context, "name"); // récupère le paramètre "name"
                                                    ServerLevel level = context.getSource().getLevel();
                                                    boolean success = TeamManager.getInstance().createTeam(level, teamName);

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
                                .then(
                                        Commands.literal("join")
                                                .then(
                                                        Commands.argument("name", StringArgumentType.word())
                                                                .executes(context -> {
                                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                                    String teamName = StringArgumentType.getString(context, "name");

                                                                    TeamManager manager = TeamManager.getInstance();

                                                                    // Vérifie si le joueur est déjà dans une team
                                                                    if (manager.isPlayerInTeam(player)) {
                                                                        context.getSource().sendFailure(
                                                                                Component.literal("Quitte d'abord ta team avant d'en rejoindre une autre.")
                                                                        );
                                                                        return 1;
                                                                    }

                                                                    // Essaie de rejoindre la team
                                                                    ServerLevel level = context.getSource().getLevel();
                                                                    boolean success = TeamManager.getInstance().joinTeam(level, player, teamName);

                                                                    if (success) {
                                                                        context.getSource().sendSuccess(
                                                                                () -> Component.literal("Tu as rejoint l'équipe " + teamName),
                                                                                false
                                                                        );
                                                                    } else {
                                                                        context.getSource().sendFailure(
                                                                                Component.literal("Cette équipe n'existe pas.")
                                                                        );
                                                                    }

                                                                    return 1;
                                                                })
                                                )
                                )
                                .then(
                                        Commands.literal("leave")
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();

                                                    ServerLevel level = context.getSource().getLevel();
                                                    boolean success = TeamManager.getInstance().leaveTeam(level, player);

                                                    if (success) {
                                                        context.getSource().sendSuccess(
                                                                () -> Component.literal("Tu as quitté ton équipe."),
                                                                false
                                                        );
                                                    } else {
                                                        context.getSource().sendFailure(
                                                                Component.literal("Tu n'es dans aucune équipe.")
                                                        );
                                                    }

                                                    return 1;
                                                })
                                )
                        )
        );
    }
}