package com.i0dev.StaffPro.handlers;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.MiscManager;
import com.i0dev.StaffPro.templates.AbstractListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MiscHandler extends AbstractListener {
    public MiscHandler(Heart heart) {
        super(heart);
    }

    /*
    This method manages the auto-mod, blacklisted word replace feature.
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!heart.cnf().isChatFilterEnabled()) return;
        for (String word : heart.cnf().getChatFilterBlacklistedWords()) {
            word = word.toLowerCase();
            if (!e.getMessage().toLowerCase().contains(word)) continue;
            e.setMessage(e.getMessage().replace(word, getStars(word)));
        }
    }

    public String getStars(String s) {
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            stars.append("*");
        }
        return stars.toString();
    }

    /*
    The following methods are to handle the examine feature.
     */

    @EventHandler
    public void onInventoryClickInExamineMode(InventoryClickEvent e) {
        if (e.getInventory().getHolder() instanceof MiscManager.ExamineInventoryHolder) {
            if (e.getWhoClicked().hasPermission("staffpro.examine.takeitem")) {
                if (e.getSlot() < 18) e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryCloseExamine(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof MiscManager.ExamineInventoryHolder) {
            e.getInventory().clear();
        }
    }

}
