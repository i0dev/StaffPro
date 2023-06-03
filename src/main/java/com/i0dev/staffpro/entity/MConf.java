package com.i0dev.staffpro.entity;

import com.i0dev.staffpro.entity.object.ConfigItem;
import com.i0dev.staffpro.entity.object.ExamineInventory;
import com.i0dev.staffpro.entity.object.ModModeInventory;
import com.massivecraft.massivecore.command.editor.annotation.EditorName;
import com.massivecraft.massivecore.store.Entity;
import com.massivecraft.massivecore.util.MUtil;
import com.massivecraft.massivecore.util.TimeUnit;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

@EditorName("config")
public class MConf extends Entity<MConf> {

    protected static transient MConf i;

    public static MConf get() {
        return i;
    }

    public List<String> aliasesStaffPro = MUtil.list("staffpro");
    public long cleanInactivityToleranceMillis = 10 * TimeUnit.MILLIS_PER_DAY; // 10 days
    public String actionbarOverriding = "&4&lOVERRIDING";
    public String actionbarVanished = "&a&LVANISHED";
    public String actionbarModMode = "&d&lMODMODE";
    public String actionbarFrozen = "&b&lFROZEN";
    public String actionBarDelimiter = " &8| ";
    public List<String> frozenCommandWhitelist = Arrays.asList(
            "/msg",
            "/r",
            "/m",
            "/discord"
    );
    public ConfigItem borderGlass = new ConfigItem("&f", 1, 0, Material.BLACK_STAINED_GLASS_PANE, null, true);
    public String combatListTitle = "&4&lCOMBAT LIST";
    public ExamineInventory examineInventory = new ExamineInventory();
    public ModModeInventory modModeInventory = new ModModeInventory();
    public ConfigItem combatListPlayerItem = new ConfigItem("&7%player%", 1, 0, Material.PLAYER_HEAD, MUtil.list(
            "",
            "&c&lInformation:",
            "&4 * &7Faction: &c%faction%",
            "&4 * &7Tagged: &c%taggedName%",
            "",
            "&7Click to teleport to them!"), true);


    @Override
    public MConf load(MConf that) {
        super.load(that);
        return this;
    }

}
