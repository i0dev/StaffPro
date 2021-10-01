package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractCommand;
import com.i0dev.StaffPro.utility.Utility;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdPing extends AbstractCommand {

    public CmdPing(Heart heart, String command) {
        super(heart, command);
    }

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.ping.cmd")) return;
        Player toGetPing;
        if (args.length == 0) {
            if (sender instanceof ConsoleCommandSender) {
                heart.msgManager().msg(sender, heart.msg().getConsoleError());
                return;
            }
            toGetPing = ((Player) sender);
        } else {
            toGetPing = heart.msgManager().getPlayer(args[0]);
            if (toGetPing == null) {
                heart.msgManager().msg(sender, heart.msg().getCantFindPlayer(), new MessageManager.Pair<>("{player}", args[0]));
                return;
            }
        }

        Object entity = Utility.getOBCClass("entity.CraftPlayer").cast(toGetPing);
        Object handle = entity.getClass().getMethod("getHandle").invoke(entity);
        Object ping = handle.getClass().getField("ping").get(handle);
        heart.msgManager().msg(sender, heart.msg().getPingOf(), new MessageManager.Pair<>("{player}", toGetPing.getName()), new MessageManager.Pair<>("{ping}", ping + ""));
    }

    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return null;
        return blank;
    }
}
