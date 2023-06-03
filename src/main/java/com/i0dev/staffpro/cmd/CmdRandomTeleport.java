package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CmdRandomTeleport extends StaffProCommand {

    private static CmdRandomTeleport i = new CmdRandomTeleport();

    public static CmdRandomTeleport get() {
        return i;
    }

    public CmdRandomTeleport() {
        this.setAliases("randomTeleport", "rtp");
        this.addRequirements(RequirementHasPerm.get(Perm.RANDOMTELEPORT));
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        if (getRealPlayers().size() == 0) {
            msg(Utils.prefixAndColor(MLang.get().noPlayersToRandomTeleport));
            return;
        }

        Player teleportedTo = randomTeleport(me);
        msg(Utils.prefixAndColor(MLang.get().randomTeleport, new Pair<>("%player%", teleportedTo.getName())));
    }

    // TODO: Add alt check when implemented into factions
    public List<Player> getRealPlayers() {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> !player.isOp())
                .filter(player -> !Perm.STAFF.has(player))
                .filter(player -> !Perm.RANDOMTELEPORT_BYPASS.has(player))
                .collect(Collectors.toList());
    }

    public Player randomTeleport(Player who) {
        Player toTeleport = getRealPlayers().get(ThreadLocalRandom.current().nextInt(0, getRealPlayers().size()));
        who.teleport(toTeleport);
        return toTeleport;
    }


}
