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
 * File Created @ [7 May 2013, 18:28:45 (GMT)]
 */
package vazkii.tinkerer.research;

import java.util.Arrays;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import thaumcraft.api.research.ResearchList;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibItemNames;
import cpw.mods.fml.common.registry.GameRegistry;

public final class ModCraftingRecipes {

	public static void initCraftingRecipes() {
			registerResearchItem(LibItemNames.DARK_QUARTZ_R + 0, new ItemStack(ModItems.darkQuartz, 8),
					"QQQ", "QCQ", "QQQ",
					'Q', Item.netherQuartz,
					'C', Item.coal);
			registerResearchItem(LibItemNames.DARK_QUARTZ_R + 0, new ItemStack(ModItems.darkQuartz, 8),
					"QQQ", "QCQ", "QQQ",
					'Q', Item.netherQuartz,
					'C', new ItemStack(Item.coal, 1, 1));
			registerResearchItem(LibItemNames.DARK_QUARTZ_R + 1, new ItemStack(ModBlocks.darkQuartz),
					"QQ", "QQ",
					'Q', ModItems.darkQuartz);
			registerResearchItem(LibItemNames.DARK_QUARTZ_R + 2, new ItemStack(ModBlocks.darkQuartzSlab, 6),
					"QQQ",
					'Q', ModBlocks.darkQuartz);
			registerResearchItem(LibItemNames.DARK_QUARTZ_R + 3, new ItemStack(ModBlocks.darkQuartz, 2, 2),
					"Q", "Q",
					'Q', ModBlocks.darkQuartz);
			registerResearchItem(LibItemNames.DARK_QUARTZ_R + 4, new ItemStack(ModBlocks.darkQuartz, 1, 1),
					"Q", "Q",
					'Q', ModBlocks.darkQuartzSlab);
			registerResearchItem(LibItemNames.DARK_QUARTZ_R + 5, new ItemStack(ModBlocks.darkQuartzStairs, 4),
					"  Q", " QQ", "QQQ",
					'Q', ModBlocks.darkQuartz);
			registerResearchItem("", new ItemStack(ModBlocks.darkQuartzStairs, 4),
					"Q  ", "QQ ", "QQQ",
					'Q', ModBlocks.darkQuartz);
	}

	private static void registerResearchItem(String name, ItemStack output, Object... stuff) {
		GameRegistry.addRecipe(output, stuff);
		if(name != null && name.length() != 0)
			ResearchList.craftingRecipesForResearch.put(name, Arrays.asList(CraftingManager.getInstance().getRecipeList().size() -1));
	}

}
