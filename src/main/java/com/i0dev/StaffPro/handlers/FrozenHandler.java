package com.i0dev.StaffPro.handlers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.MessageConfig;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.managers.SPlayerManager;
import com.i0dev.StaffPro.managers.VanishManager;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractListener;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class FrozenHandler extends AbstractListener {
    public FrozenHandler(Heart heart) {
        super(heart);
    }

    public BukkitTask task;

    @Override
    public void initialize() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(getHeart(), taskRunParticles, 60L, 60L);
    }

    @Override
    public void deinitialize() {
        if (task != null) task.cancel();
    }

    /*
    This task will spawn particles around frozen players.
     */

    public Runnable taskRunParticles = () -> {
        getHeart().getSPlayerManager().getCache()
                .stream()
                .filter(SPlayer::isFrozen)
                .filter(sPlayer -> {
                    Player player = Bukkit.getPlayer(sPlayer.getUuid());
                    return player != null;
                })
                .forEach(sPlayer -> {
                    Player player = Bukkit.getPlayer(sPlayer.getUuid());
                    heart.msgManager().msg(player, heart.msg().getFrozenMessage());
                    double size = 0.75;
                    for (int i = 0; i < 360; i = i + 8) {
                        double angle = (i * Math.PI / 180);
                        double x = size * Math.cos(angle);
                        double z = size * Math.sin(angle);
                        Location loc = player.getLocation().clone().add(0 + x, 0, 0 + z);
                        if (!player.getWorld().getName().equalsIgnoreCase(loc.getWorld().getName())) {
                            break;
                        }
                        player.getLocation().getWorld().playEffect(loc, Effect.SNOWBALL_BREAK, 10);
                    }
                });
    };

    /*
    these methods inform staff about players that log online/offline while frozen.
     */

    @EventHandler
    public void onFrozenJoin(PlayerJoinEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.getSPlayerManager().messageAllStaff(heart.msg().getFrozenLoginAnnounce().replace("{player}", e.getPlayer().getName()));
        heart.msgManager().msg(e.getPlayer(), heart.msg().getFrozenLoginMessage());
    }


    @EventHandler
    public void onFrozenKick(PlayerKickEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.getSPlayerManager().messageAllStaff(heart.msg().getFrozenLogoutAnnounce().replace("{player}", e.getPlayer().getName()));
    }

    @EventHandler
    public void onFrozenLeave(PlayerQuitEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.getSPlayerManager().messageAllStaff(heart.msg().getFrozenLogoutAnnounce().replace("{player}", e.getPlayer().getName()));
    }

    /*
    The following methods are to prevent player movement or interactions while frozen.
     */

    @EventHandler
    public void onEnderPearl(ProjectileLaunchEvent e) {
        Player player = (Player) e.getEntity().getShooter();
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(player.getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getEntity(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "ender pearl"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getZ() != e.getTo().getZ()) {
            heart.msgManager().msg(e.getPlayer(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "move"));
            e.setTo(e.getFrom());
        }
    }

    @EventHandler
    public void onBow(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getEntity().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getEntity(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "interact"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.LEFT_CLICK_AIR)) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getPlayer(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "interact"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getPlayer(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "place blocks"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getPlayer(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "break blocks"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (!(e.getDamager() instanceof Player)) return;
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getDamager().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getDamager(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "attack others"));
        e.setCancelled(true);
    }


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getPlayer(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "drop items"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryMove(InventoryClickEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getWhoClicked().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getWhoClicked(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "click in inventory"));
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getWhoClicked().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        heart.msgManager().msg(e.getWhoClicked(), heart.msg().getCantDoWhileFrozen(), new MessageManager.Pair<>("{action}", "drag in inventory"));
        e.setCancelled(true);
    }


    @EventHandler
    public void commandSay(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String command = e.getMessage().split(" ")[0].toLowerCase();
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        if (!heart.cnf().getFrozenCommandWhitelist().contains(command)) {
            e.setCancelled(true);
            heart.msgManager().msg(e.getPlayer(), heart.msg().getCantRunCmdWhileFrozen(), new MessageManager.Pair<>("{command}", e.getMessage()));
        }
    }


    /*
    Prevent players from attacking the frozen player, or to take damage in any way, or hunger.
     */

    @EventHandler
    public void onAnyDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getEntity().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getEntity().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isFrozen()) return;
        e.setCancelled(true);
    }
}
