package com.i0dev.StaffPro.commands;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.FreezeManager;
import com.i0dev.StaffPro.managers.MessageManager;
import com.i0dev.StaffPro.templates.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CmdSoftFreeze extends AbstractCommand {

    public CmdSoftFreeze(Heart heart, String command) {
        super(heart, command);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("staffpro.softfreeze.cmd")) {
            heart.msgManager().msg(sender, heart.msg().getNoPermission());
            return;
        }
        if (args.length != 1) {
            heart.msgManager().msg(sender, heart.msg().getSoftFreezeFormat());
            return;
        }
        Player player = heart.msgManager().getPlayer(args[0]);
        if (player == null) {
            heart.msgManager().msg(sender, heart.msg().getCantFindPlayer(), new MessageManager.Pair<>("{player}", args[0]));
            return;
        }
        if (heart.getSPlayerManager().getSPlayer(player.getUniqueId()).isSoft_frozen()) {
            heart.msgManager().msg(sender, heart.msg().getAlreadySoftFrozen(), new MessageManager.Pair<>("{player}", player.getName()));
            return;
        }
        if (player.hasPermission("staffpro.softfreeze.bypass")) {
            heart.msgManager().msg(sender, heart.msg().getYouCantSoftFreeze(), new MessageManager.Pair<>("{player}", player.getName()));
            return;
        }

        heart.getManager(FreezeManager.class).softFreeze(player);
        heart.msgManager().msg(sender, heart.msg().getYouSoftFroze(), new MessageManager.Pair<>("{player}", player.getName()));
        heart.msgManager().msg(player, heart.msg().getYouBeenSoftFrozen(), new MessageManager.Pair<>("{player}", sender.getName()));
        heart.getSPlayerManager().messageAllStaff(heart.msg().getSoftFrozeAnnounce().replace("{player}", player.getName()).replace("{staff}", sender.getName()), sender);
    }


    List<String> blank = new ArrayList<>();

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return null;
        return blank;
    }
}
