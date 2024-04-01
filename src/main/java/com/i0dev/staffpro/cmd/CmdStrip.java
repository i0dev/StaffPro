package com.i0dev.staffpro.cmd;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.entity.MLang;
import com.i0dev.staffpro.util.Pair;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.MassiveException;
import com.massivecraft.massivecore.command.Visibility;
import com.massivecraft.massivecore.command.requirement.RequirementHasPerm;
import com.massivecraft.massivecore.command.type.sender.TypePlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdStrip extends StaffProCommand {

    private static CmdStrip i = new CmdStrip();

    public static CmdStrip get() {
        return i;
    }

    public CmdStrip() {
        this.setAliases("strip");
        this.addParameter(TypePlayer.get(), "player");
        this.addRequirements(RequirementHasPerm.get(Perm.STRIP));
        this.setVisibility(Visibility.SECRET);
    }


    @Override
    public void perform() throws MassiveException {
        Player player = this.readArg();

        if (Perm.STRIP_BYPASS.has(player)) {
            msg(Utils.prefixAndColor(MLang.get().playerStripBypass, new Pair<>("%player%", player.getName())));
            return;
        }

        strip(player);

        msg(Utils.prefixAndColor(MLang.get().youStrippedPlayer,
                new Pair<>("%player%", player.getName())));
    }


    public void strip(Player player) {
        ItemStack[] armour = player.getInventory().getArmorContents().clone();
        player.updateInventory();
        player.getInventory().setArmorContents(null);

        for (ItemStack item : armour) {
            if (item == null) continue;
            if (Material.AIR.equals(item.getType())) continue;
            if (getNonAirContents(player.getInventory().getContents()) != player.getInventory().getSize()) {
                player.getInventory().addItem(item);
            } else {
                player.getLocation().getWorld().dropItemNaturally(player.getLocation(), item);
            }
        }
    }


    public static int getNonAirContents(ItemStack[] contents) {
        int realItems = 0;
        for (ItemStack stack : contents) {
            if (stack == null) continue;
            if (Material.AIR.equals(stack.getType())) continue;
            realItems++;
        }
        return realItems;
    }


}
