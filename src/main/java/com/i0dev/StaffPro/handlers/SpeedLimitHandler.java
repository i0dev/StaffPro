package com.i0dev.StaffPro.handlers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.templates.AbstractListener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpeedLimitHandler extends AbstractListener {
    public SpeedLimitHandler(Heart heart) {
        super(heart);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMove(final PlayerMoveEvent event) {
        if (!heart.cnf().isPlayerSpeedLimiterEnabled()) return;
        if (event.getPlayer().hasPermission("staffpro.staff")) return;
        if (!event.getPlayer().isInsideVehicle()) {
            Location to = event.getTo();
            Location from = event.getFrom();
            if (to.getBlockX() != from.getBlockX() || to.getBlockZ() != from.getBlockZ()) {
                final double distance = Math.hypot(from.getX() - to.getX(), from.getZ() - to.getZ());
                if (distance > (event.getPlayer().isFlying() ? heart.cnf().getPlayerSpeedLimiterMaxFlySpeed() : heart.cnf().getPlayerSpeedLimiterMaxMoveSpeed())) {
                    event.setTo(from);
                }
            }
        }
    }


}
