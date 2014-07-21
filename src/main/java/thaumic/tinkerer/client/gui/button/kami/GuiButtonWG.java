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
 * File Created @ [Jan 10, 2014, 5:58:46 PM (GMT)]
 */
package thaumic.tinkerer.client.gui.button.kami;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import thaumic.tinkerer.client.lib.LibResources;

public class GuiButtonWG extends GuiButton {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_WARP_GATE);

	public boolean enabled = false;

	public GuiButtonWG(int par1, int par2, int par3, boolean enabled) {
		super(par1, par2, par3, 13, 13, "");
		this.enabled = enabled;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (enabled) {
			par1Minecraft.renderEngine.bindTexture(gui);
			int y = !enabled ? 13 : 0;
			drawTexturedModalRect(xPosition, yPosition, 176, y, width, height);
		}
	}
}