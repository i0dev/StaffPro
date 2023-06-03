package com.i0dev.staffpro;

import com.massivecraft.massivecore.Identified;
import com.massivecraft.massivecore.util.PermissionUtil;
import org.bukkit.permissions.Permissible;

public enum Perm implements Identified {

    BASECOMMAND,
    COMBATLIST,
    EXAMINE,
    FREEZE,
    FREEZE_BYPASS,
    MODMODE,
    PING,
    RANDOMTELEPORT,
    RANDOMTELEPORT_BYPASS,
    STAFF,
    STRIP,
    STRIP_BYPASS,
    UNFREEZE,
    UNVANISH,
    VANISH,
    VERSION;

    private final String id;

    Perm() {
        this.id = PermissionUtil.createPermissionId(StaffProPlugin.get(), this);
    }

    @Override
    public String getId() {
        return id;
    }

    public boolean has(Permissible permissible, boolean verboose) {
        return PermissionUtil.hasPermission(permissible, this, verboose);
    }

    public boolean has(Permissible permissible) {
        return PermissionUtil.hasPermission(permissible, this);
    }

}
