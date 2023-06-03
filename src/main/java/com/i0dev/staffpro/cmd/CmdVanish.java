package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.entity.MPlayer;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;
import com.massivecraft.massivecore.util.MUtil;

public class CmdVanish extends StaffProCommand {

    private static CmdVanish i = new CmdVanish();

    public static CmdVanish get() {
        return i;
    }

    public CmdVanish() {
        this.setAliases("vanish", "v");
        this.addRequirements(RequirementHasPerm.get(Perm.VANISH));
        this.addRequirements(RequirementIsPlayer.get());
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() {
        MPlayer mPlayer = MPlayer.get(sender);
        if (mPlayer.isVanished()) {
            CmdUnVanish.get().execute(sender, MUtil.list());
            return;
        }

        mPlayer.enableVanish();
        msg(Utils.prefixAndColor(MLang.get().vanishEnabled));
        Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().getVanishEnabledAnnouncement, new Pair<>("%player%", me.getName())), me);
    }
}
