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

public class CmdUnFreeze extends StaffProCommand {

    private static CmdUnFreeze i = new CmdUnFreeze();

    public static CmdUnFreeze get() {
        return i;
    }

    public CmdUnFreeze() {
        this.setAliases("unfreeze");
        this.addParameter(TypePlayer.get(), "player");
        this.addRequirements(RequirementHasPerm.get(Perm.UNFREEZE));
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        MPlayer mPlayer = MPlayer.get(player);

        if (!mPlayer.isFrozen()) {
            msg(Utils.prefixAndColor(MLang.get().playerNotFrozen, new Pair<>("%player%", player.getName())));
            return;
        }

        mPlayer.setFrozen(false);
        msg(Utils.prefixAndColor(MLang.get().playerUnfrozen, new Pair<>("%player%", player.getName())));
        mPlayer.msg(Utils.prefixAndColor(MLang.get().youHaveBeenUnfrozen, new Pair<>("%player%", sender.getName())));
        Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().playerUnfrozenAnnouncement, new Pair<>("%sender%", sender.getName()), new Pair<>("%player%", player.getName())), sender instanceof Player ? (Player) sender : null);
        TaskDisplayActionbar.get().displayActionBar(player);
    }
}
