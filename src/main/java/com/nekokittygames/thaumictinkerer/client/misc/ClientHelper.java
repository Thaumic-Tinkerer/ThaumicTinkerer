package com.nekokittygames.thaumictinkerer.client.misc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

/**
 * Helper class for the client
 */
public class ClientHelper {

    private static RenderItem renderItem;

    /**
     * Gets the Render item, caching it if needed
     *
     * @return Render item, from cache or fresh
     */
    public static RenderItem getRenderItem() {
        if (renderItem == null) {
            renderItem = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_175621_X");
        }
        return renderItem;
    }

    /**
     * Converts a <c>EnumFacing</c> to degrees
     *
     * @param facing facing to convert
     * @return facing in degrees
     */
    public static float toDegrees(EnumFacing facing) {
        if (facing == null)
            return 0.0f;
        switch (facing) {
            case SOUTH:
                return 90.0F;

            case NORTH:
                return 270.0F;

            case WEST:
                return 0.0F;

            case EAST:
                return 180.0F;
            default:
                return 0.0f;
        }
    }

    /**
     * Renders a list of tooltips
     * @param x xPos
     * @param y yPos
     * @param tooltipData list of tooltips
     */
    public static void renderTooltip(int x, int y, List<String> tooltipData) {
        int color = 0x505000ff;
        int color2 = 0xf0100010;

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);

        if (!tooltipData.isEmpty()) {
            int var5 = 0;
            int var6;
            int var7;
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            for (var6 = 0; var6 < tooltipData.size(); ++var6) {
                var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
                if (var7 > var5)
                    var5 = var7;
            }
            var6 = x + 12;
            var7 = y - 12;
            int var9 = 8;
            if (tooltipData.size() > 1)
                var9 += 2 + (tooltipData.size() - 1) * 10;
            float z = 300.0F;
            drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
            drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
            drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
            int var12 = (color & 0xFFFFFF) >> 1 | color & -16777216;
            drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, color, var12);
            drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, color, var12);
            drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, color, color);
            drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);
            for (int var13 = 0; var13 < tooltipData.size(); ++var13) {
                String var14 = tooltipData.get(var13);
                fontRenderer.drawString(var14, var6, var7, -1, true);
                if (var13 == 0)
                    var7 += 2;
                var7 += 10;
            }
        }
    }

    /**
     * Draws the gradient rect for the tooltips
     *
     * @param x       xPos of origin point
     * @param y       yPos of origin point
     * @param z       zPos of origin point
     * @param xMax    xPos of max point
     * @param yMax    yPos of max point
     * @param colour1 origin colour
     * @param colour2 colour to gradient to
     */
    private static void drawGradientRect(int x, int y, float z, int xMax, int yMax, int colour1, int colour2) {
        float var7 = (colour1 >> 24 & 255) / 255.0F;
        float var8 = (colour1 >> 16 & 255) / 255.0F;
        float var9 = (colour1 >> 8 & 255) / 255.0F;
        float var10 = (colour1 & 255) / 255.0F;
        float var11 = (colour2 >> 24 & 255) / 255.0F;
        float var12 = (colour2 >> 16 & 255) / 255.0F;
        float var13 = (colour2 >> 8 & 255) / 255.0F;
        float var14 = (colour2 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(xMax, y, z).color(var8, var9, var10, var7).endVertex();
        bufferbuilder.pos(x, y, z).color(var8, var9, var10, var7).endVertex();
        bufferbuilder.pos(x, yMax, z).color(var12, var13, var14, var11).endVertex();
        bufferbuilder.pos(xMax, yMax, z).color(var12, var13, var14, var11).endVertex();
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
}
