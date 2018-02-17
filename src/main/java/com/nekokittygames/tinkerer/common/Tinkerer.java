package com.nekokittygames.tinkerer.common;

import com.nekokittygames.tinkerer.common.libs.LibMisc;
import com.nekokittygames.tinkerer.common.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = LibMisc.MOD_ID,version = LibMisc.MOD_VERSION,dependencies = LibMisc.MOD_DEPENDENCIES,guiFactory = "com.nekokittygames.tinkerer.client.config.GuiConfigFactoryTinkerer")
public class Tinkerer {

    public static Logger TTLog;
    @Mod.Instance()
    public static Tinkerer instance;

    @SidedProxy(clientSide = "com.nekokittygames.tinkerer.client.proxy.ClientProxy",serverSide = "com.nekokittygames.tinkerer.common.proxy.CommonProxy")
    public static IProxy iProxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        TTLog=event.getModLog();
    }
}
