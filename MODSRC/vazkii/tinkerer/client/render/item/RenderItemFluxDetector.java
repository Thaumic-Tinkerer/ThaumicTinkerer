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

import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import thaumcraft.client.renderers.item.HandheldItemRenderer;

public class RenderItemFluxDetector extends HandheldItemRenderer {
	// Because I'm a lasy bastard and don't want to code
	// the whole renderer. <3 you Azanor.

	@Override
	public void renderThaumometer(ItemStack arg0, EntityPlayer arg1, RenderEngine arg2, float arg3) {
		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
		GL11.glScalef(3F, 3F, 3F);
		renderItemStack(arg1, arg0, 0, false);
		// TODO Actually make the parts I should make...
		GL11.glPopMatrix();
	}
}
