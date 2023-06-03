package com.i0dev.staffpro.integration.combattagplus;

import com.massivecraft.massivecore.Integration;

public class IntegrationCombatTagPlus extends Integration {

    private static IntegrationCombatTagPlus i = new IntegrationCombatTagPlus();

    private IntegrationCombatTagPlus() {
        setPluginName("CombatTagPlus");
    }

    public static IntegrationCombatTagPlus get() {
        return i;
    }

    @Override
    public EngineCombatTagPlus getEngine() {
        return EngineCombatTagPlus.get();
    }

}
