package com.i0dev.StaffPro.managers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.config.GeneralConfig;
import com.i0dev.StaffPro.templates.AbstractManager;
import com.i0dev.StaffPro.utility.Utility;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class MiscManager extends AbstractManager {
    public MiscManager(Heart heart) {
        super(heart);
    }

    public List<Player> getRealPlayers() {
        return Bukkit.getOnlinePlayers().stream().filter(player -> {
            if (player.isOp()) return false;
            if (player.hasPermission("staffpro.staff")) return false;
            if (player.hasPermission("staffpro.rtp.bypass")) return false;
            if (getHeart().isUsingMCoreFactions()) {
                com.massivecraft.factions.entity.MPlayer mPlayer = com.massivecraft.factions.entity.MPlayer.get(player);
                if (mPlayer.isAlt()) return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

    public Player randomTeleport(Player who) {
        Player toTeleport = getRealPlayers().get(ThreadLocalRandom.current().nextInt(0, getRealPlayers().size()));
        who.teleport(toTeleport);
        return toTeleport;
    }

    /*
    The following methods are relating the examine feature. The ExamineInventoryHolder class is just to set the identity of the inventory opened, so actions inside that inventory can be canceled.
     */

    public void startExamining(Player staff, Player who) {
        Inventory inventory = Bukkit.createInventory(new ExamineInventoryHolder(), 54, Utility.color("&7Examining &c" + who.getName()));
        ItemStack[] armor = who.getInventory().getArmorContents();
        inventory.setItem(0, armor[3]);
        inventory.setItem(1, armor[2]);
        inventory.setItem(2, armor[1]);
        inventory.setItem(3, armor[0]);
        GeneralConfig cnf = heart.cnf();
        inventory.setItem(cnf.getExamineEXPIndex(), Utility.makeItem(Material.getMaterial(cnf.getExamineEXPMaterial()), Math.min(who.getTotalExperience(), 64), cnf.getExamineEXPData(), cnf.getExamineEXPTitle()
                        .replace("{exp}", who.getTotalExperience() + "")
                , new ArrayList<>(), cnf.isExamineEXPGlow()));
        inventory.setItem(cnf.getExaminePotionsIndex(), Utility.makeItem(Material.getMaterial(cnf.getExaminePotionsMaterial()), Math.min(getPotionCount(who.getInventory().getContents()), 64), cnf.getExaminePotionsData(), cnf.getExaminePotionsTitle()
                        .replace("{pots}", getPotionCount(who.getInventory().getContents()) + "")
                , new ArrayList<>(), cnf.isExaminePotionsGlow()));
        inventory.setItem(cnf.getExamineHealthIndex(), Utility.makeItem(Material.getMaterial(cnf.getExamineHealthMaterial()), (int) who.getHealth(), cnf.getExamineHealthData(), cnf.getExamineHealthTitle()
                        .replace("{health}", who.getHealth() + "")
                , new ArrayList<>(), cnf.isExamineHealthGlow()));
        inventory.setItem(cnf.getExamineHungerIndex(), Utility.makeItem(Material.getMaterial(cnf.getExamineHungerMaterial()), who.getFoodLevel(), cnf.getExamineHungerData(), cnf.getExamineHungerTitle()
                        .replace("{hunger}", who.getFoodLevel() + "")
                , new ArrayList<>(), cnf.isExamineHungerGlow()));
        ItemStack skull = Utility.itemFromBase64(Utility.getDataFromName(who.getName()));
        ItemMeta skullData = skull.getItemMeta();
        skullData.setDisplayName(Utility.color(cnf.getExamineHeadTitle()
                .replace("{displayName}", who.getDisplayName())

        ));
        skull.setItemMeta(skullData);
        inventory.setItem(cnf.getExamineHeadIndex(), skull);

        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, Utility.makeItem(Material.STAINED_GLASS_PANE, 1, (short) 15, Utility.color("&a"), new ArrayList<>(), true));
        }

        ArrayList<ItemStack> playerItems = new ArrayList<>(Arrays.asList(who.getInventory().getContents()));
        for (int i = 53; i > playerItems.size() - 19; i--) {
            inventory.setItem(i, playerItems.get(i - 18));
        }

        staff.openInventory(inventory);
    }

    public static int getPotionCount(ItemStack[] drops) {
        int potCount = 0;
        for (ItemStack item : drops) {
            if (item == null) continue;
            if (item.getType() == null) continue;
            if (item.getType() == Material.AIR) continue;
            if (item.getType() == Material.POTION && (item.getData().getData() == ((byte) 16421) || item.getData().getData() == ((byte) 16485))) {
                potCount = potCount + item.getAmount();
            }
        }

        return potCount;
    }


    public static class ExamineInventoryHolder implements InventoryHolder {
        @Override
        public Inventory getInventory() {
            return null;
        }
    }

}
