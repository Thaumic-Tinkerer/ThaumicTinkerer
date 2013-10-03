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
 * File Created @ [13 May 2013, 21:37:42 (GMT)]
 */
package vazkii.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import vazkii.tinkerer.lib.LibResources;

public class GuiButtonAT extends GuiButton {

	public boolean enabled = false;;

	public GuiButtonAT(int par1, int par2, int par3, boolean enabled) {
		super(par1, par2, par3, 13, 13, "");
		this.enabled = enabled;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(drawButton) {
			par1Minecraft.renderEngine.bindTexture(LibResources.GUI_ANIMATION_TABLET);
			int y = enabled ? 13 : 0;
			drawTexturedModalRect(xPosition, yPosition, 176, y, width, height);
		}
	}
}
