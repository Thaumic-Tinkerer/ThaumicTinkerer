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
 * File Created @ [9 Sep 2013, 16:33:24 (GMT)]
 */
package vazkii.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import vazkii.tinkerer.client.lib.LibResources;

public class GuiButtonAT extends GuiButton {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ANIMATION_TABLET);

	public boolean enabled = false;

	public GuiButtonAT(int par1, int par2, int par3, boolean enabled) {
		super(par1, par2, par3, 13, 13, "");
		this.enabled = enabled;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(enabled) {
			par1Minecraft.renderEngine.bindTexture(gui);
			int y = enabled ? 13 : 0;
			drawTexturedModalRect(xPosition, yPosition, 176, y, width, height);
		}
	}
}