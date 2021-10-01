package com.i0dev.StaffPro.handlers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.FreezeManager;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.managers.MiscManager;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractListener;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class ModModeHandler extends AbstractListener {
    public ModModeHandler(Heart heart) {
        super(heart);
    }

    /*
    These methods will handle all modmode requests, when clicking a modmode item.
     */


    @EventHandler
    public void onModModeFreeze(PlayerInteractEntityEvent e) {
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (!(e.getRightClicked() instanceof Player)) return;
        if (itemInHand == null || Material.AIR.equals(itemInHand.getType())) return;
        NBTItem nbtItem = new NBTItem(itemInHand);
        if (!"freeze".equalsIgnoreCase(nbtItem.getString("staffpro_modmode_item"))) return;

        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getRightClicked().getUniqueId());
        if (!sPlayer.isFrozen()) {
            if (!e.getPlayer().hasPermission("staffpro.freeze.modmode")) {
                heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
                return;
            }
            if (e.getRightClicked().hasPermission("staffpro.freeze.bypass")) {
                heart.msgManager().msg(e.getPlayer(), heart.msg().getYouCantFreeze(), new MessageManager.Pair<>("{player}", e.getRightClicked().getName()));
                return;
            }

            heart.getManager(FreezeManager.class).freeze(((Player) e.getRightClicked()));
            heart.msgManager().msg(e.getPlayer(), heart.msg().getYouFroze(), new MessageManager.Pair<>("{player}", e.getRightClicked().getName()));
            heart.msgManager().msg(e.getRightClicked(), heart.msg().getYouBeenFrozen(), new MessageManager.Pair<>("{player}", e.getPlayer().getName()));
            heart.getSPlayerManager().messageAllStaff(heart.msg().getFrozeAnnounce().replace("{player}", e.getRightClicked().getName()).replace("{staff}", e.getPlayer().getName()), e.getPlayer());


        } else {
            if (!e.getPlayer().hasPermission("staffpro.unfreeze.modmode")) {
                heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
                return;
            }

            heart.getManager(FreezeManager.class).unfreeze(((Player) e.getRightClicked()));
            heart.msgManager().msg(e.getPlayer(), heart.msg().getYouUnFroze(), new MessageManager.Pair<>("{player}", e.getRightClicked().getName()));
            heart.msgManager().msg(e.getRightClicked(), heart.msg().getYouBeenUnFrozen(), new MessageManager.Pair<>("{player}", e.getPlayer().getName()));
            heart.getSPlayerManager().messageAllStaff(heart.msg().getUnfrozeAnnounce().replace("{player}", e.getRightClicked().getName()).replace("{staff}", e.getPlayer().getName()), e.getPlayer());

        }
    }

    @EventHandler
    public void onCombatListClick(PlayerInteractEvent e) {
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        if (itemInHand == null || Material.AIR.equals(itemInHand.getType())) return;
        NBTItem nbtItem = new NBTItem(itemInHand);
        if (!"combatList".equalsIgnoreCase(nbtItem.getString("staffpro_modmode_item"))) return;
        if (!e.getPlayer().hasPermission("staffpro.combatList.modmode")) {
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
            return;
        }

        heart.msgManager().msg(e.getPlayer(), "&cThis is not enabled yet. It's coming soon!");

    }

    @EventHandler
    public void onVanishClick(PlayerInteractEvent e) {
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        if (itemInHand == null || Material.AIR.equals(itemInHand.getType())) return;
        NBTItem nbtItem = new NBTItem(itemInHand);
        if (!"vanish".equalsIgnoreCase(nbtItem.getString("staffpro_modmode_item")) && !"unVanish".equalsIgnoreCase(nbtItem.getString("staffpro_modmode_item")))
            return;
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());

        if (!sPlayer.isVanished()) {
            if (!e.getPlayer().hasPermission("staffpro.vanish.modmode")) {
                heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
                return;
            }

            heart.getVanishManager().vanish(e.getPlayer());
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNowVanished());
            heart.getSPlayerManager().messageAllStaff(heart.msg().getNowVanishedAnnounce().replace("{player}", e.getPlayer().getName()), e.getPlayer());
            heart.getModModeManager().setModModeInventory(e.getPlayer());
        } else {
            if (!e.getPlayer().hasPermission("staffpro.unvanish.modmode")) {
                heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
                return;
            }
            heart.getVanishManager().unvanish(e.getPlayer());
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoLongerVanished());
            heart.getSPlayerManager().messageAllStaff(heart.msg().getNoLongerVanishedAnnounce().replace("{player}", e.getPlayer().getName()), e.getPlayer());
            heart.getModModeManager().setModModeInventory(e.getPlayer());

        }

    }

    @EventHandler
    public void onExamineClick(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) return;
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (itemInHand == null || Material.AIR.equals(itemInHand.getType())) return;
        NBTItem nbtItem = new NBTItem(itemInHand);
        if (!"examine".equalsIgnoreCase(nbtItem.getString("staffpro_modmode_item"))) return;
        if (!e.getPlayer().hasPermission("staffpro.examine.modmode")) {
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
            return;
        }
        if (e.getRightClicked().hasPermission("staffpro.examine.bypass")) {
            heart.msgManager().msg(e.getPlayer(), heart.msg().getYouCantExamine(), new MessageManager.Pair<>("{player}", e.getRightClicked().getName()));
            return;
        }
        heart.msgManager().msg(e.getPlayer(), heart.msg().getNowExamining(), new MessageManager.Pair<>("{player}", e.getRightClicked().getName()));
        heart.getMiscManager().startExamining(e.getPlayer(), ((Player) e.getRightClicked()));
    }

    @EventHandler
    public void onStripClick(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) return;
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (itemInHand == null || Material.AIR.equals(itemInHand.getType())) return;
        NBTItem nbtItem = new NBTItem(itemInHand);
        if (!"strip".equalsIgnoreCase(nbtItem.getString("staffpro_modmode_item"))) return;
        if (!e.getPlayer().hasPermission("staffpro.strip.modmode")) {
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
            return;
        }
        heart.getMiscManager().strip(((Player) e.getRightClicked()));
        heart.msgManager().msg(e.getPlayer(), heart.msg().getYouStripped(), new MessageManager.Pair<>("{player}", e.getRightClicked().getName()));
        heart.msgManager().msg(e.getRightClicked(), heart.msg().getYouWereStripped());
    }

    @EventHandler
    public void onRandomTPClick(PlayerInteractEvent e) {
        ItemStack itemInHand = e.getPlayer().getItemInHand();
        if (!e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        if (itemInHand == null || Material.AIR.equals(itemInHand.getType())) return;
        NBTItem nbtItem = new NBTItem(itemInHand);
        if (!"randomTP".equalsIgnoreCase(nbtItem.getString("staffpro_modmode_item"))) return;
        if (!e.getPlayer().hasPermission("staffpro.rtp.modmode")) {
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoPermission());
            return;
        }
        if (heart.getMiscManager().getRealPlayers().size() == 0) {
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoRealPlayers());
            return;
        }
        Player teleportedTo = heart.getMiscManager().randomTeleport(e.getPlayer());
        heart.msgManager().msg(e.getPlayer(), heart.msg().getRandomTeleported(), new MessageManager.Pair<>("{player}", teleportedTo.getName()));
    }

    /*
    These methods will prevent staff members in modmode from doing certain actions.
     */

    @EventHandler
    public void onModModeBlockPlace(BlockPlaceEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModeModeInventory(InventoryClickEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getWhoClicked().getUniqueId());
        if (!sPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModeCreativeEvent(InventoryCreativeEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getWhoClicked().getUniqueId());
        if (!sPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModePickupItem(PlayerPickupItemEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModeDropItem(PlayerDropItemEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onModModeDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getEntity().getUniqueId());
        if (!sPlayer.isModmode()) return;
        e.setCancelled(true);
    }

    /*
    These methods will inform other staff if a player logs in while in modmode.
     */

    @EventHandler
    public void onModModeJoin(PlayerJoinEvent e) {
        if (!heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId()).isModmode()) return;
        if (!e.getPlayer().hasPermission("staffpro.modmode.login")) {
            heart.getModModeManager().unmodmode(e.getPlayer());
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoLongerModMode());
            heart.getSPlayerManager().messageAllStaff(heart.msg().getNoLongerModModeAnnounce().replace("{player}", e.getPlayer().getName()), e.getPlayer());
            return;
        }
        heart.msgManager().msg(e.getPlayer(), heart.msg().getLoggedInModMode());
    }
}
