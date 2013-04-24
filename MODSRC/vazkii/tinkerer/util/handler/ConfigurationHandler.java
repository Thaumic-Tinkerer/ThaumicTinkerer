package vazkii.tinkerer.util.handler;

import java.io.File;

import vazkii.tinkerer.lib.LibItemIDs;
import vazkii.tinkerer.lib.LibItemNames;

import net.minecraftforge.common.Configuration;

public final class ConfigurationHandler {

	private static Configuration config;
	
	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);
		
		config.load();
		
		LibItemIDs.idWandTinkerer = loadItem(LibItemNames.WAND_TINKERER, LibItemIDs.DEFAULT_WAND_TINKERER);
		
		config.save();
	}
	
	public static int loadItem(String label, int defaultID) {
		return config.getItem(label, defaultID).getInt(defaultID);
	}
	
}
