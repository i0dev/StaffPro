package com.i0dev.StaffPro.handlers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.MessageManager;
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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitTask;

public class SoftFrozenHandler extends AbstractListener {
    public SoftFrozenHandler(Heart heart) {
        super(heart);
    }

    public BukkitTask task;

    @Override
    public void initialize() {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(getHeart(), taskRunParticles, 60L, 60L * 8L);
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
                .filter(SPlayer::isSoft_frozen)
                .filter(sPlayer -> {
                    Player player = Bukkit.getPlayer(sPlayer.getUuid());
                    return player != null;
                })
                .forEach(sPlayer -> {
                    Player player = Bukkit.getPlayer(sPlayer.getUuid());
                    heart.msgManager().msg(player, heart.msg().getSoftFrozenMessage());
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
        if (!sPlayer.isSoft_frozen()) return;
        heart.getSPlayerManager().messageAllStaff(heart.msg().getSoftFrozenLoginAnnounce().replace("{player}", e.getPlayer().getName()));
        heart.msgManager().msg(e.getPlayer(), heart.msg().getSoftFrozenLoginMessage());
    }


    @EventHandler
    public void onFrozenKick(PlayerKickEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isSoft_frozen()) return;
        heart.getSPlayerManager().messageAllStaff(heart.msg().getSoftFrozenLogoutAnnounce().replace("{player}", e.getPlayer().getName()));
    }

    @EventHandler
    public void onFrozenLeave(PlayerQuitEvent e) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId());
        if (!sPlayer.isSoft_frozen()) return;
        heart.getSPlayerManager().messageAllStaff(heart.msg().getSoftFrozenLogoutAnnounce().replace("{player}", e.getPlayer().getName()));
    }
}
