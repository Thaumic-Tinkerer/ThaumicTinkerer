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
 * File Created @ [24 Apr 2013, 22:14:28 (GMT)]
 */
package vazkii.tinkerer.research;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.common.Config;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibEnchantmentNames;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.util.handler.ConfigurationHandler;

public final class ModResearchItems {

	public static ResearchItem wandTinkerer;
	public static ResearchItem glowstoneGas;
	public static ResearchItem spellCloth;
	public static ResearchItem stopwatch;
	public static ResearchItem wandDislocation;
	public static ResearchItem nametag;
	public static ResearchItem transmutator;
	public static ResearchItem xpTalisman;
	public static ResearchItem fireBracelet;
	public static ResearchItem wardChest;
	public static ResearchItem darkQuartz;
	public static ResearchItem sigilTeleport;
	public static ResearchItem wandUprising;
	public static ResearchItem swordCondor;
	public static ResearchItem deathRune;
	public static ResearchItem animationTablet;
	public static ResearchItem silkSword;
	public static ResearchItem fortuneMaul;
	public static ResearchItem enderMirror;
	public static ResearchItem goliathLegs;
	public static ResearchItem darkGas;
	public static ResearchItem gasRemover;
	public static ResearchItem cleansingTalisman;
	public static ResearchItem fluxDetector;
	public static ResearchItem lostEnchants;
	public static ResearchItem phantomBlocks;
	public static ResearchItem brightNitor;
	public static ResearchItem magnet;
	public static ResearchItem scythe;
	public static ResearchItem rainwaterBottle;
	public static ResearchItem weatherCrystals;

