package vazkii.tinkerer.util.handler;

import java.io.File;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import vazkii.tinkerer.lib.LibBlockIDs;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibItemIDs;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.lib.LibPotions;

public final class ConfigurationHandler {

	private static Configuration config;

	private static final String CATEGORY_POTIONS = "potions";
	private static final String CATEGORY_BALANCE = "balance";

	private static ConfigCategory categoryPotions;
	private static ConfigCategory categoryBalance;

	private static String NODE_TRANSMUTATOR_VIS_MULTI = "transmutator.visMultiplier";
	private static String NODE_TRANSMUTATOR_ESSENTIA_MULTI = "transmutator.aspectMultiplier";
	private static String NODE_TRANSMUTATOR_MAX_VALUE = "transmutator.maxValue";
	private static String NODE_TRANSMUTATOR_ENABLE = "transmutator.enable";

	public static int transmutatorVisMultiplier = 2;
	public static int transmutatorEssentiaMultiplier = 4;
	public static int transmutatorMaxValue = 75;
	public static boolean enableTransmutator = true;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		categoryPotions = new ConfigCategory(CATEGORY_POTIONS);
		categoryBalance = new ConfigCategory(CATEGORY_BALANCE);

		config.load();

		LibItemIDs.idWandTinkerer = loadItem(LibItemNames.WAND_TINKERER, LibItemIDs.DEFAULT_WAND_TINKERER);
		LibItemIDs.idGlowstoneGas = loadItem(LibItemNames.GLOWSTONE_GAS, LibItemIDs.DEFAULT_GLOWSTONE_GAS);
		LibItemIDs.idSpellCloth = loadItem(LibItemNames.SPELL_CLOTH, LibItemIDs.DEFAULT_SPELL_CLOTH);
		LibItemIDs.idStopwatch = loadItem(LibItemNames.STOPWATCH, LibItemIDs.DEFAULT_STOPWATCH);
		LibItemIDs.idWandDislocation = loadItem(LibItemNames.WAND_DISLOCATION, LibItemIDs.DEFAULT_WAND_DISLOCATION);
		LibItemIDs.idNametag = loadItem(LibItemNames.NAMETAG, LibItemIDs.DEFAULT_NAMETAG);
		LibItemIDs.idXpTalisman = loadItem(LibItemNames.XP_TALISMAN, LibItemIDs.DEFAULT_XP_TALISMAN);
		LibItemIDs.idFireBracelet = loadItem(LibItemNames.FIRE_BRACELET, LibItemIDs.DEFAULT_FIRE_BRACELET);
		LibItemIDs.idDarkQuartz = loadItem(LibItemNames.DARK_QUARTZ, LibItemIDs.DEFAULT_DARK_QUARTZ);
		LibItemIDs.idTeleportSigil = loadItem(LibItemNames.TELEPORTATION_SIGIL, LibItemIDs.DEFAULT_TELEPORT_SIGIL);
		LibItemIDs.idWandUprising = loadItem(LibItemNames.WAND_UPRISING, LibItemIDs.DEFAULT_WAND_UPRISING);
		LibItemIDs.idSwordCondor = loadItem(LibItemNames.SWORD_CONDOR, LibItemIDs.DEFAULT_SWORD_CONDOR);
		LibItemIDs.idDeathRune = loadItem(LibItemNames.DEATH_RUNE, LibItemIDs.DEFAULT_DEATH_RUNE);
		LibItemIDs.idSilkSword = loadItem(LibItemNames.SILK_SWORD, LibItemIDs.DEFAULT_SILK_SWORD);
		LibItemIDs.idFortuneMaul = loadItem(LibItemNames.FORTUNE_MAUL, LibItemIDs.DEFAULT_FORTUNE_MAUL);
		LibItemIDs.idEnderMirror = loadItem(LibItemNames.ENDER_MIRROR, LibItemIDs.DEFAULT_ENDER_MIRROR);
		LibItemIDs.idGoliathLegs = loadItem(LibItemNames.GOLIATH_LEGS, LibItemIDs.DEFAULT_GOLIATH_LEGS);
		LibItemIDs.idDarkGas = loadItem(LibItemNames.DARK_GAS, LibItemIDs.DEFAULT_DARK_GAS);

