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
 * File Created @ [4 Sep 2013, 16:53:24 (GMT)]
 */
package vazkii.tinkerer.common.core.handler;

import java.io.File;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;

public final class ConfigHandler {

	private static Configuration config;

	private static final String CATEGORY_POTIONS = "potions";
	private static final String CATEGORY_ENCHANTMENTS = "enchantments";

	private static ConfigCategory categoryPotions;
	private static ConfigCategory categoryEnchants;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		categoryPotions = new ConfigCategory(CATEGORY_POTIONS);
		categoryEnchants = new ConfigCategory(CATEGORY_ENCHANTMENTS);

		config.load();


		config.save();
	}

}
