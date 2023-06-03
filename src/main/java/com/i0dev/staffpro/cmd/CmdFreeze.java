package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.entity.MPlayer;
import com.i0dev.staffpro.task.TaskDisplayActionbar;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.entity.Player;

public class CmdFreeze extends StaffProCommand {

    private static CmdFreeze i = new CmdFreeze();

    public static CmdFreeze get() {
        return i;
    }

    public CmdFreeze() {
        this.setAliases("freeze");
        this.addParameter(TypePlayer.get(), "player");
        this.addRequirements(RequirementHasPerm.get(Perm.FREEZE));
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        MPlayer mPlayer = MPlayer.get(player);

        if (Perm.FREEZE_BYPASS.has(player)) {
            msg(Utils.prefixAndColor(MLang.get().playerFreezeBypass, new Pair<>("%player%", player.getName())));
            return;
        }

        if (mPlayer.isFrozen()) {
            msg(Utils.prefixAndColor(MLang.get().playerAlreadyFrozen, new Pair<>("%player%", player.getName())));
            return;
        }

        mPlayer.setFrozen(true);
        msg(Utils.prefixAndColor(MLang.get().playerFrozen, new Pair<>("%player%", player.getName())));
        mPlayer.msg(Utils.prefixAndColor(MLang.get().youHaveBeenFrozen, new Pair<>("%player%", sender.getName())));
        Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().playerFrozenAnnouncement, new Pair<>("%sender%", sender.getName()), new Pair<>("%player%", player.getName())), sender instanceof Player ? (Player) sender : null);
        TaskDisplayActionbar.get().displayActionBar(player);
    }
}
