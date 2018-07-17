package com.nekokittygames.thaumictinkerer.client.rendering.tileentities;

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
import net.minecraftforge.client.model.animation.FastTESR;
import org.lwjgl.opengl.GL11;

public class TileEntityExampleRenderer extends TileEntitySpecialRenderer<TileEntityExample> {

    @Override
    public void render(TileEntityExample te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        BufferBuilder buffer=Tessellator.getInstance().getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        IBlockState blockState=te.getGuideBlockType();
        blockrendererdispatcher.renderBlock(blockState,new BlockPos(x,y,z),getWorld(),buffer);
        Tessellator.getInstance().draw();
    }

    @Override
    public void renderTileEntityFast(TileEntityExample te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {

    }
}
