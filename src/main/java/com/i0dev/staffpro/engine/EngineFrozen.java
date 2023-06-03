package com.i0dev.staffpro.engine;

import com.i0dev.staffpro.entity.MConf;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.entity.MPlayer;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.Engine;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;

public class EngineFrozen extends Engine {

    private static EngineFrozen i = new EngineFrozen();

    public static EngineFrozen get() {
        return i;
    }

    // these methods inform staff about players that log online/offline while frozen.

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFrozenJoin(PlayerJoinEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().playerFrozenLoginAnnouncement, new Pair<>("%player%", mPlayer.getName())));
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onFrozenKick(PlayerKickEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().playerFrozenLogoutAnnouncement, new Pair<>("%player%", mPlayer.getName())));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onFrozenLeave(PlayerQuitEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().playerFrozenLogoutAnnouncement, new Pair<>("%player%", mPlayer.getName())));
    }

    // The following methods are to prevent player movement or interactions while frozen.

    @EventHandler
    public void onEnderPearl(ProjectileLaunchEvent e) {
        Player player = (Player) e.getEntity().getShooter();
        MPlayer mPlayer = MPlayer.get(player);
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "throw an ender pearl")));
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
            mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "move")));
            e.setTo(e.getFrom());
        }
    }

    @EventHandler
    public void onBow(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        MPlayer mPlayer = MPlayer.get(e.getEntity());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "shoot a bow")));
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_AIR)) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "interact")));
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "place blocks")));
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "break blocks")));
        e.setCancelled(true);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        MPlayer mPlayer = MPlayer.get(e.getDamager());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "attack")));
        e.setCancelled(true);
    }


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "drop items")));
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryMove(InventoryClickEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getWhoClicked());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "move items in inventory")));
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getWhoClicked());
        if (!mPlayer.isFrozen()) return;
        mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "move items in inventory")));
        e.setCancelled(true);
    }


    @EventHandler
    public void commandSay(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String command = e.getMessage().split(" ")[0].toLowerCase();
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        if (!MConf.get().frozenCommandWhitelist.contains(command)) {
            e.setCancelled(true);
            mPlayer.msg(Utils.prefixAndColor(MLang.get().cantDoThatWhileFrozen, new Pair<>("%action%", "run '" + command + "' command")));
        }
    }


    /*
    Prevent players from attacking the frozen player, or to take damage in any way, or hunger.
     */

    @EventHandler
    public void onAnyDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        MPlayer mPlayer = MPlayer.get(e.getEntity());
        if (!mPlayer.isFrozen()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getEntity());
        if (!mPlayer.isFrozen()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent e) {
        MPlayer mPlayer = MPlayer.get(e.getPlayer());
        if (!mPlayer.isFrozen()) return;
        e.setCancelled(true);
    }

}
