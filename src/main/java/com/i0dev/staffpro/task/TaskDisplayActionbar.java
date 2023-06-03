package com.i0dev.staffpro.task;

import com.i0dev.staffpro.entity.MConf;
import com.i0dev.staffpro.entity.MPlayer;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.ModuloRepeatTask;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TaskDisplayActionbar extends ModuloRepeatTask {

    private final static TaskDisplayActionbar i = new TaskDisplayActionbar();

    public static TaskDisplayActionbar get() {
        return i;
    }

    @Override
    public long getDelayMillis() {
        return 2000L;
    }

    MConf cnf;

    @Override
    public void invoke(long l) {
        cnf = MConf.get();
        for (Player player : Bukkit.getOnlinePlayers()) {
            displayActionBar(player);
        }
    }

    public void displayActionBar(Player player) {
        List<String> title = new ArrayList<>();
        MPlayer mPlayer = MPlayer.get(player);
        if (mPlayer.isVanished()) {
            title.add(cnf.actionbarVanished);
        }
        if (mPlayer.isModmode()) {
            title.add(cnf.actionbarModMode);
        }
        if (com.massivecraft.factions.entity.MPlayer.get(player).isOverriding()) {
            title.add(cnf.actionbarOverriding);
        }
        if (mPlayer.isFrozen()) {
            title.add(cnf.actionbarFrozen);
            displayFrozenParticles(player);
        }

        StringBuilder titleString = new StringBuilder();
        for (int i = 0; i < title.size(); i++) {
            titleString.append(title.get(i)).append(i == title.size() - 1 ? "" : cnf.actionBarDelimiter);
        }

        if (title.size() > 0) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Utils.color(titleString.toString())));
        }
    }


    public void displayFrozenParticles(Player player) {
        Location location = player.getLocation();
        double size = 0.75;
        // message frozen msg
        for (int i = 0; i < 360; i = i + 8) {
            double angle = (i * Math.PI / 180);
            double x = size * Math.cos(angle);
            double z = size * Math.sin(angle);
            Location loc = location.clone().add(0 + x, 0, 0 + z);
            if (!player.getWorld().getName().equalsIgnoreCase(loc.getWorld().getName())) {
                break;
            }
            location.getWorld().spawnParticle(Particle.SNOWBALL, loc, 1);
        }
        location.getWorld().playSound(player.getLocation(), Sound.BLOCK_SNOW_BREAK, 1, 1);
    }

}
