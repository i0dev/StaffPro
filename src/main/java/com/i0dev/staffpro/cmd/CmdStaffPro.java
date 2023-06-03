package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.StaffProPlugin;
import com.i0dev.staffpro.entity.MConf;
import com.massivecraft.massivecore.command.MassiveCommandVersion;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;

import java.util.List;

public class CmdStaffPro extends StaffProCommand {
    private static CmdStaffPro i = new CmdStaffPro();
    public CmdExamine cmdExamine = new CmdExamine();
    public CmdFreeze cmdFreeze = new CmdFreeze();
    public CmdUnFreeze cmdUnFreeze = new CmdUnFreeze();
    public CmdModMode cmdModMode = new CmdModMode();
    public CmdPing cmdPing = new CmdPing();
    public CmdRandomTeleport cmdRandomTeleport = new CmdRandomTeleport();
    public CmdStrip cmdStrip = new CmdStrip();
    public CmdVanish cmdVanish = new CmdVanish();
    public CmdUnVanish cmdUnVanish = new CmdUnVanish();
    public CmdCombatList cmdCombatList = new CmdCombatList();
    public MassiveCommandVersion cmdFactionsVersion = new MassiveCommandVersion(StaffProPlugin.get()).setAliases("version").addRequirements(RequirementHasPerm.get(Perm.VERSION));

    public static CmdStaffPro get() {
        return i;
    }

    @Override
    public List<String> getAliases() {
        return MConf.get().aliasesStaffPro;
    }

}
