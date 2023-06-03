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
import com.massivecraft.massivecore.command.type.primitive.TypeBooleanOn;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.entity.Player;

public class CmdModMode extends StaffProCommand {

    private static CmdModMode i = new CmdModMode();

    public static CmdModMode get() {
        return i;
    }

    public CmdModMode() {
        this.setAliases("modmode");
        this.addRequirements(RequirementHasPerm.get(Perm.MODMODE));
        this.addRequirements(RequirementIsPlayer.get());
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        MPlayer mPlayer = MPlayer.get(sender);
        boolean enable = !mPlayer.isModmode();

        if (enable) {
            mPlayer.enableModMode();
            msg(Utils.prefixAndColor(MLang.get().modModeEnabled));
            Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().getModModeEnabledAnnouncement, new Pair<>("%player%", me.getName())), me);
        } else {
            mPlayer.disableModMode();
            msg(Utils.prefixAndColor(MLang.get().modModeDisabled, new Pair<>("%player%", me.getName())));
            Utils.messageAllStaffExcludes(Utils.prefixAndColor(MLang.get().getModModeDisabledAnnouncement, new Pair<>("%player%", me.getName())), me);
        }
    }
}
