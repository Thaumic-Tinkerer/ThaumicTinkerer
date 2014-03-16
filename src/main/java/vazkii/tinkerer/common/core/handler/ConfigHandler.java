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

import cpw.mods.fml.common.Loader;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import thaumcraft.common.config.Config;
import vazkii.tinkerer.common.dim.OreClusterGenerator;
import vazkii.tinkerer.common.lib.*;

import java.io.File;

public final class ConfigHandler {

	private static Configuration config;

	private static final String CATEGORY_POTIONS = "potions";
	private static final String CATEGORY_ENCHANTMENTS = "enchantments";

	private static final String CATEGORY_KAMI_ITEMS = "item.kami";
	private static final String CATEGORY_KAMI_BLOCKS = "block.kami";
	private static final String CATEGORY_KAMI_GENERAL = "general.kami";

	public static boolean enableKami = false;
	public static boolean useTootlipIndicators = true;
	public static boolean enableSurvivalShareTome = true;
	public static boolean enableEasymodeResearch = false;
	public static boolean enableDebugCommands=false;
	public static boolean useOreDictMetal = true;
	public static boolean repairTConTools = false;
	
	public static boolean showPlacementMirrorBlocks = true;
	public static int netherDimensionID = -1;
	public static int endDimensionID = 1;
	public static int bedrockDimensionID=19;
	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		new ConfigCategory(CATEGORY_POTIONS);
		new ConfigCategory(CATEGORY_ENCHANTMENTS);
		new ConfigCategory(CATEGORY_KAMI_ITEMS);
		new ConfigCategory(CATEGORY_KAMI_BLOCKS);
		new ConfigCategory(CATEGORY_KAMI_GENERAL);

		String comment = "These will only be used if KAMI is loaded. (KAMI is a separate download you can find in the Thaumic Tinkerer thread)";
		config.addCustomCategoryComment(CATEGORY_KAMI_ITEMS, comment);
		config.addCustomCategoryComment(CATEGORY_KAMI_BLOCKS, comment);
		config.addCustomCategoryComment(CATEGORY_KAMI_GENERAL, comment);

		config.load();

		Property propEnableKami=config.get(Configuration.CATEGORY_GENERAL, "kami.forceenabled", false);
		propEnableKami.comment = "Set to true to enable all kami stuff (note, either this OR the kami mod file will work)";
		enableKami = Loader.isModLoaded("ThaumicTinkererKami") || propEnableKami.getBoolean(false);


		Property propEnableTooltips = config.get(Configuration.CATEGORY_GENERAL, "tooltipIndicators.enabled", true);
		propEnableTooltips.comment = "Set to false to disable the [TT] tooltips in the thauminomicon.";
		useTootlipIndicators = propEnableTooltips.getBoolean(true);

		Property propEnableSurvivalShareTome = config.get(Configuration.CATEGORY_GENERAL, "shareTome.survival.enabled", true);
		propEnableSurvivalShareTome.comment = "Set to false to disable the crafting recipe for the Tome of Research Sharing.";
		enableSurvivalShareTome = propEnableSurvivalShareTome.getBoolean(true);

		Property propEasymodeResearch = config.get(Configuration.CATEGORY_GENERAL, "research.easymode.enabled", false);
		propEasymodeResearch.comment = "Set to true to enable Easy Research (getting research notes = instant discovery). For those who don't like research. (DEPRECATED: Please use thaumcraft.cfg to edit this now, all this does is alter that)";
		enableEasymodeResearch = propEasymodeResearch.getBoolean(false);
		Config.researchDifficulty=(enableEasymodeResearch)?-1:Config.researchDifficulty;
		
		Property propDebugCommands = config.get(Configuration.CATEGORY_GENERAL, "debugCommands.enabled", false);
		propDebugCommands.comment = "Set to true to enable debugging commands.";
		enableDebugCommands = propDebugCommands.getBoolean(false);
		
