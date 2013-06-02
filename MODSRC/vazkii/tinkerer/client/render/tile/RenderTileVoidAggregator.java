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
 * File Created @ [2 Jun 2013, 21:38:14 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityVoidAggregator;
import vazkii.tinkerer.util.helper.MiscHelper;

public class RenderTileVoidAggregator extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(d0, d1, d2);
		GL11.glTranslatef(0F, -0.39F, 0F);
		renderVortex((TileEntityVoidAggregator) tileentity);
		GL11.glRotatef(180F, 1F, 0F, 0F);
		GL11.glTranslatef(0F, -0.77F, -1F);
		renderVortex((TileEntityVoidAggregator) tileentity);
		GL11.glPopMatrix();
	}

	private void renderVortex(TileEntityVoidAggregator aggregator) {
		Minecraft mc = MiscHelper.getMc();
		mc.renderEngine.bindTexture(LibResources.MISC_VOID_AGGREGATOR_VORTEX);
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef(0.5F, 0.4F, 0.5F);
		float deg = (float) (aggregator.ticksExisted / 2F % 360F);
		float size = (float) ((Math.cos(aggregator.ticksExisted / 10F) + 1F) * 0.05F);
		GL11.glRotatef(deg, 0F, 1F, 0F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 0F, 0F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(-0.4 - size, 0, 0.4 + size, 0, 1);
		tess.addVertexWithUV(0.4 + size, 0, 0.4 + size, 1, 1);
		tess.addVertexWithUV(0.4 + size, 0, -0.4 - size, 1, 0);
		tess.addVertexWithUV(-0.4 - size, 0, -0.4 - size, 0, 0);
		tess.draw();
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}
}
