package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.engine.EngineInventory;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.entity.Player;

public class CmdExamine extends StaffProCommand {

    private static CmdExamine i = new CmdExamine();

    public static CmdExamine get() {
        return i;
    }

    public CmdExamine() {
        this.setAliases("examine");
        this.addParameter(TypePlayer.get(), "player");
        this.addRequirements(RequirementHasPerm.get(Perm.EXAMINE));
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        me.openInventory(EngineInventory.get().getExamineInventory(player));
        msg(Utils.prefixAndColor(MLang.get().nowExamining, new Pair<>("%player%", player.getName())));
    }
}
