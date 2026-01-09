package net.cosette.columbina.item;

import net.cosette.columbina.team.PlayerTeamManager;
import net.cosette.columbina.team.TeamManager;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TokenItem extends Item {

    public TokenItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide && context.getPlayer() != null) {

            String team = PlayerTeamManager.getTeam((ServerPlayer) context.getPlayer());

            if (team != null) {
                TeamManager.getInstance().addPoints(team, 1);

                context.getPlayer().sendSystemMessage(
                        Component.literal("§a +1 point pour l'équipe " + team)
                );

                context.getItemInHand().shrink(1); // consomme le jeton
                return InteractionResult.SUCCESS;
            } else {
                context.getPlayer().sendSystemMessage(
                        Component.literal("§c Vous n'êtes dans aucune équipe.")
                );
            }
        }

        return InteractionResult.PASS;
    }
}