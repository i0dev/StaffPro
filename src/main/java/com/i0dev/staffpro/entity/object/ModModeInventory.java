package com.i0dev.staffpro.entity.object;

import com.massivecraft.massivecore.util.MUtil;
import org.bukkit.Material;

public class ModModeInventory {
    public ConfigItem freezeItem = new ConfigItem("&b&lFreeze", 1, 0, Material.BLUE_ICE, MUtil.list("", "&7Click on a player with this item to freeze them."), true);
    public ConfigItem combatListItem = new ConfigItem("&b&lCombat List", 1, 1, Material.CLOCK, MUtil.list("", "&7Click to open the combat list."), true);
    public ConfigItem vanishEnableItem = new ConfigItem("&9&lVanish: &c&lINACTIVE", 1, 4, Material.RED_DYE, MUtil.list("", "&7Click to enable vanish."), true);
    public ConfigItem vanishDisableItem = new ConfigItem("&9&lVanish: &a&lACTIVE", 1, 4, Material.LIME_DYE, MUtil.list("", "&7Click to disable vanish."), true);
    public ConfigItem examineItem = new ConfigItem("&c&lExamine Player", 1, 7, Material.ENDER_CHEST, MUtil.list("", "&7Click on a player to examine them!"), true);
    public ConfigItem worldEditItem = new ConfigItem("&b&lWorldEdit", 1, 2, Material.WOODEN_AXE, MUtil.list("", "&7Use this like a world edit wand"), true);
    public ConfigItem stripItem = new ConfigItem("&a&lStrip Player", 1, 6, Material.SHEARS, MUtil.list("", "&7Click on a player to strip them!"), true);
    public ConfigItem randomTeleportItem = new ConfigItem("&b&lRandom Teleport", 1, 8, Material.NETHER_STAR, MUtil.list("", "&7Click to teleport to a random player!"), true);

    public ConfigColorItem modModeHelmet = new ConfigColorItem("&b&lModMode Helmet", 1, 3, Material.LEATHER_HELMET, MUtil.list("", "&7This epic armour belongs to an all mighty staff member!"), true, "FF0000");
    public ConfigColorItem modModeChestplate = new ConfigColorItem("&b&lModMode Chestplate", 1, 2, Material.LEATHER_CHESTPLATE, MUtil.list("", "&7This epic armour belongs to an all mighty staff member!"), true, "FF0000");
    public ConfigColorItem modModeLeggings = new ConfigColorItem("&b&lModMode Leggings", 1, 1, Material.LEATHER_LEGGINGS, MUtil.list("", "&7This epic armour belongs to an all mighty staff member!"), true, "FF0000");
    public ConfigColorItem modModeBoots = new ConfigColorItem("&b&lModMode Boots", 1, 0, Material.LEATHER_BOOTS, MUtil.list("", "&7This epic armour belongs to an all mighty staff member!"), true, "FF0000");
}
