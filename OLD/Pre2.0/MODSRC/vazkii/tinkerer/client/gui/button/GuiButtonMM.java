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
 * File Created @ [12 Jul 2013, 22:12:30 (GMT)]
 */
package vazkii.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import vazkii.tinkerer.lib.LibResources;

public class GuiButtonMM extends GuiButton {

	public boolean enabled = false;;

	public GuiButtonMM(int par1, int par2, int par3, boolean enabled) {
		super(par1, par2, par3, 13, 13, "");
		this.enabled = enabled;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(drawButton) {
			par1Minecraft.renderEngine.bindTexture(LibResources.GUI_MOB_MAGNET);
			int y = enabled ? 13 : 0;
			drawTexturedModalRect(xPosition, yPosition, 176, y, width, height);
		}
	}
}
