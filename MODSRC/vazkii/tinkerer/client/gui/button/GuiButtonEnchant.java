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

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.gui.GuiEnchanting;
import vazkii.tinkerer.client.lib.LibResources;
import vazkii.tinkerer.common.block.tile.TileEnchanter;

public class GuiButtonEnchant extends GuiButton {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ENCHANTER);
	TileEnchanter enchanter;
	GuiEnchanting parent;

	public GuiButtonEnchant(GuiEnchanting parent, TileEnchanter enchanter, int par1, int par2, int par3) {
		super(par1, par2, par3, 15, 15, "");
		this.enchanter = enchanter;
		this.parent = parent;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if(!enabled)
			return;

		final int x = 176;
		final int y = enchanter.working ? 39 : 24;

		ClientHelper.minecraft().renderEngine.bindTexture(gui);
		drawTexturedModalRect(xPosition, yPosition, x, y, 15, 15);

		if(par2 >= xPosition && par2 < xPosition + 15 && par3 >= yPosition && par3 < yPosition + 15 && !enchanter.working) {
			List<String> tooltip = new ArrayList();
			tooltip.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("ttmisc.startEnchant"));
			parent.tooltip = tooltip;
		}
	}

}
