package com.nekokittygames.thaumictinkerer.client.rendering.special;

import com.nekokittygames.thaumictinkerer.client.misc.RenderEvents;
import com.nekokittygames.thaumictinkerer.common.items.ItemConnector;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

/**
 * Renders the transvector interface linking
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = LibMisc.MOD_ID)
public class TransvectorRendering {
public static float lastLineTick=0;
    /**
     * Event called on block rendering
     *
     * @param evt {@link RenderWorldLastEvent}  event object
     */
    @SubscribeEvent
    public static void renderBlocks(final RenderWorldLastEvent evt) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!(stack.getItem() instanceof ItemConnector)) {
            stack = player.getHeldItem(EnumHand.OFF_HAND);
            if (!(stack.getItem() instanceof ItemConnector))
                return;
        }
        BlockPos pos = ItemConnector.getTarget(stack);
        if (pos != null) {
            float ticks=(Minecraft.getMinecraft().world.getTotalWorldTime()+evt.getPartialTicks())/10f;
            double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.getPartialTicks();
            double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.getPartialTicks();
            double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.getPartialTicks();

            GlStateManager.pushMatrix();

            GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

            GlStateManager.disableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            Minecraft.getMinecraft().entityRenderer.disableLightmap();
            GlStateManager.glLineWidth(4);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            float blockX = pos.getX();
            float blockY = pos.getY();
            float blockZ = pos.getZ();

            float r = 0.5f + 0.5f*MathHelper.sin(ticks);
            float g = 0.2f;
            float b = 0.3f;
            float a = 0.5f;
            //Minecraft.getMinecraft().getTextureManager().bindTexture(LibClientResources.MARK_TEXTURE);
            buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR);
            drawTexturedOutline(buffer, blockX, blockY, blockZ, r, g, b, a,RenderEvents.MARK_SPRITE);
            TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
            if (te instanceof TileEntityTransvector) {
                TileEntityTransvector transvector = (TileEntityTransvector) te;
                BlockPos linkPos = transvector.getTilePos();
                if (linkPos != null) {
                    blockX = linkPos.getX();
                    blockY = linkPos.getY();
                    blockZ = linkPos.getZ();
                    r = 0.2f;
                    g = 0.5f + 0.5f*MathHelper.sin(ticks);
                    b = 0.3f;
                    a = 0.5f;

                    drawTexturedOutline(buffer, blockX, blockY, blockZ, r, g, b, a, RenderEvents.MARK_SPRITE);
                    tessellator.draw();
                    buffer = tessellator.getBuffer();
                    GlStateManager.disableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.disableAlpha();
                    GlStateManager.enableTexture2D();
                    buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);

                    drawLine(buffer,pos.getX(),pos.getY(),pos.getZ(),blockX,blockY,blockZ,1f,0.2f,0.3f,0.8f,0.2f,1f,0.3f,0.8f);
                }
            }
            tessellator.draw();

           Minecraft.getMinecraft().entityRenderer.enableLightmap();
           GlStateManager.enableDepth();

           GlStateManager.popMatrix();

        }


    }

    private static void drawLine(BufferBuilder buffer, int originX, int originY, int originZ, float blockX, float blockY, float blockZ, float r, float g, float b, float a,float gr, float gg, float gb, float ga) {
        buffer.pos(originX+0.5f,originY+0.5f,originZ+0.5f).color(r,g,b,a).endVertex();
        buffer.pos(blockX+0.5f,blockY+0.5f,blockZ+0.5f).color(gr,gg,gb,ga).endVertex();
    }


    private static void drawTexturedOutline(BufferBuilder buffer, float mx, float my, float mz, float r, float g, float b, float a, TextureAtlasSprite texture )
    {
        //GlStateManager.enableTexture2D();


        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float u0=texture.getInterpolatedU(0);
        float u1=texture.getInterpolatedU(16);
        float v0=texture.getInterpolatedV(0);
        float v1=texture.getInterpolatedV(16);

        // Face 1
        buffer.pos(mx, my, mz).tex(u0,v0).color(r,g,b,a).endVertex(); // Triangle 1: 0,0,0
        buffer.pos(mx,my+1,mz).tex(u1,v0).color(r,g,b,a).endVertex(); // 0,1,0
        buffer.pos(mx+1,my,mz).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,0,0
        buffer.pos(mx, my+1, mz).tex(u1,v0).color(r,g,b,a).endVertex(); // Triangle 2: 0,1,0
        buffer.pos(mx+1,my+1,mz).tex(u1,v1).color(r,g,b,a).endVertex(); // 1,1,0
        buffer.pos(mx+1,my,mz).tex(u0,v1).color(r,g,b,a).endVertex(); //1,0,0

        // Face 2
        buffer.pos(mx+1, my, mz).tex(u0,v0).color(r,g,b,a).endVertex(); // Triangle 1: 1,0,0
        buffer.pos(mx+1,my+1,mz).tex(u1,v0).color(r,g,b,a).endVertex(); // 1,1,0
        buffer.pos(mx+1,my,mz+1).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,0,1
        buffer.pos(mx+1, my+1, mz).tex(u1,v0).color(r,g,b,a).endVertex(); // Triangle 2: 1,1,0
        buffer.pos(mx+1,my+1,mz+1).tex(u1,v1).color(r,g,b,a).endVertex(); // 1,1,1
        buffer.pos(mx+1,my,mz+1).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,0,1

        // Face 3
        buffer.pos(mx+1, my, mz+1).tex(u0,v0).color(r,g,b,a).endVertex(); // Triangle 1: 1,0,1
        buffer.pos(mx+1,my+1,mz+1).tex(u1,v0).color(r,g,b,a).endVertex(); // 1,1,1
        buffer.pos(mx,my,mz+1).tex(u0,v1).color(r,g,b,a).endVertex(); // 0,0,1
        buffer.pos(mx+1, my+1, mz+1).tex(u1,v0).color(r,g,b,a).endVertex(); // Triangle 2: 1,1,1
        buffer.pos(mx,my+1,mz+1).tex(u1,v1).color(r,g,b,a).endVertex(); // 0,1,1
        buffer.pos(mx,my,mz+1).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,0,1

        // Face 4
        buffer.pos(mx, my, mz+1).tex(u0,v0).color(r,g,b,a).endVertex(); // Triangle 1: 0,0,1
        buffer.pos(mx,my+1,mz+1).tex(u1,v0).color(r,g,b,a).endVertex(); // 0,1,1
        buffer.pos(mx,my,mz).tex(u0,v1).color(r,g,b,a).endVertex(); // 0,0,0
        buffer.pos(mx, my+1, mz+1).tex(u1,v0).color(r,g,b,a).endVertex(); // Triangle 2: 0,1,1
        buffer.pos(mx,my+1,mz).tex(u1,v1).color(r,g,b,a).endVertex(); // 0,1,0
        buffer.pos(mx,my,mz).tex(u0,v1).color(r,g,b,a).endVertex(); // 0,0,0

        // Face 5
        buffer.pos(mx, my+1, mz).tex(u0,v0).color(r,g,b,a).endVertex(); // Triangle 1: 0,1,0
        buffer.pos(mx,my+1,mz+1).tex(u1,v0).color(r,g,b,a).endVertex(); // 0,1,1
        buffer.pos(mx+1,my+1,mz).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,1,0
        buffer.pos(mx, my+1, mz+1).tex(u1,v0).color(r,g,b,a).endVertex(); // Triangle 2: 0,1,1
        buffer.pos(mx+1,my+1,mz+1).tex(u1,v1).color(r,g,b,a).endVertex(); // 1,1,1
        buffer.pos(mx+1,my+1,mz).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,1,0

        // Face 6
        buffer.pos(mx, my, mz+1).tex(u0,v0).color(r,g,b,a).endVertex(); // Triangle 1: 0,0,1
        buffer.pos(mx,my,mz).tex(u1,v0).color(r,g,b,a).endVertex(); // 0,0,0
        buffer.pos(mx+1,my,mz+1).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,0,1
        buffer.pos(mx, my, mz).tex(u1,v0).color(r,g,b,a).endVertex(); // Triangle 2: 0,0,0
        buffer.pos(mx+1,my,mz).tex(u1,v1).color(r,g,b,a).endVertex(); // 1,0,0
        buffer.pos(mx+1,my,mz+1).tex(u0,v1).color(r,g,b,a).endVertex(); // 1,0,1
        //
        //GlStateManager.disableTexture2D();

    }

    /**
     * Draws outline around an area
     *
     * @param buffer current Vertex buffer
     * @param mx     minimum xPos
     * @param my     minimum yPos
     * @param mz     minimum zPos
     * @param r      red value of colour
     * @param g      green value of colour
     * @param b      lue value of colour
     * @param a      alpha value of colour
     */
    private static void drawOutline(BufferBuilder buffer, float mx, float my, float mz, float r, float g, float b, float a) {

        buffer.pos(mx, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.pos(mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.pos(mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my + 1, mz).color(r, g, b, a).endVertex();

        buffer.pos(mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx + 1, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my, mz + 1).color(r, g, b, a).endVertex();
        buffer.pos(mx, my + 1, mz + 1).color(r, g, b, a).endVertex();
    }
}
