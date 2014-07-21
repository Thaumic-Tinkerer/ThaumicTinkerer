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
package thaumic.tinkerer.client.core.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.client.gui.GuiResearchRecipe;
import thaumic.tinkerer.client.core.handler.kami.ToolModeHUDHandler;
import thaumic.tinkerer.client.core.helper.ClientHelper;
import thaumic.tinkerer.client.gui.GuiResearchPeripheral;
import thaumic.tinkerer.common.lib.LibResearch;

public class ClientTickHandler {

	public static int elapsedTicks;

	@SubscribeEvent
	public void tickEnd(TickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			Minecraft mc = ClientHelper.minecraft();
			if (mc.currentScreen != null && mc.currentScreen instanceof GuiResearchRecipe && !(mc.currentScreen instanceof GuiResearchPeripheral)) {
				ResearchItem research = ReflectionHelper.getPrivateValue(GuiResearchRecipe.class, (GuiResearchRecipe) mc.currentScreen, 9);
				if (research.key.equals(LibResearch.KEY_PERIPHERALS) || research.key.equals(LibResearch.KEY_GOLEMCONNECTOR))
					mc.displayGuiScreen(new GuiResearchPeripheral(research));
			}

			ToolModeHUDHandler.clientTick();

			++elapsedTicks;
		}
	}

}
