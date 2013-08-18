/**
 * This class was created by <TheWhiteWolves>. It's distributed as
 * part of the Thaumic Tinkerer Mod.
 *
 * Thaumic Tinkerer is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * Thaumic Tinkerer is a Derivative Work on Thaumcraft 3.
 * Thaumcraft 3 © Azanor 2012
 * (http://www.minecraftforum.net/topic/1585216-)
 *
 * File Created @ [5 Aug 2013, 20:00:44 (GMT)]
 */
package vazkii.tinkerer.client.util.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.lib.LibResources;
import vazkii.tinkerer.util.helper.MiscHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TinkerShieldClientHandler {
	@SideOnly(Side.CLIENT)
	public static int clientPlayerShield = 0;

	@SideOnly(Side.CLIENT)
	@ForgeSubscribe
	public void renderShieldBar(RenderGameOverlayEvent event) {
		if(event.type == ElementType.AIR && clientPlayerShield > 0) {
			if(event instanceof RenderGameOverlayEvent.Post) {
				Minecraft mc = MiscHelper.getMc();

				int x = event.resolution.getScaledWidth() / 2 - 91;
				int y = event.resolution.getScaledHeight() - 49;

				GL11.glTranslatef(0F, 10F, 0F);
				
				mc.renderEngine.bindTexture(LibResources.GUI_TINKER_SHIELDS_ON);
				
				int it = 0;
				for(int i = 0; i < clientPlayerShield; i++) {
					boolean half = i == clientPlayerShield - 1 && clientPlayerShield % 2 != 0;
					if(half || i % 2 == 0) {
						renderShield(x + it * 8, y, !half);
						it++;
					}
				}

				mc.renderEngine.bindTexture("/gui/icons.png");
			}

			GL11.glTranslatef(0F, -10F, 0F);
		}

		if(event.type == ElementType.AIR && event instanceof RenderGameOverlayEvent.Post && clientPlayerShield > 0)
			GL11.glTranslatef(0F, 10F, 0F);
	}

	@SideOnly(Side.CLIENT)
	private static void renderShield(int x, int y, boolean full) {
		Tessellator tess = Tessellator.instance;
		float size = 1 / 16F;

		float startX = full ? 0 : 9 * size;
		float endX = full ? 9 * size : 1;
		float startY = 0;
		float endY = 9 * size;

		tess.startDrawingQuads();
		tess.addVertexWithUV(x, y + 9, 0, startX, endY);
		tess.addVertexWithUV(x + (full ? 9 : 7), y + 9, 0, endX, endY);
		tess.addVertexWithUV(x + (full ? 9 : 7), y, 0, endX, startY);
		tess.addVertexWithUV(x, y, 0, startX, startY);
		tess.draw();
	}
}