		Property propRepairTCon = config.get(Configuration.CATEGORY_GENERAL, "repairTconTools.enabled", false);
		propRepairTCon.comment = "Can Thaumic Tinkerer repair Tinkers Construct tools.";
		repairTConTools = propRepairTCon.getBoolean(false);
		
		Property propOreDict = config.get(Configuration.CATEGORY_GENERAL, "oreDictMetal.enabled", true);
		propOreDict.comment = "Set to false to disable usage of ore dictionary metals (tin and copper).";
		useOreDictMetal = propOreDict.getBoolean(true);
		
		if(enableKami) {
			Property propDimensionID = config.get(CATEGORY_KAMI_GENERAL, "Bedrock dimension id", -19);
			propDimensionID.comment = "Set to the dimension id wished for bedrock dimension, or 0 to disable";
			bedrockDimensionID= propDimensionID.getInt(-19);

			Property oreBlacklist = config.get(CATEGORY_KAMI_GENERAL, "Bedrock dimension ore Blacklist", new String[]{"oreFirestone"});
			oreBlacklist.comment = "These ores will not be spawned in the bedrock dimension";
			OreClusterGenerator.blacklist= oreBlacklist.getStringList();

			Property propOreDensity=config.get(Configuration.CATEGORY_GENERAL, "Bedrock Dimension ore density", 1);
			propOreDensity.comment = "The number of verticle veins of ore per chunk. Default: 1";
			OreClusterGenerator.density=propOreDensity.getInt(1);


			Property propShowPlacementMirrorBlocks = config.get(CATEGORY_KAMI_GENERAL, "placementMirror.blocks.show", true);
			propShowPlacementMirrorBlocks.comment = "Set to false to remove the phantom blocks displayed by the Worldshaper's Seeing Glass.";
			showPlacementMirrorBlocks = propShowPlacementMirrorBlocks.getBoolean(true);

			Property propNetherID = config.get(CATEGORY_KAMI_GENERAL, "dimension.nether.id", -1);
			propNetherID.comment = "The Dimension ID for the Nether, leave at -1 if you don't modify it with another mod/plugin.";
			netherDimensionID = propNetherID.getInt(-1);

			Property propEndID = config.get(CATEGORY_KAMI_GENERAL, "dimension.end.id", 1);
			propEndID.comment = "The Dimension ID for the End, leave at 1 if you don't modify it with another mod/plugin.";
			endDimensionID = propEndID.getInt(1);
		}

		LibBlockIDs.idDarkQuartz = loadBlock(LibBlockNames.DARK_QUARTZ, LibBlockIDs.idDarkQuartz);
		LibBlockIDs.idDarkQuartzSlab = loadBlock(LibBlockNames.DARK_QUARTZ_SLAB, LibBlockIDs.idDarkQuartzSlab);
		LibBlockIDs.idDarkQuartzSlabFull = loadBlock(LibBlockNames.DARK_QUARTZ_SLAB_FULL, LibBlockIDs.idDarkQuartzSlabFull);
		LibBlockIDs.idDarkQuartzStairs = loadBlock(LibBlockNames.DARK_QUARTZ_STAIRS, LibBlockIDs.idDarkQuartzStairs);
		LibBlockIDs.idInterface = loadBlock(LibBlockNames.INTERFACE, LibBlockIDs.idInterface);
		LibBlockIDs.idGaseousLight = loadBlock(LibBlockNames.GASEOUS_LIGHT, LibBlockIDs.idGaseousLight);
		LibBlockIDs.idGaseousShadow = loadBlock(LibBlockNames.GASEOUS_SHADOW, LibBlockIDs.idGaseousShadow);
		LibBlockIDs.idAnimationTablet = loadBlock(LibBlockNames.ANIMATION_TABLET, LibBlockIDs.idAnimationTablet);
		LibBlockIDs.idNitorGas = loadBlock(LibBlockNames.NITOR_GAS, LibBlockIDs.idNitorGas);
		LibBlockIDs.idMagnet = loadBlock(LibBlockNames.MAGNET, LibBlockIDs.idMagnet);
		LibBlockIDs.idEnchanter = loadBlock(LibBlockNames.ENCHANTER, LibBlockIDs.idEnchanter);
		LibBlockIDs.idFunnel = loadBlock(LibBlockNames.FUNNEL, LibBlockIDs.idFunnel);
		LibBlockIDs.idDislocator = loadBlock(LibBlockNames.DISLOCATOR, LibBlockIDs.idDislocator);
		LibBlockIDs.idRepairer = loadBlock(LibBlockNames.REPAIRER, LibBlockIDs.idRepairer);
		LibBlockIDs.idAspectAnalyzer = loadBlock(LibBlockNames.ASPECT_ANALYZER, LibBlockIDs.idAspectAnalyzer);
		LibBlockIDs.idPlatform = loadBlock(LibBlockNames.PLATFORM, LibBlockIDs.idPlatform);

