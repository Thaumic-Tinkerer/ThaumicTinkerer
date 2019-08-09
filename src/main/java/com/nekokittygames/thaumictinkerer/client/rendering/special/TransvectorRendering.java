package com.nekokittygames.thaumictinkerer.client.rendering.special;

import com.nekokittygames.thaumictinkerer.common.items.ItemConnector;
import com.nekokittygames.thaumictinkerer.common.libs.LibMisc;
import com.nekokittygames.thaumictinkerer.common.tileentity.transvector.TileEntityTransvector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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

    /**
     * Event called on block rendering
     *
     * @param evt event object
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
            double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.getPartialTicks();
            double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.getPartialTicks();
            double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.getPartialTicks();

            GlStateManager.pushMatrix();
            GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

            GlStateManager.disableDepth();
            GlStateManager.enableTexture2D();
            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
            Minecraft.getMinecraft().entityRenderer.disableLightmap();
            GlStateManager.disableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.disableLighting();
            GlStateManager.disableAlpha();
            GlStateManager.glLineWidth(4);
            GlStateManager.color(1, 1, 1);

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            float blockX = pos.getX();
            float blockY = pos.getY();
            float blockZ = pos.getZ();
            float r = 1f;
            float g = 0.2f;
            float b = 0.3f;
            float a = 0.8f;
            buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
            drawOutline(buffer, blockX, blockY, blockZ, r, g, b, a);
            TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
            if (te instanceof TileEntityTransvector) {
                TileEntityTransvector transvector = (TileEntityTransvector) te;
                BlockPos linkPos = transvector.getTilePos();
                if (linkPos != null) {
                    blockX = linkPos.getX();
                    blockY = linkPos.getY();
                    blockZ = linkPos.getZ();
                    r = 0.2f;
                    g = 1f;
                    b = 0.3f;
                    a = 0.8f;

                    drawOutline(buffer, blockX, blockY, blockZ, r, g, b, a);
                }
            }
            tessellator.draw();

            Minecraft.getMinecraft().entityRenderer.enableLightmap();
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();

            GlStateManager.popMatrix();

        }


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
