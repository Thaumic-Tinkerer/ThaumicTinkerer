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

import cpw.mods.fml.common.Loader;
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
import vazkii.tinkerer.common.item.ItemSpellCloth;
import vazkii.tinkerer.common.item.foci.ItemFocusTelekinesis;
import vazkii.tinkerer.common.item.kami.ItemKamiResource;
import vazkii.tinkerer.common.item.quartz.ItemDarkQuartz;
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
		new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 1, new ItemStack(ModBlocks.darkQuartz),
				"QQ", "QQ",
				'Q', ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemDarkQuartz.class));
		new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 2, new ItemStack(ModBlocks.darkQuartzSlab, 6),
				"QQQ",
				'Q', ModBlocks.darkQuartz);
		new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 3, new ItemStack(ModBlocks.darkQuartz, 2, 2),
				"Q", "Q",
				'Q', ModBlocks.darkQuartz);
		new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 4, new ItemStack(ModBlocks.darkQuartz, 1, 1),
				"Q", "Q",
				'Q', ModBlocks.darkQuartzSlab);
		new ThaumicTinkererCraftingBenchRecipe(LibResearch.KEY_DARK_QUARTZ + 5, new ItemStack(ModBlocks.darkQuartzStairs, 4),
				"  Q", " QQ", "QQQ",
				'Q', ModBlocks.darkQuartz);
		new ThaumicTinkererCraftingBenchRecipe("", new ItemStack(ModBlocks.darkQuartzStairs, 4),
				"Q  ", "QQ ", "QQQ",
				'Q', ModBlocks.darkQuartz);


	}

	private static void initArcaneRecipes() {

		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_SUMMON + "0", LibResearch.KEY_SUMMON, new ItemStack(ModBlocks.spawner), new AspectList().add(Aspect.ORDER, 50).add(Aspect.ENTROPY, 50), "WWW", "SSS", 'S', new ItemStack(Blocks.stone), 'W', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1));
		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_INTERFACE, LibResearch.KEY_INTERFACE, new ItemStack(ModBlocks.interfase), new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16),
				"BRB", "LEL", "BRB",
				'B', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
				'E', new ItemStack(Items.ender_pearl),
				'L', new ItemStack(Items.dye, 1, 4),
				'R', new ItemStack(Items.redstone));

		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ANIMATION_TABLET, LibResearch.KEY_ANIMATION_TABLET, new ItemStack(ModBlocks.animationTablet), new AspectList().add(Aspect.AIR, 25).add(Aspect.ORDER, 15).add(Aspect.FIRE, 10),
				"GIG", "ICI",
				'G', new ItemStack(Items.gold_ingot),
				'I', new ItemStack(Items.iron_ingot),
				'C', new ItemStack(ConfigItems.itemGolemCore, 1, 100));
		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_MAGNET, LibResearch.KEY_MAGNETS, new ItemStack(ModBlocks.magnet), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 5),
				" I ", "SIs", "WFW",
				'I', new ItemStack(Items.iron_ingot),
				's', new ItemStack(ConfigItems.itemShard, 1, 3),
				'S', new ItemStack(ConfigItems.itemShard),
				'W', new ItemStack(ConfigBlocks.blockMagicalLog),
				'F', new ItemStack(ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemFocusTelekinesis.class)));
		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_MOB_MAGNET, LibResearch.KEY_MAGNETS, new ItemStack(ModBlocks.magnet, 1, 1), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 5),
				" G ", "SGs", "WFW",
				'G', oreDictOrStack(new ItemStack(Items.gold_ingot), "ingotCopper"),
				's', new ItemStack(ConfigItems.itemShard, 1, 3),
				'S', new ItemStack(ConfigItems.itemShard),
				'W', new ItemStack(ConfigBlocks.blockMagicalLog),
				'F', new ItemStack(ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemFocusTelekinesis.class)));
		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_FUNNEL, LibResearch.KEY_FUNNEL, new ItemStack(ModBlocks.funnel), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
				"STS",
				'S', new ItemStack(Blocks.stone),
				'T', new ItemStack(ConfigItems.itemResource, 1, 2));

		if (Config.allowMirrors) {
			new ThaumicTinkererArcaneRecipe(LibResearch.KEY_DISLOCATOR, LibResearch.KEY_DISLOCATOR, new ItemStack(ModBlocks.dislocator), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5),
					" M ", " I ", " C ",
					'M', new ItemStack(ConfigItems.itemResource, 1, 10),
					'I', new ItemStack(ModBlocks.interfase),
					'C', new ItemStack(Items.comparator));

		}
		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_ASPECT_ANALYZER, LibResearch.KEY_ASPECT_ANALYZER, new ItemStack(ModBlocks.aspectAnalyzer), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
				"TWT", "WMW", "TWT",
				'W', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
				'M', new ItemStack(ConfigItems.itemThaumometer),
				'T', new ItemStack(ConfigItems.itemResource, 1, 2));
		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_PLATFORM, LibResearch.KEY_PLATFORM, new ItemStack(ModBlocks.platform, 2), new AspectList().add(Aspect.AIR, 2).add(Aspect.ENTROPY, 4),
				" S ", "G G",
				'G', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
				'S', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7));
		new ThaumicTinkererArcaneRecipe(LibResearch.KEY_RELAY, LibResearch.KEY_MOBILIZER, new ItemStack(ModBlocks.mobilizerRelay), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15),
				"WFW", "SIs", "WFW",
				'I', new ItemStack(Items.iron_ingot),
				's', new ItemStack(ConfigItems.itemShard, 1, 3),
				'S', new ItemStack(ConfigItems.itemShard),
				'W', new ItemStack(ConfigBlocks.blockMagicalLog),
				'F', new ItemStack(Blocks.glass));
		if (Loader.isModLoaded("ComputerCraft")) {
			new ThaumicTinkererArcaneRecipe(LibResearch.KEY_GOLEMCONNECTOR, LibResearch.KEY_GOLEMCONNECTOR, new ItemStack(ModBlocks.golemConnector), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 15),
					"WFW", "sIs", "WFW",
					'I', new ItemStack(ConfigItems.itemGolemBell),
					's', new ItemStack(Items.ender_pearl),
					'W', new ItemStack(ConfigBlocks.blockMagicalLog),
					'F', new ItemStack(Blocks.redstone_block));
		}
	}

	private static void initInfusionRecipes() {
		new ThaumicTinkererInfusionRecipe(LibResearch.KEY_ENCHANTER, new ItemStack(ModBlocks.enchanter), 15, new AspectList().add(Aspect.MAGIC, 50).add(Aspect.ENERGY, 20).add(Aspect.ELDRITCH, 20).add(Aspect.VOID, 20).add(Aspect.MIND, 10), new ItemStack(Blocks.enchanting_table),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemSpellCloth.class)));
		new ThaumicTinkererInfusionRecipe(LibResearch.KEY_REPAIRER, new ItemStack(ModBlocks.repairer), 8, new AspectList().add(Aspect.TOOL, 15).add(Aspect.CRAFT, 20).add(Aspect.ORDER, 10).add(Aspect.MAGIC, 15), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.gold_ingot), new ItemStack(Items.diamond), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.planks), new ItemStack(Items.leather), new ItemStack(ConfigItems.itemResource, 1, 7), new ItemStack(ConfigItems.itemResource, 1, 2));

		new ThaumicTinkererInfusionRecipe(LibResearch.KEY_MOBILIZER, new ItemStack(ModBlocks.mobilizer), 4, new AspectList().add(Aspect.MOTION, 15).add(Aspect.ORDER, 20).add(Aspect.MAGIC, 15), new ItemStack(ConfigBlocks.blockLifter),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.feather), new ItemStack(Items.iron_ingot), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1));

		if (ConfigHandler.enableKami) {

			if (Config.allowMirrors) {

				new ThaumicTinkererInfusionRecipe(LibResearch.KEY_WARP_GATE, new ItemStack(ModBlocks.warpGate), 8, new AspectList().add(Aspect.TRAVEL, 64).add(Aspect.ELDRITCH, 50).add(Aspect.FLIGHT, 50), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 2),
						new ItemStack(ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemKamiResource.class)), new ItemStack(ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemKamiResource.class), 1, 7), new ItemStack(ModBlocks.dislocator), new ItemStack(ThaumicTinkerer.registryItems.getFirstItemFromClass(ItemKamiResource.class), 1, 6), new ItemStack(Items.diamond), new ItemStack(Items.feather));
			}
		}
	}

	private static void initCrucibleRecipes() {

	}

	public static Object oreDictOrStack(ItemStack stack, String oreDict) {
		return OreDictionary.getOres(oreDict).isEmpty() && ConfigHandler.useOreDictMetal ? stack : oreDict;
	}


}
