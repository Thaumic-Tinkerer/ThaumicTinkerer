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

import java.awt.Color;

import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.client.lib.UtilsFX;
import thaumcraft.client.renderers.item.HandheldItemRenderer;
import thaumcraft.common.items.armor.ItemGoggles;
import vazkii.tinkerer.client.util.handler.ClientTickHandler;
import vazkii.tinkerer.item.ItemFluxDetector;
import vazkii.tinkerer.util.helper.MiscHelper;

public class RenderItemFluxDetector extends HandheldItemRenderer {
	// Because I'm a lasy bastard and don't want to code
	// the whole renderer. <3 you Azanor.

	@Override
	public void renderThaumometer(ItemStack arg0, EntityPlayer arg1, RenderEngine arg2, float arg3) {
		MiscHelper.getMc();

		boolean goggles = arg1.getCurrentItemOrArmor(4) != null && arg1.getCurrentItemOrArmor(4).getItem() instanceof ItemGoggles;

		GL11.glPushMatrix();
		GL11.glTranslatef(-0.5F, -0.5F, 0F);
		GL11.glScalef(3F, 3F, 3F);
		renderItemStack(arg1, arg0, 0, false);
		GL11.glScalef(0.3334F, 0.3334F, 0.3334F);

		GL11.glScalef(-1F, -1F, 1F);
		GL11.glTranslatef(-1.5F, -1.25F, 0F);


		float zLevel = 5F;
		ObjectTags tags = ItemFluxDetector.getTags(arg0);
		if(tags != null && tags.size() > 0 && !arg1.isSwingInProgress) {
			int degPerTag = 360 / tags.size();
			int renderDeg = -90 + (int) (ClientTickHandler.clientTicksElapsed % 360);
			for(EnumTag tag : tags.getAspects()) {
				int amount = tags.getAmount(tag);
				float radius = Math.max(0, Math.min(60, arg1.rotationPitch - 30)) / 30F;
				float xpos = (float) (radius * Math.cos(renderDeg * Math.PI / 180));
				float ypos = (float) (radius * Math.sin(renderDeg * Math.PI / 180));
				if(amount == 100 && arg1.rotationPitch >= 90 && goggles) {
					xpos += (Math.random() - 0.5) / 25F;
					ypos += (Math.random() - 0.5) / 25F;
				}
				GL11.glPushMatrix();
				float opacity = Math.min(arg1.rotationPitch / 100F, Math.max(0.3F, amount / 100F));
				if(!goggles)
					opacity = (float) Math.min(arg1.rotationPitch / 100F, Math.cos(ClientTickHandler.clientTicksElapsed / 5F) + 1.5F);
				drawTag(goggles ? tag : EnumTag.UNKNOWN, 1.5F - arg1.rotationPitch / 100F, xpos, ypos, zLevel, opacity);
				GL11.glTranslatef(0F, 0F, -1F);
				GL11.glPopMatrix();

				renderDeg += degPerTag;
				zLevel -= 0.001F;
			}
		}

		GL11.glPopMatrix();
	}

	private void drawTag(EnumTag tag, float defScale, float x, float y, float z, float opacity) {
		final String tex = "/mods/thaumcraft/textures/misc/ss_tags_1.png";
		Color color = new Color(tag.color);

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glTranslatef(x, y, z);
		UtilsFX.renderQuadCenteredFromTexture(tex, 8, tag.id, defScale + (float) Math.cos(ClientTickHandler.clientTicksElapsed / 5F) / 20F, color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 255, GL11.GL_ONE_MINUS_SRC_ALPHA, opacity);
		GL11.glPopMatrix();
	}
}
