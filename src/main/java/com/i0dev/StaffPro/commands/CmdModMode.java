package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.FreezeManager;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdModMode extends AbstractCommand {

    public CmdModMode(Heart heart, String command) {
        super(heart, command);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.modmode.cmd")) return;
        Player toModMode;
        if (args.length == 0) {
            if (sender instanceof ConsoleCommandSender) {
                heart.msgManager().msg(sender, heart.msg().getConsoleError());
                return;
            }
            toModMode = ((Player) sender);
        } else {
            toModMode = heart.msgManager().getPlayer(args[0]);
            if (toModMode == null) {
                heart.msgManager().msg(sender, heart.msg().getCantFindPlayer(), new MessageManager.Pair<>("{player}", args[0]));
                return;
            }
        }
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(toModMode.getUniqueId());

        if (sPlayer.isModmode()) {
            heart.getModModeManager().unmodmode(toModMode);
            heart.msgManager().msg(toModMode, heart.msg().getNoLongerModMode());
            heart.getSPlayerManager().messageAllStaff(heart.msg().getNoLongerModModeAnnounce().replace("{player}", toModMode.getName()), toModMode);
            return;
        }

        heart.getModModeManager().modmode(toModMode);
        heart.msgManager().msg(toModMode, heart.msg().getNowModMode());
        heart.getSPlayerManager().messageAllStaff(heart.msg().getNowModModeAnnounce().replace("{player}", toModMode.getName()), toModMode);
    }


    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return null;
        return blank;
    }
}
