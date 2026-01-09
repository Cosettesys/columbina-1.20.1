package net.cosette.columbina.team;

import java.util.HashMap;
import java.util.Map;

public class TeamManager {

    private static final TeamManager INSTANCE = new TeamManager();

    // Nom de l'équipe -> points
    private final Map<String, Integer> teamPoints = new HashMap<>();

    private TeamManager() {}

    public static TeamManager getInstance() {
        return INSTANCE;
    }

    public boolean createTeam(String name) {
        if (teamPoints.containsKey(name)) return false; // déjà existante
        teamPoints.put(name, 0);
        return true;
    }

    public void addPoints(String teamName, int points) {
        teamPoints.merge(teamName, points, Integer::sum);
    }

    public int getPoints(String teamName) {
        return teamPoints.getOrDefault(teamName, 0);
    }

    public Map<String, Integer> getAllTeams() {
        return Map.copyOf(teamPoints);
    }
}