		LibBlockIDs.idGlowstoneGas = loadBlock(LibBlockNames.GLOWSTONE_GAS, LibBlockIDs.DEFAULT_GLOWSTONE_GAS);
		LibBlockIDs.idTransmutator = loadBlock(LibBlockNames.TRANSMUTATOR, LibBlockIDs.DEFAULT_TRANSMUTATOR);
		LibBlockIDs.idWardChest = loadBlock(LibBlockNames.WARD_CHEST, LibBlockIDs.DEFAULT_WARD_CHEST);
		LibBlockIDs.idDarkQuartz = loadBlock(LibBlockNames.DARK_QUARTZ, LibBlockIDs.DEFAULT_DARK_QUARTZ);
		LibBlockIDs.idDarkQuartzStairs = loadBlock(LibBlockNames.DARK_QUARTZ_STAIRS, LibBlockIDs.DEFAULT_DARK_QUARTZ_STAIRS);
		LibBlockIDs.idDarkQuartzSlab = loadBlock(LibBlockNames.DARK_QUARTZ_SLAB, LibBlockIDs.DEFAULT_DARK_QUARTZ_SLAB);
		LibBlockIDs.idDarkQuartzSlabFull = loadBlock(LibBlockNames.DARK_QUARTZ_SLAB_FULL, LibBlockIDs.DEFAULT_DARK_QUARTZ_SLAB_FULL);
		LibBlockIDs.idAnimationTablet = loadBlock(LibBlockNames.ANIMATION_TABLET, LibBlockIDs.DEFAULT_ANIMATION_TABLET);
		LibBlockIDs.idDarkGas = loadBlock(LibBlockNames.DARK_GAS, LibBlockIDs.DEFAULT_DARK_GAS);

		LibPotions.idStopwatch = loadPotion(LibPotions.NAME_STOPWATCH, LibPotions.DEFAULT_ID_STOPWATCH);

		transmutatorVisMultiplier = loadBalanceInt(NODE_TRANSMUTATOR_VIS_MULTI, transmutatorVisMultiplier, "The multiplier of the vis cost for an item on the transmutator. X times the total amount of aspect value.");
		transmutatorEssentiaMultiplier = loadBalanceInt(NODE_TRANSMUTATOR_ESSENTIA_MULTI, transmutatorEssentiaMultiplier, "The multiplier of the aspect cost for an item on the transmutator, X times the amount of the original value.");
		transmutatorMaxValue = loadBalanceInt(NODE_TRANSMUTATOR_MAX_VALUE, transmutatorMaxValue, "The maximum cost of an item that can be put in a transmutator. Cost refers to the total amount of aspect value.");
		enableTransmutator = loadBalanceBool(NODE_TRANSMUTATOR_ENABLE, enableTransmutator, "Set to false to completely disable the transmutator.");

		config.save();
	}

	private static int loadItem(String label, int defaultID) {
		return config.getItem(label, defaultID).getInt(defaultID);
	}

	private static int loadBlock(String label, int defaultID) {
		return config.getBlock(label, defaultID).getInt(defaultID);
	}

	private static int loadPotion(String label, int deafultID) {
		return config.get(CATEGORY_POTIONS, label, deafultID).getInt(deafultID);
	}

	private static boolean loadBalanceBool(String label, boolean defaultValue, String comment) {
		Property prop = config.get(CATEGORY_BALANCE, label, defaultValue);
		prop.comment = comment;
		return prop.getBoolean(defaultValue);
	}

	private static int loadBalanceInt(String label, int defaultValue, String comment) {
		Property prop = config.get(CATEGORY_BALANCE, label, defaultValue);
		prop.comment = comment;
		return prop.getInt(defaultValue);
	}
}
