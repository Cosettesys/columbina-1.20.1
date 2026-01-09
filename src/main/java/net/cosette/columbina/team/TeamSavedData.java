package net.cosette.columbina.team;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.*;

public class TeamSavedData extends SavedData {

    private Map<String, Set<UUID>> teams = new HashMap<>();
    public static final String DATA_NAME = "columbina_teams";

    public TeamSavedData() {
        super();
    }

    public TeamSavedData(CompoundTag tag) {
        super();
        teams = new HashMap<>();
        for (String key : tag.getAllKeys()) {
            ListTag list = tag.getList(key, 8); // 8 = STRING
            Set<UUID> members = new HashSet<>();
            list.forEach(nbt -> members.add(UUID.fromString(nbt.getAsString())));
            teams.put(key, members);
        }
    }

    public static TeamSavedData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                tag -> new TeamSavedData(tag),  // reader
                TeamSavedData::new,             // supplier si absent
                DATA_NAME
        );
    }

    public boolean createTeam(String name) {
        if (teams.containsKey(name)) return false;
        teams.put(name, new HashSet<>());
        setDirty();
        return true;
    }

    public boolean joinTeam(UUID playerId, String teamName) {
        if (!teams.containsKey(teamName)) return false;
        for (Set<UUID> members : teams.values()) {
            if (members.contains(playerId)) return false;
        }
        teams.get(teamName).add(playerId);
        setDirty();
        return true;
    }

    public boolean leaveTeam(UUID playerId) {
        for (Set<UUID> members : teams.values()) {
            if (members.remove(playerId)) {
                setDirty();
                return true;
            }
        }
        return false;
    }

    public boolean teamExists(String name) {
        return teams.containsKey(name);
    }

    public Set<UUID> getMembers(String teamName) {
        return teams.getOrDefault(teamName, Collections.emptySet());
    }

    public Set<String> getTeams() {
        return teams.keySet();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        for (Map.Entry<String, Set<UUID>> entry : teams.entrySet()) {
            ListTag list = new ListTag();
            for (UUID uuid : entry.getValue()) {
                list.add(StringTag.valueOf(uuid.toString()));
            }
            tag.put(entry.getKey(), list);
        }
        return tag;
    }
}