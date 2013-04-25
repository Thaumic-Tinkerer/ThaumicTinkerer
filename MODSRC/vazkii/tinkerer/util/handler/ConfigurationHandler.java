package vazkii.tinkerer.util.handler;

import java.io.File;

import net.minecraftforge.common.Configuration;
import vazkii.tinkerer.lib.LibBlockIDs;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibItemIDs;
import vazkii.tinkerer.lib.LibItemNames;

public final class ConfigurationHandler {

	private static Configuration config;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();

		LibItemIDs.idWandTinkerer = loadItem(LibItemNames.WAND_TINKERER, LibItemIDs.DEFAULT_WAND_TINKERER);
		LibItemIDs.idGlowstoneGas = loadItem(LibItemNames.GLOWSTONE_GAS, LibItemIDs.DEFAULT_GLOWSTONE_GAS);
		LibItemIDs.idSpellCloth = loadItem(LibItemNames.SPELL_CLOTH, LibItemIDs.DEFAULT_SPELL_CLOTH);
		
		LibBlockIDs.idGlowstoneGas = loadBlock(LibBlockNames.GLOWSTONE_GAS, LibBlockIDs.DEFAULT_GLOWSTONE_GAS);

		config.save();
	}

	private static int loadItem(String label, int defaultID) {
		return config.getItem(label, defaultID).getInt(defaultID);
	}

	private static int loadBlock(String label, int defaultID) {
		return config.getBlock(label, defaultID).getInt(defaultID);
	}

}
