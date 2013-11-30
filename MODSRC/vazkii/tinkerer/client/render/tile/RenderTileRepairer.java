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
 * File Created @ [Nov 30, 2013, 5:27:39 PM (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.client.model.ModelRepairer;
import vazkii.tinkerer.common.lib.LibMisc;

public class RenderTileRepairer extends TileEntitySpecialRenderer {

	ModelRepairer model = new ModelRepairer();

	private static final ResourceLocation modelTex = new ResourceLocation(LibResources.MODEL_REPAIRER);
	private static final ResourceLocation repair = new ResourceLocation(LibResources.MISC_REPAIR);
	private static final ResourceLocation repairOff = new ResourceLocation(LibResources.MISC_REPAIR_OFF);

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		int meta = tileentity.worldObj == null ? 3 : tileentity.getBlockMetadata();
		int rotation = meta == 2 ? 0 : meta == 3 ? 180 : meta == 4 ? 270 : 90;

		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		GL11.glTranslatef((float)x, (float)y , (float)z);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		bindTexture(modelTex);
		GL11.glTranslatef(0F, 2F, 1F);
		GL11.glScalef(1F, -1F, -1F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		model.render();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
	}

}
