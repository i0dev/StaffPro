package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.FreezeManager;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.templates.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdExamine extends AbstractCommand {

    public CmdExamine(Heart heart, String command) {
        super(heart, command);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.examine.cmd")) {
            heart.msgManager().msg(sender, heart.msg().getNoPermission());
            return;
        }
        if (sender instanceof ConsoleCommandSender) {
            getHeart().msgManager().msg(sender, heart.msg().getConsoleError());
            return;
        }
        if (args.length != 1) {
            heart.msgManager().msg(sender, heart.msg().getExamineFormat());
            return;
        }
        Player player = heart.msgManager().getPlayer(args[0]);
        if (player == null) {
            heart.msgManager().msg(sender, heart.msg().getCantFindPlayer(), new MessageManager.Pair<>("{player}", args[0]));
            return;
        }
        if (player.hasPermission("staffpro.examine.bypass")) {
            heart.msgManager().msg(sender, heart.msg().getYouCantExamine(), new MessageManager.Pair<>("{player}", player.getName()));
            return;
        }
        heart.msgManager().msg(sender, heart.msg().getNowExamining(), new MessageManager.Pair<>("{player}", player.getName()));
        heart.getMiscManager().startExamining(((Player) sender), player);
    }


    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return null;
        return blank;
    }
}
