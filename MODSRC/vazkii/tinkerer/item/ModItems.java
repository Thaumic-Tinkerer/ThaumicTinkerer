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
 * File Created @ [24 Apr 2013, 22:53:42 (GMT)]
 */
package vazkii.tinkerer.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.lib.LibItemIDs;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.lib.LibMisc;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class ModItems {

	public static Item wandTinkerer;
	public static Item glowstoneGas;
	public static Item spellCloth;
	public static Item stopwatch;
	public static Item wandDislocation;
	public static Item nametag;
	public static Item xpTalisman;
	public static Item fireBracelet;
	public static Item darkQuartz;
	public static Item teleportSigil;
	public static Item wandUprising;
	public static Item swordCondor;
	public static Item deathRune;
	public static Item silkSword;
	public static Item fortuneMaul;
	public static Item enderMirror;
	public static Item goliathLegs;
	public static Item darkGas;
	public static Item gasRemover;
	public static Item cleansingTalisman;
	public static Item fluxDetector;
	public static Item dummyEnchantbook;
	public static Item researchShareBook;
	public static Item brightNitor;
	public static Item rune;
	public static Item scythe;
	public static Item rainwaterBottle;
	public static Item weatherCrystal;
	public static Item lovePotion;
	public static Item soulMould;
	public static Item connector;

	public static void initItems() {
		wandTinkerer = new ItemWandTinkerer(LibItemIDs.idWandTinkerer).setUnlocalizedName(LibItemNames.WAND_TINKERER);
		glowstoneGas = new ItemGas(LibItemIDs.idGlowstoneGas, ModBlocks.glowstoneGas).setUnlocalizedName(LibItemNames.GLOWSTONE_GAS);
		spellCloth = new ItemSpellCloth(LibItemIDs.idSpellCloth).setUnlocalizedName(LibItemNames.SPELL_CLOTH);
		stopwatch = new ItemStopwatch(LibItemIDs.idStopwatch).setUnlocalizedName(LibItemNames.STOPWATCH);
		wandDislocation = new ItemWandDislocation(LibItemIDs.idWandDislocation).setUnlocalizedName(LibItemNames.WAND_DISLOCATION);
		nametag = new ItemNametag(LibItemIDs.idNametag).setUnlocalizedName(LibItemNames.NAMETAG);
		xpTalisman = new ItemXPTalisman(LibItemIDs.idXpTalisman).setUnlocalizedName(LibItemNames.XP_TALISMAN);
		fireBracelet = new ItemFireBracelet(LibItemIDs.idFireBracelet).setUnlocalizedName(LibItemNames.FIRE_BRACELET);
		darkQuartz = new ItemMod(LibItemIDs.idDarkQuartz).setUnlocalizedName(LibItemNames.DARK_QUARTZ);
		teleportSigil = new ItemTeleportSigil(LibItemIDs.idTeleportSigil).setUnlocalizedName(LibItemNames.TELEPORTATION_SIGIL);
		wandUprising = new ItemWandUprising(LibItemIDs.idWandUprising).setUnlocalizedName(LibItemNames.WAND_UPRISING);
		swordCondor = new ItemSwordCondor(LibItemIDs.idSwordCondor).setUnlocalizedName(LibItemNames.SWORD_CONDOR);
		deathRune = new ItemDeathRune(LibItemIDs.idDeathRune).setUnlocalizedName(LibItemNames.DEATH_RUNE);
		silkSword = new ItemDefaultEnchant(LibItemIDs.idSilkSword, Enchantment.silkTouch, 1).setUnlocalizedName(LibItemNames.SILK_SWORD);
		fortuneMaul = new ItemDefaultEnchant(LibItemIDs.idFortuneMaul, Enchantment.fortune, 3).setUnlocalizedName(LibItemNames.FORTUNE_MAUL);
		enderMirror = new ItemEnderMirror(LibItemIDs.idEnderMirror).setUnlocalizedName(LibItemNames.ENDER_MIRROR);
		goliathLegs = new ItemGoliathLegs(LibItemIDs.idGoliathLegs).setUnlocalizedName(LibItemNames.GOLIATH_LEGS);
		darkGas = new ItemGas(LibItemIDs.idDarkGas, ModBlocks.darkGas).setUnlocalizedName(LibItemNames.DARK_GAS);
		gasRemover = new ItemGasRemover(LibItemIDs.idGasRemover).setUnlocalizedName(LibItemNames.GAS_REMOVER);
		cleansingTalisman = new ItemCleansingTalisman(LibItemIDs.idCleansingTalisman).setUnlocalizedName(LibItemNames.CLEANSING_TALISMAN);
		fluxDetector = new ItemFluxDetector(LibItemIDs.idFluxDetector).setUnlocalizedName(LibItemNames.FLUX_DETECTOR);
		dummyEnchantbook = new ItemDummyEnchantbook(LibItemIDs.idDummyEnchantbook).setUnlocalizedName(LibItemNames.DUMMY_ENCHANTBOOK);
		researchShareBook = new ItemShareTome(LibItemIDs.idResearchShareBook).setUnlocalizedName(LibItemNames.RESEARCH_SHARE_BOOK);
		brightNitor = new ItemBrightNitor(LibItemIDs.idBrightNitor).setUnlocalizedName(LibItemNames.BRIGHT_NITOR);
		rune = new ItemRune(LibItemIDs.idRune).setUnlocalizedName(LibItemNames.RUNE);
		scythe = new ItemScythe(LibItemIDs.idScythe).setUnlocalizedName(LibItemNames.SCYTHE);
		rainwaterBottle = new ItemRainwaterBottle(LibItemIDs.idRainwaterBottle).setUnlocalizedName(LibItemNames.RAINWATER_BOTTLE);
		weatherCrystal = new ItemWeatherCrystal(LibItemIDs.idWeatherCrystal).setUnlocalizedName(LibItemNames.WEATHER_CRYSTAL);
		lovePotion = new ItemLovePotion(LibItemIDs.idLovePotion).setUnlocalizedName(LibItemNames.LOVE_POTION);
		soulMould = new ItemSoulMould(LibItemIDs.idSoulMould).setUnlocalizedName(LibItemNames.SOUL_MOULD);
		connector = new ItemConnector(LibItemIDs.idConnector).setUnlocalizedName(LibItemNames.CONNECTOR);

		nameItems();
	}

	private static void nameItems() {
		LanguageRegistry.addName(wandTinkerer, LibItemNames.WAND_TINKERER_D);
		LanguageRegistry.addName(glowstoneGas, LibItemNames.GLOWSTONE_GAS_D);
		LanguageRegistry.addName(spellCloth, LibItemNames.SPELL_CLOTH_D);
		LanguageRegistry.addName(stopwatch, LibItemNames.STOPWATCH_D);
		LanguageRegistry.addName(wandDislocation, LibItemNames.WAND_DISLOCATION_D);
		LanguageRegistry.addName(nametag, LibItemNames.NAMETAG_D);
		LanguageRegistry.addName(xpTalisman, LibItemNames.XP_TALISMAN_D);
		LanguageRegistry.addName(fireBracelet, LibItemNames.FIRE_BRACELET_D);
		LanguageRegistry.addName(darkQuartz, LibItemNames.DARK_QUARTZ_D);
		LanguageRegistry.addName(teleportSigil, LibItemNames.TELEPORTATION_SIGIL_D);
		LanguageRegistry.addName(wandUprising, LibItemNames.WAND_UPRISING_D);
		LanguageRegistry.addName(swordCondor, LibItemNames.SWORD_CONDOR_D);
		LanguageRegistry.addName(deathRune, LibItemNames.DEATH_RUNE_D);
		LanguageRegistry.addName(silkSword, LibItemNames.SILK_SWORD_D);
		LanguageRegistry.addName(fortuneMaul, LibItemNames.FORTUNE_MAUL_D);
		LanguageRegistry.addName(enderMirror, LibItemNames.ENDER_MIRROR_D);
		LanguageRegistry.addName(goliathLegs, LibItemNames.GOLIATH_LEGS_D);
		LanguageRegistry.addName(darkGas, LibItemNames.DARK_GAS_D);
		LanguageRegistry.addName(gasRemover, LibItemNames.GAS_REMOVER_D);
		LanguageRegistry.addName(cleansingTalisman, LibItemNames.CLEANSING_TALISMAN_D);
		LanguageRegistry.addName(fluxDetector, LibItemNames.FLUX_DETECTOR_D);
		LanguageRegistry.addName(researchShareBook, LibItemNames.RESEARCH_SHARE_BOOK_D);
		LanguageRegistry.addName(brightNitor, LibItemNames.BRIGHT_NITOR_D);
		LanguageRegistry.addName(scythe, LibItemNames.SCYTHE_D);
		LanguageRegistry.addName(rainwaterBottle, LibItemNames.RAINWATER_BOTTLE_D);
		LanguageRegistry.addName(lovePotion, LibItemNames.LOVE_POTION_D);
		LanguageRegistry.addName(soulMould, LibItemNames.SOUL_MOULD_D);
		LanguageRegistry.addName(connector, LibItemNames.CONNECTOR_D);
	}

	public static void applyObjectTags() {
		ObjectTags tags = new ObjectTags().add(EnumTag.MAGIC, 48).add(EnumTag.VOID, 12).add(EnumTag.CRYSTAL, 15);
		ThaumcraftApi.registerObjectTag(wandTinkerer.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.LIGHT, 12).add(EnumTag.WIND, 4).add(EnumTag.POWER, 3);
		ThaumcraftApi.registerObjectTag(glowstoneGas.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.MAGIC, 15).add(EnumTag.CLOTH, 12).add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.registerObjectTag(spellCloth.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.TIME, 12).add(EnumTag.MECHANISM, 12).add(EnumTag.POWER, 9);
		ThaumcraftApi.registerObjectTag(stopwatch.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.MAGIC, 24).add(EnumTag.CRYSTAL, 6).add(EnumTag.EXCHANGE, 12).add(EnumTag.TRAP, 8);
		ThaumcraftApi.registerObjectTag(wandDislocation.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.WOOD, 2).add(EnumTag.DARK, 1);
		ThaumcraftApi.registerObjectTag(nametag.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.EVIL, 12).add(EnumTag.KNOWLEDGE, 12).add(EnumTag.TRAP, 24).add(EnumTag.MAGIC, 8);
		ThaumcraftApi.registerObjectTag(xpTalisman.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.MECHANISM, 12).add(EnumTag.MOTION, 8).add(EnumTag.ELDRITCH, 12);
		ThaumcraftApi.registerObjectTag(teleportSigil.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.FLIGHT, 8).add(EnumTag.WIND, 8).add(EnumTag.MOTION, 9).add(EnumTag.POWER, 20).add(EnumTag.MAGIC, 16);
		ThaumcraftApi.registerObjectTag(wandUprising.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.FLIGHT, 12).add(EnumTag.WIND, 24).add(EnumTag.MOTION, 20).add(EnumTag.POWER, 24).add(EnumTag.MAGIC, 30);
		ThaumcraftApi.registerObjectTag(swordCondor.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.DEATH, 16).add(EnumTag.TRAP, 12).add(EnumTag.POWER, 12).add(EnumTag.MAGIC, 22);
		ThaumcraftApi.registerObjectTag(deathRune.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.DARK, 1);
		ThaumcraftApi.registerComplexObjectTag(darkQuartz.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.HEAL, 16).add(EnumTag.KNOWLEDGE, 18).add(EnumTag.WATER, 6).add(EnumTag.MAGIC, 10).add(EnumTag.TOOL, 12);
		ThaumcraftApi.registerObjectTag(cleansingTalisman.itemID, LibMisc.TAG_META_WILDCARD, tags);

		for(EnumTag tag : EnumTag.class.getEnumConstants()) {
			tags = new ObjectTags().add(tag, 1);
			ThaumcraftApi.registerObjectTag(rune.itemID, tag.id, tags);
		}

		tags = new ObjectTags().add(EnumTag.WEATHER, 2).add(EnumTag.WATER, 1).add(EnumTag.CRYSTAL, 1);
		ThaumcraftApi.registerObjectTag(rainwaterBottle.itemID, LibMisc.TAG_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.LIFE, 4).add(EnumTag.KNOWLEDGE, 6).add(EnumTag.CRYSTAL, 2).add(EnumTag.SPIRIT, 4);
		ThaumcraftApi.registerObjectTag(soulMould.itemID, LibMisc.TAG_META_WILDCARD, tags);
	}
}
