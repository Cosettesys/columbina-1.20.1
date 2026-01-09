package net.cosette.columbina.team;

import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamManager {

    private static final TeamManager INSTANCE = new TeamManager();

    private final Map<String, Integer> teamPoints = new HashMap<>();

    private final Map<UUID, String> playerTeams = new HashMap<>();

    private TeamManager() {}

    public static TeamManager getInstance() {
        return INSTANCE;
    }

    public boolean createTeam(String name) {
        if (teamPoints.containsKey(name)) {
            return false;
        }
        teamPoints.put(name, 0);
        return true;
    }

    public void addPoints(String team, int points) {
        teamPoints.put(team, teamPoints.getOrDefault(team, 0) + points);
    }

    public int getPoints(String team) {
        return teamPoints.getOrDefault(team, 0);
    }

    public boolean teamExists(String name) {
        return teamPoints.containsKey(name);
    }

    public boolean joinTeam(ServerPlayer player, String teamName) {
        if (!teamPoints.containsKey(teamName)) {
            return false;
        }
        playerTeams.put(player.getUUID(), teamName);
        return true;
    }

    public String getPlayerTeam(ServerPlayer player) {
        return playerTeams.get(player.getUUID());
    }
}