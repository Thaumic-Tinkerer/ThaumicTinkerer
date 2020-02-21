/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.api.rendering.IMultiBlockPreviewRenderer;
import com.nekokittygames.thaumictinkerer.client.misc.Shaders;
import com.nekokittygames.thaumictinkerer.common.intl.MultiBlockPreviewRendering;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityExample;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

/**
 * TESR for the example renderer
 */
public class TileEntityExampleRenderer extends TileEntitySpecialRenderer<TileEntityExample> {

    /**
     * renders the example block
     *
     * @param te           {@link TileEntityExampleRenderer} entity
     * @param x            xPos of the block
     * @param y            yPos of the block
     * @param z            zPos of the block
     * @param partialTicks udpate ticks
     * @param destroyStage stage of the block destruction
     * @param alpha        alpha amount of the block
     */
    @Override
    public void render(TileEntityExample te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
        if (te.getGuideBlockType().size() <= 0)
            return;
        IBlockState blockState = te.getGuideBlockType().get(0);
        if (blockState == null) {
            return;
        }
        if (blockState == Blocks.AIR.getDefaultState()) {
            return;
        }

        GlStateManager.pushMatrix();
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        GlStateManager.enableBlend();
        //GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableAlpha();
        GlStateManager.disableLighting();
        float growthFactor = (((float) te.getTime()) / ((float) TileEntityExample.GROW_TIME));
        float transpose = (10f / 20f) - ((9f / 20f) * growthFactor);
        GlStateManager.translate(x + transpose, y + transpose, z + transpose);
        Shaders.useShader(Shaders.getChangeAlphaShader(), shaderId -> {
            int alpha1 = ARBShaderObjects.glGetUniformLocationARB(shaderId, "alpha");
            ARBShaderObjects.glUniform1fARB(alpha1, 0.75F);
        });
        float maxScale = 18 / 20f;
        float currentScale = maxScale * growthFactor;
        GlStateManager.scale(currentScale, currentScale, currentScale);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.5f);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();

        blockrendererdispatcher.renderBlock(blockState, new BlockPos(0, 0, 0), getWorld(), buffer);
        Tessellator.getInstance().draw();
        Shaders.releaseShader();
        GlStateManager.disableBlend();
        //GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.enableAlpha();
        GL11.glPopAttrib();
        GlStateManager.popMatrix();
        IMultiBlockPreviewRenderer iMultiBlockPreviewRenderer = MultiBlockPreviewRendering.getRenderer(blockState.getBlock().getClass());
        if (iMultiBlockPreviewRenderer != null)
            iMultiBlockPreviewRenderer.render(te.getPos(), x, y, z, getWorld(), blockState);

    }

}
