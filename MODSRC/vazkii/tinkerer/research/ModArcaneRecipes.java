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
 * File Created @ [27 Apr 2013, 22:44:38 (GMT)]
 */
package vazkii.tinkerer.research;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.ShapedArcaneCraftingRecipes;
import thaumcraft.api.research.ResearchList;
import thaumcraft.common.Config;
import thaumcraft.common.lib.ThaumcraftCraftingManager;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.lib.LibMisc;

public final class ModArcaneRecipes {

	public static void initArcaneRecipes() {
		ThaumcraftApi.addArcaneCraftingRecipe(LibItemNames.NAMETAG_R, LibItemNames.NAMETAG_R, 2, new ItemStack(ModItems.nametag),
				"SI",
				'S', new ItemStack(Item.sign),
				'I', new ItemStack(Item.dyePowder));

		ThaumcraftApi.addArcaneCraftingRecipe(LibItemNames.GAS_REMOVER_R, LibItemNames.GAS_REMOVER_R, 15, new ItemStack(ModItems.gasRemover),
				"DDD", "T G", "QQQ",
				'D', new ItemStack(ModItems.darkQuartz),
				'T', new ItemStack(ModItems.darkGas),
				'G', new ItemStack(ModItems.glowstoneGas),
				'Q', new ItemStack(Item.netherQuartz));

		ThaumcraftApi.addArcaneCraftingRecipe(LibItemNames.FLUX_DETECTOR_R, LibItemNames.FLUX_DETECTOR_R, 75, new ItemStack(ModItems.fluxDetector),
				" G ", "FMF", " G ",
				'G', new ItemStack(Item.ingotGold),
				'F', new ItemStack(Config.itemResource, 1, 8),
				'M', new ItemStack(Config.itemThaumometer));


		for(int i = 0; i < 16; i++)
			ThaumcraftApi.addArcaneCraftingRecipe(LibBlockNames.PHANTOM_STONE_R, 120, new ItemStack(ModBlocks.phantomStone, 4, i),
					"SSS", "TBT", "SDS",
					'S', new ItemStack(Block.whiteStone),
					'T', new ItemStack(Config.blockInfusionWorkbench, 1, 0),
					'B', new ItemStack(Config.itemResource, 1, 5),
					'D', new ItemStack(Item.dyePowder, 1, i));
		ResearchList.craftingRecipesForResearch.put(LibBlockNames.PHANTOM_STONE_R, Arrays.asList(new ShapedArcaneCraftingRecipes[] { ThaumcraftCraftingManager.createFakeArcaneRecipe(
				LibBlockNames.PHANTOM_STONE_R, 120, new ItemStack(ModBlocks.phantomStone, 4, LibMisc.CRAFTING_META_WILDCARD),
				"SSS", "TBT", "SDS",
				'S', new ItemStack(Block.whiteStone),
				'T', new ItemStack(Config.blockInfusionWorkbench, 1, 0),
				'B', new ItemStack(Config.itemResource, 1, 5),
				'D', new ItemStack(Item.dyePowder, 1, LibMisc.CRAFTING_META_WILDCARD)) }));

		ThaumcraftApi.addArcaneCraftingRecipe(LibItemNames.SOUL_MOULD_R, LibItemNames.SOUL_MOULD_R, 50, new ItemStack(ModItems.soulMould),
				" G ", "TET", " G ",
				'G', new ItemStack(Item.ingotGold),
				'T', new ItemStack(Config.itemResource, 1, 2),
				'E', new ItemStack(Item.enderPearl));
	}

}
