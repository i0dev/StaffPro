package com.i0dev.staffpro.integration.combattagplus;

import com.i0dev.staffpro.task.TaskCombatList;
import com.massivecraft.massivecore.Engine;
import net.minelink.ctplus.CombatTagPlus;
import net.minelink.ctplus.Tag;
import net.minelink.ctplus.event.PlayerCombatTagEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.util.UUID;

public class EngineCombatTagPlus extends Engine {

    private static EngineCombatTagPlus i = new EngineCombatTagPlus();
    private CombatTagPlus combatTagPlus;

    public static EngineCombatTagPlus get() {
        return i;
    }

    public CombatTagPlus getEssentials() {
        return combatTagPlus;
    }

    @Override
    public void setActive(boolean active) {
        combatTagPlus = active ? (CombatTagPlus) Bukkit.getServer().getPluginManager().getPlugin("CombatTagPlus") : null;
        super.setActive(active);
    }

    public boolean isPlayerInCombat(UUID uuid) {
        return combatTagPlus.getTagManager().isTagged(uuid);
    }

    public String getTaggedWith(UUID uuid) {
        Tag tag = combatTagPlus.getTagManager().getTag(uuid);
        if (tag == null) return "None";
        if (tag.getAttackerId().equals(uuid)) return tag.getVictimName();
        return tag.getAttackerName();
    }


    @EventHandler
    public void onCombatTag(PlayerCombatTagEvent e) {
        if (e.getAttacker() != null)
            TaskCombatList.get().addButCheck(e.getAttacker());
        if (e.getVictim() != null)
            TaskCombatList.get().addButCheck(e.getVictim());
    }

}