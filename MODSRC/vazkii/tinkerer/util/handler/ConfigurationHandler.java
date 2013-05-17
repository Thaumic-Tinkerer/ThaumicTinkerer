package vazkii.tinkerer.util.handler;

import java.io.File;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import vazkii.tinkerer.lib.LibBlockIDs;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibItemIDs;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.lib.LibPotions;

public final class ConfigurationHandler {

	private static Configuration config;

	private static final String CATEGORY_POTIONS = "potions";

	private static ConfigCategory categoryPotions;

	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		categoryPotions = new ConfigCategory(CATEGORY_POTIONS);

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

		LibBlockIDs.idGlowstoneGas = loadBlock(LibBlockNames.GLOWSTONE_GAS, LibBlockIDs.DEFAULT_GLOWSTONE_GAS);
		LibBlockIDs.idTransmutator = loadBlock(LibBlockNames.TRANSMUTATOR, LibBlockIDs.DEFAULT_TRANSMUTATOR);
		LibBlockIDs.idWardChest = loadBlock(LibBlockNames.WARD_CHEST, LibBlockIDs.DEFAULT_WARD_CHEST);
		LibBlockIDs.idDarkQuartz = loadBlock(LibBlockNames.DARK_QUARTZ, LibBlockIDs.DEFAULT_DARK_QUARTZ);
		LibBlockIDs.idDarkQuartzStairs = loadBlock(LibBlockNames.DARK_QUARTZ_STAIRS, LibBlockIDs.DEFAULT_DARK_QUARTZ_STAIRS);
		LibBlockIDs.idDarkQuartzSlab = loadBlock(LibBlockNames.DARK_QUARTZ_SLAB, LibBlockIDs.DEFAULT_DARK_QUARTZ_SLAB);
		LibBlockIDs.idDarkQuartzSlabFull = loadBlock(LibBlockNames.DARK_QUARTZ_SLAB_FULL, LibBlockIDs.DEFAULT_DARK_QUARTZ_SLAB_FULL);
		LibBlockIDs.idAnimationTablet = loadBlock(LibBlockNames.ANIMATION_TABLET, LibBlockIDs.DEFAULT_ANIMATION_TABLET);

		LibPotions.idStopwatch = loadPotion(LibPotions.NAME_STOPWATCH, LibPotions.DEFAULT_ID_STOPWATCH);

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

}
