package com.nekokittygames.thaumictinkerer.client.libs;

import net.minecraft.util.ResourceLocation;

public class LibClientResources {
    public static final String ASSET_NAME = "thaumictinkerer";
    public static final ResourceLocation GUI_MOBMAGNET = new ResourceLocation(ASSET_NAME, "textures/gui/mob_magnet.png");
    public static final ResourceLocation GUI_ENCHANTER = new ResourceLocation(ASSET_NAME, "textures/gui/enchanter.png");
    public static final ResourceLocation GUI_ANIMATION_TABLET = new ResourceLocation(ASSET_NAME, "textures/gui/animation_tablet.png");
    public static final ResourceLocation MISC_AT_CENTER = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/center.png");
    public static final ResourceLocation MISC_AT_LEFT = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/left.png");
    public static final ResourceLocation MISC_AT_RIGHT = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/right.png");
    public static final ResourceLocation MISC_AT_INDENT = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/facing_indent.png");
    ;


    public static final class Shaders {
        public static final String PREFIX_SHADER = "/assets/thaumictinkerer/shaders/";
        public static final String CHANGE_ALPHA_VERT = PREFIX_SHADER + "change_alpha.vert";
        public static final String CHANGE_ALPHA_FRAG = PREFIX_SHADER + "change_alpha.frag";
    }
}
