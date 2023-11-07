/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityEnchanter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.common.items.resources.ItemCrystalEssence;

import java.awt.*;
import java.util.Objects;

/**
 * TESR for the enchanter
 */
public class TileEntityEnchanterRenderer extends TileEntitySpecialRenderer<TileEntityEnchanter> {

    EntityItem entityitem = null;
    /**
     * renders the Enchanter
     *
     * @param te           {@link TileEntityEnchanter}  entity
     * @param x            xPos of the block
     * @param y            yPos of the block
     * @param z            zPos of the block
     * @param partialTicks udpate ticks
     * @param destroyStage stage of the block destruction
     * @param alpha        alpha amount of the block
     */
    @Override
    public void render(TileEntityEnchanter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        float ticks = (float) Objects.requireNonNull(Minecraft.getMinecraft().getRenderViewEntity()).ticksExisted + partialTicks;
        if (te.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
            if(te.isWorking() && te.getEnchantmentCost().size()>0) {
                int q = te.getEnchantmentCost().size();
                float ang = (float)(360 / q);

                for(int a = 0; a < q; ++a) {
                    float angle = ticks % 720.0F / 2.0F + ang * (float) a;
                    float bob = MathHelper.sin((ticks + (float) (a * 10)) / 12.0F) * 0.02F + 0.02F;
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 1.3F, (float) z + 0.5F);
                    GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, bob, 0.4F);
                    GL11.glRotatef(-angle, 0.0F, 1.0F, 0.0F);
                    this.bindTexture(ParticleEngine.particleTexture);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 1);
                    GL11.glAlphaFunc(516, 0.003921569F);
                    GL11.glDepthMask(false);
                    Color c = new Color(((ItemCrystalEssence)ItemsTC.crystalEssence).getAspects(te.getEnchantmentCost().get(a)).getAspects()[0].getColor());
                    float r = (float) c.getRed() / 255.0F;
                    float g = (float) c.getGreen() / 255.0F;
                    float b = (float) c.getBlue() / 255.0F;
                    GL11.glColor4f(r, g, b, 0.66F);
                    UtilsFX.renderBillboardQuad(0.17499999701976776D, 64, 64, 320 + Minecraft.getMinecraft().getRenderViewEntity().ticksExisted % 16);
                    GL11.glDepthMask(true);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3042);
                    GlStateManager.alphaFunc(516, 0.1F);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) x + 0.5F, (float) y + 1.05F, (float) z + 0.5F);
                    GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(0.0F, bob, 0.4F);
                    GL11.glScaled(0.5D, 0.5D, 0.5D);
                    ItemStack is = ThaumcraftApiHelper.makeCrystal(((ItemCrystalEssence)ItemsTC.crystalEssence).getAspects(te.getEnchantmentCost().get(a)).getAspects()[0]);
                    this.entityitem = new EntityItem(te.getWorld(), 0.0D, 0.0D, 0.0D, is);
                    this.entityitem.hoverStart = 0.0F;
                    //this.renderRay(angle, a, bob, r, g, b, ticks);
                    //this.renderRay(angle, (a + 1) * 5, bob, r, g, b, ticks);
                    RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
                    rendermanager.renderEntity(this.entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
                    GL11.glPopMatrix();
                }
            }
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(x,y,z);
        if (te.getInventory().getStackInSlot(0) != ItemStack.EMPTY) {
            EntityItem customitem = new EntityItem(te.getWorld(), 0.0d, 0.0d, 0.0d, te.getInventory().getStackInSlot(0).copy());
            customitem.getItem().setCount(1);
            customitem.hoverStart = 0.0F;
            GlStateManager.pushMatrix();
            float tmp = 0.7f;
            float progress;
            if (te.isWorking()) {
                progress = Math.min(1.0f, ((float) te.getProgress()) / 225f);
                tmp *= progress;
            } else if (te.getCooldown() > 0) {
                progress = Math.max(0.0f, ((float) te.getCooldown()) / 28f);
                tmp = 0.7f - (0.7f * (1 - progress));
            } else {
                tmp = 0.0f;
                progress = 0f;
            }

            GlStateManager.translate(0.5F, 0.9F + tmp, 0.5F);
            GlStateManager.rotate(90f * (1 - progress), 1, 0, 0);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.disableLighting();
            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            Minecraft.getMinecraft().getRenderItem().renderItem(customitem.getItem(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();

        }
        GlStateManager.popMatrix();
    }
}
