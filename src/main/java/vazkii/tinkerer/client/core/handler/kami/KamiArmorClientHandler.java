package vazkii.tinkerer.client.core.handler.kami;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.util.StatCollector;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.network.packet.kami.PacketToggleArmor;

/**
 * Created by Katrina on 02/03/14.
 */
public class KamiArmorClientHandler {

	public static boolean ArmorEnabled = true;

	public static void SetStatus(boolean status) {
		if (FMLClientHandler.instance().getClient().currentScreen == null) {
			if (status)
				ToolModeHUDHandler.setTooltip(StatCollector.translateToLocal("ttmisc.enableAllArmor"));
			else
				ToolModeHUDHandler.setTooltip(StatCollector.translateToLocal("ttmisc.disableAllArmor"));
			ArmorEnabled = status;
			ThaumicTinkerer.netHandler.sendToServer(new PacketToggleArmor(status));
		}
	}
}
