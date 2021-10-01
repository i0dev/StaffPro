package com.i0dev.StaffPro.handlers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.MessageConfig;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.managers.SPlayerManager;
import com.i0dev.StaffPro.managers.VanishManager;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class VanishHandler extends AbstractListener {
    public VanishHandler(Heart heart) {
        super(heart);
    }

    /*
    This method makes sure the staff members are vanished when someone new joins the server.
     */
    @EventHandler
    public void onNormalJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission("staffpro.staff")) return;
        if (e.getPlayer().isOp()) return;

        heart.getSPlayerManager().getCache().stream().filter(SPlayer::isVanished).forEach(sPlayer -> {
            Player player = Bukkit.getPlayer(sPlayer.getUuid());
            if (player == null) return;
            e.getPlayer().hidePlayer(player);
        });
    }

    /*
    These methods prevent vanished staff members from picking up or dropping items.
     */
    @EventHandler
    public void onVanishPickupItem(PlayerPickupItemEvent e) {
        if (heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId()).isVanished()) e.setCancelled(true);
    }

//    @EventHandler
//    public void onVanishPickupArrow(PlayerPickupArrowEvent e) {
//        if (sManager.getSPlayer(e.getPlayer().getUniqueId()).isVanished()) e.setCancelled(true);
//    }

    @EventHandler
    public void onVanishDropItem(PlayerDropItemEvent e) {
        if (heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId()).isVanished()) e.setCancelled(true);
    }

    /*
    This method is to insure when a player logs in when they are in the vanished state, that they have the correct permission, and it properly vanishes them.
     */

    @EventHandler
    public void onVanishJoin(PlayerJoinEvent e) {
        if (!heart.getSPlayerManager().getSPlayer(e.getPlayer().getUniqueId()).isVanished()) return;
        if (!e.getPlayer().hasPermission("staffpro.vanish.login")) {
            heart.getVanishManager().unvanish(e.getPlayer());
            heart.msgManager().msg(e.getPlayer(), heart.msg().getNoLongerVanished());
            heart.getSPlayerManager().messageAllStaff(heart.msg().getNoLongerVanishedAnnounce().replace("{player}", e.getPlayer().getName()), e.getPlayer());
            return;
        }
        heart.getVanishManager().vanish(e.getPlayer());
        heart.msgManager().msg(e.getPlayer(), heart.msg().getLoggedInVanish());
    }

}
