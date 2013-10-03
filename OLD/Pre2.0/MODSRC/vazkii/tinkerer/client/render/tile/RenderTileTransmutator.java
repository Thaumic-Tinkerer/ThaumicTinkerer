/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ThaumicTinkerer Mod.
 *
 * ThaumicTinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * ThaumicTinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 � Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [28 Apr 2013, 21:08:49 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import java.awt.Color;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.tile.TileEntityTransmutator;
import vazkii.tinkerer.util.helper.MiscHelper;

public class RenderTileTransmutator extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,	double d2, float partialTicks) {
		TileEntityTransmutator tile = (TileEntityTransmutator) tileentity;

		GL11.glPushMatrix();
		GL11.glTranslated(d0, d1, d2);
		renderGlyphs(tile);
		GL11.glTranslated(0.25, 0.45 + Math.cos(ClientTickHandler.clientTicksElapsed / 12D) / 18F, 0.5);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		renderItem(tile);
		GL11.glPopMatrix();
	}

	private void renderItem(TileEntityTransmutator transmutator) {
		ItemStack stack = transmutator.getStackInSlot(0);
		Minecraft mc = MiscHelper.getMc();
		if(stack != null) {
	       mc.renderEngine.bindTexture(stack.getItem() instanceof ItemBlock ? "/terrain.png" : "/gui/items.png");

			if(stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.blocksList[stack.itemID].getRenderType())) {
            	GL11.glTranslatef(0.5F, 0.4F, 0F);
				new RenderBlocks().renderBlockAsItem(Block.blocksList[stack.itemID], stack.getItemDamage(), 1F);
            	GL11.glTranslatef(-0.5F, -0.4F, 0F);
            } else {
            	int renderPass = 0;
				do {
	            	Icon icon = stack.getItem().getIcon(stack, renderPass);
	            	if(icon != null) {
	            		 Color color = new Color(stack.getItem().getColorFromItemStack(stack, renderPass));
	            		 GL11.glColor3ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue());
	            		 float f = icon.getMinU();
	                     float f1 = icon.getMaxU();
	                     float f2 = icon.getMinV();
	                     float f3 = icon.getMaxV();
	                     ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), LibMisc.MODEL_DEFAULT_RENDER_SCALE);
	                     GL11.glColor3f(1F, 1F, 1F);
	            	}
	            	renderPass++;
	        	} while(renderPass < stack.getItem().getRenderPasses(stack.getItemDamage()));
            }
        }
	}

	private void renderGlyphs(TileEntityTransmutator transmutator) {
		Minecraft mc = MiscHelper.getMc();
		mc.renderEngine.bindTexture(LibResources.MISC_GLYPHS);
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glTranslatef(0.5F, 0.4F, 0.5F);
		float deg = (float) (transmutator.ticksExisted % 360F);
		GL11.glRotatef(deg, 0F, 1F, 0F);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.addVertexWithUV(-0.5, 0, 0.5, 0, 1);
		tess.addVertexWithUV(0.5, 0, 0.5, 1, 1);
		tess.addVertexWithUV(0.5, 0, -0.5, 1, 0);
		tess.addVertexWithUV(-0.5, 0, -0.5, 0, 0);
		tess.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}
}
