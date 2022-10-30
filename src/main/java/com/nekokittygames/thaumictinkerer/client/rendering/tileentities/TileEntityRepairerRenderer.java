/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityRepairer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * TESR for the repairer
 */
public class TileEntityRepairerRenderer extends TileEntitySpecialRenderer<TileEntityRepairer> {


    /**
     * renders the repairer
     *
     * @param te           {@link TileEntityRepairer} entity
     * @param x            xPos of the block
     * @param y            yPos of the block
     * @param z            zPos of the block
     * @param partialTicks udpate ticks
     * @param destroyStage stage of the block destruction
     * @param alpha        alpha amount of the block
     */
    @Override
    public void render(TileEntityRepairer te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        if (te.isInvalid()) {
            return;
        }
        ItemStack stack = te.getInventory().getStackInSlot(0);
        GlStateManager.pushMatrix();

        double d5 = z + .5;
        GlStateManager.translate(x + 0.5D, y + 0.5D, d5 + 0.5D);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, 0.4375F);
        if (stack != ItemStack.EMPTY) {
            EntityItem customitem = new EntityItem(te.getWorld(), 0.0d, 0.0d, 0.0d, stack.copy());
            GL11.glTranslatef(0, (float) (Math.sin(te.getTicksExisted() / 10F) * 0.1F), 0);
            float deg = te.getTicksExisted() * 0.75F % 360F;
            GL11.glRotatef(deg, 0, 1, 0);
            customitem.getItem().setCount(1);
            customitem.hoverStart = 0.0f;

            GlStateManager.pushMatrix();
            GlStateManager.disableLighting();
            GlStateManager.scale(0.9F, 0.9f, 0.9F);
            if (!Minecraft.getMinecraft().getRenderItem().shouldRenderItemIn3D(customitem.getItem())) {
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }
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
}