	public static void registerModResearchItems() {
		ObjectTags tags = new ObjectTags().add(EnumTag.MAGIC, 16).add(EnumTag.VOID, 8);
		wandTinkerer = new ResearchItem(LibItemNames.WAND_TINKERER_R, tags, -5, 3, ModItems.wandTinkerer).setParentsHidden(Config.researchGoggles).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.LIGHT, 7).add(EnumTag.WIND, 4).add(EnumTag.POWER, 3);
		glowstoneGas = new ResearchItem(LibItemNames.GLOWSTONE_GAS_R, tags, 4, 2, ModItems.glowstoneGas).setParents(Config.researchNitor).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.CLOTH, 9).add(EnumTag.MAGIC, 8).add(EnumTag.EXCHANGE, 6);
		spellCloth = new ResearchItem(LibItemNames.SPELL_CLOTH_R, tags, -6, 1, ModItems.spellCloth).setParents(Config.researchFabric).setParentsHidden(Config.researchUTFT).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.TIME, 4).add(EnumTag.MECHANISM, 8);
		stopwatch = new ResearchItem(LibItemNames.STOPWATCH_R, tags, -2, 24, ModItems.stopwatch).setParents(Config.researchTTOE).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.TRAP, 10).add(EnumTag.EXCHANGE, 8).add(EnumTag.CRYSTAL, 4);
		wandDislocation = new ResearchItem(LibItemNames.WAND_DISLOCATION_R, tags, -1, 7, ModItems.wandDislocation).setParents(Config.researchWandTrade).setParentsHidden(Config.researchPortableHole).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.WOOD, 1).add(EnumTag.DARK, 1);
		nametag = new ResearchItem(LibItemNames.NAMETAG_R, tags, 4, 18, ModItems.nametag).setParents(Config.researchGolemancy).registerResearchItem();

		if(ConfigurationHandler.enableTransmutator) {
			tags = new ObjectTags().add(EnumTag.FLUX, 12).add(EnumTag.EXCHANGE, 20);
			transmutator = new ResearchItem(LibBlockNames.TRANSMUTATOR_R, tags, -5, 24, ModBlocks.transmutator).setParents(Config.researchCrystalCore).setParentsHidden(Config.researchWandTrade).registerResearchItem();
		}

		tags = new ObjectTags().add(EnumTag.EVIL, 8).add(EnumTag.TRAP, 16).add(EnumTag.KNOWLEDGE, 12);
		xpTalisman = new ResearchItem(LibItemNames.XP_TALISMAN_R, tags, -4, 14, ModItems.xpTalisman).setParents(Config.researchJarBrain).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.FIRE, 8).add(EnumTag.ROCK, 12).add(EnumTag.METAL, 4);
		fireBracelet = new ResearchItem(LibItemNames.FIRE_BRACELET_R, tags, -4, 9, ModItems.fireBracelet).setParents(Config.researchArcaneBellows).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.WOOD, 12).add(EnumTag.VOID, 12).add(EnumTag.MAGIC, 16);
		wardChest = new ResearchItem(LibBlockNames.WARD_CHEST_R, tags, 3, 11, ModBlocks.wardChest).setParents(Config.researchArcaneDoor).setHidden().registerResearchItem();

		tags = new ObjectTags();
		darkQuartz = new ResearchItem(LibItemNames.DARK_QUARTZ_R, tags, -7, -3, ModItems.darkQuartz).setAutoUnlock().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.MECHANISM, 12).add(EnumTag.MOTION, 16).add(EnumTag.ELDRITCH, 5);
		sigilTeleport = new ResearchItem(LibItemNames.TELEPORTATION_SIGIL_R, tags, -2, 26, new ItemStack(ModItems.teleportSigil, 0, 1)).setParents(Config.researchTTOE).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.MAGIC, 8).add(EnumTag.POWER, 8).add(EnumTag.MOTION, 12).add(EnumTag.FLIGHT, 14);
		wandUprising = new ResearchItem(LibItemNames.WAND_UPRISING_R, tags, 2, 7, ModItems.wandUprising).setParents(Config.researchElementalSword).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.FLIGHT, 12).add(EnumTag.MOTION, 8).add(EnumTag.POWER, 14).add(EnumTag.MAGIC, 12);
		swordCondor = new ResearchItem(LibItemNames.SWORD_CONDOR_R, tags, 4, 7, ModItems.swordCondor).setParents(wandUprising).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.DEATH, 20).add(EnumTag.POWER, 16).add(EnumTag.TRAP, 12);
		deathRune = new ResearchItem(LibItemNames.DEATH_RUNE_R, tags, 5, 11, ModItems.deathRune).setParents(wardChest).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.CONTROL, 12).add(EnumTag.MOTION, 6).add(EnumTag.MECHANISM, 12).add(EnumTag.MAGIC, 4);
		animationTablet = new ResearchItem(LibBlockNames.ANIMATION_TABLET_R, tags, 4, 24, ModBlocks.animationTablet).setParents(Config.researchGolemIronGuardian).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.TOOL, 12).add(EnumTag.DARK, 1).add(EnumTag.CLOTH, 48);
		silkSword = new ResearchItem(LibItemNames.SILK_SWORD_R, tags, 7, 24, ModItems.silkSword).setParentsHidden(Config.researchThaumium).setHidden().setLost().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.TOOL, 12).add(EnumTag.DARK, 1).add(EnumTag.VALUABLE, 12);
		fortuneMaul = new ResearchItem(LibItemNames.FORTUNE_MAUL_R, tags, 8, 24, ModItems.fortuneMaul).setParentsHidden(Config.researchThaumium).setHidden().setLost().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.TOOL, 8).add(EnumTag.ELDRITCH, 6).add(EnumTag.MAGIC, 12).add(EnumTag.VISION, 12);
		enderMirror = new ResearchItem(LibItemNames.ENDER_MIRROR_R, tags, 5, 5, ModItems.enderMirror).setParents(Config.researchHandMirror).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.BEAST, 12).add(EnumTag.ARMOR, 12);
		goliathLegs = new ResearchItem(LibItemNames.GOLIATH_LEGS_R, tags, 1, 15, ModItems.goliathLegs).setParentsHidden(Config.researchRobes).setParents(Config.researchUTFT).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.DARK, 4).add(EnumTag.WIND, 5).add(EnumTag.POWER, 4);
		darkGas = new ResearchItem(LibItemNames.DARK_GAS_R, tags, 5, -1, ModItems.darkGas).setParents(Config.researchAlumentum).setParentsHidden(glowstoneGas).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.LIGHT, 1).add(EnumTag.DARK, 1).add(EnumTag.WIND, 1);
		gasRemover = new ResearchItem(LibItemNames.GAS_REMOVER_R, tags, 5, -2, ModItems.gasRemover).setParents(darkGas).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.HEAL, 10).add(EnumTag.TOOL, 16).add(EnumTag.MAGIC, 8).add(EnumTag.WATER, 4).add(EnumTag.KNOWLEDGE, 16);
		cleansingTalisman = new ResearchItem(LibItemNames.CLEANSING_TALISMAN_R, tags, 1, 18, ModItems.cleansingTalisman).setParents(Config.researchUTFT).setHidden().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.VISION, 16).add(EnumTag.MAGIC, 12).add(EnumTag.FLUX, 10);
		fluxDetector = new ResearchItem(LibItemNames.FLUX_DETECTOR_R, tags, -3, 19, ModItems.fluxDetector).setParents(Config.researchBasicFlux).setParentsHidden(Config.researchGoggles).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.MAGIC, 12).add(EnumTag.KNOWLEDGE, 24).add(EnumTag.COLD, 12).add(EnumTag.SPIRIT, 12).add(EnumTag.FIRE, 12).add(EnumTag.INSECT, 12).add(EnumTag.ELDRITCH, 16).add(EnumTag.POWER, 12).add(EnumTag.WEAPON, 14).add(EnumTag.TOOL, 14);
		lostEnchants = new ResearchItem(LibEnchantmentNames.LOST_ENCHANTS_RESEARCH_NAME, tags, 8, 26, Item.enchantedBook).setParentsHidden(Config.researchTTOE).setHidden().setLost().setSpecial().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.SPIRIT, 16).add(EnumTag.CRYSTAL, 12).add(EnumTag.TRAP, 6).add(EnumTag.ROCK, 12).add(EnumTag.ELDRITCH, 4);
		phantomBlocks = new ResearchItem(LibBlockNames.PHANTOM_STONE_R, tags, 2, 13, new ItemStack(ModBlocks.phantomStone, 1, 15)).setHidden().setParents(Config.researchArcaneDoor).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.FIRE, 12).add(EnumTag.LIGHT, 24).add(EnumTag.POWER, 30).add(EnumTag.MOTION, 12).add(EnumTag.DESTRUCTION, 6).add(EnumTag.VISION, 16);
		brightNitor = new ResearchItem(LibItemNames.BRIGHT_NITOR_R, tags, 2, 27, new ItemStack(ModItems.brightNitor)).setHidden().setParents(Config.researchTTOE).setParentsHidden(glowstoneGas).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.POWER, 6).add(EnumTag.CONTROL, 12).add(EnumTag.WIND, 6).add(EnumTag.METAL, 5).add(EnumTag.MECHANISM, 16);
		magnet = new ResearchItem(LibBlockNames.MAGNET_R, tags, -4, 27, new ItemStack(ModBlocks.magnet)).setHidden().setParents(Config.researchHoverHarness).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.DARK, 12).add(EnumTag.DEATH, 24).add(EnumTag.ELDRITCH, 6).add(EnumTag.EVIL, 9).add(EnumTag.FLESH, 14).add(EnumTag.SPIRIT, 24).add(EnumTag.LIFE, 16);
		scythe = new ResearchItem(LibItemNames.SCYTHE_R, tags, -2, 28, new ItemStack(ModItems.scythe)).setHidden().setParents(Config.researchTTOE).registerResearchItem();

		tags = new ObjectTags();
		rainwaterBottle = new ResearchItem(LibItemNames.RAINWATER_BOTTLE_R, tags, -7, -2, new ItemStack(ModItems.rainwaterBottle)).setAutoUnlock().registerResearchItem();

		tags = new ObjectTags().add(EnumTag.WEATHER, 12).add(EnumTag.FIRE, 12).add(EnumTag.WATER, 12).add(EnumTag.CRYSTAL, 10).add(EnumTag.VISION, 6);
		weatherCrystals = new ResearchItem(LibItemNames.WEATHER_CRYSTAL_R, tags, 2, 28, new ItemStack(ModItems.weatherCrystal)).setHidden().setParents(Config.researchTTOE).registerResearchItem();
	}
}