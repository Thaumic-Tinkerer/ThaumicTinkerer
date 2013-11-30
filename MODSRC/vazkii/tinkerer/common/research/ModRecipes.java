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
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.common.config.ConfigBlocks;
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
		initInfusionRecipes();
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
		registerResearchItem(LibResearch.KEY_INFUSED_INKWELL, new ItemStack(ModItems.infusedInkwell),
				"QQQ", "QCQ", "QQQ",
				'Q', new ItemStack(Item.dyePowder, 1, 0),
				'C', new ItemStack(ModItems.infusedInkwell, 1, 32767));
	}

	private static void initArcaneRecipes() {
		registerResearchItem(LibResearch.KEY_INTERFACE, LibResearch.KEY_INTERFACE, new ItemStack(ModBlocks.interfase), new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16),
				"BRB", "LEL", "BRB",
				'B', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
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
		registerResearchItem(LibResearch.KEY_MAGNET, LibResearch.KEY_MAGNETS, new ItemStack(ModBlocks.magnet), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 5),
			" I ", "SIs", "WFW",
			'I', new ItemStack(Item.ingotIron),
			's', new ItemStack(ConfigItems.itemShard, 1, 3),
			'S', new ItemStack(ConfigItems.itemShard),
			'W', new ItemStack(ConfigBlocks.blockMagicalLog),
			'F', new ItemStack(ModItems.focusTelekinesis));
		registerResearchItem(LibResearch.KEY_MOB_MAGNET, LibResearch.KEY_MAGNETS, new ItemStack(ModBlocks.magnet, 1, 1), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 5),
			" G ", "SGs", "WFW",
			'G', oreDictOrStack(new ItemStack(Item.ingotGold), "ingotCopper"),
			's', new ItemStack(ConfigItems.itemShard, 1, 3),
			'S', new ItemStack(ConfigItems.itemShard),
			'W', new ItemStack(ConfigBlocks.blockMagicalLog),
			'F', new ItemStack(ModItems.focusTelekinesis));
		registerResearchItem(LibResearch.KEY_FUNNEL, LibResearch.KEY_FUNNEL, new ItemStack(ModBlocks.funnel), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
			"STS",
			'S', new ItemStack(Block.stone),
			'T', new ItemStack(ConfigItems.itemResource, 1, 2));
		registerResearchItem(LibResearch.KEY_FOCUS_SMELT, LibResearch.KEY_FOCUS_SMELT, new ItemStack(ModItems.focusSmelt), new AspectList().add(Aspect.FIRE, 10).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 6),
			"FNE",
			'F', new ItemStack(ConfigItems.itemFocusFire),
			'E', new ItemStack(ConfigItems.itemFocusExcavation),
			'N', new ItemStack(ConfigItems.itemResource, 1, 1));
		registerResearchItem(LibResearch.KEY_FOCUS_ENDER_CHEST, LibResearch.KEY_FOCUS_ENDER_CHEST, new ItemStack(ModItems.focusEnderChest), new AspectList().add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10),
			"M", "E", "P",
			'M', new ItemStack(ConfigBlocks.blockMirror),
			'E', new ItemStack(Item.eyeOfEnder),
			'P', new ItemStack(ConfigItems.itemFocusPortableHole));
		registerResearchItem(LibResearch.KEY_DISLOCATOR, LibResearch.KEY_DISLOCATOR, new ItemStack(ModBlocks.dislocator), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5),
			" M ", "MIM", " C ",
			'M', new ItemStack(ConfigItems.itemResource, 1, 10),
			'I', new ItemStack(ModBlocks.interfase),
			'C', new ItemStack(Item.comparator));
		registerResearchItem(LibResearch.KEY_REVEALING_HELM, LibResearch.KEY_REVEALING_HELM, new ItemStack(ModItems.revealingHelm), new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.WATER, 5).add(Aspect.AIR, 5).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 5),
			"GH",
			'G', new ItemStack(ConfigItems.itemGoggles),
			'H', new ItemStack(ConfigItems.itemHelmetThaumium));
	}

	private static void initInfusionRecipes() {
		registerResearchItemI(LibResearch.KEY_FOCUS_FLIGHT, new ItemStack(ModItems.focusFlight), 3, new AspectList().add(Aspect.AIR, 15).add(Aspect.MOTION, 20).add(Aspect.TRAVEL, 10), new ItemStack(Item.enderPearl),
				new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.feather), new ItemStack(Item.feather), new ItemStack(ConfigItems.itemShard, 1, 0));
		registerResearchItemI(LibResearch.KEY_FOCUS_DISLOCATION, new ItemStack(ModItems.focusDislocation), 10, new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.DARKNESS, 10).add(Aspect.VOID, 25).add(Aspect.MAGIC, 20).add(Aspect.TAINT, 5), new ItemStack(Item.enderPearl),
				new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(ConfigItems.itemResource, 1, 6), new ItemStack(ConfigItems.itemResource, 1, 6), new ItemStack(ConfigItems.itemResource, 1, 6), new ItemStack(Item.diamond));
		registerResearchItemI(LibResearch.KEY_FOCUS_TELEKINESIS, new ItemStack(ModItems.focusTelekinesis), 5, new AspectList().add(Aspect.MOTION, 10).add(Aspect.AIR, 20).add(Aspect.ENTROPY, 20).add(Aspect.MIND, 10), new ItemStack(Item.enderPearl),
				new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz), new ItemStack(Item.netherQuartz),	new ItemStack(Item.ingotIron), new ItemStack(Item.ingotGold), new ItemStack(ConfigItems.itemShard, 1, 0));
		registerResearchItemI(LibResearch.KEY_CLEANSING_TALISMAN, new ItemStack(ModItems.cleansingTalisman), 5, new AspectList().add(Aspect.HEAL, 10).add(Aspect.TOOL, 10).add(Aspect.MAN, 20).add(Aspect.LIFE, 10), new ItemStack(Item.enderPearl),
				new ItemStack(ModItems.darkQuartz), new ItemStack(ModItems.darkQuartz), new ItemStack(ModItems.darkQuartz), new ItemStack(ModItems.darkQuartz), new ItemStack(Item.ghastTear), new ItemStack(ConfigItems.itemResource, 1, 1));
		registerResearchItemI(LibResearch.KEY_ENCHANTER, new ItemStack(ModBlocks.enchanter), 15, new AspectList().add(Aspect.MAGIC, 50).add(Aspect.ENERGY, 20).add(Aspect.ELDRITCH, 20).add(Aspect.VOID, 20).add(Aspect.MIND, 10), new ItemStack(Block.enchantmentTable),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ModItems.spellCloth));
		registerResearchItemI(LibResearch.KEY_XP_TALISMAN, new ItemStack(ModItems.xpTalisman), 6, new AspectList().add(Aspect.GREED, 20).add(Aspect.EXCHANGE, 10).add(Aspect.BEAST, 10).add(Aspect.MECHANISM, 5), new ItemStack(Item.ingotGold),
				new ItemStack(Item.netherQuartz), new ItemStack(ModItems.darkQuartz), new ItemStack(ConfigItems.itemResource, 1, 5), new ItemStack(Item.diamond));
		registerResearchItemI(LibResearch.KEY_FOCUS_HEAL,new ItemStack(ModItems.focusHeal), 4, new AspectList().add(Aspect.HEAL, 10).add(Aspect.SOUL, 10).add(Aspect.LIFE, 15), new ItemStack(ConfigItems.itemFocusPech),
				new ItemStack(Item.goldenCarrot), new ItemStack(Item.appleGold), new ItemStack(Item.goldNugget), new ItemStack(Item.goldNugget));
		registerResearchItemI(LibResearch.KEY_BLOOD_SWORD, new ItemStack(ModItems.bloodSword), 6, new AspectList().add(Aspect.HUNGER, 20).add(Aspect.DARKNESS, 5).add(Aspect.SOUL, 10).add(Aspect.MAN, 6), new ItemStack(ConfigItems.itemSwordThaumium),
				new ItemStack(Item.rottenFlesh), new ItemStack(Item.porkRaw), new ItemStack(Item.beefRaw), new ItemStack(Item.bone), new ItemStack(Item.diamond), new ItemStack(Item.ghastTear));
		registerResearchItemI(LibResearch.KEY_INFUSED_INKWELL, new ItemStack(ModItems.infusedInkwell), 2, new AspectList().add(Aspect.VOID, 8).add(Aspect.DARKNESS, 8), new ItemStack(ConfigItems.itemInkwell), 
				new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigBlocks.blockJar), new ItemStack(ConfigItems.itemResource, 1, 3));
	}

	private static void initCrucibleRecipes() {
		registerResearchItem(LibResearch.KEY_GASEOUS_LIGHT, new ItemStack(ModItems.gaseousLight), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.LIGHT, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
		registerResearchItem(LibResearch.KEY_GASEOUS_SHADOW, new ItemStack(ModItems.gaseousShadow), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
		registerResearchItem(LibResearch.KEY_SPELL_CLOTH, new ItemStack(ModItems.spellCloth), new ItemStack(ConfigItems.itemResource, 0, 7), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.ENTROPY, 6).add(Aspect.EXCHANGE, 4));
		registerResearchItem(LibResearch.KEY_BRIGHT_NITOR, new ItemStack(ModItems.brightNitor), new ItemStack(ConfigItems.itemResource, 1, 1), new AspectList().add(Aspect.ENERGY, 25).add(Aspect.LIGHT, 25).add(Aspect.AIR, 10).add(Aspect.FIRE, 10));
		registerResearchItem(LibResearch.KEY_MAGNETS, new ItemStack(ModItems.soulMould), new ItemStack(Item.enderPearl), new AspectList().add(Aspect.BEAST, 4).add(Aspect.MIND, 8).add(Aspect.SENSES, 8));
	}

	private static void registerResearchItem(String name, String research, ItemStack output, AspectList aspects, Object... stuff) {
		ShapedArcaneRecipe recipe = ThaumcraftApi.addArcaneCraftingRecipe(research, output, aspects, stuff);
		ConfigResearch.recipes.put(name, recipe);
	}

	private static void registerResearchItem(String name, ItemStack output, Object... stuff) {
		GameRegistry.addRecipe(output, stuff);
		List<IRecipe> recipeList = CraftingManager.getInstance().getRecipeList();
		if(name != null && name.length() != 0)
			ConfigResearch.recipes.put(name, recipeList.get(recipeList.size() - 1));
	}

	private static void registerResearchItem(String name, ItemStack output, ItemStack input, AspectList aspects) {
		CrucibleRecipe recipe = ThaumcraftApi.addCrucibleRecipe(name, output, input, aspects);
		ConfigResearch.recipes.put(name, recipe);
	}

	private static void registerResearchItemI(String name, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
		InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(name, output, instability, aspects, input, stuff);
		ConfigResearch.recipes.put(name, recipe);
	}

	private static Object oreDictOrStack(ItemStack stack, String oreDict) {
		return OreDictionary.getOres(oreDict).isEmpty() ? stack : oreDict;
	}
}
