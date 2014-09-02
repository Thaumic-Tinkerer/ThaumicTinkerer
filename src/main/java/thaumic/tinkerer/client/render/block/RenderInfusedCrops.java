package thaumic.tinkerer.client.render.block;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.client.core.proxy.TTClientProxy;
import thaumic.tinkerer.client.lib.LibRenderIDs;
import thaumic.tinkerer.common.block.BlockInfusedGrain;
import thaumic.tinkerer.common.item.ItemInfusedSeeds;

/**
 * Created by pixlepix on 8/4/14.
 */
public class RenderInfusedCrops implements ISimpleBlockRenderingHandler {


    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        Aspect aspect = BlockInfusedGrain.getAspect(world, x, y, z);

        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT);
        if (aspect != null && !aspect.isPrimal()) {
            //Hex to RGB code from vanilla tesselator
            float r = (aspect.getColor() >> 16 & 0xFF) / 255.0F;
            float g = (aspect.getColor() >> 8 & 0xFF) / 255.0F;
            float b = (aspect.getColor() & 0xFF) / 255.0F;

            GL11.glColor4f(r, g, b, 1F);
            Tessellator.instance.setColorRGBA_I(aspect.getColor(), 255);
        }

        renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, world.getBlockMetadata(x, y, z)));
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        renderer.renderBlockCropsImpl(block, world.getBlockMetadata(x, y, z), x, y - 0.0625F, z);
        renderer.clearOverrideBlockTexture();
        //Tessellator.instance.setColorOpaque_I(0xFFFFFF);

        GL11.glPopAttrib();
        GL11.glPopMatrix();
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return LibRenderIDs.idGrain;
    }
}
