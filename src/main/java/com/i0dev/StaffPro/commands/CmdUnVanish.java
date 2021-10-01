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
import java.util.List;

public class CmdUnVanish extends AbstractCommand {


    public CmdUnVanish(Heart heart, String command) {
        super(heart, command);
    }


    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.unvanish.cmd")) return;
        Player toUnVanish;
        if (args.length == 0) {
            if (sender instanceof ConsoleCommandSender) {
                heart.msgManager().msg(sender, heart.msg().getConsoleError());
                return;
            }
            toUnVanish = ((Player) sender);
        } else {
            toUnVanish = heart.msgManager().getPlayer(args[0]);
            if (toUnVanish == null) {
                heart.msgManager().msg(sender, heart.msg().getCantFindPlayer(), new MessageManager.Pair<>("{player}", args[0]));
                return;
            }
        }

        heart.getVanishManager().unvanish(toUnVanish);
        heart.msgManager().msg(toUnVanish, heart.msg().getNoLongerVanished());
        heart.getSPlayerManager().messageAllStaff(heart.msg().getNoLongerVanishedAnnounce().replace("{player}", toUnVanish.getName()), toUnVanish);
    }


    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return null;
        return blank;
    }
}
