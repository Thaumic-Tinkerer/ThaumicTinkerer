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
 * File Created @ [4 Sep 2013, 16:29:41 (GMT)]
 */
package vazkii.tinkerer.common.core.proxy;

import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.enchantment.ModEnchantments;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.potion.ModPotions;
import vazkii.tinkerer.common.research.ModRecipes;
import vazkii.tinkerer.common.research.ModResearch;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class TTCommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.loadConfig(event.getSuggestedConfigurationFile());

		ModBlocks.initBlocks();
		ModItems.initItems();
	}

	public void init(FMLInitializationEvent event) {
		ModEnchantments.initEnchantments();
		ModPotions.initPotions();
		ModBlocks.initTileEntities();
	}

	public void postInit(FMLPostInitializationEvent event) {
		ModRecipes.initRecipes();
		ModResearch.initResearch();
	}

	public void registerResearchPages() {
		// NO-OP
	}
}
