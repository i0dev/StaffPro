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
import org.bukkit.inventory.meta.ItemMeta;

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

        ItemStack freeze = Utility.makeItem(Material.getMaterial(Material.PACKED_ICE.toString()), 1, ((short) 0), "freeze", null, true);
        freeze = addNBTValue(freeze, "freeze");

        ItemStack combatList = Utility.makeItem(Material.getMaterial(Material.WATCH.toString()), 1, ((short) 0), "combat", null, true);
        combatList = addNBTValue(combatList, "combatList");

        ItemStack vanish = Utility.makeItem(Material.getMaterial(Material.STONE.toString()), 1, ((short) 0), "van", null, true);
        vanish = addNBTValue(vanish, "vanish");

        ItemStack unVanish = Utility.makeItem(Material.getMaterial(Material.GRASS.toString()), 1, ((short) 0), "unvan", null, true);
        unVanish = addNBTValue(unVanish, "unVanish");

        ItemStack examine = Utility.makeItem(Material.getMaterial(Material.ENDER_CHEST.toString()), 1, ((short) 0), "exam", null, true);
        examine = addNBTValue(examine, "examine");

        ItemStack randomTP = Utility.makeItem(Material.getMaterial(Material.NETHER_STAR.toString()), 1, ((short) 0), "rtp", null, true);
        randomTP = addNBTValue(randomTP, "randomTP");

        inventory.setItem(cnf.getModModeFreezeIndex(), freeze);
        inventory.setItem(cnf.getModModeCombatListIndex(), combatList);
        if (!sPlayer.isVanished()) inventory.setItem(cnf.getModModeVanishIndex(), vanish);
        else inventory.setItem(cnf.getModModeVanishIndex(), unVanish);
        inventory.setItem(cnf.getModMOdeExamineIndex(), examine);
        inventory.setItem(cnf.getModModeRandomTPIndex(), randomTP);

    }


    public void unmodmode(Player who) {
        SPlayer sPlayer = heart.getSPlayerManager().getSPlayer(who.getUniqueId());
        sPlayer.setModmode(false);

        who.getInventory().setContents(Utility.decrypt(sPlayer.getSavedInventoryContents()));
        who.getInventory().setArmorContents(Utility.decrypt(sPlayer.getSavedArmorContents()));
        Location location = new Location(Bukkit.getWorld(sPlayer.getSavedLocationWorld()),
                sPlayer.getSavedLocationX(),
                sPlayer.getSavedLocationY(),
                sPlayer.getSavedLocationZ(),
                sPlayer.getSavedLocationYaw(),
                sPlayer.getSavedLocationPitch()
        );
        who.teleport(location);

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
