package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

import com.nekokittygames.thaumictinkerer.client.misc.Shaders;
import com.nekokittygames.thaumictinkerer.common.tileentity.TileEntityExample;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.blocks.misc.BlockNitor;

import java.util.function.Consumer;

public class TileEntityExampleRenderer extends TileEntitySpecialRenderer<TileEntityExample> {

    @Override
    public void render(TileEntityExample te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);


        GlStateManager.pushMatrix();
        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();
        float growthFactor=(((float)te.getTime())/((float)TileEntityExample.GROW_TIME));

        GlStateManager.translate(x + 10/20f, y + 10/20f, z + 10/20f);
        GlStateManager.translate(x,y,z);
        Shaders.useShader(Shaders.getChangeAlphaShader(), shaderId -> {
            int alpha1 = ARBShaderObjects.glGetUniformLocationARB(shaderId, "alpha");
            ARBShaderObjects.glUniform1fARB(alpha1, 0.4F);
        });
        float maxScale=18/20f;
        float currentScale=maxScale*growthFactor;
        GlStateManager.scale(currentScale, currentScale, currentScale);
        GlStateManager.color(1.0f,1.0f,1.0f,0.5f);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BufferBuilder buffer=Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBlockState blockState=te.getGuideBlockType();
        blockrendererdispatcher.renderBlock(blockState,new BlockPos(0,0,0),getWorld(),buffer);
        Tessellator.getInstance().draw();
        Shaders.releaseShader();
        GL11.glPopAttrib();
        GlStateManager.popMatrix();
        if(blockState.getBlock() instanceof BlockNitor)
        {
            BlockNitor nitor= (BlockNitor) blockState.getBlock();
            BlockPos pos=te.getPos();
            FXDispatcher.INSTANCE.drawNitorFlames((double)((float)pos.getX() + 0.5F) + getWorld().rand.nextGaussian() * 0.025D, (double)((float)pos.getY() + 0.45F) + getWorld().rand.nextGaussian() * 0.025D, (double)((float)pos.getZ() + 0.5F) + getWorld().rand.nextGaussian() * 0.025D, getWorld().rand.nextGaussian() * 0.0025D, (double)getWorld().rand.nextFloat() * 0.06D, getWorld().rand.nextGaussian() * 0.0025D, nitor.getMapColor(blockState, getWorld(), te.getPos()).colorValue, 0);
        }

    }

    @Override
    public void renderTileEntityFast(TileEntityExample te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {

    }
}
