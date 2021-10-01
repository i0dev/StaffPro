package com.i0dev.StaffPro.config;

import com.i0dev.StaffPro.Heart;
import com.i0dev.StaffPro.templates.AbstractConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageConfig extends AbstractConfiguration {

    String reloadUsage = "&cUsage: &7/StaffPro reload";
    String vanishUsage = "&cUsage: &7/vanish [player]";
    String unVanishUsage = "&cUsage: &7/unvanish [player]";
    String stripUsage = "&cUsage: &7/strip <player>";
    String RTPUsage = "&cUsage: &7/rtp";
    String freezeFormat = "&cUsage: &7/freeze <player>";
    String unFreezeFormat = "&cUsage: &7/unfreeze <player>";
    String examineFormat = "&cUsage: &7/examine <player>";
    String modModeUsage = "&cUsage: &7/modmode <player>";
    String pingUsage = "&cUsage: &7/ping [player]";

    String helpHeader = "&7&m-------&r&7[ &cStaffPro Help &7]&m-------";

    //examine
    String youCantExamine = "&7You cannot examine &c{player}&7 because they bypass examination.";
    String nowExamining = "&7You are now examining&c {player}";


    //freeze
    String alreadyFrozen = "&c{player}&7 is already frozen!";
    String notFrozen = "&c{player}&7 is not currently frozen!";
    String youFroze = "&7You froze &c{player}";
    String youUnFroze = "&7You unfroze &a{player}";
    String youBeenFrozen = "&cYou have been frozen by {player}";
    String youBeenUnFrozen = "&aYou have been unfrozen by  {player}";
    String youCantFreeze = "&7You cannot freeze &c{player}&7 because they bypass being frozen.";
    String frozeAnnounce = "&c{player} &7has been frozen by &c{staff}";
    String unfrozeAnnounce = "&c{player} &7has been unfrozen by &c{staff}";
    List<String> frozenMessage = Arrays.asList(
            "&f████&c█&f████",
            "&f███&c█&6█&c█&f███",
            "&f██&c█&6█&0█&6█&c█&f██ &c&lYOU ARE FROZEN",
            "&f██&c█&6█&0█&6█&c█&f██ &7Please join our Discord",
            "&f█&c█&6██&0█&6██&c█&f█ &f&ndiscord.gg/MCRivals",
            "&f█&c█&6█████&c█&f█",
            "&c█&6███&0█&6███&c█",
            "&c█████████",
            "&f█████████"
    );
    String cantDoWhileFrozen = "&7You cannot &c{action}&7 while frozen!";
    String cantRunCmdWhileFrozen = "&7You cannot run the command &c{command}&7 while frozen!";
    String frozenLoginAnnounce = "&7The player &c{player}&7 has logged&a online&7 while frozen!";
    String frozenLogoutAnnounce = "&7The player &c{player}&7 has logged&c offline&7 while frozen!";
    String frozenLoginMessage = "&7You have logged in the server while frozen.";

    //modmode
    String noLongerModMode = "&cYou are no longer in mod mode.";
    String nowModMode = "&aYou are now in mod mode.";
    String noLongerModModeAnnounce = "&c{player}&7 is no longer in mod mode.";
    String nowModModeAnnounce = "&a{player}&7 has entered mod mode.";
    String loggedInModMode = "&7You logged in while &ain mod mode&7.";
    //vanish
    String noLongerVanished = "&cYou are no longer vanished.";
    String nowVanished = "&aYou are now vanished.";
    String noLongerVanishedAnnounce = "&c{player}&7 is no longer vanished.";
    String nowVanishedAnnounce = "&a{player}&7 is now vanished.";
    String loggedInVanish = "&7You logged in while&a vanished&7.";

    //strip
    String youStripped = "&7You have stripped &c{player}";
    String youWereStripped = "&7You have been stripped of all your gear.";

    //rtp
    String noRealPlayers = "&cThere are currently no real players online.";
    String randomTeleported = "&7You have randomly teleported to &c{player}";

    //other
    String pingOf = "&7The ping of &c{player}&7 is &f{ping}ms";
    String reloadedConfig = "&7You have&a reloaded&7 the configuration.";
    String noPermission = "&cYou don not have permission to run that command.";
    String cantFindPlayer = "&cThe player: &f{player}&c cannot be found!";
    String invalidNumber = "&cThe number &f{num} &cis invalid! Try again.";
    String consoleError = "&cYou cannot run that command from console.";

    public MessageConfig(Heart heart, String path) {
        this.path = path;
        this.heart = heart;
    }
}
