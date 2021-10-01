package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.templates.AbstractCommand;
import com.i0dev.StaffPro.utility.Utility;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CmdStrip extends AbstractCommand {

    public CmdStrip(Heart heart, String command) {
        super(heart, command);
    }

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.strip.cmd")) {
            heart.msgManager().msg(sender, heart.msg().getNoPermission());
            return;
        }
        if (args.length != 1) {
            heart.msgManager().msg(sender, heart.msg().getStripUsage());
            return;
        }

        Player player = heart.msgManager().getPlayer(args[0]);
        if (player == null) {
            heart.msgManager().msg(sender, heart.msg().getCantFindPlayer(), new MessageManager.Pair<>("{player}", args[0]));
            return;
        }

        heart.getMiscManager().strip(player);
        heart.msgManager().msg(sender, heart.msg().getYouStripped(), new MessageManager.Pair<>("{player}", player.getName()));
        heart.msgManager().msg(player, heart.msg().getYouWereStripped());
    }

    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return null;
        return blank;
    }
}
