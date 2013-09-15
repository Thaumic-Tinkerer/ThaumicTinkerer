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
 * File Created @ [14 Sep 2013, 21:56:21 (GMT)]
 */
package vazkii.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.TileEnchanter;

public class GuiButtonEnchant extends GuiButton {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ENCHANTER);
	TileEnchanter enchanter;
	
	public GuiButtonEnchant(TileEnchanter enchanter, int par1, int par2, int par3) {
		super(par1, par2, par3, 15, 15, "");
		this.enchanter = enchanter;
	}
	
	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(!enabled)
			return;
		
		final int x = 176;
		final int y = enchanter.working ? 38 : 24;
		
		ClientHelper.minecraft().renderEngine.func_110577_a(gui);
		drawTexturedModalRect(xPosition, yPosition, x, y, 15, 15);
	}

}
