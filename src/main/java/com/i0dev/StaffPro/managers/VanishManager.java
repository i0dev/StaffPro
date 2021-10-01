package com.i0dev.StaffPro.managers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.MessageConfig;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class VanishManager extends AbstractManager {
    public VanishManager(Heart heart) {
        super(heart);
    }


    public void vanish(Player who) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(who.getUniqueId());
        sPlayer.setVanished(true);
        heart.getSPlayerManager().add(sPlayer);
        Bukkit.getOnlinePlayers().stream().filter(player -> !player.hasPermission("staffpro.staff") && !player.isOp()).forEach(player -> player.hidePlayer(who));
    }

    public void unvanish(Player who) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(who.getUniqueId());
        sPlayer.setVanished(false);
        heart.getSPlayerManager().add(sPlayer);
        Bukkit.getOnlinePlayers().stream().filter(player -> !player.hasPermission("staffpro.staff") && !player.isOp()).forEach(player -> player.showPlayer(who));
    }

}
