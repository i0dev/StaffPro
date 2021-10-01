package com.i0dev.StaffPro.config;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.templates.AbstractConfiguration;
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

    int examineEXPIndex = 4;
    String examineEXPTitle = "&7Experience: &c{exp}";
    String examineEXPMaterial = Material.EXP_BOTTLE.toString();
    short examineEXPData = 0;
    boolean examineEXPGlow = true;

    int examinePotionsIndex = 5;
    String examinePotionsTitle = "&7Potions: &c{pots}";
    String examinePotionsMaterial = Material.POTION.toString();
    short examinePotionsData = 16421;
    boolean examinePotionsGlow = true;

    int examineHealthIndex = 6;
    String examineHealthTitle = "&7Health: &c{health}";
    String examineHealthMaterial = Material.NETHER_STALK.toString();
    short examineHealthData = 0;
    boolean examineHealthGlow = true;

    int examineHungerIndex = 7;
    String examineHungerTitle = "&7Hunger: &c{hunger}";
    String examineHungerMaterial = Material.SPECKLED_MELON.toString();
    short examineHungerData = 0;
    boolean examineHungerGlow = true;

    int examineHeadIndex = 8;
    String examineHeadTitle = "{displayName}";

    // modmode inventory
    int modModeFreezeIndex = 0;
    int modModeCombatListIndex = 1;
    int modModeVanishIndex = 4;
    int modMOdeExamineIndex = 7;
    int modModeRandomTPIndex = 8;

    public GeneralConfig(Heart heart, String path) {
        this.path = path;
        this.heart = heart;
    }

}