		LibBlockIDs.idWarpGate = loadBlock(LibBlockNames.WARP_GATE, LibBlockIDs.idWarpGate);

		LibBlockIDs.idPortal = loadBlock(LibBlockNames.PORTAL, LibBlockIDs.idPortal);

		LibBlockIDs.idMobilizer= loadBlock(LibBlockNames.MOBILIZER, LibBlockIDs.idMobilizer);

		LibBlockIDs.idMobilizerRelay= loadBlock(LibBlockNames.MOBILIZER_RELAY, LibBlockIDs.idMobilizerRelay);
		LibBlockIDs.idGolemConnector= loadBlock(LibBlockNames.GOLEMCONNECTOR, LibBlockIDs.idGolemConnector);

		LibItemIDs.idDarkQuartz = loadItem(LibItemNames.DARK_QUARTZ, LibItemIDs.idDarkQuartz);
		LibItemIDs.idConnector = loadItem(LibItemNames.CONNECTOR, LibItemIDs.idConnector);
		LibItemIDs.idGaseousLight = loadItem(LibItemNames.GASEOUS_LIGHT, LibItemIDs.idGaseousLight);
		LibItemIDs.idGaseousShadow = loadItem(LibItemNames.GASEOUS_SHADOW, LibItemIDs.idGaseousShadow);
		LibItemIDs.idGasRemover = loadItem(LibItemNames.GAS_REMOVER, LibItemIDs.idGasRemover);
		LibItemIDs.idSpellCloth = loadItem(LibItemNames.SPELL_CLOTH, LibItemIDs.idSpellCloth);
		LibItemIDs.idFocusFlight = loadItem(LibItemNames.FOCUS_FLIGHT, LibItemIDs.idFocusFlight);
		LibItemIDs.idFocusDislocation = loadItem(LibItemNames.FOCUS_DISLOCATION, LibItemIDs.idFocusDislocation);
		LibItemIDs.idCleansingTalisman = loadItem(LibItemNames.CLEANSING_TALISMAN, LibItemIDs.idCleansingTalisman);
		LibItemIDs.idFocusTelekinesis = loadItem(LibItemNames.FOCUS_TELEKINESIS, LibItemIDs.idFocusTelekinesis);
		LibItemIDs.idBrightNitor = loadItem(LibItemNames.BRIGHT_NTIOR, LibItemIDs.idBrightNitor);
		LibItemIDs.idSoulMould = loadItem(LibItemNames.SOUL_MOULD, LibItemIDs.idSoulMould);
		LibItemIDs.idXPTalisman = loadItem(LibItemNames.XP_TALISMAN, LibItemIDs.idXPTalisman);
		LibItemIDs.idFocusSmelt = loadItem(LibItemNames.FOCUS_SMELT, LibItemIDs.idFocusSmelt);
		LibItemIDs.idFocusHeal = loadItem(LibItemNames.FOCUS_HEAL, LibItemIDs.idFocusHeal);
		LibItemIDs.idFocusEnderChest = loadItem(LibItemNames.FOCUS_ENDER_CHEST, LibItemIDs.idFocusEnderChest);
		LibItemIDs.idBloodSword = loadItem(LibItemNames.BLOOD_SWORD, LibItemIDs.idBloodSword);
		LibItemIDs.idRevealingHelm = loadItem(LibItemNames.REVEALING_HELM, LibItemIDs.idRevealingHelm);
		LibItemIDs.idInfusedInkwell = loadItem(LibItemNames.INFUSED_INKWELL, LibItemIDs.idInfusedInkwell);
		LibItemIDs.idFocusDeflect = loadItem(LibItemNames.FOCUS_DEFLECT, LibItemIDs.idFocusDeflect);
		LibItemIDs.idShareBook = loadItem(LibItemNames.SHARE_BOOK, LibItemIDs.idShareBook);

