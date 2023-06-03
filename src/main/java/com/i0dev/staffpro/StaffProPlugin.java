package com.i0dev.staffpro;

import com.i0dev.staffpro.entity.MConfColl;
import com.i0dev.staffpro.entity.MLangColl;
import com.i0dev.staffpro.entity.MPlayerColl;
import com.massivecraft.massivecore.MassivePlugin;
import com.massivecraft.massivecore.collections.MassiveList;

import java.util.List;

public class StaffProPlugin extends MassivePlugin {

    private static StaffProPlugin i;

    public StaffProPlugin() {
        StaffProPlugin.i = this;
    }

    public static StaffProPlugin get() {
        return i;
    }

    @Override
    public void onEnableInner() {
        this.activateAuto();
    }

    @Override
    public List<Class<?>> getClassesActiveColls() {
        return new MassiveList<>(
                MConfColl.class,
                MLangColl.class,
                MPlayerColl.class
        );
    }


}