package thaumic.tinkerer.client.render.block;

import baubles.client.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import thaumic.tinkerer.client.core.proxy.TTClientProxy;
import thaumic.tinkerer.common.block.BlockInfusedGrain;

/**
 * Created by pixlepix on 8/4/14.
 * Exactly like the normal crop render, but it takes into account TE information for getting the texture
 */
public class RenderInfusedCrops implements ISimpleBlockRenderingHandler {


    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, world.getBlockMetadata(x, y, z)));
        renderer.renderBlockCrops(block, x, y, z);
        renderer.clearOverrideBlockTexture();
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return TTClientProxy.CROP_RENDER_ID;
    }
}
