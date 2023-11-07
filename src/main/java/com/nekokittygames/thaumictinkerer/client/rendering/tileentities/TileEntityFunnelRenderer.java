/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityFunnel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.client.lib.RenderCubes;

import java.awt.*;

/**
 * TESR for the funnel
 */
public class TileEntityFunnelRenderer extends TileEntitySpecialRenderer<TileEntityFunnel> {

    /**
     * renders the funnel
     *
     * @param te           {@link TileEntityFunnel} entity
     * @param x            xPos of the block
     * @param y            yPos of the block
     * @param z            zPos of the block
     * @param partialTicks udpate ticks
     * @param destroyStage stage of the block destruction
     * @param alpha        alpha amount of the block
     */
    @Override
    public void render(TileEntityFunnel te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(te.getWorld().getBlockState(new BlockPos(x, y, z)));
        if (te.getInventory().getStackInSlot(0) != ItemStack.EMPTY && ((IEssentiaContainerItem) te.getInventory().getStackInSlot(0).getItem()).getAspects(te.getInventory().getStackInSlot(0)) != null && ((IEssentiaContainerItem) te.getInventory().getStackInSlot(0).getItem()).getAspects(te.getInventory().getStackInSlot(0)).size() > 0) {
            GL11.glPushMatrix();
            GL11.glDisable(2884);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.01F, (float) z + 0.5F);
            GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(2896);
            if (((IEssentiaContainerItem) te.getInventory().getStackInSlot(0).getItem()).getAspects(te.getInventory().getStackInSlot(0)).size() > 0) {
                Aspect aspect = ((IEssentiaContainerItem) te.getInventory().getStackInSlot(0).getItem()).getAspects(te.getInventory().getStackInSlot(0)).getAspects()[0];
                int amount = ((IEssentiaContainerItem) te.getInventory().getStackInSlot(0).getItem()).getAspects(te.getInventory().getStackInSlot(0)).getAmount(aspect);
                if (amount > 0)
                    renderTCJar(amount, aspect);
            }
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            GL11.glPopMatrix();
        }

    }

    /**
     * Renders the TC jar inside the funnel
     *
     * @param amount amount of essentia inside the jar
     * @param aspect which aspect is inside the jar
     */
    private void renderTCJar(int amount, Aspect aspect) {
        GL11.glPushMatrix();
        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
        //GL11.glTranslatef(x + 0.5F, y + 0.1F, 0);
        RenderCubes renderBlocks = new RenderCubes();
        GL11.glDisable(2896);
        float level = (float) amount / 250.0F * 0.625F;
        Tessellator t = Tessellator.getInstance();
        renderBlocks.setRenderBounds(0.25D, 0.0625D, 0.25D, 0.75D, 0.1875D + (double) level, 0.75D);
        t.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);
        Color co = new Color(0);
        if (aspect != null) {
            co = new Color(aspect.getColor());
        }

        TextureAtlasSprite icon = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("thaumcraft:blocks/animatedglow");
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderBlocks.renderFaceYNeg(BlocksTC.jarNormal, -0.5D, 0.0D, -0.5D, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
        renderBlocks.renderFaceYPos(BlocksTC.jarNormal, -0.5D, 0.0D, -0.5D, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
        renderBlocks.renderFaceZNeg(BlocksTC.jarNormal, -0.5D, 0.0D, -0.5D, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
        renderBlocks.renderFaceZPos(BlocksTC.jarNormal, -0.5D, 0.0D, -0.5D, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
        renderBlocks.renderFaceXNeg(BlocksTC.jarNormal, -0.5D, 0.0D, -0.5D, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
        renderBlocks.renderFaceXPos(BlocksTC.jarNormal, -0.5D, 0.0D, -0.5D, icon, (float) co.getRed() / 255.0F, (float) co.getGreen() / 255.0F, (float) co.getBlue() / 255.0F, 200);
        t.draw();
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }


}
