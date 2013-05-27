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
 * File Created @ [5 May 2013, 18:23:31 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityWardChest;
import vazkii.tinkerer.util.helper.MiscHelper;

public class RenderTileWardChest extends TileEntitySpecialRenderer {
	
	ModelChest chestModel;

	public RenderTileWardChest() {
		chestModel = new ModelChest();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		int meta = tileentity.worldObj == null ? 3 : tileentity.getBlockMetadata();
		int rotation = meta == 2 ? 180 : meta == 3 ? 0 : meta == 4 ? 90 : 270;

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float)x, (float)y , (float)z);

		bindTextureByName(LibResources.MODEL_WARD_CHEST);
        GL11.glTranslatef(0F, 1F, 1F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glRotatef(rotation, 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        chestModel.chestBelow.render(LibMisc.MODEL_DEFAULT_RENDER_SCALE);
        chestModel.chestKnob.render(LibMisc.MODEL_DEFAULT_RENDER_SCALE);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);

        GL11.glTranslatef((float) x, (float) y, (float) z);
        renderOverlay((TileEntityWardChest) tileentity);
        GL11.glTranslatef((float) -x, (float) -y, (float) -z);

		bindTextureByName(LibResources.MODEL_WARD_CHEST);
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glTranslatef((float)x, (float)y , (float)z);

		bindTextureByName(LibResources.MODEL_WARD_CHEST);
        GL11.glTranslatef(0F, 1F, 1F);
        GL11.glScalef(1F, -1F, -1F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        GL11.glRotatef(rotation, 0F, 1F, 0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        chestModel.chestLid.render(LibMisc.MODEL_DEFAULT_RENDER_SCALE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        GL11.glColor4f(1F, 1F, 1F, 1F);
	}

	private void renderOverlay(TileEntityWardChest chest) {
		Minecraft mc = MiscHelper.getMc();
		mc.renderEngine.bindTexture(LibResources.MISC_WARD_CHEST_OVERLAY);
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glTranslatef(0.5F, 0.65F, 0.5F);
		float deg = (float) ((chest.worldObj == null ? ClientTickHandler.clientTicksElapsed : chest.ticksExisted) % 360F);
		GL11.glRotatef(deg, 0F, 1F, 0F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(-0.45, 0, 0.45, 0, 1);
		tess.addVertexWithUV(0.45, 0, 0.45, 1, 1);
		tess.addVertexWithUV(0.45, 0, -0.45, 1, 0);
		tess.addVertexWithUV(-0.45, 0, -0.45, 0, 0);
		tess.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
}
