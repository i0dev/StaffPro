package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.MessageConfig;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.templates.AbstractCommand;
import com.i0dev.StaffPro.utility.Utility;
import lombok.SneakyThrows;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdStaffPro extends AbstractCommand {

    public CmdStaffPro(Heart heart, String command) {
        super(heart, command);
    }

    @SneakyThrows
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            heart.msgManager().msg(sender, heart.msg().getReloadUsage());
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("staffpro.reload.cmd")) {
                heart.msgManager().msg(sender, heart.msg().getNoPermission());
                return;
            }
            getHeart().reload();
            heart.msgManager().msg(sender, heart.msg().getReloadedConfig());
            return;
        }

        if (args[0].equalsIgnoreCase("help")) {
            if (!sender.hasPermission("staffpro.help.cmd")) {
                heart.msgManager().msg(sender, heart.msg().getNoPermission());
                return;
            }

            heart.msgManager().msg(sender, heart.msg().getHelpHeader());
            heart.msgManager().msg(sender, heart.msg().getReloadUsage());
            heart.msgManager().msg(sender, heart.msg().getVanishUsage());
            heart.msgManager().msg(sender, heart.msg().getUnVanishUsage());
            heart.msgManager().msg(sender, heart.msg().getStripUsage());
            heart.msgManager().msg(sender, heart.msg().getRTPUsage());
            heart.msgManager().msg(sender, heart.msg().getFreezeFormat());
            heart.msgManager().msg(sender, heart.msg().getUnFreezeFormat());
            heart.msgManager().msg(sender, heart.msg().getExamineFormat());
            heart.msgManager().msg(sender, heart.msg().getModModeUsage());
            heart.msgManager().msg(sender, heart.msg().getPingUsage());
        }

    }

    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return Arrays.asList("reload", "help");
        return blank;
    }
}
