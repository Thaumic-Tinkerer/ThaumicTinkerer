package com.nekokittygames.thaumictinkerer.common.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public interface ITTProxy {


    public void registerRenderers();
    public String localize(String unlocalized, Object... args);
    void init(FMLInitializationEvent event);

}
