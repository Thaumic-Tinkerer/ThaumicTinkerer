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
 * File Created @ [16 Sep 2013, 15:31:55 (GMT)]
 */
package vazkii.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.gui.GuiEnchanting;
import vazkii.tinkerer.client.lib.LibResources;

public class GuiButtonFramedEnchantment extends GuiButtonEnchantment {

	private static final ResourceLocation gui = new ResourceLocation(LibResources.GUI_ENCHANTER);

	public GuiButtonFramedEnchantment(GuiEnchanting parent, int par1, int par2, int par3) {
		super(parent, par1, par2, par3);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (dontRender() || parent.enchanter.enchantments.isEmpty() || parent.enchanter.levels.isEmpty())
			return;

		ClientHelper.minecraft().renderEngine.bindTexture(gui);
		drawTexturedModalRect(xPosition - 4, yPosition - 4, 176, 0, 24, 24);

		int index = parent.enchanter.enchantments.indexOf(enchant.effectId);
		if (index != -1) {
			int level = parent.enchanter.levels.get(index);
			par1Minecraft.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("enchantment.level." + level), xPosition + 26, yPosition + 8, 0xFFFFFF);
		}

		super.drawButton(par1Minecraft, par2, par3);
	}

}
