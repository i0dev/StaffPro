package com.i0dev.StaffPro.managers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.hooks.CombatTagPlusHook;
import com.i0dev.StaffPro.hooks.MCoreFactionsHook;
import com.i0dev.StaffPro.templates.AbstractManager;
import com.i0dev.StaffPro.utility.Utility;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class CombatManager extends AbstractManager {
    public CombatManager(Heart heart) {
        super(heart);
    }

    Set<UUID> playersInCombat;
    BukkitTask task;

    @Override
    public void initialize() {
        playersInCombat = new HashSet<>();
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(heart, taskCheckCombatStatus, 20L, 20L);
    }

    @Override
    public void deinitialize() {
        if (playersInCombat != null) playersInCombat.clear();
        playersInCombat = null;
        if (task != null) task.cancel();
        task = null;
    }


    public void addPlayerToCombat(Player player) {
        playersInCombat.add(player.getUniqueId());
    }

    public void removePlayerFromCombat(Player player) {
        playersInCombat.remove(player.getUniqueId());
    }

    public List<Player> getPlayersInCombat() {
        List<Player> ret = new ArrayList<>();
        playersInCombat.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) ret.add(player);
        });
        return ret;
    }

    Runnable taskCheckCombatStatus = () -> {
        List<UUID> toRemove = new ArrayList<>();
        for (UUID uuid : playersInCombat) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) {
                playersInCombat.remove(uuid);
                continue;
            }
            if (!CombatTagPlusHook.isPlayerInCombat(uuid))
                toRemove.add(uuid);
        }
        toRemove.forEach(playersInCombat::remove);
    };

    public void addButCheck(Player player) {
        if (CombatTagPlusHook.isPlayerInCombat(player.getUniqueId()))
            playersInCombat.add(player.getUniqueId());
    }

    public Inventory getCombatGUI(UUID observer) {
        Inventory inv = Bukkit.createInventory(new CombatHolder(), Utility.getMaxInventorySize(playersInCombat.size()), Utility.color(heart.cnf().getCombatGUITitle()));
        for (int i = 0; i < playersInCombat.size(); i++) {
            UUID uuid = playersInCombat.toArray(new UUID[0])[i];
            Player player = Bukkit.getPlayer(uuid);
            if (player == null) continue;
            ItemStack item = Utility.itemFromBase64(Utility.getDataFromName(player.getName()));
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            meta.setDisplayName(Utility.color(heart.cnf().getCombatGUIHeadName().replace("{displayName}", player.getDisplayName())));

            List<String> lore = new ArrayList<>();
            heart.cnf().getCombatGuiHeadLore().forEach(s -> {
                lore.add(heart.msgManager().color(s
                        .replace("{faction}", MCoreFactionsHook.getFacName(observer, player.getUniqueId()))
                        .replace("{taggedName}", CombatTagPlusHook.getTaggedWith(player.getUniqueId()))
                ));
            });

            meta.setLore(lore);
            item.setItemMeta(meta);

            NBTItem nbtItem = new NBTItem(item);
            nbtItem.setString("combat-list-uuid", player.getUniqueId().toString());
            item = nbtItem.getItem();
            inv.setItem(i, item);


        }
        return inv;
    }


    public static class CombatHolder implements InventoryHolder {
        @Override
        public Inventory getInventory() {
            return null;
        }
    }

}
