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
 * File Created @ [28 Sep 2013, 20:24:39 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.tile.ItemJarFilledRenderer;
import vazkii.tinkerer.common.block.tile.TileFunnel;

public class RenderTileFunnel extends TileEntitySpecialRenderer {

	ItemJarFilledRenderer jarRenderer = new ItemJarFilledRenderer();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		TileFunnel funnel = (TileFunnel) tileentity;
		ItemStack stack = funnel.getStackInSlot(0);
		if(stack != null) {
			GL11.glPushMatrix();
			GL11.glTranslated(d0 + 0.5, d1 + 0.365, d2 + 0.5);
			jarRenderer.renderItem(ItemRenderType.ENTITY, stack, (Object[]) null);
			GL11.glPopMatrix();
		}
	}

}
