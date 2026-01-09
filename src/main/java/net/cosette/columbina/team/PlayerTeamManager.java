package net.cosette.columbina.team;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerTeamManager {

    private static final Map<UUID, String> PLAYER_TEAMS = new HashMap<>();

    public static void setTeam(ServerPlayer player, String teamName) {
        PLAYER_TEAMS.put(player.getUUID(), teamName);
    }

    public static String getTeam(ServerPlayer player) {
        return PLAYER_TEAMS.get(player.getUUID());
    }

    public static boolean hasTeam(ServerPlayer player) {
        return PLAYER_TEAMS.containsKey(player.getUUID());
    }

    public static void removeTeam(ServerPlayer player) {
        PLAYER_TEAMS.remove(player.getUUID());
    }
}