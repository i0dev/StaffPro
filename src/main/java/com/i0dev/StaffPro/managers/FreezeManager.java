package com.i0dev.StaffPro.managers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.MessageConfig;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
public class FreezeManager extends AbstractManager {
    public FreezeManager(Heart heart) {
        super(heart);
    }

    public void freeze(Player player) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(player.getUniqueId());
        sPlayer.setFrozen(true);
        heart.getSPlayerManager().add(sPlayer);
    }


    public void unfreeze(Player player) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(player.getUniqueId());
        sPlayer.setFrozen(false);
        heart.getSPlayerManager().add(sPlayer);
    }

    public void softFreeze(Player player) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(player.getUniqueId());
        sPlayer.setSoft_frozen(true);
        heart.getSPlayerManager().add(sPlayer);
    }


    public void unSoftFreeze(Player player) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(player.getUniqueId());
        sPlayer.setSoft_frozen(false);
        heart.getSPlayerManager().add(sPlayer);
    }

}
