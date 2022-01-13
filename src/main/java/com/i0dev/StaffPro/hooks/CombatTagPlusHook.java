package com.i0dev.StaffPro.hooks;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.managers.CombatManager;
import com.i0dev.StaffPro.templates.AbstractListener;
import net.minelink.ctplus.CombatTagPlus;
import net.minelink.ctplus.Tag;
import net.minelink.ctplus.event.PlayerCombatTagEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class CombatTagPlusHook extends AbstractListener {

    static CombatTagPlus combatTagPlus;

    public CombatTagPlusHook(Heart heart) {
        super(heart);
    }

    public static void setupHook() {
        if (combatTagPlus == null)
            combatTagPlus = (CombatTagPlus) Bukkit.getServer().getPluginManager().getPlugin("CombatTagPlus");
    }


    public static boolean isPlayerInCombat(UUID uuid) {
        setupHook();
        return combatTagPlus.getTagManager().isTagged(uuid);
    }

    public static String getTaggedWith(UUID uuid) {
        setupHook();
        Tag tag = combatTagPlus.getTagManager().getTag(uuid);
        if (tag == null) return "None";
        if (tag.getAttackerId().equals(uuid)) return tag.getVictimName();
        return tag.getAttackerName();
    }


    @EventHandler
    public void onCombatTag(PlayerCombatTagEvent e) {
        heart.getManager(CombatManager.class).addButCheck(e.getAttacker());
        heart.getManager(CombatManager.class).addButCheck(e.getVictim());
    }

}
