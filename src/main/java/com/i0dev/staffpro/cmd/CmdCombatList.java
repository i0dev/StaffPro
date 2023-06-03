package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.engine.EngineInventory;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.integration.combattagplus.IntegrationCombatTagPlus;
import com.i0dev.staffpro.task.TaskCombatList;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.requirement.RequirementIsPlayer;

public class CmdCombatList extends StaffProCommand {

    private static CmdCombatList i = new CmdCombatList();

    public static CmdCombatList get() {
        return i;
    }

    public CmdCombatList() {
        this.setAliases("combatlist");
        this.addRequirements(RequirementHasPerm.get(Perm.COMBATLIST));
        this.addRequirements(RequirementIsPlayer.get());
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {

        if (!IntegrationCombatTagPlus.get().isActive()) {
            msg("&cCombatTagPlus is not enabled on the server so this feature is disabled.");
            return;
        }

        if (TaskCombatList.get().getPlayersInCombat().isEmpty()) {
            msg(Utils.prefixAndColor(MLang.get().noPlayersInCombat));
            return;
        }

        me.openInventory(EngineInventory.get().getCombatListInventory(me));
        msg(Utils.prefixAndColor(MLang.get().openingCombatListInventory));
    }
}
