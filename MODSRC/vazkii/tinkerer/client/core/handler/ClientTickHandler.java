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
 * File Created @ [13 Sep 2013, 15:00:40 (GMT)]
 */
package vazkii.tinkerer.client.core.handler;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.client.gui.GuiResearchRecipe;
import thaumcraft.client.gui.GuiResearchTable;
import vazkii.tinkerer.client.core.helper.ClientHelper;
import vazkii.tinkerer.client.gui.GuiResearchPeripheral;
import vazkii.tinkerer.common.item.ItemInfusedInkwell;
import vazkii.tinkerer.common.lib.LibMisc;
import vazkii.tinkerer.common.lib.LibResearch;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ClientTickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		Minecraft mc = ClientHelper.minecraft();
		if(mc.currentScreen != null && mc.currentScreen instanceof GuiResearchRecipe && !(mc.currentScreen instanceof GuiResearchPeripheral)) {
			ResearchItem research = ReflectionHelper.getPrivateValue(GuiResearchRecipe.class, (GuiResearchRecipe) mc.currentScreen, 9);
			if(research.key.equals(LibResearch.KEY_PERIPHERALS))
				mc.displayGuiScreen(new GuiResearchPeripheral(research));
		}
		
		if(mc.currentScreen != null && mc.currentScreen instanceof GuiResearchTable) {
			Container container = ((GuiContainer) mc.currentScreen).inventorySlots;
			ItemInfusedInkwell.messWithContainer(container);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return LibMisc.MOD_NAME;
	}

}
