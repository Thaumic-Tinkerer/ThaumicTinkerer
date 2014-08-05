package thaumic.tinkerer.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import thaumic.tinkerer.common.block.BlockInfusedGrain;

/**
 * Created by pixlepix on 8/4/14.
 * Exactly like the normal crop render, but it takes into account TE information for getting the texture
 */
public class RenderInfusedCrops extends TileEntitySpecialRenderer {


    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        RenderBlocks.getInstance().blockAccess = tile.getWorldObj();
        RenderBlocks.getInstance().renderBlockUsingTexture(tile.blockType, tile.xCoord, tile.yCoord, tile.zCoord, tile.blockType.getIcon(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, tile.getWorldObj().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord)));
    }
}
