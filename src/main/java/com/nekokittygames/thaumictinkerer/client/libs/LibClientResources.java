/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.libs;

import net.minecraft.util.ResourceLocation;

/**
 * The Client Resource Library
 */
public class LibClientResources {
    private static final String ASSET_NAME = "thaumictinkerer";
    /**
     * The constant GUI_MOBMAGNET.
     */
    public static final ResourceLocation GUI_MOBMAGNET = new ResourceLocation(ASSET_NAME, "textures/gui/mob_magnet.png");
    /**
     * The constant GUI_ENCHANTER.
     */
    public static final ResourceLocation GUI_ENCHANTER = new ResourceLocation(ASSET_NAME, "textures/gui/enchanter.png");
    /**
     * The constant GUI_ANIMATION_TABLET.
     */
    public static final ResourceLocation GUI_ANIMATION_TABLET = new ResourceLocation(ASSET_NAME, "textures/gui/animation_tablet.png");
    /**
     * The constant MISC_AT_CENTER.
     */
    public static final ResourceLocation MISC_AT_CENTER = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/center.png");
    /**
     * The constant MISC_AT_LEFT.
     */
    public static final ResourceLocation MISC_AT_LEFT = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/left.png");
    /**
     * The constant MISC_AT_RIGHT.
     */
    public static final ResourceLocation MISC_AT_RIGHT = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/right.png");
    /**
     * The constant MISC_AT_INDENT.
     */
    public static final ResourceLocation MISC_AT_INDENT = new ResourceLocation(ASSET_NAME, "textures/misc/animation_tablet/facing_indent.png");
    /**
     * The constant MARK_TEXTURE.
     */
    public static final ResourceLocation MARK_TEXTURE=new ResourceLocation(ASSET_NAME,"misc/mark");


    /**
     * The type Shaders.
     */
    public static final class Shaders {
        /**
         * The constant PREFIX_SHADER.
         */
        public static final String PREFIX_SHADER = "/assets/thaumictinkerer/shaders/";
        /**
         * The constant CHANGE_ALPHA_VERT.
         */
        public static final String CHANGE_ALPHA_VERT = PREFIX_SHADER + "change_alpha.vert";
        /**
         * The constant CHANGE_ALPHA_FRAG.
         */
        public static final String CHANGE_ALPHA_FRAG = PREFIX_SHADER + "change_alpha.frag";
    }
}
