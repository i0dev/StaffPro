package com.i0dev.staffpro.entity;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.StaffProPlugin;
import com.i0dev.staffpro.entity.object.ModModeInventory;
import com.i0dev.staffpro.task.TaskDisplayActionbar;
import com.i0dev.staffpro.util.ItemBuilder;
import com.i0dev.staffpro.util.Utils;
import com.massivecraft.massivecore.store.SenderEntity;
import com.massivecraft.massivecore.util.MUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@Getter
@Setter
public class MPlayer extends SenderEntity<MPlayer> {

    public static MPlayer get(Object oid) {
        return MPlayerColl.get().get(oid);
    }

    private long lastActivityMillis = System.currentTimeMillis();
    private boolean frozen = false;
    private boolean vanished = false;
    private boolean modmode = false;
    private String savedInventory = "";
    private GameMode savedGameMode = GameMode.SURVIVAL;

    @Override
    public MPlayer load(MPlayer that) {
        this.setLastActivityMillis(that.lastActivityMillis);
        this.setFrozen(that.frozen);
        this.setVanished(that.vanished);
        this.setModmode(that.modmode);
        this.setSavedInventory(that.savedInventory);
        this.setSavedGameMode(that.savedGameMode);
        return this;
    }

    private void forceActionbar() {
        TaskDisplayActionbar.get().displayActionBar(this.getPlayer());
    }

    public void enableModMode() {
        setModmode(true);
        this.changed();
        this.forceActionbar();

        // Save
        setSavedInventory(Utils.encrypt(getPlayer().getInventory().getContents()));
        setSavedGameMode(getPlayer().getGameMode());

        // Set ModMode Inventory
        this.setModModeInventory();
        getPlayer().setGameMode(GameMode.CREATIVE);
    }

    public void setModModeInventory() {
        ModModeInventory cnf = MConf.get().modModeInventory;
        PlayerInventory inventory = getPlayer().getInventory();
        inventory.clear();
        inventory.setItem(cnf.freezeItem.slot, Utils.setDataValue(ItemBuilder.fromConfigItem(cnf.freezeItem), "staffpro_modmode_item", "freeze"));
        inventory.setItem(cnf.combatListItem.slot, Utils.setDataValue(ItemBuilder.fromConfigItem(cnf.combatListItem), "staffpro_modmode_item", "combatlist"));
        setModModeVanishItem();
        inventory.setItem(cnf.examineItem.slot, Utils.setDataValue(ItemBuilder.fromConfigItem(cnf.examineItem), "staffpro_modmode_item", "examine"));
        inventory.setItem(cnf.worldEditItem.slot, ItemBuilder.fromConfigItem(cnf.worldEditItem));
        inventory.setItem(cnf.stripItem.slot, Utils.setDataValue(ItemBuilder.fromConfigItem(cnf.stripItem), "staffpro_modmode_item", "strip"));
        inventory.setItem(cnf.randomTeleportItem.slot, Utils.setDataValue(ItemBuilder.fromConfigItem(cnf.randomTeleportItem), "staffpro_modmode_item", "randomteleport"));

        ItemStack[] armorContents = inventory.getArmorContents();
        armorContents[cnf.modModeHelmet.slot] = ItemBuilder.fromConfigColorItem(cnf.modModeHelmet);
        armorContents[cnf.modModeChestplate.slot] = ItemBuilder.fromConfigColorItem(cnf.modModeChestplate);
        armorContents[cnf.modModeLeggings.slot] = ItemBuilder.fromConfigColorItem(cnf.modModeLeggings);
        armorContents[cnf.modModeBoots.slot] = ItemBuilder.fromConfigColorItem(cnf.modModeBoots);
        inventory.setArmorContents(armorContents);
    }

    public void setModModeVanishItem() {
        PlayerInventory inventory = getPlayer().getInventory();
        ModModeInventory cnf = MConf.get().modModeInventory;
        if (isVanished())
            inventory.setItem(cnf.vanishDisableItem.slot, Utils.setDataValue(ItemBuilder.fromConfigItem(cnf.vanishDisableItem), "staffpro_modmode_item", "unvanish"));
        else
            inventory.setItem(cnf.vanishEnableItem.slot, Utils.setDataValue(ItemBuilder.fromConfigItem(cnf.vanishEnableItem), "staffpro_modmode_item", "vanish"));
    }

    public void disableModMode() {
        setModmode(false);
        this.changed();
        this.forceActionbar();

        getPlayer().getInventory().clear();

        // Restore
        getPlayer().getInventory().setContents(Utils.decrypt(this.savedInventory));
        getPlayer().setGameMode(this.savedGameMode);

        this.setSavedInventory("");
    }

    public void enableVanish() {
        if (!vanished) {
            setVanished(true);
            this.changed();
        }
        this.forceActionbar();
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> !Perm.STAFF.has(player))
                .filter(player -> !player.isOp())
                .forEach(player -> player.hidePlayer(StaffProPlugin.get(), this.getPlayer()));
    }

    public void disableVanish() {
        setVanished(false);
        this.changed();
        this.forceActionbar();
        Bukkit.getOnlinePlayers().forEach(player -> player.showPlayer(StaffProPlugin.get(), this.getPlayer()));
    }


    public void setLastActivityMillis(long lastActivityMillis) {
        // Detect No change
        if (MUtil.equals(this.lastActivityMillis, lastActivityMillis)) return;

        // Apply
        this.lastActivityMillis = lastActivityMillis;

        // Mark as changed
        this.changed();
    }

    public void setLastActivityMillis() {
        this.setLastActivityMillis(System.currentTimeMillis());
    }

}
