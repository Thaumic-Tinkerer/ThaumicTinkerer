package com.nekokittygames.thaumictinkerer.common.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEICompat implements IModPlugin {

    public static void initJEI() {


    }

    @Override
    public void register(IModRegistry registry) {
        registry.addAdvancedGuiHandlers(new JEIEnchanterHandler());
    }
}
