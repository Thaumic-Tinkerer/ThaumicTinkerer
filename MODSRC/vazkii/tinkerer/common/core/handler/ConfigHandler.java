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
import vazkii.tinkerer.common.lib.LibBlockIDs;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.lib.LibEnchantIDs;
import vazkii.tinkerer.common.lib.LibEnchantNames;
import vazkii.tinkerer.common.lib.LibItemIDs;
import vazkii.tinkerer.common.lib.LibItemNames;

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
		LibItemIDs.idThaumiumPipe = loadItem(LibItemNames.THAUMIUM_PIPE, LibItemIDs.idThaumiumPipe);
		
		LibEnchantIDs.idAscentBoost = loadEnchant(LibEnchantNames.ASCENT_BOOST, LibEnchantIDs.idAscentBoost);
		LibEnchantIDs.idSlowFall = loadEnchant(LibEnchantNames.SLOW_FALL, LibEnchantIDs.idSlowFall);
		LibEnchantIDs.idAutoSmelt = loadEnchant(LibEnchantNames.AUTO_SMELT, LibEnchantIDs.idAutoSmelt);
		LibEnchantIDs.idDesintegrate = loadEnchant(LibEnchantNames.DESINTEGRATE, LibEnchantIDs.idDesintegrate);
		LibEnchantIDs.idQuickDraw = loadEnchant(LibEnchantNames.QUICK_DRAW, LibEnchantIDs.idQuickDraw);
		LibEnchantIDs.idVampirism = loadEnchant(LibEnchantNames.VAMPIRISM, LibEnchantIDs.idVampirism);

		config.save();
	}

	private static int loadItem(String label, int defaultID) {
		return config.getItem("id_item." + label, defaultID).getInt(defaultID);
	}

	private static int loadBlock(String label, int defaultID) {
		return config.getBlock("id_tile." + label, defaultID).getInt(defaultID);
	}

	private static int loadEnchant(String label, int deafultID) {
		return config.get(CATEGORY_ENCHANTMENTS, "id_enchant." + label, deafultID).getInt(deafultID);
	}
}
