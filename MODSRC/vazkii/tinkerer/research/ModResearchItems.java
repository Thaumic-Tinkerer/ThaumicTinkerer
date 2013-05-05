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

import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.common.Config;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibItemNames;

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

	public static void registerModResearchItems() {
		ObjectTags tags = new ObjectTags().add(EnumTag.MAGIC, 16).add(EnumTag.VOID, 8);
		wandTinkerer = new ResearchItem(LibItemNames.WAND_TINKERER_R, tags, -5, 3, ModItems.wandTinkerer).setParentsHidden(Config.researchGoggles).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.LIGHT, 7).add(EnumTag.WIND, 4).add(EnumTag.POWER, 3);
		glowstoneGas = new ResearchItem(LibItemNames.GLOWSTONE_GAS_R, tags, 5, 2, ModItems.glowstoneGas).setParents(Config.researchNitor).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.CLOTH, 9).add(EnumTag.MAGIC, 8).add(EnumTag.EXCHANGE, 6);
		spellCloth = new ResearchItem(LibItemNames.SPELL_CLOTH_R, tags, -6, 1, ModItems.spellCloth).setParents(Config.researchFabric).setParentsHidden(Config.researchUTFT).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.TIME, 4).add(EnumTag.MECHANISM, 8);
		stopwatch = new ResearchItem(LibItemNames.STOPWATCH_R, tags, -2, 24, ModItems.stopwatch).setParents(Config.researchTTOE).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.TRAP, 10).add(EnumTag.EXCHANGE, 8).add(EnumTag.CRYSTAL, 4);
		wandDislocation = new ResearchItem(LibItemNames.WAND_DISLOCATION_R, tags, -1, 7, ModItems.wandDislocation).setParents(Config.researchWandTrade).setParentsHidden(Config.researchPortableHole).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.WOOD, 1).add(EnumTag.DARK, 1);
		nametag = new ResearchItem(LibItemNames.NAMETAG_R, tags, 4, 18, ModItems.nametag).setParents(Config.researchGolemancy).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.FLUX, 12).add(EnumTag.EXCHANGE, 20);
		transmutator = new ResearchItem(LibBlockNames.TRANSMUTATOR_R, tags, -5, 24, ModBlocks.transmutator).setParents(Config.researchCrystalCore).setParentsHidden(Config.researchWandTrade).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.EVIL, 8).add(EnumTag.TRAP, 16).add(EnumTag.KNOWLEDGE, 12);
		xpTalisman = new ResearchItem(LibItemNames.XP_TALISMAN_R, tags, -4, 14, ModItems.xpTalisman).setParents(Config.researchJarBrain).setHidden().registerResearchItem();
	
		tags = new ObjectTags().add(EnumTag.FIRE, 8).add(EnumTag.ROCK, 12).add(EnumTag.METAL, 4);
		fireBracelet = new ResearchItem(LibItemNames.FIRE_BRACELET_R, tags, -4, 9, ModItems.fireBracelet).setParents(Config.researchArcaneBellows).setHidden().registerResearchItem();
	}
}
