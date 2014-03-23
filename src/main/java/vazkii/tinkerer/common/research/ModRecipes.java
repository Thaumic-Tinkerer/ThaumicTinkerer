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
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.config.ConfigResearch;
import vazkii.tinkerer.common.block.ModBlocks;
import vazkii.tinkerer.common.core.handler.ConfigHandler;
import vazkii.tinkerer.common.item.ModItems;
import vazkii.tinkerer.common.lib.LibResearch;

import java.util.List;

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
				'Q', Items.quartz,
				'C', Items.coal);
		registerResearchItem(LibResearch.KEY_DARK_QUARTZ + 0, new ItemStack(ModItems.darkQuartz, 8),
				"QQQ", "QCQ", "QQQ",
				'Q', Items.quartz,
				'C', new ItemStack(Items.coal, 1, 1));
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
		registerResearchItem(LibResearch.KEY_INFUSED_INKWELL + 0, new ItemStack(ModItems.infusedInkwell),
				"QQQ", "QCQ", "QQQ",
				'Q', new ItemStack(Items.dye, 1, 0),
				'C', new ItemStack(ModItems.infusedInkwell, 1, 32767));

		if(ConfigHandler.enableSurvivalShareTome) {
			registerResearchItem(LibResearch.KEY_SHARE_TOME, new ItemStack(ModItems.shareBook),
				" S ", "PTP", " P ",
				'S', new ItemStack(ConfigItems.itemInkwell),
				'T', new ItemStack(ConfigItems.itemThaumonomicon),
				'P', new ItemStack(Items.paper));
		}

		for(int i=0;i<16;i++){
			registerResearchItem(LibResearch.KEY_SUMMON+"1",new ItemStack(ModItems.mobAspect, 1, i+20), "XXX", "XXX", "XXX", 'X', new ItemStack(ModItems.mobAspect, 1, i));
		}

		if(ConfigHandler.enableKami) {
			GameRegistry.addShapelessRecipe(new ItemStack(ModItems.kamiResource, 9, 3), new ItemStack(ModItems.kamiResource, 1, 2));
		}
	}

	private static void initArcaneRecipes() {

		registerResearchItem(LibResearch.KEY_SUMMON+"0", new ItemStack(ModBlocks.spawner), "WWW", "SSS", 'S', new ItemStack(Blocks.stone), 'W', new ItemStack(ConfigBlocks.blockCosmeticSolid,1,1));
		registerResearchItem(LibResearch.KEY_INTERFACE, LibResearch.KEY_INTERFACE, new ItemStack(ModBlocks.interfase), new AspectList().add(Aspect.ORDER, 12).add(Aspect.ENTROPY, 16),
				"BRB", "LEL", "BRB",
				'B', new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 6),
				'E', new ItemStack(Items.ender_pearl),
				'L', new ItemStack(Items.dye, 1, 4),
				'R', new ItemStack(Items.redstone));
		registerResearchItem(LibResearch.KEY_CONNECTOR, LibResearch.KEY_PERIPHERALS,  new ItemStack(ModItems.connector), new AspectList().add(Aspect.ORDER, 2),
				" I ", " WI", "S  ",
				'I', new ItemStack(Items.iron_ingot),
				'W', new ItemStack(Items.stick),
				'S', new ItemStack(ConfigItems.itemShard, 1, 4));
		registerResearchItem(LibResearch.KEY_GAS_REMOVER, LibResearch.KEY_GAS_REMOVER, new ItemStack(ModItems.gasRemover), new AspectList().add(Aspect.AIR, 2).add(Aspect.ORDER, 2),
				"DDD", "T G", "QQQ",
				'D', new ItemStack(ModItems.darkQuartz),
				'T', new ItemStack(ModItems.gaseousShadow),
				'G', new ItemStack(ModItems.gaseousLight),
				'Q', new ItemStack(Items.quartz));
		registerResearchItem(LibResearch.KEY_ANIMATION_TABLET, LibResearch.KEY_ANIMATION_TABLET, new ItemStack(ModBlocks.animationTablet), new AspectList().add(Aspect.AIR, 25).add(Aspect.ORDER, 15).add(Aspect.FIRE, 10),
				"GIG", "ICI",
				'G', new ItemStack(Items.gold_ingot),
				'I', new ItemStack(Items.iron_ingot),
				'C', new ItemStack(ConfigItems.itemGolemCore, 1, 100));
		registerResearchItem(LibResearch.KEY_MAGNET, LibResearch.KEY_MAGNETS, new ItemStack(ModBlocks.magnet), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 5),
			" I ", "SIs", "WFW",
			'I', new ItemStack(Items.iron_ingot),
			's', new ItemStack(ConfigItems.itemShard, 1, 3),
			'S', new ItemStack(ConfigItems.itemShard),
			'W', new ItemStack(ConfigBlocks.blockMagicalLog),
			'F', new ItemStack(ModItems.focusTelekinesis));
		registerResearchItem(LibResearch.KEY_MOB_MAGNET, LibResearch.KEY_MAGNETS, new ItemStack(ModBlocks.magnet, 1, 1), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 5),
			" G ", "SGs", "WFW",
			'G', oreDictOrStack(new ItemStack(Items.gold_ingot), "ingotCopper"),
			's', new ItemStack(ConfigItems.itemShard, 1, 3),
			'S', new ItemStack(ConfigItems.itemShard),
			'W', new ItemStack(ConfigBlocks.blockMagicalLog),
			'F', new ItemStack(ModItems.focusTelekinesis));
		registerResearchItem(LibResearch.KEY_FUNNEL, LibResearch.KEY_FUNNEL, new ItemStack(ModBlocks.funnel), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
			"STS",
			'S', new ItemStack(Blocks.stone),
			'T', new ItemStack(ConfigItems.itemResource, 1, 2));
		registerResearchItem(LibResearch.KEY_FOCUS_SMELT, LibResearch.KEY_FOCUS_SMELT, new ItemStack(ModItems.focusSmelt), new AspectList().add(Aspect.FIRE, 10).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 6),
			"FNE",
			'F', new ItemStack(ConfigItems.itemFocusFire),
			'E', new ItemStack(ConfigItems.itemFocusExcavation),
			'N', new ItemStack(ConfigItems.itemResource, 1, 1));
        if (Config.allowMirrors) {
            registerResearchItem(LibResearch.KEY_DISLOCATOR, LibResearch.KEY_DISLOCATOR, new ItemStack(ModBlocks.dislocator), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5),
			" M ", " I ", " C ",
			'M', new ItemStack(ConfigItems.itemResource, 1, 10),
			'I', new ItemStack(ModBlocks.interfase),
			'C', new ItemStack(Items.comparator));
            registerResearchItem(LibResearch.KEY_FOCUS_ENDER_CHEST, LibResearch.KEY_FOCUS_ENDER_CHEST, new ItemStack(ModItems.focusEnderChest), new AspectList().add(Aspect.ORDER, 10).add(Aspect.ENTROPY, 10),
                    "M", "E", "P",
                    'M', new ItemStack(ConfigBlocks.blockMirror),
                    'E', new ItemStack(Items.ender_eye),
                    'P', new ItemStack(ConfigItems.itemFocusPortableHole));
        }
        registerResearchItem(LibResearch.KEY_REVEALING_HELM, LibResearch.KEY_REVEALING_HELM, new ItemStack(ModItems.revealingHelm), new AspectList().add(Aspect.EARTH, 5).add(Aspect.FIRE, 5).add(Aspect.WATER, 5).add(Aspect.AIR, 5).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 5),
			"GH",
			'G', new ItemStack(ConfigItems.itemGoggles),
			'H', new ItemStack(ConfigItems.itemHelmetThaumium));
		registerResearchItem(LibResearch.KEY_ASPECT_ANALYZER, LibResearch.KEY_ASPECT_ANALYZER, new ItemStack(ModBlocks.aspectAnalyzer), new AspectList().add(Aspect.ORDER, 1).add(Aspect.ENTROPY, 1),
			"TWT", "WMW", "TWT",
			'W', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
			'M', new ItemStack(ConfigItems.itemThaumometer),
			'T', new ItemStack(ConfigItems.itemResource, 1, 2));
		registerResearchItem(LibResearch.KEY_PLATFORM, LibResearch.KEY_PLATFORM, new ItemStack(ModBlocks.platform, 2), new AspectList().add(Aspect.AIR, 2).add(Aspect.ENTROPY, 4),
			" S ", "G G",
			'G', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 6),
			'S', new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 7));
        registerResearchItem(LibResearch.KEY_RELAY, LibResearch.KEY_MOBILIZER, new ItemStack(ModBlocks.mobilizerRelay), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.EARTH, 15),
                "WFW", "SIs", "WFW",
				'I', new ItemStack(Items.iron_ingot),
				's', new ItemStack(ConfigItems.itemShard, 1, 3),
				'S', new ItemStack(ConfigItems.itemShard),
				'W', new ItemStack(ConfigBlocks.blockMagicalLog),
				'F', new ItemStack(Blocks.glass));
		if(Loader.isModLoaded("ComputerCraft"))
		{
			registerResearchItem(LibResearch.KEY_GOLEMCONNECTOR, LibResearch.KEY_GOLEMCONNECTOR, new ItemStack(ModBlocks.golemConnector), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 5).add(Aspect.ENTROPY, 15),
					"WFW", "sIs", "WFW",
					'I', new ItemStack(ConfigItems.itemGolemBell),
					's', new ItemStack(Items.ender_pearl),
					'W', new ItemStack(ConfigBlocks.blockMagicalLog),
					'F', new ItemStack(Blocks.redstone_block));
		}
		if(ConfigHandler.enableKami) {
			registerResearchItem(LibResearch.KEY_ICHOR_CLOTH, LibResearch.KEY_ICHOR_CLOTH, new ItemStack(ModItems.kamiResource, 3, 1), new AspectList().add(Aspect.FIRE, 125).add(Aspect.EARTH, 125).add(Aspect.WATER, 125).add(Aspect.AIR, 125).add(Aspect.ORDER, 125).add(Aspect.ENTROPY, 125),
				"CCC", "III", "DDD",
				'C', new ItemStack(ConfigItems.itemResource, 1, 7),
				'I', new ItemStack(ModItems.kamiResource, 1, 0),
				'D', new ItemStack(Items.diamond));
			registerResearchItem(LibResearch.KEY_ICHORIUM, LibResearch.KEY_ICHORIUM, new ItemStack(ModItems.kamiResource, 1, 2), new AspectList().add(Aspect.FIRE, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100).add(Aspect.AIR, 100).add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100),
				" T ", "IDI", " I ",
				'T', new ItemStack(ConfigItems.itemResource, 1, 2),
				'I', new ItemStack(ModItems.kamiResource, 1, 0),
				'D', new ItemStack(Items.diamond));
			registerResearchItem(LibResearch.KEY_ICHOR_CAP, LibResearch.KEY_ICHOR_CAP, new ItemStack(ModItems.kamiResource, 2, 4), new AspectList().add(Aspect.FIRE, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100).add(Aspect.AIR, 100).add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100),
					"ICI", " M ", "ICI",
					'M', new ItemStack(ModItems.kamiResource, 1, 2),
					'I', new ItemStack(ModItems.kamiResource, 1, 0),
					'C', new ItemStack(ConfigItems.itemWandCap, 1, 2));
			registerResearchItem(LibResearch.KEY_ICHORCLOTH_HELM, LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(ModItems.ichorHelm), new AspectList().add(Aspect.WATER, 75),
					"CCC", "C C",
					'C', new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItem(LibResearch.KEY_ICHORCLOTH_CHEST, LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(ModItems.ichorChest), new AspectList().add(Aspect.AIR, 75),
					"C C", "CCC", "CCC",
					'C', new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItem(LibResearch.KEY_ICHORCLOTH_LEGS, LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(ModItems.ichorLegs), new AspectList().add(Aspect.FIRE, 75),
					"CCC", "C C", "C C",
					'C', new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItem(LibResearch.KEY_ICHORCLOTH_BOOTS, LibResearch.KEY_ICHORCLOTH_ARMOR, new ItemStack(ModItems.ichorBoots), new AspectList().add(Aspect.EARTH, 75),
					"C C", "C C",
					'C', new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItem(LibResearch.KEY_ICHOR_PICK, LibResearch.KEY_ICHOR_TOOLS, new ItemStack(ModItems.ichorPick), new AspectList().add(Aspect.FIRE, 75),
					"III", " R ", " R ",
					'R', new ItemStack(ConfigItems.itemWandRod, 1, 2),
					'I', new ItemStack(ModItems.kamiResource, 1, 2));
			registerResearchItem(LibResearch.KEY_ICHOR_SHOVEL, LibResearch.KEY_ICHOR_TOOLS, new ItemStack(ModItems.ichorShovel), new AspectList().add(Aspect.EARTH, 75),
					" I ", " R ", " R ",
					'R', new ItemStack(ConfigItems.itemWandRod, 1, 2),
					'I', new ItemStack(ModItems.kamiResource, 1, 2));
			registerResearchItem(LibResearch.KEY_ICHOR_AXE, LibResearch.KEY_ICHOR_TOOLS, new ItemStack(ModItems.ichorAxe), new AspectList().add(Aspect.WATER, 75),
					"II ", "IR ", " R ",
					'R', new ItemStack(ConfigItems.itemWandRod, 1, 2),
					'I', new ItemStack(ModItems.kamiResource, 1, 2));
			registerResearchItem("", LibResearch.KEY_ICHOR_TOOLS, new ItemStack(ModItems.ichorAxe), new AspectList().add(Aspect.WATER, 75),
					" II", " RI", " R ",
					'R', new ItemStack(ConfigItems.itemWandRod, 1, 2),
					'I', new ItemStack(ModItems.kamiResource, 1, 2));
			registerResearchItem(LibResearch.KEY_ICHOR_SWORD, LibResearch.KEY_ICHOR_TOOLS, new ItemStack(ModItems.ichorSword), new AspectList().add(Aspect.AIR, 75),
					" I ", " I ", " R ",
					'R', new ItemStack(ConfigItems.itemWandRod, 1, 2),
					'I', new ItemStack(ModItems.kamiResource, 1, 2));
		}
	}

	private static void initInfusionRecipes() {
		registerResearchItemI(LibResearch.KEY_FOCUS_FLIGHT, new ItemStack(ModItems.focusFlight), 3, new AspectList().add(Aspect.AIR, 15).add(Aspect.MOTION, 20).add(Aspect.TRAVEL, 10), new ItemStack(Items.ender_pearl),
				new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.feather), new ItemStack(Items.feather), new ItemStack(ConfigItems.itemShard, 1, 0));
		registerResearchItemI(LibResearch.KEY_FOCUS_DISLOCATION, new ItemStack(ModItems.focusDislocation), 8, new AspectList().add(Aspect.ELDRITCH, 20).add(Aspect.DARKNESS, 10).add(Aspect.VOID, 25).add(Aspect.MAGIC, 20).add(Aspect.TAINT, 5), new ItemStack(Items.ender_pearl),
				new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(ConfigItems.itemResource, 1, 6), new ItemStack(ConfigItems.itemResource, 1, 6), new ItemStack(ConfigItems.itemResource, 1, 6), new ItemStack(Items.diamond));
		registerResearchItemI(LibResearch.KEY_FOCUS_TELEKINESIS, new ItemStack(ModItems.focusTelekinesis), 5, new AspectList().add(Aspect.MOTION, 10).add(Aspect.AIR, 20).add(Aspect.ENTROPY, 20).add(Aspect.MIND, 10), new ItemStack(Items.ender_pearl),
				new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz),	new ItemStack(Items.iron_ingot), new ItemStack(Items.gold_ingot), new ItemStack(ConfigItems.itemShard, 1, 0));
		registerResearchItemI(LibResearch.KEY_CLEANSING_TALISMAN, new ItemStack(ModItems.cleansingTalisman), 5, new AspectList().add(Aspect.HEAL, 10).add(Aspect.TOOL, 10).add(Aspect.MAN, 20).add(Aspect.LIFE, 10), new ItemStack(Items.ender_pearl),
				new ItemStack(ModItems.darkQuartz), new ItemStack(ModItems.darkQuartz), new ItemStack(ModItems.darkQuartz), new ItemStack(ModItems.darkQuartz), new ItemStack(Items.ghast_tear), new ItemStack(ConfigItems.itemResource, 1, 1));
		registerResearchItemI(LibResearch.KEY_ENCHANTER, new ItemStack(ModBlocks.enchanter), 15, new AspectList().add(Aspect.MAGIC, 50).add(Aspect.ENERGY, 20).add(Aspect.ELDRITCH, 20).add(Aspect.VOID, 20).add(Aspect.MIND, 10), new ItemStack(Blocks.enchanting_table),
				new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 1), new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ConfigItems.itemResource, 1, 2), new ItemStack(ModItems.spellCloth));
		registerResearchItemI(LibResearch.KEY_XP_TALISMAN, new ItemStack(ModItems.xpTalisman), 6, new AspectList().add(Aspect.GREED, 20).add(Aspect.EXCHANGE, 10).add(Aspect.BEAST, 10).add(Aspect.MECHANISM, 5), new ItemStack(Items.gold_ingot),
				new ItemStack(Items.quartz), new ItemStack(ModItems.darkQuartz), new ItemStack(ConfigItems.itemResource, 1, 5), new ItemStack(Items.diamond));
		registerResearchItemI(LibResearch.KEY_FOCUS_HEAL,new ItemStack(ModItems.focusHeal), 4, new AspectList().add(Aspect.HEAL, 10).add(Aspect.SOUL, 10).add(Aspect.LIFE, 15), new ItemStack(ConfigItems.itemFocusPech),
				new ItemStack(Items.golden_carrot), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget), new ItemStack(Items.gold_nugget));
		registerResearchItemI(LibResearch.KEY_BLOOD_SWORD, new ItemStack(ModItems.bloodSword), 6, new AspectList().add(Aspect.HUNGER, 20).add(Aspect.DARKNESS, 5).add(Aspect.SOUL, 10).add(Aspect.MAN, 6), new ItemStack(ConfigItems.itemSwordThaumium),
				new ItemStack(Items.rotten_flesh), new ItemStack(Items.porkchop), new ItemStack(Items.beef), new ItemStack(Items.bone), new ItemStack(Items.diamond), new ItemStack(Items.ghast_tear));
		registerResearchItemI(LibResearch.KEY_INFUSED_INKWELL, new ItemStack(ModItems.infusedInkwell), 2, new AspectList().add(Aspect.VOID, 8).add(Aspect.DARKNESS, 8), new ItemStack(ConfigItems.itemInkwell),
				new ItemStack(ConfigItems.itemShard, 1, 0), new ItemStack(ConfigBlocks.blockJar), new ItemStack(ConfigItems.itemResource, 1, 3));
		registerResearchItemI(LibResearch.KEY_REPAIRER, new ItemStack(ModBlocks.repairer), 8, new AspectList().add(Aspect.TOOL, 15).add(Aspect.CRAFT, 20).add(Aspect.ORDER, 10).add(Aspect.MAGIC, 15), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 4),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.gold_ingot), new ItemStack(Items.diamond), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.planks), new ItemStack(Items.leather), new ItemStack(ConfigItems.itemResource, 1, 7), new ItemStack(ConfigItems.itemResource, 1, 2));

		registerResearchItemI(LibResearch.KEY_MOBILIZER, new ItemStack(ModBlocks.mobilizer), 4, new AspectList().add(Aspect.MOTION, 15).add(Aspect.ORDER, 20).add(Aspect.MAGIC, 15), new ItemStack(ConfigBlocks.blockLifter),
				new ItemStack(Items.iron_ingot), new ItemStack(Items.feather), new ItemStack(Items.iron_ingot), new ItemStack(ConfigBlocks.blockCosmeticSolid,1,1));



		for(int i=0;i<16;i++){
			ItemStack input=new ItemStack(ModItems.mobAspect, 1, i+20);
			registerResearchItemI(LibResearch.KEY_SUMMON, new ItemStack(ModItems.mobAspect, 1, i + 40), 4,
					new AspectList().add(ModItems.mobAspect.getAspect(new ItemStack(ModItems.mobAspect, 1, i)), 10), input,
					new ItemStack[]{ input, input, input, input, input, input, input, input });
		}

        if (Config.allowMirrors) {
            registerResearchItemI(LibResearch.KEY_FOCUS_DEFLECT, new ItemStack(ModItems.focusDeflect), 5, new AspectList().add(Aspect.AIR, 15).add(Aspect.ARMOR, 5).add(Aspect.ORDER, 20), new ItemStack(ModItems.focusFlight),
                    new ItemStack(ConfigItems.itemResource, 1, 10), new ItemStack(ConfigItems.itemResource, 1, 10), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 3), new ItemStack(ConfigItems.itemShard, 1, 4));

        }

		if(ConfigHandler.enableKami) {
			registerResearchItemI(LibResearch.KEY_ICHOR, new ItemStack(ModItems.kamiResource, 8, 0), 7, new AspectList().add(Aspect.MAN, 32).add(Aspect.LIGHT, 32).add(Aspect.SOUL, 64), new ItemStack(Items.nether_star),
				new ItemStack(Items.diamond), new ItemStack(ModItems.kamiResource, 8, 7), new ItemStack(Items.ender_eye), new ItemStack(ModItems.kamiResource, 8, 6));
			registerResearchItemI(LibResearch.KEY_ICHORCLOTH_ROD, new ItemStack(ModItems.kamiResource, 1, 5), 9, new AspectList().add(Aspect.MAGIC, 100).add(Aspect.LIGHT, 32).add(Aspect.TOOL, 32), new ItemStack(ConfigItems.itemWandRod, 1, 2),
				new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource, 1, 1), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(Items.ghast_tear), new ItemStack(ConfigItems.itemResource, 1, 14), new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItemI(LibResearch.KEY_ICHORCLOTH_HELM_GEM, new ItemStack(ModItems.ichorHelmGem), 13, new AspectList().add(Aspect.WATER, 50).add(Aspect.ARMOR, 32).add(Aspect.HUNGER, 32).add(Aspect.AURA, 32).add(Aspect.LIGHT, 64).add(Aspect.FLESH, 16).add(Aspect.MIND, 16), new ItemStack(ModItems.ichorHelm),
				new ItemStack(Items.diamond, 1), new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource),  new ItemStack(ConfigItems.itemManaBean), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(Items.golden_helmet), new ItemStack(Items.potionitem, 1, 8198), new ItemStack(ConfigItems.itemGoggles),  new ItemStack(ModItems.cleansingTalisman), new ItemStack(Items.fish), new ItemStack(Items.cake
            ), new ItemStack(Items.ender_eye));
			registerResearchItemI(LibResearch.KEY_ICHORCLOTH_CHEST_GEM, new ItemStack(ModItems.ichorChestGem), 13, new AspectList().add(Aspect.AIR, 50).add(Aspect.ARMOR, 32).add(Aspect.FLIGHT, 32).add(Aspect.ORDER, 32).add(Aspect.LIGHT, 64).add(Aspect.ELDRITCH, 16).add(Aspect.SENSES, 16), new ItemStack(ModItems.ichorChest),
				new ItemStack(Items.diamond, 1), new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource),  new ItemStack(ConfigItems.itemManaBean), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(Items.golden_chestplate), new ItemStack(ModItems.focusFlight), new ItemStack(ConfigItems.itemHoverHarness),  new ItemStack(ModItems.focusDeflect), new ItemStack(Items.feather), new ItemStack(Items.fireworks), new ItemStack(Items.arrow));
			registerResearchItemI(LibResearch.KEY_ICHORCLOTH_LEGS_GEM, new ItemStack(ModItems.ichorLegsGem), 13, new AspectList().add(Aspect.FIRE, 50).add(Aspect.ARMOR, 32).add(Aspect.HEAL, 32).add(Aspect.ENERGY, 32).add(Aspect.LIGHT, 64).add(Aspect.GREED, 16).add(Aspect.ELDRITCH, 16), new ItemStack(ModItems.ichorLegs),
				new ItemStack(Items.diamond, 1), new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource),  new ItemStack(ConfigItems.itemManaBean), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(Items.golden_chestplate), new ItemStack(Items.potionitem, 1, 8195), new ItemStack(ModItems.focusSmelt), new ItemStack(ModItems.brightNitor), new ItemStack(Items.lava_bucket), new ItemStack(Items.fire_charge), new ItemStack(Items.blaze_rod));
			registerResearchItemI(LibResearch.KEY_ICHORCLOTH_BOOTS_GEM, new ItemStack(ModItems.ichorBootsGem), 13, new AspectList().add(Aspect.EARTH, 50).add(Aspect.ARMOR, 32).add(Aspect.MINE, 32).add(Aspect.MOTION, 32).add(Aspect.LIGHT, 64).add(Aspect.PLANT, 16).add(Aspect.TRAVEL, 16), new ItemStack(ModItems.ichorBoots),
				new ItemStack(Items.diamond, 1), new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource),  new ItemStack(ConfigItems.itemManaBean), new ItemStack(ConfigItems.itemWispEssence), new ItemStack(Items.golden_boots), new ItemStack(Blocks.grass), new ItemStack(ConfigBlocks.blockWoodenDevice, 1, 5), new ItemStack(ConfigBlocks.blockMetalDevice, 1, 8), new ItemStack(Items.wheat_seeds), new ItemStack(Blocks.wool), new ItemStack(Items.lead));
			registerResearchItemI(LibResearch.KEY_CAT_AMULET, new ItemStack(ModItems.catAmulet), 8, new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.ORDER, 32).add(Aspect.MIND, 16), new ItemStack(Blocks.quartz_block),
				new ItemStack(ModItems.kamiResource), new ItemStack(Items.gold_ingot), new ItemStack(Items.gold_ingot), new ItemStack(Items.dye, 1, 3), new ItemStack(Blocks.leaves, 1, 3), new ItemStack(Items.fish));
			registerResearchItemI(LibResearch.KEY_ICHOR_PICK_GEM, new ItemStack(ModItems.ichorPickGem), 15, new AspectList().add(Aspect.FIRE, 50).add(Aspect.MINE, 64).add(Aspect.METAL, 32).add(Aspect.EARTH, 32).add(Aspect.HARVEST, 32).add(Aspect.GREED, 16).add(Aspect.SENSES, 16), new ItemStack(ModItems.ichorPick),
				new ItemStack(ModItems.kamiResource, 1, 2), new ItemStack(ModItems.kamiResource), new ItemStack(ConfigItems.itemPickElemental), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(Blocks.tnt), new ItemStack(ConfigItems.itemNugget, 1, 21), new ItemStack(ConfigItems.itemNugget, 1, 16), new ItemStack(ConfigItems.itemNugget, 1, 31), new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(ConfigItems.itemPickElemental), new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItemI(LibResearch.KEY_ICHOR_SHOVEL_GEM, new ItemStack(ModItems.ichorShovelGem), 15, new AspectList().add(Aspect.EARTH, 50).add(Aspect.MINE, 64).add(Aspect.TOOL, 32).add(Aspect.EARTH, 32).add(Aspect.HARVEST, 32).add(Aspect.TRAP, 16).add(Aspect.SENSES, 16), new ItemStack(ModItems.ichorShovel),
					new ItemStack(ModItems.kamiResource, 1, 2), new ItemStack(ModItems.kamiResource), new ItemStack(ConfigItems.itemShovelElemental), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(Blocks.tnt), new ItemStack(ConfigItems.itemNugget, 1, 21), new ItemStack(ConfigItems.itemNugget, 1, 16), new ItemStack(ConfigItems.itemNugget, 1, 31), new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(ConfigItems.itemShovelElemental), new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItemI(LibResearch.KEY_ICHOR_AXE_GEM, new ItemStack(ModItems.ichorAxeGem), 15, new AspectList().add(Aspect.WATER, 50).add(Aspect.MINE, 64).add(Aspect.TOOL, 32).add(Aspect.TREE, 32).add(Aspect.HARVEST, 32).add(Aspect.CROP, 16).add(Aspect.SENSES, 16), new ItemStack(ModItems.ichorAxe),
					new ItemStack(ModItems.kamiResource, 1, 2), new ItemStack(ModItems.kamiResource), new ItemStack(ConfigItems.itemAxeElemental), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(Blocks.tnt), new ItemStack(ConfigItems.itemNugget, 1, 21), new ItemStack(ConfigItems.itemNugget, 1, 16), new ItemStack(ConfigItems.itemNugget, 1, 31), new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(ConfigItems.itemAxeElemental), new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItemI(LibResearch.KEY_ICHOR_SWORD_GEM, new ItemStack(ModItems.ichorSwordGem), 15, new AspectList().add(Aspect.AIR, 50).add(Aspect.HUNGER, 64).add(Aspect.SOUL, 32).add(Aspect.WEAPON, 32).add(Aspect.ENERGY, 32).add(Aspect.ORDER, 16).add(Aspect.CRYSTAL, 16), new ItemStack(ModItems.ichorSword),
					new ItemStack(ModItems.kamiResource, 1, 2), new ItemStack(ModItems.kamiResource), new ItemStack(ConfigItems.itemSwordElemental), new ItemStack(ConfigItems.itemFocusFrost), new ItemStack(Blocks.cactus), new ItemStack(ConfigItems.itemNugget, 1, 21), new ItemStack(ConfigItems.itemNugget, 1, 16), new ItemStack(ConfigItems.itemNugget, 1, 31), new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusFrost), new ItemStack(ConfigItems.itemSwordElemental), new ItemStack(ModItems.kamiResource, 1, 1));
			registerResearchItemI(LibResearch.KEY_ICHOR_POUCH, new ItemStack(ModItems.ichorPouch), 9, new AspectList().add(Aspect.VOID, 64).add(Aspect.MAN, 32).add(Aspect.CLOTH, 32).add(Aspect.ELDRITCH, 32).add(Aspect.AIR, 64), new ItemStack(ConfigItems.itemFocusPouch),
					new ItemStack(ModItems.kamiResource, 1, 1), new ItemStack(ConfigItems.itemFocusPortableHole), new ItemStack(Items.diamond), new ItemStack(ModItems.kamiResource, 1, 1), new ItemStack(ConfigBlocks.blockChestHungry), new ItemStack(ConfigBlocks.blockJar, 1, 3));
			if(Loader.isModLoaded("EnderStorage")) {	
				EnderStorageRecepie.loadRecipes();
			}
				registerResearchItemI(LibResearch.KEY_BLOCK_TALISMAN, new ItemStack(ModItems.blockTalisman), 9, new AspectList().add(Aspect.VOID, 65).add(Aspect.DARKNESS, 32).add(Aspect.MAGIC, 50).add(Aspect.ELDRITCH, 32), new ItemStack(ConfigItems.itemFocusPortableHole),
						new ItemStack(ModItems.kamiResource), new ItemStack(Blocks.ender_chest), new ItemStack(Items.diamond), new ItemStack(ModItems.kamiResource), new ItemStack(ConfigItems.itemResource, 1, 11), new ItemStack(ConfigBlocks.blockJar, 1, 3));
			registerResearchItemI(LibResearch.KEY_PLACEMENT_MIRROR, new ItemStack(ModItems.placementMirror), 12, new AspectList().add(Aspect.CRAFT, 65).add(Aspect.CRYSTAL, 32).add(Aspect.MAGIC, 50).add(Aspect.MIND, 32), new ItemStack(ModItems.blockTalisman),
					new ItemStack(ModItems.kamiResource), new ItemStack(Blocks.dropper), new ItemStack(Items.diamond), new ItemStack(Blocks.glass), new ItemStack(Items.blaze_powder), new ItemStack(ModItems.kamiResource));
			registerResearchItemI(LibResearch.KEY_FOCUS_SHADOWBEAM, new ItemStack(ModItems.focusShadowbeam), 12, new AspectList().add(Aspect.DARKNESS, 65).add(Aspect.ELDRITCH, 32).add(Aspect.MAGIC, 50).add(Aspect.WEAPON, 32), new ItemStack(ConfigItems.itemFocusShock),
					new ItemStack(ModItems.kamiResource), new ItemStack(Items.arrow), new ItemStack(Items.diamond), new ItemStack(ConfigItems.itemFocusExcavation), new ItemStack(ModItems.focusDeflect), new ItemStack(ModItems.kamiResource));
			registerResearchItemI(LibResearch.KEY_FOCUS_XP_DRAIN, new ItemStack(ModItems.focusXPDrain), 12, new AspectList().add(Aspect.MIND, 65).add(Aspect.TAINT, 16).add(Aspect.MAGIC, 50).add(Aspect.AURA, 32), new ItemStack(Items.ender_pearl),
					new ItemStack(ModItems.kamiResource), new ItemStack(Items.experience_bottle), new ItemStack(Items.diamond), new ItemStack(ModItems.xpTalisman), new ItemStack(Blocks.enchanting_table), new ItemStack(ModItems.kamiResource));
			registerResearchItemI(LibResearch.KEY_PROTOCLAY, new ItemStack(ModItems.protoclay), 4, new AspectList().add(Aspect.MINE, 16).add(Aspect.TOOL, 16), new ItemStack(Items.clay_ball),
                    
					new ItemStack(Blocks.dirt), new ItemStack(Blocks.stone), new ItemStack(Blocks.log), new ItemStack(ModItems.kamiResource, 1, 7));



			if(Config.allowMirrors) {
                
                registerResearchItemI(LibResearch.KEY_WARP_GATE, new ItemStack(ModBlocks.warpGate), 8, new AspectList().add(Aspect.TRAVEL, 64).add(Aspect.ELDRITCH, 50).add(Aspect.FLIGHT, 50), new ItemStack(ConfigBlocks.blockCosmeticSolid, 1, 2),
						new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource, 1, 7), new ItemStack(ModBlocks.dislocator), new ItemStack(ModItems.kamiResource, 1, 6), new ItemStack(Items.diamond), new ItemStack(Items.feather));
				registerResearchItemI(LibResearch.KEY_SKY_PEARL, LibResearch.KEY_WARP_GATE, new ItemStack(ModItems.skyPearl, 2), 6, new AspectList().add(Aspect.TRAVEL, 32).add(Aspect.ELDRITCH, 32).add(Aspect.FLIGHT, 32).add(Aspect.AIR, 16), new ItemStack(Items.ender_pearl),
						new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource, 1, 7), new ItemStack(Blocks.lapis_block), new ItemStack(Items.diamond));
				registerResearchItemI(LibResearch.KEY_FOCUS_RECALL, new ItemStack(ModItems.focusRecall), 10, new AspectList().add(Aspect.TRAVEL, 100).add(Aspect.ELDRITCH, 64).add(Aspect.MAGIC, 50), new ItemStack(ModItems.skyPearl),
					new ItemStack(ModItems.kamiResource), new ItemStack(ModItems.kamiResource), new ItemStack(Items.ender_pearl), new ItemStack(Items.diamond), new ItemStack(ConfigBlocks.blockMirror), new ItemStack(ConfigItems.itemFocusPortableHole));
			}
		}
	}

	private static void initCrucibleRecipes() {
		registerResearchItem(LibResearch.KEY_GASEOUS_LIGHT, new ItemStack(ModItems.gaseousLight), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.LIGHT, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
		registerResearchItem(LibResearch.KEY_GASEOUS_SHADOW, new ItemStack(ModItems.gaseousShadow), new ItemStack(ConfigItems.itemEssence, 1, 0), new AspectList().add(Aspect.DARKNESS, 16).add(Aspect.AIR, 10).add(Aspect.MOTION, 8));
		registerResearchItem(LibResearch.KEY_SPELL_CLOTH, new ItemStack(ModItems.spellCloth), new ItemStack(ConfigItems.itemResource, 0, 7), new AspectList().add(Aspect.MAGIC, 10).add(Aspect.ENTROPY, 6).add(Aspect.EXCHANGE, 4));
		registerResearchItem(LibResearch.KEY_BRIGHT_NITOR, new ItemStack(ModItems.brightNitor), new ItemStack(ConfigItems.itemResource, 1, 1), new AspectList().add(Aspect.ENERGY, 25).add(Aspect.LIGHT, 25).add(Aspect.AIR, 10).add(Aspect.FIRE, 10));
		registerResearchItem(LibResearch.KEY_MAGNETS, new ItemStack(ModItems.soulMould), new ItemStack(Items.ender_pearl), new AspectList().add(Aspect.BEAST, 4).add(Aspect.MIND, 8).add(Aspect.SENSES, 8));
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

	static void registerResearchItemI(String name, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
		registerResearchItemI(name, name, output, instability, aspects, input, stuff);
	}

	private static void registerResearchItemI(String name, String research, Object output, int instability, AspectList aspects, ItemStack input, ItemStack... stuff) {
		InfusionRecipe recipe = ThaumcraftApi.addInfusionCraftingRecipe(research, output, instability, aspects, input, stuff);
		ConfigResearch.recipes.put(name, recipe);
	}

	private static Object oreDictOrStack(ItemStack stack, String oreDict) {
		return OreDictionary.getOres(oreDict).isEmpty() && ConfigHandler.useOreDictMetal ? stack : oreDict;
	}
}
