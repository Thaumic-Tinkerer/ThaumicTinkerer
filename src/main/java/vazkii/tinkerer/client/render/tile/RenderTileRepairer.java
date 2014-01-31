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

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.client.model.ModelRepairer;
import vazkii.tinkerer.common.block.tile.TileRepairer;

public class RenderTileRepairer extends TileEntitySpecialRenderer {

	ModelRepairer model = new ModelRepairer();

	private static final ResourceLocation modelTex = new ResourceLocation(LibResources.MODEL_REPAIRER);
	private static final ResourceLocation repair = new ResourceLocation(LibResources.MISC_REPAIR);
	private static final ResourceLocation repairOff = new ResourceLocation(LibResources.MISC_REPAIR_OFF);

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float t) {
		int meta = tileentity.worldObj == null ? 3 : tileentity.getBlockMetadata();
		int rotation = meta == 2 ? 0 : meta == 3 ? 180 : meta == 4 ? 270 : 90;

		TileRepairer repairer = (TileRepairer) tileentity;

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
		GL11.glScalef(1F, -1F, -1F);

		ItemStack item = ((TileRepairer) tileentity).getStackInSlot(0);
		if(item != null) {
			GL11.glPushMatrix();
			final float scale = 0.5F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(-0.5F, (float) (-2.5F + Math.sin(repairer.ticksExisted / 10F) * 0.1F), 0F);

			float deg = repairer.ticksExisted * 0.75F % 360F;
			GL11.glTranslatef(1F / 2F, 1F / 2F, 1F / 32F);
			GL11.glRotatef(deg, 0F, 1F, 0F);
			GL11.glTranslatef(-1F / 2F, -1F / 2F, -1F / 32F);

			bindTexture(TextureMap.locationItemsTexture);

			int renderPass = 0;
			do {
            	Icon icon = item.getItem().getIcon(item, renderPass);
            	if(icon != null) {
            		 Color color = new Color(item.getItem().getColorFromItemStack(item, renderPass));
            		 GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
            		 float f = icon.getMinU();
                     float f1 = icon.getMaxU();
                     float f2 = icon.getMinV();
                     float f3 = icon.getMaxV();
                     ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 1F / 16F);
                     GL11.glColor3f(1F, 1F, 1F);
            	}
            	renderPass++;
        	} while(renderPass < item.getItem().getRenderPasses(item.getItemDamage()));
			GL11.glPopMatrix();
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		bindTexture(modelTex);
		GL11.glScalef(1F, -1F, -1F);
		GL11.glRotatef(rotation, 0F, 1F, 0F);
		model.renderGlass();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glScalef(1F, -1F, -1F);

		renderOverlay((TileRepairer) tileentity, ((TileRepairer) tileentity).tookLastTick ? repair : repairOff , 1.25F);
		GL11.glPopMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
	}


	private void renderOverlay(TileRepairer tablet, ResourceLocation texture, double size) {
		Minecraft mc = ClientHelper.minecraft();
		mc.renderEngine.bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef(0F, -0.525F, 0F);
		float deg = tablet.ticksExisted * 0.75F % 360F;
		GL11.glRotatef(deg, 0F, 1F, 0F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Tessellator tess = Tessellator.instance;
		double size1 = size / 2;
		double size2 = -size1;
		tess.startDrawingQuads();
		tess.addVertexWithUV(size2, 0, size1, 0, 1);
		tess.addVertexWithUV(size1, 0, size1, 1, 1);
		tess.addVertexWithUV(size1, 0, size2, 1, 0);
		tess.addVertexWithUV(size2, 0, size2, 0, 0);
		tess.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

}
