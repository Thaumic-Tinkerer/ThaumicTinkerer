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
 * File Created @ [1 Jun 2013, 23:04:39 (GMT)]
 */
package vazkii.tinkerer.client.render.tile;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.model.ModelFluxCollector;
import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.util.helper.MiscHelper;

public class RenderTileFluxCollector extends TileEntitySpecialRenderer {

	ModelFluxCollector model = new ModelFluxCollector();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1, double d2, float partTicks) {
		bindTextureByName(LibResources.MODEL_FLUX_COLLECTOR);
		GL11.glPushMatrix();
		GL11.glTranslated(d0 + 0.5F, d1 + 1.5F, d2 + 0.5F);
		GL11.glScalef(1F, -1F, -1F);
		model.render();
		GL11.glScalef(1F, -1F, -1F);
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		GL11.glRotatef(90F, 1F, 0F, 0F);
		GL11.glTranslatef(0, 0, 1.6F);
		long ticks = ClientTickHandler.clientTicksElapsed;
		float deg = ticks * 2 % 360F;
		MiscHelper.getMc().renderEngine.bindTexture("/gui/items.png");
		Icon icon = ModItems.voidCraftingMaterial.getIconFromDamage(2);
		float f = icon.getMinU();
		float f1 = icon.getMaxU();
		float f2 = icon.getMinV();
		float f3 = icon.getMaxV();
		GL11.glRotatef(deg, 0F, 0F, 1F);
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
		ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), LibMisc.MODEL_DEFAULT_RENDER_SCALE);
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glPopMatrix();
		GL11.glColor4f(1F, 1F, 1F, 1F);
	}

}
