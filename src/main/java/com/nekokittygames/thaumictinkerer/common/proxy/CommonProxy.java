package com.nekokittygames.thaumictinkerer.common.proxy;

import net.minecraft.util.text.translation.I18n;

public class CommonProxy implements ITTProxy {


    @Override
    public void registerRenderers() {

    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }
}
