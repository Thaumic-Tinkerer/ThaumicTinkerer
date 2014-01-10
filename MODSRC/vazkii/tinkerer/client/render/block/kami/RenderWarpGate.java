/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 4.
 * Thaumcraft 4 (c) Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [Jan 10, 2014, 4:03:03 PM (GMT)]
 */
package vazkii.tinkerer.client.render.block.kami;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.block.BlockRenderer;
import thaumcraft.common.blocks.BlockCustomOre;
import thaumcraft.common.config.ConfigBlocks;
import vazkii.tinkerer.client.lib.LibRenderIDs;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.block.kami.BlockWarpGate;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderWarpGate extends BlockRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		Icon topIcon = BlockWarpGate.icons[0];
		Icon sideIcon = BlockWarpGate.icons[1];
		drawFaces(renderer, block, sideIcon, topIcon, sideIcon, sideIcon, sideIcon, sideIcon, false);
		GL11.glColor3f(1F, 1F, 1F);
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setBrightness(255);
		t.setNormal(0, 1, 0);
		renderer.renderFaceYPos(block, 0, 0, 0, BlockWarpGate.icons[2]);
		t.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		System.out.println("call");
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);
		
		Tessellator t = Tessellator.instance;
		t.setColorOpaque_I(0xFFFFFF);
		t.setBrightness(255);
		renderer.renderFaceYPos(block, x, y, z, BlockWarpGate.icons[2]);

		renderer.clearOverrideBlockTexture();
		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.renderStandardBlock(block, x, y, z);
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return LibRenderIDs.idWarpGate;
	}

}
