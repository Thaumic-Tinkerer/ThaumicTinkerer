package com.nekokittygames.thaumictinkerer.client.misc;

import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = LibMisc.MOD_ID,value = Side.CLIENT)
public class RenderEvents {

    public static TextureAtlasSprite MARK_SPRITE;
    @SubscribeEvent
    public static void AddTextures(final TextureStitchEvent.Pre event) {
        MARK_SPRITE=event.getMap().registerSprite(new ResourceLocation("thaumictinkerer:textures/misc/mark.png"));
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