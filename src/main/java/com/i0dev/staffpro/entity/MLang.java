package com.i0dev.staffpro.entity;

import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;

@EditorName("config")
public class MLang extends Entity<MLang> {

    protected static transient MLang i;

    public static MLang get() {
        return i;
    }

    public String prefix = "&8[&6StaffPro&8]&7";

    public String nowExamining = "%prefix% &cYou are now examining %player%!";
    public String playerPing = "%prefix% &c%player% ping is %ping%ms!";
    public String randomTeleport = "%prefix% &cYou have been randomly teleported to %player%!";
    public String youStrippedPlayer = "%prefix% &cYou have stripped %player%!";
    public String playerStripBypass = "%prefix% &c%player% has bypassed the strip!";
    public String noPlayersToRandomTeleport = "%prefix% &cThere are no players to randomly teleport to!";
    public String noPlayersInCombat = "%prefix% &cThere are no players in combat!";
    public String openingCombatListInventory = "%prefix% &cOpening combat list inventory...";

    public String playerAlreadyFrozen = "%prefix% &c%player% is already frozen!";
    public String playerNotFrozen = "%prefix% &c%player% is not frozen!";
    public String playerFrozen = "%prefix% &c%player% is now frozen!";
    public String playerFreezeBypass = "%prefix% &c%player% has bypassed the freeze!";
    public String youHaveBeenFrozen = "%prefix% &cYou have been frozen by %player%";
    public String playerFrozenAnnouncement = "%prefix% &c%player% has been frozen by %sender%";
    public String playerUnfrozen = "%prefix% &c%player% is now unfrozen!";
    public String youHaveBeenUnfrozen = "%prefix% &cYou have been unfrozen by %player%";
    public String playerUnfrozenAnnouncement = "%prefix% &c%player% has been unfrozen by %sender%";
    public String playerFrozenLoginAnnouncement = "%prefix% &c%player% has logged in while frozen!";
    public String playerFrozenLogoutAnnouncement = "%prefix% &c%player% has logged out while frozen!";
    public String cantDoThatWhileFrozen = "%prefix% &7You can't &c%action% &7while frozen!";

    public String modModeEnabled = "%prefix% &cYou are now in Mod Mode!";
    public String modModeDisabled = "%prefix% &cYou are no longer in Mod Mode!";
    public String getModModeEnabledAnnouncement = "%prefix% &c%player% is now in Mod Mode!";
    public String getModModeDisabledAnnouncement = "%prefix% &c%player% is no longer in Mod Mode!";

    public String vanishEnabled = "%prefix% &cYou are now vanished!";
    public String vanishDisabled = "%prefix% &cYou are no longer vanished!";
    public String getVanishEnabledAnnouncement = "%prefix% &c%player% is now vanished!";
    public String getVanishDisabledAnnouncement = "%prefix% &c%player% is no longer vanished!";


    @Override
    public MLang load(MLang that) {
        super.load(that);
        return this;
    }
}
