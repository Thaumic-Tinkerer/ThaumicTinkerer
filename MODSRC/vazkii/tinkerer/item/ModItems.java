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

import net.minecraft.item.Item;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
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

	public static void initItems() {
		wandTinkerer = new ItemWandTinkerer(LibItemIDs.idWandTinkerer).setUnlocalizedName(LibItemNames.WAND_TINKERER);
		glowstoneGas = new ItemGlowstoneGas(LibItemIDs.idGlowstoneGas).setUnlocalizedName(LibItemNames.GLOWSTONE_GAS);
		spellCloth = new ItemSpellCloth(LibItemIDs.idSpellCloth).setUnlocalizedName(LibItemNames.SPELL_CLOTH);
		stopwatch = new ItemStopwatch(LibItemIDs.idStopwatch).setUnlocalizedName(LibItemNames.STOPWATCH);
		wandDislocation = new ItemWandDislocation(LibItemIDs.idWandDislocation).setUnlocalizedName(LibItemNames.WAND_DISLOCATION);
		nametag = new ItemNametag(LibItemIDs.idNametag).setUnlocalizedName(LibItemNames.NAMETAG);
		xpTalisman = new ItemXPTalisman(LibItemIDs.idXpTalisman).setUnlocalizedName(LibItemNames.XP_TALISMAN);
		fireBracelet = new ItemFireBracelet(LibItemIDs.idFireBracelet).setUnlocalizedName(LibItemNames.FIRE_BRACELET);

		nameItems();
		applyObjectTags();
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
	}

	private static void applyObjectTags() {
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

	}
}
