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
 * File Created @ [26 Apr 2013, 23:46:45 (GMT)]
 */
package vazkii.tinkerer.client.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumChatFormatting;
import thaumcraft.api.EnumTag;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.potion.ModPotions;
import vazkii.tinkerer.util.helper.MiscHelper;

public class HUDStopwatch implements IHUDElement {

	@Override
	public boolean shouldRender() {
		return MiscHelper.getMc().currentScreen == null && MiscHelper.getClientPlayer() != null && MiscHelper.getClientPlayer().isPotionActive(ModPotions.effectStopwatch.id);
	}

	@Override
	public void render(float partialTicks) {
		Minecraft mc = MiscHelper.getMc();
		ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		
		int width = res.getScaledWidth();
		int height = res.getScaledHeight();
		
		boolean unicode = mc.fontRenderer.getUnicodeFlag();
		mc.fontRenderer.setUnicodeFlag(true);
		String timeStr = EnumChatFormatting.BOLD + Potion.getDurationString(mc.thePlayer.getActivePotionEffect(ModPotions.effectStopwatch));
		RenderItem itemRender = new RenderItem();
		int lenght = 18 + mc.fontRenderer.getStringWidth(timeStr);
		
		RenderHelper.enableStandardItemLighting();
		itemRender.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, new ItemStack(ModItems.stopwatch), width / 2 - lenght / 2, height - 85);
		RenderHelper.disableStandardItemLighting();

		mc.fontRenderer.drawStringWithShadow(timeStr, width / 2 - lenght / 2 + 18, height - 81, EnumTag.TIME.color);
		mc.fontRenderer.setUnicodeFlag(unicode);
	}

	@Override
	public void clientTick() { }
}
