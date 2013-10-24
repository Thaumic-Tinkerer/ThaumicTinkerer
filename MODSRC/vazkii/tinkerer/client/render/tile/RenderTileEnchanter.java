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
 * File Created @ [16 Sep 2013, 22:39:36 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import java.awt.Color;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.item.ItemWandRenderer;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.common.block.tile.TileEnchanter;

public class RenderTileEnchanter extends TileEntitySpecialRenderer {

	ItemWandRenderer wandRenderer = new ItemWandRenderer();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float partTicks) {
		TileEnchanter enchanter = (TileEnchanter) tileentity;

		GL11.glPushMatrix();
		GL11.glTranslated(d0, d1 + 0.75, d2);

		ItemStack item = enchanter.getStackInSlot(0);
		if(item != null) {
			GL11.glPushMatrix();
			GL11.glRotatef(90F, 1F, 0F, 0F);
			final float scale = 0.7F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0.6F, -0.2F, 0F);
			GL11.glRotatef(30F, 0F, 0F, 1F);

			ClientHelper.minecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);

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

		item = enchanter.getStackInSlot(1);
		if(item != null) {
			GL11.glPushMatrix();
			GL11.glRotatef(90F, 1F, 0F, 0F);
			final float scale = 0.5F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0.6F, 1.5F, -0.1F);
			GL11.glRotatef(-70F, 0F, 0F, 1F);
			long millis = System.currentTimeMillis();

			GL11.glTranslatef(0F, 0F, (float) (Math.cos((double) millis / 1000F) - 1.2F) / 10F);
			GL11.glTranslatef(0F, 0.325F, 0F);
			GL11.glRotatef((float) Math.cos((double) millis / 500F) * 5F, 1F, 0F, 0F);
			GL11.glTranslatef(0F, -0.325F, 0F);

			wandRenderer.renderItem(ItemRenderType.ENTITY, item, (Object[]) null);
			GL11.glPopMatrix();
		}

		GL11.glPopMatrix();
	}

}
