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
 * File Created @ [Jan 10, 2014, 4:44:15 PM (GMT)]
 */
package vazkii.tinkerer.client.render.tile.kami;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.model.kami.ModelSpinningCubes;

public class RenderTileWarpGate extends TileEntitySpecialRenderer {

	ModelSpinningCubes cubes = new ModelSpinningCubes();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslated(x + 0.5, y + 2.5, z + 0.5);
		GL11.glRotatef(180F, 1F, 0F, 1F);
		int repeat = 5;
		cubes.renderSpinningCubes(12, repeat, repeat);
		GL11.glPopMatrix();
	}

}
