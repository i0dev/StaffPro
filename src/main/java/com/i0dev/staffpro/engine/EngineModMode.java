package com.i0dev.staffpro.engine;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.StaffProPlugin;
import com.i0dev.staffpro.cmd.*;
import com.i0dev.staffpro.entity.MPlayer;
import com.i0dev.staffpro.task.TaskDisplayActionbar;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class EngineModMode extends Engine {

    private static EngineModMode i = new EngineModMode();

    public static EngineModMode get() {
        return i;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null) return;
        if (item.getType().equals(Material.AIR)) return;
        if (!item.hasItemMeta()) return;
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR) && !e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        MPlayer mPlayerSender = MPlayer.get(e.getPlayer());
        if (!mPlayerSender.isModmode()) return;
        e.setCancelled(true);
        NamespacedKey key = new NamespacedKey(StaffProPlugin.get(), "staffpro_modmode_item");
        String value = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (value == null) return;
        switch (value) {
            case "combatlist" -> CmdCombatList.get().execute(e.getPlayer(), MUtil.list());
            case "randomteleport" -> CmdRandomTeleport.get().execute(e.getPlayer(), MUtil.list());
            case "vanish" -> {
                CmdVanish.get().execute(e.getPlayer(), MUtil.list());
                MPlayer.get(e.getPlayer()).setModModeVanishItem();
            }
            case "unvanish" -> {
                CmdUnVanish.get().execute(e.getPlayer(), MUtil.list());
                MPlayer.get(e.getPlayer()).setModModeVanishItem();
            }
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (!(entity instanceof Player)) return;
        ItemStack item = e.getPlayer().getItemInUse();
        if (item == null) return;
        if (item.getType().equals(Material.AIR)) return;
        if (!item.hasItemMeta()) return;
        MPlayer mPlayerSender = MPlayer.get(e.getPlayer());
        if (!mPlayerSender.isModmode()) return;
        e.setCancelled(true);
        NamespacedKey key = new NamespacedKey(StaffProPlugin.get(), "staffpro_modmode_item");
        String value = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (value == null) return;
        MPlayer mPlayer = MPlayer.get(e.getRightClicked());

        switch (value) {
            case "freeze" -> {
                if (mPlayer.isFrozen()) CmdUnFreeze.get().execute(e.getPlayer(), MUtil.list(mPlayer.getName()));
                else CmdFreeze.get().execute(e.getPlayer(), MUtil.list(mPlayer.getName()));
            }
            case "examine" -> CmdExamine.get().execute(e.getPlayer(), MUtil.list(mPlayer.getName()));
            case "strip" -> CmdStrip.get().execute(e.getPlayer(), MUtil.list(mPlayer.getName()));
        }
    }


    // These methods will prevent staff members in modmode from doing certain actions.
    @EventHandler
    public void onModModeBlockPlace(BlockPlaceEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModeModeInventory(InventoryClickEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getWhoClicked());
        if (!mPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModeCreativeEvent(InventoryCreativeEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getWhoClicked());
        if (!mPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModePickupItem(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        MPlayer mPlayer = MPlayer.get(e.getEntity());
        if (!mPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModeDropItem(PlayerDropItemEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModeDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        MPlayer mPlayer = MPlayer.get(e.getEntity());
        if (!mPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    // Prevents non-staff members from logging in while modmode still
    @EventHandler
    public void onModModeJoin(PlayerJoinEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isModmode()) return;
        if (!Perm.STAFF.has(e.getPlayer())) {
            mPlayer.disableModMode();
        }
    }


}
