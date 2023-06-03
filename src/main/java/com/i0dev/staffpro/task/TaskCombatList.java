package com.i0dev.staffpro.task;

import com.i0dev.staffpro.integration.combattagplus.IntegrationCombatTagPlus;
import com.massivecraft.massivecore.ModuloRepeatTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class TaskCombatList extends ModuloRepeatTask {

    private static TaskCombatList i = new TaskCombatList();

    public static TaskCombatList get() {
        return i;
    }

    @Override
    public long getDelayMillis() {
        return 2000L;
    }

    private Set<UUID> playersInCombat = new HashSet<>();

    @Override
    public void invoke(long l) {
        if (!IntegrationCombatTagPlus.get().isActive()) {
            return;
        }

        List<UUID> toRemove = new ArrayList<>();
        for (UUID uuid : playersInCombat) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                playersInCombat.remove(uuid);
                continue;
            }
            if (!IntegrationCombatTagPlus.get().getEngine().isPlayerInCombat(uuid))
                toRemove.add(uuid);
        }
        toRemove.forEach(playersInCombat::remove);
    }


    public void addPlayerToCombat(Player player) {
        playersInCombat.add(player.getUniqueId());
    }

    public void removePlayerFromCombat(Player player) {
        playersInCombat.remove(player.getUniqueId());
    }

    public void addButCheck(Player player) {
        if (IntegrationCombatTagPlus.get().getEngine().isPlayerInCombat(player.getUniqueId()))
            playersInCombat.add(player.getUniqueId());
    }

    public List<Player> getPlayersInCombat() {
        List<Player> ret = new ArrayList<>();
        playersInCombat.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) ret.add(player);
        });
        return ret;
    }


}
