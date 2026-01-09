package net.cosette.columbina.team;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Set;

public class TeamManager {

    private static final TeamManager INSTANCE = new TeamManager();

    // Map des points par équipe
    private final Map<String, Integer> teamPoints = new HashMap<>();

    // Map de l'équipe de chaque joueur
    private final Map<UUID, String> playerTeams = new HashMap<>();

    private TeamManager() {}

    public static TeamManager getInstance() {
        return INSTANCE;
    }

    /** Charge les teams depuis TeamSavedData */
    public void loadFromSavedData(ServerLevel level) {
        TeamSavedData savedData = TeamSavedData.get(level);

        // Charger les équipes existantes
        for (String teamName : savedData.getTeams()) {
            teamPoints.putIfAbsent(teamName, 0);

            // Charger les joueurs de chaque team
            Set<UUID> members = savedData.getMembers(teamName);
            for (UUID playerId : members) {
                playerTeams.put(playerId, teamName);
            }
        }
    }

    /** Crée une équipe et l'enregistre dans SavedData */
    public boolean createTeam(ServerLevel level, String name) {
        TeamSavedData savedData = TeamSavedData.get(level);

        if (savedData.teamExists(name)) return false;

        savedData.createTeam(name); // marque le savedData comme dirty
        teamPoints.put(name, 0);
        return true;
    }

    public boolean isPlayerInTeam(ServerPlayer player) {
        return playerTeams.containsKey(player.getUUID());
    }

    public boolean joinTeam(ServerLevel level, ServerPlayer player, String teamName) {
        TeamSavedData savedData = TeamSavedData.get(level);

        if (!savedData.teamExists(teamName)) return false;

        // Vérifie que le joueur n'est pas déjà dans une équipe
        if (playerTeams.containsKey(player.getUUID())) return false;

        playerTeams.put(player.getUUID(), teamName);
        savedData.joinTeam(player.getUUID(), teamName);
        return true;
    }

    public boolean leaveTeam(ServerLevel level, ServerPlayer player) {
        TeamSavedData savedData = TeamSavedData.get(level);

        String team = playerTeams.remove(player.getUUID());
        if (team == null) return false;

        savedData.leaveTeam(player.getUUID());
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

    public String getPlayerTeam(ServerPlayer player) {
        return playerTeams.get(player.getUUID());
    }

    /** Retourne toutes les équipes existantes */
    public Set<String> getAllTeams() {
        return teamPoints.keySet();
    }
}