		LibItemIDs.idKamiResource = loadKamiItem(LibItemNames.KAMI_RESOURCE, LibItemIDs.idKamiResource);
		LibItemIDs.idIchorHelm = loadKamiItem(LibItemNames.ICHOR_HELM, LibItemIDs.idIchorHelm);
		LibItemIDs.idIchorChest = loadKamiItem(LibItemNames.ICHOR_CHEST, LibItemIDs.idIchorChest);
		LibItemIDs.idIchorLegs = loadKamiItem(LibItemNames.ICHOR_LEGS, LibItemIDs.idIchorLegs);
		LibItemIDs.idIchorBoots = loadKamiItem(LibItemNames.ICHOR_BOOTS, LibItemIDs.idIchorBoots);
		LibItemIDs.idIchorHelmGem = loadKamiItem(LibItemNames.ICHOR_HELM_GEM, LibItemIDs.idIchorHelmGem);
		LibItemIDs.idIchorChestGem = loadKamiItem(LibItemNames.ICHOR_CHEST_GEM, LibItemIDs.idIchorChestGem);
		LibItemIDs.idIchorLegsGem = loadKamiItem(LibItemNames.ICHOR_LEGS_GEM, LibItemIDs.idIchorLegsGem);
		LibItemIDs.idIchorBootsGem = loadKamiItem(LibItemNames.ICHOR_BOOTS_GEM, LibItemIDs.idIchorBootsGem);
		LibItemIDs.idCatAmulet = loadKamiItem(LibItemNames.CAT_AMULET, LibItemIDs.idCatAmulet);
		LibItemIDs.idIchorPick = loadKamiItem(LibItemNames.ICHOR_PICK, LibItemIDs.idIchorPick);
		LibItemIDs.idIchorShovel = loadKamiItem(LibItemNames.ICHOR_SHOVEL, LibItemIDs.idIchorShovel);
		LibItemIDs.idIchorAxe = loadKamiItem(LibItemNames.ICHOR_AXE, LibItemIDs.idIchorAxe);
		LibItemIDs.idIchorSword = loadKamiItem(LibItemNames.ICHOR_SWORD, LibItemIDs.idIchorSword);
		LibItemIDs.idIchorPickGem = loadKamiItem(LibItemNames.ICHOR_PICK_GEM, LibItemIDs.idIchorPickGem);
		LibItemIDs.idIchorShovelGem = loadKamiItem(LibItemNames.ICHOR_SHOVEL_GEM, LibItemIDs.idIchorShovelGem);
		LibItemIDs.idIchorAxeGem = loadKamiItem(LibItemNames.ICHOR_AXE_GEM, LibItemIDs.idIchorAxeGem);
		LibItemIDs.idIchorSwordGem = loadKamiItem(LibItemNames.ICHOR_SWORD_GEM, LibItemIDs.idIchorSwordGem);
		LibItemIDs.idIchorPouch = loadKamiItem(LibItemNames.ICHOR_POUCH, LibItemIDs.idIchorPouch);
		LibItemIDs.idBlockTalisman = loadKamiItem(LibItemNames.BLOCK_TALISMAN, LibItemIDs.idBlockTalisman);
		LibItemIDs.idPlacementMirror = loadKamiItem(LibItemNames.PLACEMENT_MIRROR, LibItemIDs.idPlacementMirror);
		LibItemIDs.idFocusShadowbeam = loadKamiItem(LibItemNames.FOCUS_SHADOWBEAM, LibItemIDs.idFocusShadowbeam);
		LibItemIDs.idFocusXPDrain = loadKamiItem(LibItemNames.FOCUS_XP_DRAIN, LibItemIDs.idFocusXPDrain);
		LibItemIDs.idProtoclay = loadKamiItem(LibItemNames.PROTOCLAY, LibItemIDs.idProtoclay);
		LibItemIDs.idSkyPearl = loadKamiItem(LibItemNames.SKY_PEARL, LibItemIDs.idSkyPearl);
		LibItemIDs.idFocusRecall = loadKamiItem(LibItemNames.FOCUS_RECALL, LibItemIDs.idFocusRecall);

