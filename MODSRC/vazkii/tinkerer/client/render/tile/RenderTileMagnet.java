/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 * 
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 * 
 * File Created @ [28 Jun 2013, 18:28:15 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import vazkii.tinkerer.client.model.ModelMagnet;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;

public class RenderTileMagnet extends TileEntitySpecialRenderer {

	ModelMagnet model = new ModelMagnet();
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float)x, (float)y , (float)z);
		bindTextureByName(tileentity.getBlockMetadata() == 0 ? LibResources.MODEL_MAGNET_N : LibResources.MODEL_MAGNET_S);
        GL11.glTranslatef(0.5F, 1.5F, 0.5F);
        GL11.glScalef(1F, -1F, -1F);
        model.render();
        GL11.glPopMatrix();
	}

}
