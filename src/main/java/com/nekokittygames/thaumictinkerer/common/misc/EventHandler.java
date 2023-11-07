package com.nekokittygames.thaumictinkerer.common.misc;


import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
    public static void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new LivingEvents());
    }
}
