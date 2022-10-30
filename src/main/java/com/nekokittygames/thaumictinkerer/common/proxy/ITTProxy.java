package com.nekokittygames.thaumictinkerer.common.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface ITTProxy {


    void registerRenderers();

    String localize(String translationKey, Object... args);

    void init(FMLInitializationEvent event);

    void preInit(FMLPreInitializationEvent event);

}
