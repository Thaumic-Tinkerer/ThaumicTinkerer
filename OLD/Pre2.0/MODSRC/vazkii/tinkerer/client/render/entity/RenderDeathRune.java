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
 * File Created @ [11 May 2013, 23:05:52 (GMT)]
 */
package vazkii.tinkerer.client.render.entity;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibMisc;
import vazkii.tinkerer.util.helper.MiscHelper;

public class RenderDeathRune extends Render {

	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f4, float f5) {
		GL11.glPushMatrix();
		double xShake = (Math.random() - 0.5) / 12;
		double yShake = (Math.random() - 0.5) / 12;
		double zShake = (Math.random() - 0.25) / 12;
		GL11.glTranslated(d0 + xShake - 0.2, d1 + yShake - 0.25, d2 + zShake);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		MiscHelper.getMc().renderEngine.bindTexture("/gui/items.png");
		Icon icon = ModItems.deathRune.getIconFromDamage(0);
		float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();
        ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, icon.getSheetWidth(), icon.getSheetHeight(), LibMisc.MODEL_DEFAULT_RENDER_SCALE);
		GL11.glPopMatrix();
	}
}
