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

public class CmdPing extends StaffProCommand {

    private static CmdPing i = new CmdPing();

    public static CmdPing get() {
        return i;
    }

    public CmdPing() {
        this.setAliases("ping");
        this.addParameter(TypePlayer.get(), "player");
        this.addRequirements(RequirementHasPerm.get(Perm.PING));
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();
        msg(Utils.prefixAndColor(MLang.get().playerPing,
                new Pair<>("%ping%", String.valueOf(player.getPing())),
                new Pair<>("%player%", player.getName())));
    }
}
