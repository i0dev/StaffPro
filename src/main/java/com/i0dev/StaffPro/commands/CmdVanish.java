package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.MessageConfig;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.managers.SPlayerManager;
import com.i0dev.StaffPro.managers.VanishManager;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdVanish extends AbstractCommand {


    public CmdVanish(Heart heart, String command) {
        super(heart, command);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.vanish.cmd")) return;
        Player toVanish;
        if (args.length == 0) {
            if (sender instanceof ConsoleCommandSender) {
                heart.msgManager().msg(sender, heart.msg().getConsoleError());
                return;
            }
            toVanish = ((Player) sender);
        } else {
            toVanish = heart.msgManager().getPlayer(args[0]);
            if (toVanish == null) {
                heart.msgManager().msg(sender, heart.msg().getCantFindPlayer(), new MessageManager.Pair<>("{player}", args[0]));
                return;
            }
        }
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(toVanish.getUniqueId());

        if (sPlayer.isVanished()) {
            heart.getVanishManager().unvanish(toVanish);
            heart.msgManager().msg(toVanish, heart.msg().getNoLongerVanished());
            heart.getSPlayerManager().messageAllStaff(heart.msg().getNoLongerVanishedAnnounce().replace("{player}", toVanish.getName()), toVanish);
            return;
        }

        heart.getVanishManager().vanish(toVanish);
        heart.msgManager().msg(toVanish, heart.msg().getNowVanished());
        heart.getSPlayerManager().messageAllStaff(heart.msg().getNowVanishedAnnounce().replace("{player}", toVanish.getName()), toVanish);
    }

    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return null;
        return blank;
    }
}
