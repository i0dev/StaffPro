package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.entity.MPlayer;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;

public class CmdUnVanish extends StaffProCommand {

    private static CmdUnVanish i = new CmdUnVanish();

    public static CmdUnVanish get() {
        return i;
    }

    public CmdUnVanish() {
        this.setAliases("unvanish", "unv");
        this.addRequirements(RequirementHasPerm.get(Perm.UNVANISH));
        this.addRequirements(RequirementIsPlayer.get());
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() {
        MPlayer mPlayer = MPlayer.get(sender);
        mPlayer.disableVanish();
        msg(Utils.prefixAndColor(MLang.get().vanishDisabled));
        Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().getVanishDisabledAnnouncement, new Pair<>("%player%", me.getName())), me);
    }
}
