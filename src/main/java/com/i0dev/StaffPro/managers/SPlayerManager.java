package com.i0dev.StaffPro.managers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.GeneralConfig;
import com.i0dev.StaffPro.config.SPlayerStorage;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractManager;
import com.i0dev.StaffPro.utility.ConfigUtil;
import com.i0dev.StaffPro.utility.Utility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;


@Getter
@Setter
public class SPlayerManager extends AbstractManager {
    public SPlayerManager(Heart heart) {
        super(heart);
    }

    public Set<SPlayer> cache;
    BukkitTask task;

    @Override
    public void initialize() {
        setCache(heart.getSPlayerStorage().getStorage());
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(getHeart(), taskSendActionBar, 20L, 20L);
    }

    @Override
    public void deinitialize() {
        save();
        if (task != null) task.cancel();
        if (cache != null) cache.clear();
    }

    public void save() {
        List<SPlayer> toRemove = new ArrayList<>();
        cache.stream().filter(sPlayer -> !sPlayer.isFrozen() && !sPlayer.isModmode() && !sPlayer.isVanished()).forEach(toRemove::add);
        toRemove.forEach(cache::remove);

        heart.getSPlayerStorage().setStorage(cache);
        ConfigUtil.save(heart.getSPlayerStorage(), heart.getSPlayerStorage().getPath());
    }


    public SPlayer getSPlayer(UUID uuid) {
        SPlayer ret = cache.stream().filter(sPlayer -> sPlayer.getUuid().equals(uuid)).findFirst().orElse(new SPlayer(uuid));
        cache.add(ret);
        return ret;
    }

    public void add(SPlayer sPlayer) {
        if (contains(sPlayer)) cache.remove(sPlayer);
        cache.add(sPlayer);
    }

    public boolean contains(SPlayer sPlayer) {
        return cache.contains(sPlayer);
    }

    public void messageAllStaff(String msg) {
        messageAllStaff(msg, null);
    }

    public void messageAllStaff(String msg, CommandSender sender) {
        if (sender instanceof Player)
            messageAllStaff(msg, ((Player) sender));
        else
            messageAllStaff(msg, null);
    }

    public void messageAllStaff(String msg, Player exclude) {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("staffpro.staff"))
                .forEach(player -> {
                    if ((exclude != null && player.getUniqueId().equals(exclude.getUniqueId()))) return;
                    heart.msgManager().msg(player, msg);
                });
    }

    Runnable taskSendActionBar = () -> {
        getCache().forEach(sPlayer -> {
            Player player = Bukkit.getPlayer(sPlayer.getUuid());
            if (player == null) return;
            if (sPlayer.isVanished() && sPlayer.isModmode())
                Utility.sendActionText(player, heart.cnf().getBothActionBarText());
            else if (sPlayer.isModmode()) Utility.sendActionText(player, heart.cnf().getModModeActionBarText());
            else if (sPlayer.isVanished()) Utility.sendActionText(player, heart.cnf().getVanishActionBarText());
            else if (sPlayer.isFrozen()) Utility.sendActionText(player, heart.cnf().getFrozenActionBarText());
        });
    };

}
