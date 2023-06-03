package com.i0dev.staffpro.entity.object;

import org.bukkit.Material;
import org.bukkit.potion.PotionType;

public class ExamineInventory {
    public String title = "&8Examining &4%player%";
    public ConfigItem expItem = new ConfigItem("&7Experience: &c%exp%", 1, 5, Material.EXPERIENCE_BOTTLE, null, true);
    public ConfigItem healthItem = new ConfigItem("&7Health: &c%health%", 1, 6, Material.REDSTONE, null, true);
    public ConfigItem hungerItem = new ConfigItem("&7Hunger: &c%hunger%", 1, 7, Material.COOKED_BEEF, null, true);
    public ConfigItem playerHeadItem = new ConfigItem("&7%player%", 1, 8, Material.PLAYER_HEAD, null, true);
}
