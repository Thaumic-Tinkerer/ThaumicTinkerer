package com.nekokittygames.thaumictinkerer.common.proxy;

import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static com.nekokittygames.thaumictinkerer.ThaumicTinkerer.instance;

public class CommonProxy implements ITTProxy {


    @Override
    public void registerRenderers() {

    }

    @Override
    public String localize(String unlocalized, Object... args) {
        return I18n.translateToLocalFormatted(unlocalized, args);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiProxy());
    }

    @Override
    public void preInit(FMLPreInitializationEvent event) {

    }
}
