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
 * File Created @ [4 Sep 2013, 17:02:48 (GMT)]
 */
package vazkii.tinkerer.common.research;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import vazkii.tinkerer.common.ThaumicTinkerer;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.item.kami.ItemKamiResource;
import vazkii.tinkerer.common.lib.LibResearch;
import vazkii.tinkerer.common.registry.ThaumicTinkererArcaneRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererCraftingBenchRecipe;
import vazkii.tinkerer.common.registry.ThaumicTinkererInfusionRecipe;

public final class ModRecipes {

	public static void initRecipes() {
		initCraftingRecipes();
		initArcaneRecipes();
		initInfusionRecipes();
		initCrucibleRecipes();
	}

	private static void initCraftingRecipes() {
		new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 5, new ItemStack(ModBlocks.darkQuartzStairs, 4),
				"  Q", " QQ", "QQQ",
				'Q', ModBlocks.darkQuartz);
		new ThaumicTinkererCraftingBenchRecipe("", new ItemStack(ModBlocks.darkQuartzStairs, 4),
				"Q  ", "QQ ", "QQQ",
				'Q', ModBlocks.darkQuartz);


	}

	private static void initArcaneRecipes() {

		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_SUMMON + "0", LibResearch.KEY_SUMMON, new ItemStack(ModBlocks.spawner), new AspectList().add(Aspect.ORDER, 50).add(Aspect.ENTROPY, 50), "WWW", "SSS", 'S', new ItemStack(Blocks.stone), 'W', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1));

		if (Config.allowMirrors) {
			new ThaumicTinkererArcaneRecipe(LibResearch.KEY_DISLOCATOR, LibResearch.KEY_DISLOCATOR, new ItemStack(ModBlocks.dislocator), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5),
					" M ", " I ", " C ",
					'M', new ItemStack(ConfigItems.itemResource, 1, 10),
					'I', new ItemStack(ModBlocks.interfase),
					'C', new ItemStack(Items.comparator));

		}

		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_RELAY, LibResearch.KEY_MOBILIZER, new ItemStack(ModBlocks.mobilizerRelay), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15),
				"WFW", "SIs", "WFW",
				'I', new ItemStack(Items.iron_ingot),
				's', new ItemStack(ConfigItems.itemShard, 1, 3),
				'S', new ItemStack(ConfigItems.itemShard),
				'W', new ItemStack(ConfigBlocks.blockMagicalLog),
				'F', new ItemStack(Blocks.glass));
	}

	private static void initInfusionRecipes() {

		new ThaumicTinkererInfusionRecipe(LibResearch.KEY_MOBILIZER, new ItemStack(ModBlocks.mobilizer), 4, new AspectList().add(Aspect.MOTION, 15).add(Aspect.ORDER, 20).add(Aspect.MAGIC, 15), new ItemStack(ConfigBlocks.blockLifter),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.feather), new ItemStack(Items.iron_ingot), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1));

		if (ConfigHandler.enableKami) {

			if (Config.allowMirrors) {

				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_WARP_GATE, new ItemStack(ModBlocks.warpGate), 8, new AspectList().add(Aspect.TRAVEL, 64).add(Aspect.ELDRITCH, 50).add(Aspect.FLIGHT, 50), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 2),
						new ItemStack(ThaumicTinkerer.TTRegistry.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ThaumicTinkerer.TTRegistry.getFirstItemFromClass(ItemKamiResource.class), 1, 7), new ItemStack(ModBlocks.dislocator), new ItemStack(ThaumicTinkerer.TTRegistry.getFirstItemFromClass(ItemKamiResource.class), 1, 6), new ItemStack(Items.diamond), new ItemStack(Items.feather));
			}
		}
	}

	private static void initCrucibleRecipes() {

	}

	public static Object oreDictOrStack(ItemStack stack, String oreDict) {
		return OreDictionary.getOres(oreDict).isEmpty() && ConfigHandler.useOreDictMetal ? stack : oreDict;
	}


}
