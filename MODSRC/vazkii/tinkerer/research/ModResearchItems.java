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
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibItemNames;

public final class ModResearchItems {

	public static ResearchItem wandTinkerer;
	public static ResearchItem glowstoneGas;

	public static void registerModResearchItems() {
		ObjectTags tags = new ObjectTags().add(EnumTag.MAGIC, 16).add(EnumTag.VOID, 8);
		wandTinkerer = new ResearchItem(LibItemNames.WAND_TINKERER_R, tags, -5, 3, ModItems.wandTinkerer).setParents(Config.researchGoggles).registerResearchItem();

		tags = new ObjectTags().add(EnumTag.LIGHT, 7).add(EnumTag.WIND, 4).add(EnumTag.POWER, 3);
		glowstoneGas = new ResearchItem(LibItemNames.GLOWSTONE_GAS_R, tags, 5, 2, ModItems.glowstoneGas).setParents(Config.researchNitor).registerResearchItem();
	}
}
