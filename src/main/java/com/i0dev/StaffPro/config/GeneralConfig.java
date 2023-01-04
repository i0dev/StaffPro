package com.i0dev.StaffPro.config;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.templates.AbstractConfiguration;
import com.i0dev.StaffPro.utility.Utility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Material;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeneralConfig extends AbstractConfiguration {

    boolean playerSpeedLimiterEnabled = true;
    double playerSpeedLimiterMaxMoveSpeed = 5.0;
    double playerSpeedLimiterMaxFlySpeed = 3.0;
    boolean teleportPlayerToOGPositionWhenLeaveModMode = false;
    String vanishActionBarText = "&a&lVanished";
    String modModeActionBarText = "&c&lMod Mode";
    String bothActionBarText = "&c&lMod Mode &8| &a&lVanished";
    String frozenActionBarText = "&b&lFrozen";
    List<String> frozenCommandWhitelist = Arrays.asList(
            "/msg",
            "/r",
            "/m",
            "/discord"
    );
    boolean chatFilterEnabled = true;
    List<String> chatFilterBlacklistedWords = Arrays.asList(
            "nigga",
            "n1gga",
            "nigger",
            "nigg",
            "faggot",
            "fag"
    );

    // examine inventory
    Utility.IndexableConfigItem examineEXPItem = new Utility.IndexableConfigItem(
            "&7Experience: &c{exp}",
            1,
            (short) 0,
            Material.EXP_BOTTLE.toString(),
            Arrays.asList(),
            true,
            4
    );

    Utility.IndexableConfigItem examinePotionsItem = new Utility.IndexableConfigItem(
            "&7Potions: &c{pots}",
            1,
            (short) 16421,
            Material.POTION.toString(),
            Arrays.asList(),
            true,
            5
    );


    Utility.IndexableConfigItem examineHealthItem = new Utility.IndexableConfigItem(
            "&7Health: &c{health}",
            1,
            (short) 0,
            Material.NETHER_STALK.toString(),
            Arrays.asList(),
            true,
            6
    );

    Utility.IndexableConfigItem examineHungerItem = new Utility.IndexableConfigItem(
            "&7Hunger: &c{hunger}",
            1,
            (short) 16421,
            Material.COOKED_BEEF.toString(),
            Arrays.asList(),
            true,
            7
    );

    Utility.IndexableConfigItem examineHeadItem = new Utility.IndexableConfigItem(
            "{displayName}",
            1,
            (short) 0,
            Material.SKULL.toString(),
            Arrays.asList(),
            true,
            8
    );

    // modmode inventory
    Utility.IndexableConfigItem modModeFreezeItem = new Utility.IndexableConfigItem(
            "&b&lFreeze",
            1,
            (short) 0,
            Material.PACKED_ICE.toString(),
            Arrays.asList("", "&7Click on a player with this item to freeze them."),
            true,
            0
    );
    Utility.IndexableConfigItem modModeCombatListItem = new Utility.IndexableConfigItem(
            "&d&lCombat List",
            1,
            (short) 0,
            Material.WATCH.toString(),
            Arrays.asList("", "&7Click to get a combat list."),
            true,
            1
    );
    Utility.IndexableConfigItem modModeVanishItem = new Utility.IndexableConfigItem(
            "&9&lVanish: &c&lINACTIVE",
            1,
            (short) 1,
            Material.INK_SACK.toString(),
            Arrays.asList("", "&7Click to toggle vanish."),
            true,
            4
    );
    Utility.IndexableConfigItem modModeUnVanishItem = new Utility.IndexableConfigItem(
            "&9&lVanish: &a&lACTIVE",
            1,
            (short) 10,
            Material.INK_SACK.toString(),
            Arrays.asList("", "&7Click to toggle vanish."),
            true,
            4
    );
    Utility.IndexableConfigItem modModeExamineItem = new Utility.IndexableConfigItem(
            "&c&lExamine Player",
            1,
            (short) 0,
            Material.ENDER_CHEST.toString(),
            Arrays.asList("", "&7Click on a player to examine them!"),
            true,
            7
    );

    Utility.IndexableConfigItem modModeWorldEditItem = new Utility.IndexableConfigItem(
            "&4&lWorld Edit Wand",
            1,
            (short) 0,
            Material.WOOD_AXE.toString(),
            Arrays.asList("", "&7Use this wand like a world edit wand!"),
            true,
            2
    );

    Utility.IndexableConfigItem modModeStripItem = new Utility.IndexableConfigItem(
            "&a&lStrip Player",
            1,
            (short) 0,
            Material.IRON_FENCE.toString(),
            Arrays.asList("", "&7Click on a player to strip them!"),
            true,
            6
    );

    Utility.IndexableConfigItem modModeRandomTPItem = new Utility.IndexableConfigItem(
            "&e&lRandom Teleport",
            1,
            (short) 0,
            Material.NETHER_STAR.toString(),
            Arrays.asList("", "&7Click to randomly teleport."),
            true,
            8
    );

    Utility.ColorConfigItem modModeHelmet = new Utility.ColorConfigItem(
            "&4&lModMode Helmet",
            1,
            (short) 0,
            Material.LEATHER_HELMET.toString(),
            Arrays.asList("", "&7This epic armour belongs to an all mighty staff member!"),
            true,
            250,
            0,
            0
    );

    Utility.ColorConfigItem modModeChestplate = new Utility.ColorConfigItem(
            "&4&lModMode Chestplate",
            1,
            (short) 0,
            Material.LEATHER_CHESTPLATE.toString(),
            Arrays.asList("", "&7This epic armour belongs to an all mighty staff member!"),
            true,
            250,
            0,
            0
    );

    Utility.ColorConfigItem modModeLeggings = new Utility.ColorConfigItem(
            "&4&lModMode Leggings",
            1,
            (short) 0,
            Material.LEATHER_LEGGINGS.toString(),
            Arrays.asList("", "&7This epic armour belongs to an all mighty staff member!"),
            true,
            250,
            0,
            0
    );

    Utility.ColorConfigItem modModeBoots = new Utility.ColorConfigItem(
            "&4&lModMode Boots",
            1,
            (short) 0,
            Material.LEATHER_BOOTS.toString(),
            Arrays.asList("", "&7This epic armour belongs to an all mighty staff member!"),
            true,
            250,
            0,
            0
    );


    public String combatGUITitle = "&4&lCombat List";

    public String combatGUIHeadName = "&c{displayName}";
    public List<String> combatGuiHeadLore = Arrays.asList(
            "",
            "&c&lInformation:",
            "&4 * &7Faction: &c{faction}",
            "&4 * &7Tagged with: &c{taggedName}",
            "",
            "&7Click to teleport to them!"
    );


    public GeneralConfig(Heart heart, String path) {
        this.path = path;
        this.heart = heart;
    }

}
