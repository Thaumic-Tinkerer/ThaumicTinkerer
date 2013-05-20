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
 * File Created @ [20 May 2013, 22:38:51 (GMT)]
 */
package vazkii.tinkerer.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.item.HandheldItemRenderer;
import vazkii.tinkerer.item.ItemFluxDetector;
import vazkii.tinkerer.util.helper.MiscHelper;

public class RenderItemFluxDetector extends HandheldItemRenderer {
	// Because I'm a lasy bastard and don't want to code
	// the whole renderer. <3 you Azanor.

	DummyGui dummyGui = new DummyGui();

	@Override
	public void renderThaumometer(ItemStack arg0, EntityPlayer arg1, RenderEngine arg2, float arg3) {
		Minecraft mc = MiscHelper.getMc();

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
		GL11.glScalef(3F, 3F, 3F);
		renderItemStack(arg1, arg0, 0, false);
		GL11.glScalef(0.3334F, 0.3334F, 0.3334F);

		float scale = 1F / 128F;
		GL11.glScalef(scale, scale, scale);

		ObjectTags tags = ItemFluxDetector.getTags(arg0);
		if(tags != null) {
			int degPerTag = 360 / tags.size();
			int renderDeg = -90;
			for(EnumTag tag : tags.getAspects()) {
				int amount = tags.getAmount(tag);
				int xpos = (int) (60 * Math.cos(renderDeg * Math.PI / 180));
				int ypos = (int) (60 * Math.sin(renderDeg * Math.PI / 180));
				GL11.glPushMatrix();
				GL11.glTranslatef(-2500F, -6000F, -500F);
				GL11.glScalef(400F, 400F, 150F);
				UtilsFX.drawTag(mc, xpos, ypos, tag, 0, 0, dummyGui, false, amount == 100, amount / 100F);
				GL11.glTranslatef(0F, 0F, -1F);
				GL11.glPopMatrix();

				renderDeg += degPerTag;
			}
		}
		GL11.glPopMatrix();
	}

	private static final class DummyGui extends GuiScreen { }
}
