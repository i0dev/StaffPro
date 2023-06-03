package com.i0dev.staffpro.engine;

import com.i0dev.staffpro.entity.MConf;
import com.i0dev.staffpro.entity.object.ConfigItem;
import com.i0dev.staffpro.entity.object.ExamineInventory;
import com.i0dev.staffpro.integration.combattagplus.IntegrationCombatTagPlus;
import com.i0dev.staffpro.task.TaskCombatList;
import com.i0dev.staffpro.util.ItemBuilder;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.Engine;
import com.massivecraft.massivecore.chestgui.ChestActionCommand;
import com.massivecraft.massivecore.chestgui.ChestGui;
import com.massivecraft.massivecore.util.Txt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.stream.IntStream;

public class EngineInventory extends Engine {

    private static EngineInventory i = new EngineInventory();

    public static EngineInventory get() {
        return i;
    }

    private ChestGui getBasicChestGui(String title, int size, boolean borderGlass) {
        Inventory inventory = Bukkit.createInventory(null, size, Txt.parse(title));
        ChestGui chestGui = ChestGui.getCreative(inventory);

        chestGui.setAutoclosing(false);
        chestGui.setAutoremoving(true);
        chestGui.setSoundOpen(null);
        chestGui.setSoundClose(null);

        if (borderGlass)
            IntStream.range(0, size).forEach(i -> chestGui.getInventory().setItem(i, ItemBuilder.fromConfigItem(MConf.get().borderGlass)));

        return chestGui;
    }


    public Inventory getExamineInventory(Player player) {
        ExamineInventory cnf = MConf.get().examineInventory;
        ChestGui chestGui = getBasicChestGui(cnf.title.replace("%player%", player.getName()), 54, false);

        IntStream.range(9, 18).forEach(i -> chestGui.getInventory().setItem(i, ItemBuilder.fromConfigItem(MConf.get().borderGlass)));

        chestGui.getInventory().setItem(cnf.expItem.slot, new ItemBuilder(cnf.expItem.getMaterial())
                .amount(cnf.expItem.amount)
                .name(cnf.expItem.getDisplayName().replace("%exp%", String.valueOf(player.getExp())))
                .lore(cnf.expItem.getLore())
                .addGlow(cnf.expItem.isGlow())
        );

        chestGui.getInventory().setItem(cnf.healthItem.slot, new ItemBuilder(cnf.healthItem.getMaterial())
                .amount(cnf.healthItem.amount)
                .name(cnf.healthItem.getDisplayName().replace("%health%", String.valueOf(player.getHealth())))
                .lore(cnf.healthItem.getLore())
                .addGlow(cnf.healthItem.isGlow())
        );

        chestGui.getInventory().setItem(cnf.hungerItem.slot, new ItemBuilder(cnf.hungerItem.getMaterial())
                .amount(cnf.hungerItem.amount)
                .name(cnf.hungerItem.getDisplayName().replace("%hunger%", String.valueOf(player.getFoodLevel())))
                .lore(cnf.hungerItem.getLore())
                .addGlow(cnf.hungerItem.isGlow())
        );

        ItemStack skull = new ItemBuilder(cnf.playerHeadItem.getMaterial())
                .amount(cnf.playerHeadItem.amount)
                .name(cnf.playerHeadItem.getDisplayName().replace("%player%", player.getDisplayName()))
                .lore(cnf.playerHeadItem.getLore())
                .addGlow(cnf.playerHeadItem.isGlow());

        SkullMeta skullMeta = ((SkullMeta) skull.getItemMeta());
        if (skullMeta != null) {
            skullMeta.setOwningPlayer(player);
            skull.setItemMeta(skullMeta);
        }
        chestGui.getInventory().setItem(cnf.playerHeadItem.slot, skull);


        // Inventory Items
        ItemStack[] armor = player.getInventory().getArmorContents();
        chestGui.getInventory().setItem(0, armor[3]);
        chestGui.getInventory().setItem(1, armor[2]);
        chestGui.getInventory().setItem(2, armor[1]);
        chestGui.getInventory().setItem(3, armor[0]);

        chestGui.getInventory().setItem(4, player.getInventory().getItemInOffHand());

        ArrayList<ItemStack> playerItems = new ArrayList<>(Arrays.asList(player.getInventory().getStorageContents()));
        Collections.reverse(playerItems);
        for (int i = 0; i < playerItems.size(); i++) {
            chestGui.getInventory().setItem(i + 18, playerItems.get(i));
        }

        return chestGui.getInventory();
    }

    public Inventory getCombatListInventory(Player observer) {
        ChestGui chestGui = getBasicChestGui(MConf.get().combatListTitle, 54, false);
        List<Player> playersInCombat = TaskCombatList.get().getPlayersInCombat();
        ConfigItem playerHeadItem = MConf.get().combatListPlayerItem;
        for (int i = 0; i < playersInCombat.size(); i++) {
            Player player = playersInCombat.get(i);
            if (player == null) continue;

            ItemStack skull = new ItemBuilder(playerHeadItem.getMaterial())
                    .amount(playerHeadItem.amount)
                    .name(playerHeadItem.getDisplayName().replace("%player%", player.getDisplayName()))
                    .addGlow(playerHeadItem.isGlow());

            SkullMeta skullMeta = ((SkullMeta) skull.getItemMeta());
            if (skullMeta != null) {
                skullMeta.setOwningPlayer(player);
                skull.setItemMeta(skullMeta);
            }

            List<String> lore = new ArrayList<>();
            playerHeadItem.getLore().forEach(s -> lore.add(Utils.color(s
                    .replace("%faction%", getFacName(observer, player))
                    .replace("%taggedName%", IntegrationCombatTagPlus.get().getEngine().getTaggedWith(player.getUniqueId()))
            )));

            SkullMeta meta = (SkullMeta) skull.getItemMeta();

            meta.setLore(lore);
            skull.setItemMeta(meta);

            chestGui.setAction(i, new ChestActionCommand("tp " + player.getName()));
        }
        return chestGui.getInventory();
    }

    public static String getFacName(Player observer, Player player) {
        com.massivecraft.factions.entity.MPlayer mPlayer = com.massivecraft.factions.entity.MPlayer.get(player);
        com.massivecraft.factions.entity.MPlayer mObserver = com.massivecraft.factions.entity.MPlayer.get(observer);
        return mPlayer.describeTo(mObserver);
    }

}
