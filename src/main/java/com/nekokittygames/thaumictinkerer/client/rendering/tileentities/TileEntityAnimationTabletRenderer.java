package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.client.libs.LibClientResources;
import com.nekokittygames.thaumictinkerer.client.misc.ClientHelper;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityAnimationTablet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class TileEntityAnimationTabletRenderer extends TileEntitySpecialRenderer<TileEntityAnimationTablet> {
    private Random random;

    private RenderEntityItem itemRenderer;

    private static EntityItem customitem;

    private static float halfPI = (float) (Math.PI / 2D);
    private static final float[][] TRANSLATIONS = new float[][]{
            { 0F, 0F, -1F },
            { -1F, 0F, 0F },
            { 0F, 0F, 0F },
            { -1F, 0F, -1F }
    };

    public TileEntityAnimationTabletRenderer() {
        super();
        this.random = new Random();
    }

    @Override
    public void render(TileEntityAnimationTablet te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        renderOverlay(te, LibClientResources.MISC_AT_CENTER, -1, false, false, 0.65, 0.13F, 0F);
        if (!te.isRightClick())
            renderOverlay(te, LibClientResources.MISC_AT_LEFT, 1, false, true, 1, 0.131F, 0F);
        else renderOverlay(te, LibClientResources.MISC_AT_RIGHT, 1, false, true, 1, 0.131F, 0F);
        renderOverlay(te, LibClientResources.MISC_AT_INDENT, 0, false, false, 0.5F, 0.132F, ClientHelper.toDegrees(te.getFacing()) + 90F);

        GlStateManager.rotate(ClientHelper.toDegrees(te.getFacing()), 0F, 1F, 0F);
        GlStateManager.translate(0.1, 0.2 + Math.cos(System.currentTimeMillis() / 600D) / 18F, 0.5);
        float[] translations = TRANSLATIONS[te.getFacing().getIndex()-2];
        GlStateManager.translate(translations[0], translations[1], translations[2]);
        GlStateManager.scale(0.8F, 0.8F, 0.8F);
        GlStateManager.translate(0.5F, 0F, 0.5F);
        GlStateManager.rotate(0, 0F, 0F, 1F);
        GlStateManager.translate(-0.5F, 0F, -0.5F);
        GlStateManager.translate(-0 / 250F, 0 / 1000F, 0F);
        GlStateManager.rotate((float) Math.cos(System.currentTimeMillis() / 400F) * 5F, 1F, 0F, 1F);
        ItemStack stack = te.getInventory().getStackInSlot(0);
        if (stack != ItemStack.EMPTY) {
            customitem=new EntityItem(te.getWorld(),0.0d,0.0d,0.0d,stack.copy());
            customitem.getItem().setCount(1);
            customitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.5F, 0.55F, 0F);
            if(stack.getItem() instanceof ItemBlock)
                GlStateManager.scale(2.5,2.5,2.5);
            else
                GlStateManager.scale(1.5,1.5,1.5);
            if (!Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(customitem.getItem()) ) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }
            GlStateManager.disableLighting();

            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(customitem.getItem(), ItemCameraTransforms.TransformType.GROUND);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();


    }


    private void renderOverlay(TileEntityAnimationTablet tablet, ResourceLocation texture, int rotationMod, boolean useLighting, boolean useBlend, double size, float height, float forceDeg) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.renderEngine.bindTexture(texture);
        GlStateManager.pushMatrix();
        //GlStateManager.depthMask(false);
        if (!useLighting)
            GlStateManager.disableLighting();
        if (useBlend) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        }
        GlStateManager.translate(0.5F, height, 0.5F);
        float deg = rotationMod == 0 ? forceDeg : (float) (tablet.getTicksExisted()* rotationMod % 360F);
        GlStateManager.rotate(deg, 0F, 1F, 0F);
        GlStateManager.color(1F, 1F, 1F, 1F);
        Tessellator tess = Tessellator.getInstance();
        double size1 = size / 2;
        double size2 = -size1;
        BufferBuilder bufferBuilder=tess.getBuffer();
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(size2,0,size1).tex(0,1).endVertex();
        bufferBuilder.pos(size1,0,size1).tex(1,1).endVertex();
        bufferBuilder.pos(size1,0,size2).tex(1,0).endVertex();
        bufferBuilder.pos(size2,0,size2).tex(0,0).endVertex();
        tess.draw();
        //GlStateManager.depthMask(true);
        if (!useLighting)
            GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
