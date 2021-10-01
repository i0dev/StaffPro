package com.i0dev.StaffPro.managers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.GeneralConfig;
import com.i0dev.StaffPro.objects.SPlayer;
import com.i0dev.StaffPro.templates.AbstractManager;
import com.i0dev.StaffPro.utility.Utility;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
public class ModModeManager extends AbstractManager {

    public ModModeManager(Heart heart) {
        super(heart);
    }

    public void modmode(Player who) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(who.getUniqueId());
        sPlayer.setModmode(true);

        sPlayer.setSavedInventoryContents(Utility.encrypt(who.getInventory().getContents()));
        sPlayer.setSavedArmorContents(Utility.encrypt(who.getInventory().getArmorContents()));
        Location loc = who.getLocation();
        sPlayer.setSavedLocationX(loc.getX());
        sPlayer.setSavedLocationY(loc.getY());
        sPlayer.setSavedLocationZ(loc.getZ());
        sPlayer.setSavedLocationWorld(loc.getWorld().getName());
        sPlayer.setSavedLocationYaw(loc.getYaw());
        sPlayer.setSavedLocationPitch(loc.getPitch());
        sPlayer.setSavedGameMode(who.getGameMode().toString());
        sPlayer.setSavedHunger(who.getFoodLevel());
        sPlayer.setSavedHealth(who.getHealth());
        sPlayer.setSavedEXP(who.getExp());
        sPlayer.setFlying(who.isFlying());
        sPlayer.setAllowFlight(who.getAllowFlight());
        heart.getSPlayerManager().add(sPlayer);

        who.getInventory().clear();
        who.getInventory().setArmorContents(null);
        who.setExp(0);
        who.setHealth(20);
        who.setGameMode(GameMode.CREATIVE);
        who.setFoodLevel(20);
        who.setFlying(true);
        who.setAllowFlight(true);

        setModModeInventory(who);
    }


    public ItemStack addNBTValue(ItemStack item, String code) {
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString("staffpro_modmode_item", code);
        return nbtItem.getItem();
    }


    public void setModModeInventory(Player player) {
        Inventory inventory = player.getInventory();
        GeneralConfig cnf = heart.cnf();
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(player.getUniqueId());

        ItemStack freeze = Utility.makeItem(cnf.getModModeFreezeItem());
        freeze = addNBTValue(freeze, "freeze");

        ItemStack combatList = Utility.makeItem(cnf.getModModeCombatListItem());
        combatList = addNBTValue(combatList, "combatList");

        ItemStack vanish = Utility.makeItem(cnf.getModModeVanishItem());
        vanish = addNBTValue(vanish, "vanish");

        ItemStack unVanish = Utility.makeItem(cnf.getModModeUnVanishItem());
        unVanish = addNBTValue(unVanish, "unVanish");

        ItemStack examine = Utility.makeItem(cnf.getModModeExamineItem());
        examine = addNBTValue(examine, "examine");

        ItemStack randomTP = Utility.makeItem(cnf.getModModeRandomTPItem());
        randomTP = addNBTValue(randomTP, "randomTP");

        ItemStack strip = Utility.makeItem(cnf.getModModeStripItem());
        strip = addNBTValue(strip, "strip");

        ItemStack worldEdit = Utility.makeItem(cnf.getModModeWorldEditItem());

        inventory.setItem(cnf.getModModeFreezeItem().index, freeze);
        inventory.setItem(cnf.getModModeCombatListItem().index, combatList);
        if (!sPlayer.isVanished()) inventory.setItem(cnf.getModModeVanishItem().index, vanish);
        else inventory.setItem(cnf.getModModeUnVanishItem().index, unVanish);
        inventory.setItem(cnf.getModModeExamineItem().index, examine);
        inventory.setItem(cnf.getModModeRandomTPItem().index, randomTP);
        inventory.setItem(cnf.getModModeWorldEditItem().index, worldEdit);
        inventory.setItem(cnf.getModModeStripItem().index, strip);

        ItemStack[] armor = player.getInventory().getArmorContents().clone();
        armor[3] = Utility.makeItem(cnf.getModModeHelmet());
        armor[2] = Utility.makeItem(cnf.getModModeChestplate());
        armor[1] = Utility.makeItem(cnf.getModModeLeggings());
        armor[0] = Utility.makeItem(cnf.getModModeBoots());
        player.getInventory().setArmorContents(armor);
    }


    public void unmodmode(Player who) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(who.getUniqueId());
        sPlayer.setModmode(false);

        who.getInventory().setContents(Utility.decrypt(sPlayer.getSavedInventoryContents()));
        who.getInventory().setArmorContents(Utility.decrypt(sPlayer.getSavedArmorContents()));

        if (heart.cnf().isTeleportPlayerToOGPositionWhenLeaveModMode()) {
            Location location = new Location(Bukkit.getWorld(sPlayer.getSavedLocationWorld()),
                    sPlayer.getSavedLocationX(),
                    sPlayer.getSavedLocationY(),
                    sPlayer.getSavedLocationZ(),
                    sPlayer.getSavedLocationYaw(),
                    sPlayer.getSavedLocationPitch()
            );
            who.teleport(location);
        }
        who.setGameMode(GameMode.valueOf(sPlayer.getSavedGameMode()));
        who.setFoodLevel(sPlayer.getSavedHunger());
        who.setHealth(sPlayer.getSavedHealth());
        who.setExp(sPlayer.getSavedEXP());
        who.setFlying(sPlayer.isFlying());
        who.setAllowFlight(sPlayer.isAllowFlight());

        sPlayer.setSavedInventoryContents("");
        sPlayer.setSavedArmorContents("");
        sPlayer.setSavedLocationX(0);
        sPlayer.setSavedLocationY(0);
        sPlayer.setSavedLocationZ(0);
        sPlayer.setSavedLocationWorld("");
        sPlayer.setSavedLocationYaw(0);
        sPlayer.setSavedLocationPitch(0);
        sPlayer.setSavedGameMode("");
        sPlayer.setSavedHunger(0);
        sPlayer.setSavedHealth(0);
        sPlayer.setSavedEXP(0);
        sPlayer.setFlying(false);
        sPlayer.setAllowFlight(false);

        heart.getSPlayerManager().add(sPlayer);

    }


}
