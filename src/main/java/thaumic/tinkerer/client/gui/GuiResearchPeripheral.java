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
 * File Created @ [13 Sep 2013, 14:50:41 (GMT)]
 */
package thaumic.tinkerer.client.gui;

import net.minecraft.util.StatCollector;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.client.gui.GuiResearchRecipe;

import java.awt.*;
import java.net.URI;

public class GuiResearchPeripheral extends GuiResearchRecipe {

	public GuiResearchPeripheral(ResearchItem research) {
		super(research, 0, -100, -75);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == 28) {
			String url = StatCollector.translateToLocal("ttresearch.webpage.peripherals");
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else super.keyTyped(par1, par2);
	}

}
