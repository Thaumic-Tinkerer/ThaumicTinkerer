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
 * File Created @ [Dec 29, 2013, 11:38:29 PM (GMT)]
 */
package vazkii.tinkerer.client.core.handler.kami;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;

import org.lwjgl.opengl.GL11;

import vazkii.tinkerer.client.core.handler.ClientTickHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ToolModeHUDHandler {

	private static String currentTooltip;
	private static int tooltipDisplayTicks;

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void drawDislocationFocusHUD(RenderGameOverlayEvent.Post event) {
		if (event.type == ElementType.ALL && tooltipDisplayTicks > 0 && !MathHelper.stringNullOrLengthZero(currentTooltip)) {
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution var5 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int var6 = var5.getScaledWidth();
			int var7 = var5.getScaledHeight();
			FontRenderer var8 = mc.fontRenderer;

			int tooltipStartX = (var6 - var8.getStringWidth(currentTooltip)) / 2;
			int tooltipStartY = var7 - 72;

			int opacity = (int) (tooltipDisplayTicks * 256.0F / 10.0F);

			if (opacity > 160)
				opacity = 160;

			if (opacity > 0) {
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				int color = Color.getHSBColor((float) Math.cos(ClientTickHandler.elapsedTicks / 25D), 0.6F, 1F).getRGB();
				var8.drawStringWithShadow(currentTooltip, tooltipStartX, tooltipStartY, color + (opacity << 24));
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}
	}

	public static void setTooltip(String tooltip) {
		if(!tooltip.equals(currentTooltip)) {
			currentTooltip = tooltip;
			tooltipDisplayTicks = 20;
		}
	}

	@SideOnly(Side.CLIENT)
	public static void clientTick() {
		if(tooltipDisplayTicks > 0)
			--tooltipDisplayTicks;
	}

}
