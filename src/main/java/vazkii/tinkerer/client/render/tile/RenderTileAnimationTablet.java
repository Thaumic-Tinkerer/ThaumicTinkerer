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
 * File Created @ [9 Sep 2013, 17:12:26 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.tablet.TileAnimationTablet;

public class RenderTileAnimationTablet extends TileEntitySpecialRenderer {

	private static final ResourceLocation overlayCenter = new ResourceLocation(LibResources.MISC_AT_CENTER);
	private static final ResourceLocation overlayLeft = new ResourceLocation(LibResources.MISC_AT_LEFT);
	private static final ResourceLocation overlayRight = new ResourceLocation(LibResources.MISC_AT_RIGHT);
	private static final ResourceLocation overlayIndent = new ResourceLocation(LibResources.MISC_AT_INDENT);

	private static final float[][] TRANSLATIONS = new float[][] {
		{ 0F, 0F, -1F },
		{ -1F, 0F, 0F },
		{ 0F, 0F, 0F },
		{ -1F, 0F, -1F }
	};

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float partialTicks) {
		TileAnimationTablet tile = (TileAnimationTablet) tileentity;

		int meta = tile.getBlockMetadata() & 7;
		if(meta < 2)
			meta = 2; // Just in case

		int rotation = meta == 2 ? 270 : meta == 3 ? 90 : meta == 4 ? 0 : 180;

		GL11.glPushMatrix();
		GL11.glTranslated(d0, d1, d2);
		renderOverlay(tile, overlayCenter, -1, false, false, 0.65, 0.13F, 0F);
		if(tile.leftClick)
			renderOverlay(tile, overlayLeft, 1, false, true, 1, 0.13F, 0F);
		else renderOverlay(tile, overlayRight, 1, false, true, 1, 0.131F, 0F);
		renderOverlay(tile, overlayIndent, 0, false, false, 0.5F, 0.13F, rotation + 90F);

		GL11.glRotatef(rotation, 0F, 1F, 0F);
		GL11.glTranslated(0.1, 0.2 + Math.cos(System.currentTimeMillis() / 600D) / 18F, 0.5);
		float[] translations = TRANSLATIONS[meta - 2];
		GL11.glTranslatef(translations[0], translations[1], translations[2]);
		GL11.glScalef(0.8F, 0.8F, 0.8F);
		GL11.glTranslatef(0.5F, 0F, 0.5F);
		GL11.glRotatef(tile.swingProgress, 0F, 0F, 1F);
		GL11.glTranslatef(-0.5F, 0F, -0.5F);
		GL11.glTranslatef(-tile.swingProgress / 250F, tile.swingProgress / 1000F, 0F);
		GL11.glRotatef((float) Math.cos(System.currentTimeMillis() / 400F) * 5F, 1F, 0F, 1F);
		renderItem(tile);
		GL11.glPopMatrix();
	}

	private void renderItem(TileAnimationTablet tablet) {
		ItemStack stack = tablet.getStackInSlot(0);
		Minecraft mc = ClientHelper.minecraft();
		if(stack != null) {
            EntityItem entityitem = new EntityItem(tablet.worldObj, 0.0D, 0.0D, 0.0D, stack);
            entityitem.getEntityItem().stackSize = 1;
            entityitem.hoverStart = 0.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(0.5F, 0.55F, 0F);
            if(stack.getItem() instanceof ItemBlock)
                GL11.glScalef(2.5F, 2.5F, 2.5F);
            else
                GL11.glScalef(1.5F, 1.5F, 1.5F);
            RenderItem.renderInFrame = true;
            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            GL11.glPopMatrix();
        }
	}

	private void renderOverlay(TileAnimationTablet tablet, ResourceLocation texture, int rotationMod, boolean useLighting, boolean useBlend, double size, float height, float forceDeg) {
		Minecraft mc = ClientHelper.minecraft();
		mc.renderEngine.bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		if(!useLighting)
			GL11.glDisable(GL11.GL_LIGHTING);
		if(useBlend) {
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		}
		GL11.glTranslatef(0.5F, height, 0.5F);
		float deg = rotationMod == 0 ? forceDeg : (float) (tablet.ticksExisted * rotationMod % 360F);
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
		if(!useLighting)
			GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}