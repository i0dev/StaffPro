package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.templates.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdRTP extends AbstractCommand {

    public CmdRTP(Heart heart, String command) {
        super(heart, command);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.rtp.cmd")) {
            heart.msgManager().msg(sender, heart.msg().getNoPermission());
            return;
        }
        if (sender instanceof ConsoleCommandSender) {
            getHeart().msgManager().msg(sender, heart.msg().getConsoleError());
            return;
        }

        if (heart.getMiscManager().getRealPlayers().size() == 0) {
            heart.msgManager().msg(sender, heart.msg().getNoRealPlayers());
            return;
        }

        Player teleportedTo = heart.getMiscManager().randomTeleport(((Player) sender));
        heart.msgManager().msg(sender, heart.msg().getRandomTeleported(), new MessageManager.Pair<>("{player}", teleportedTo.getName()));
    }


    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return blank;
    }
}
