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
 * File Created @ [15 Sep 2013, 00:13:10 (GMT)]
 */
package thaumic.tinkerer.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import thaumcraft.api.aspects.Aspect;
import thaumic.tinkerer.client.core.helper.ClientHelper;
import thaumic.tinkerer.client.gui.GuiEnchanting;
import thaumic.tinkerer.common.enchantment.core.EnchantmentData;
import thaumic.tinkerer.common.enchantment.core.EnchantmentManager;

import java.util.ArrayList;
import java.util.List;

public class GuiButtonEnchantment extends GuiButton {

	public Enchantment enchant;
	GuiEnchanting parent;

	public GuiButtonEnchantment(GuiEnchanting parent, int par1, int par2, int par3) {
		super(par1, par2, par3, 16, 16, "");
		this.parent = parent;
	}

	boolean dontRender() {
		return enchant == null || !enabled || !EnchantmentManager.enchantmentData.containsKey(enchant.effectId);
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		if (dontRender())
			return;

		EnchantmentData data = EnchantmentManager.enchantmentData.get(enchant.effectId).get(1);
		ClientHelper.minecraft().renderEngine.bindTexture(data.texture);
		GL11.glEnable(GL11.GL_BLEND);
		drawTexturedModalRect16(xPosition, yPosition, 0, 0, 16, 16);
		GL11.glDisable(GL11.GL_BLEND);

		if (par2 >= xPosition && par2 < xPosition + 16 && par3 >= yPosition && par3 < yPosition + 16) {
			List<String> tooltip = new ArrayList();
			tooltip.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal(enchant.getName()));
			for (Aspect aspect : data.aspects.getAspectsSorted())
				tooltip.add(" \u00a7" + aspect.getChatcolor() + aspect.getName() + '\u00a7' + "r x " + data.aspects.getAmount(aspect) + " " + StatCollector.translateToLocal("ttmisc.baseCost"));
			if (this instanceof GuiButtonFramedEnchantment && !parent.enchanter.working)
				tooltip.add(EnumChatFormatting.GRAY + "" + EnumChatFormatting.ITALIC + " " + StatCollector.translateToLocal("ttmisc.clickToRemove"));

			parent.tooltip = tooltip;
		}
	}

	public void drawTexturedModalRect16(int par1, int par2, int par3, int par4, int par5, int par6) {
		float f = 1F / 16F;
		float f1 = 1F / 16F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1, par2 + par6, zLevel, (par3) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + par6, zLevel, (par3 + par5) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2, zLevel, (par3 + par5) * f, (par4) * f1);
		tessellator.addVertexWithUV(par1, par2, zLevel, (par3) * f, (par4) * f1);
		tessellator.draw();
	}

}
