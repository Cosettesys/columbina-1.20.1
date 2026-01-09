package net.cosette.columbina.team;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class TeamManager {

    private static final TeamManager INSTANCE = new TeamManager();

    private final Map<String, Integer> teamPoints = new HashMap<>();
    private final Map<UUID, String> playerTeams = new HashMap<>();

    private TeamSavedData savedData; // persistance

    private TeamManager() {}

    public static TeamManager getInstance() {
        return INSTANCE;
    }

    // ⚡ Initialisation avec le SavedData pour persistance
    public void init(ServerLevel level) {
        savedData = TeamSavedData.get(level);

        // Restaurer l'état existant
        teamPoints.clear();
        playerTeams.clear();

        for (String teamName : savedData.getTeams().keySet()) {
            teamPoints.put(teamName, 0); // on met 0 pour l'instant, peut étendre pour sauvegarder points
            Set<UUID> members = savedData.getMembers(teamName);
            for (UUID uuid : members) {
                playerTeams.put(uuid, teamName);
            }
        }
    }

    public boolean createTeam(String name) {
        if (teamPoints.containsKey(name)) return false;

        boolean success = savedData.createTeam(name);
        if (!success) return false;

        teamPoints.put(name, 0);
        return true;
    }

    public boolean isPlayerInTeam(ServerPlayer player) {
        return playerTeams.containsKey(player.getUUID());
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
        if (!teamPoints.containsKey(teamName)) return false;
        if (playerTeams.containsKey(player.getUUID())) return false;

        boolean success = savedData.joinTeam(player.getUUID(), teamName);
        if (!success) return false;

        playerTeams.put(player.getUUID(), teamName);
        return true;
    }

    public boolean leaveTeam(ServerPlayer player) {
        boolean success = savedData.leaveTeam(player.getUUID());
        if (!success) return false;

        playerTeams.remove(player.getUUID());
        return true;
    }

    public String getPlayerTeam(ServerPlayer player) {
        return playerTeams.get(player.getUUID());
    }
}