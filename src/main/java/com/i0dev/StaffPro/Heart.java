package com.i0dev.StaffPro;

import com.i0dev.StaffPro.commands.*;
import com.i0dev.StaffPro.config.GeneralConfig;
import com.i0dev.StaffPro.config.MessageConfig;
import com.i0dev.StaffPro.config.SPlayerStorage;
import com.i0dev.StaffPro.handlers.FrozenHandler;
import com.i0dev.StaffPro.handlers.MiscHandler;
import com.i0dev.StaffPro.handlers.ModModeHandler;
import com.i0dev.StaffPro.handlers.VanishHandler;
import com.i0dev.StaffPro.managers.*;
import com.i0dev.StaffPro.templates.AbstractCommand;
import com.i0dev.StaffPro.templates.AbstractConfiguration;
import com.i0dev.StaffPro.templates.AbstractListener;
import com.i0dev.StaffPro.templates.AbstractManager;
import com.i0dev.StaffPro.utility.ConfigUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Heart extends JavaPlugin {

    List<AbstractManager> managers = new ArrayList<>();
    List<AbstractConfiguration> configs = new ArrayList<>();


    boolean usingPapi;
    boolean usingMCoreFactions;
    boolean usingCombatTagPlus;

    @Override
    public void onEnable() {

        usingPapi = getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        Plugin factions = getServer().getPluginManager().getPlugin("Factions");
        usingMCoreFactions = factions != null && factions.getDescription().getVersion().startsWith("2.");
        usingCombatTagPlus = getServer().getPluginManager().isPluginEnabled("CombatTagPlus");

        managers.addAll(Arrays.asList(
                new MessageManager(this),
                new VanishManager(this),
                new SPlayerManager(this),
                new MiscManager(this),
                new VanishHandler(this),
                new MiscHandler(this),
                new FreezeManager(this),
                new ModModeManager(this),
                new CombatManager(this),
                new ModModeHandler(this),
                new FrozenHandler(this),
                new CmdFreeze(this, "freeze"),
                new CmdUnFreeze(this, "unfreeze"),
                new CmdModMode(this, "modmode"),
                new CmdStaffPro(this, "staffpro"),
                new CmdPing(this, "ping"),
                new CmdVanish(this, "vanish"),
                new CmdStrip(this, "strip"),
                new CmdUnVanish(this, "unvanish"),
                new CmdExamine(this, "examine"),
                new CmdRTP(this, "rtp")
        ));
        if (usingCombatTagPlus) {
            managers.add(new com.i0dev.StaffPro.hooks.CombatTagPlusHook(this));
        }

        configs.addAll(Arrays.asList(
                new GeneralConfig(this, getDataFolder() + "/General.json"),
                new MessageConfig(this, getDataFolder() + "/Messages.json"),
                new SPlayerStorage(this, getDataFolder() + "/Storage.json")
        ));

        reload();
        registerManagers();
        System.out.println("\u001B[32m" + this.getDescription().getName() + " by: " + this.getDescription().getAuthors().get(0) + " has been enabled.");
    }

    public void reload() {
        //                                     old         ~           new
        ArrayList<MessageManager.Pair<AbstractConfiguration, AbstractConfiguration>> toReplace = new ArrayList<>();
        configs.forEach(abstractConfiguration -> toReplace.add(new MessageManager.Pair<>(abstractConfiguration, ConfigUtil.load(abstractConfiguration, this))));
        toReplace.forEach(pairs -> {
            configs.remove(pairs.getKey());
            configs.add(pairs.getValue());
        });
    }


    @Override
    public void onDisable() {
        managers.forEach(AbstractManager::deinitialize);
        HandlerList.unregisterAll(this);
        managers.clear();
        configs.clear();
        Bukkit.getScheduler().cancelTasks(this);
        System.out.println("\u001B[31m" + this.getDescription().getName() + " by: " + this.getDescription().getAuthors().get(0) + " has been disabled.");
    }

    public <T> T getManager(Class<T> clazz) {
        return (T) managers.stream().filter(manager -> manager.getClass().equals(clazz)).findFirst().orElse(null);
    }

    public <T> T getConfig(Class<T> clazz) {
        return (T) configs.stream().filter(config -> config.getClass().equals(clazz)).findFirst().orElse(null);
    }

    public void registerManagers() {
        managers.forEach(abstractManager -> {
            if (abstractManager.isLoaded()) abstractManager.deinitialize();
            if (abstractManager instanceof AbstractListener)
                getServer().getPluginManager().registerEvents((AbstractListener) abstractManager, this);
            else if (abstractManager instanceof AbstractCommand) {
                getCommand(((AbstractCommand) abstractManager).getCommand()).setExecutor(((AbstractCommand) abstractManager));
                getCommand(((AbstractCommand) abstractManager).getCommand()).setTabCompleter(((AbstractCommand) abstractManager));
            }
            abstractManager.initialize();
            abstractManager.setLoaded(true);
        });
    }

    // Custom Configs

    public GeneralConfig cnf() {
        return getConfig(GeneralConfig.class);
    }

    public MessageConfig msg() {
        return getConfig(MessageConfig.class);
    }

    public SPlayerStorage getSPlayerStorage() {
        return getConfig(SPlayerStorage.class);
    }

    // Custom Managers

    public MessageManager msgManager() {
        return getManager(MessageManager.class);
    }

    public MiscManager getMiscManager() {
        return getManager(MiscManager.class);
    }

    public SPlayerManager getSPlayerManager() {
        return getManager(SPlayerManager.class);
    }

    public ModModeManager getModModeManager() {
        return getManager(ModModeManager.class);
    }

    public VanishManager getVanishManager() {
        return getManager(VanishManager.class);
    }


}