		LibEnchantIDs.idAscentBoost = loadEnchant(LibEnchantNames.ASCENT_BOOST, LibEnchantIDs.idAscentBoost);
		LibEnchantIDs.idSlowFall = loadEnchant(LibEnchantNames.SLOW_FALL, LibEnchantIDs.idSlowFall);
		LibEnchantIDs.idAutoSmelt = loadEnchant(LibEnchantNames.AUTO_SMELT, LibEnchantIDs.idAutoSmelt);
		LibEnchantIDs.idDesintegrate = loadEnchant(LibEnchantNames.DESINTEGRATE, LibEnchantIDs.idDesintegrate);
		LibEnchantIDs.idQuickDraw = loadEnchant(LibEnchantNames.QUICK_DRAW, LibEnchantIDs.idQuickDraw);
		LibEnchantIDs.idVampirism = loadEnchant(LibEnchantNames.VAMPIRISM, LibEnchantIDs.idVampirism);

		LibEnchantIDs.focusedStrike = loadEnchant(LibEnchantNames.focusedStrike, LibEnchantIDs.focusedStrike);
		LibEnchantIDs.dispersedStrikes = loadEnchant(LibEnchantNames.dispersedStrikes, LibEnchantIDs.dispersedStrikes);
		LibEnchantIDs.finalStrike = loadEnchant(LibEnchantNames.finalStrike, LibEnchantIDs.finalStrike);
		LibEnchantIDs.valiance = loadEnchant(LibEnchantNames.valiance, LibEnchantIDs.valiance);
		LibEnchantIDs.pounce = loadEnchant(LibEnchantNames.pounce, LibEnchantIDs.pounce);
		LibEnchantIDs.shockwave = loadEnchant(LibEnchantNames.shockwave, LibEnchantIDs.shockwave);
		LibEnchantIDs.shatter = loadEnchant(LibEnchantNames.shatter, LibEnchantIDs.shatter);
		LibEnchantIDs.tunnel = loadEnchant(LibEnchantNames.tunnel, LibEnchantIDs.tunnel);

		


		config.save();
	}

	private static int loadItem(String label, int defaultID) {
		return config.getItem("id_item." + label, defaultID).getInt(defaultID);
	}

	private static int loadKamiItem(String label, int defaultID) {
		return config.getItem(CATEGORY_KAMI_ITEMS, "id_item." + label, defaultID).getInt(defaultID);
	}

	private static int loadBlock(String label, int defaultID) {
		return config.getBlock("id_tile." + label, defaultID).getInt(defaultID);
	}

	private static int loadEnchant(String label, int deafultID) {
		return config.get(CATEGORY_ENCHANTMENTS, "id_enchant." + label, deafultID).getInt(deafultID);
	}
}
