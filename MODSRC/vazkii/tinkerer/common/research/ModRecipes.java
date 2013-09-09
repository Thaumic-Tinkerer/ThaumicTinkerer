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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.lib.LibResearch;
import cpw.mods.fml.common.registry.GameRegistry;

public final class ModRecipes {

	public static void initRecipes() {
		initCraftingRecipes();
		initArcaneRecipes();
		initCrucibleRecipes();
	}

	private static void initCraftingRecipes() {
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 0, new ItemStack(ModItems.darkQuartz, 8),
				"QQQ", "QCQ", "QQQ",
				'Q', Item.netherQuartz,
				'C', Item.coal);
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 0, new ItemStack(ModItems.darkQuartz, 8),
				"QQQ", "QCQ", "QQQ",
				'Q', Item.netherQuartz,
				'C', new ItemStack(Item.coal, 1, 1));
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 1, new ItemStack(ModBlocks.darkQuartz),
				"QQ", "QQ",
				'Q', ModItems.darkQuartz);
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 2, new ItemStack(ModBlocks.darkQuartzSlab, 6),
				"QQQ",
				'Q', ModBlocks.darkQuartz);
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 3, new ItemStack(ModBlocks.darkQuartz, 2, 2),
				"Q", "Q",
				'Q', ModBlocks.darkQuartz);
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 4, new ItemStack(ModBlocks.darkQuartz, 1, 1),
				"Q", "Q",
				'Q', ModBlocks.darkQuartzSlab);
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 5, new ItemStack(ModBlocks.darkQuartzStairs, 4),
				"  Q", " QQ", "QQQ",
				'Q', ModBlocks.darkQuartz);
		registerResearchItem("", new ItemStack(ModBlocks.darkQuartzStairs, 4),
				"Q  ", "QQ ", "QQQ",
				'Q', ModBlocks.darkQuartz);
	}

	private static void initArcaneRecipes() {
		registerResearchItem(LibResearch.KEY_INTERFACE, LibResearch.KEY_INTERFACE, new ItemStack(ModBlocks.interfase), new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16),
				"BRB", "LEL", "BRB",
				'B', new ItemStack(Block.stone),
				'E', new ItemStack(Item.enderPearl),
				'L', new ItemStack(Item.dyePowder, 1, 4),
				'R', new ItemStack(Item.redstone));
		registerResearchItem(LibResearch.KEY_CONNECTOR, LibResearch.KEY_INTERFACE, new ItemStack(ModItems.connector), new AspectList().add(Aspect.ORDER, 2),
				" I ", " WI", "S  ",
				'I', new ItemStack(Item.ingotIron),
				'W', new ItemStack(Item.stick),
				'S', new ItemStack(ConfigItems.itemShard, 1, 4));
		registerResearchItem(LibResearch.KEY_GAS_REMOVER, LibResearch.KEY_GAS_REMOVER, new ItemStack(ModItems.gasRemover), new AspectList().add(Aspect.AIR, 2).add(Aspect.ORDER, 2),
				"DDD", "T G", "QQQ",
				'D', new ItemStack(ModItems.darkQuartz),
				'T', new ItemStack(ModItems.gaseousShadow),
				'G', new ItemStack(ModItems.gaseousLight),
				'Q', new ItemStack(Item.netherQuartz));
		registerResearchItem(LibResearch.KEY_ANIMATION_TABLET, LibResearch.KEY_ANIMATION_TABLET, new ItemStack(ModBlocks.animationTablet), new AspectList().add(Aspect.AIR, 25).add(Aspect.ORDER, 15).add(Aspect.FIRE, 10),
				"GIG", "ICI",
				'G', new ItemStack(Item.ingotGold),
				'I', new ItemStack(Item.ingotIron),
				'C', new ItemStack(ConfigItems.itemGolemCore, 1, 100));
	}

	private static void initCrucibleRecipes() {
		registerResearchItem(LibResearch.KEY_GASEOUS_LIGHT, new ItemStack(ModItems.gaseousLight), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.LIGHT, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
		registerResearchItem(LibResearch.KEY_GASEOUS_SHADOW, new ItemStack(ModItems.gaseousShadow), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
		registerResearchItem(LibResearch.KEY_SPELL_CLOTH, new ItemStack(ModItems.spellCloth), new ItemStack(ConfigItems.itemResource, 0, 7), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.ENTROPY, 6).add(Aspect.EXCHANGE, 4));
	}

	private static void registerResearchItem(String name, String research, ItemStack output, AspectList aspects, Object... stuff) {
		ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, stuff);
		List recipeList = ThaumcraftApi.getCraftingRecipes();
		if(name != null && name.length() != 0)
			ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
	}

	private static void registerResearchItem(String name, ItemStack output, Object... stuff) {
		GameRegistry.addRecipe(output, stuff);
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		if(name != null && name.length() != 0)
			ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
	}

	private static void registerResearchItem(String name, ItemStack output, ItemStack input, AspectList aspects) {
		ThaumcraftApi.addCrucibleRecipe(name, output, input, aspects);
		List recipeList = ThaumcraftApi.getCraftingRecipes();
		if(name != null && name.length() != 0)
			ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
	}
}
