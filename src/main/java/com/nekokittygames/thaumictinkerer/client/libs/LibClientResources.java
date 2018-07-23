package com.nekokittygames.thaumictinkerer.client.libs;

import net.minecraft.util.ResourceLocation;

public class LibClientResources {
    public static final ResourceLocation GUI_MOBMAGNET=new ResourceLocation("thaumictinkerer","textures/gui/mob_magnet.png");
    public static final ResourceLocation GUI_ENCHANTER = new ResourceLocation("thaumictinkerer", "textures/gui/enchanter.png");


    public static final class Shaders
    {
        public static final String PREFIX_SHADER = "/assets/thaumictinkerer/shaders/";
        public static final String CHANGE_ALPHA_VERT=PREFIX_SHADER+"change_alpha.vert";
        public static final String CHANGE_ALPHA_FRAG=PREFIX_SHADER+"change_alpha.frag";
    }
}
