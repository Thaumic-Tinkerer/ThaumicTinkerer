/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.misc;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Clientside Rendering events
 */
@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID,value = Side.CLIENT)
public class RenderEvents {

    public static TextureAtlasSprite MARK_SPRITE;

    /**
     * registers the textures for the mark
     * @param event {@link TextureStitchEvent.Pre} containing event arguments
     */
    @SubscribeEvent
    public static void AddTextures(final TextureStitchEvent.Pre event) {
        MARK_SPRITE=event.getMap().registerSprite(LibClientResources.MARK_TEXTURE);
    }
 /*   private static StructureRendererHandler structureHandler = new StructureRendererHandler();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        long time = System.nanoTime() / 1000000L;
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            structureHandler.handleStructureRadial(mc, time, event);
        }

    }

*/


}