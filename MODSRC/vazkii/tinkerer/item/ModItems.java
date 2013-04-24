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

	public static void initItems() {
		wandTinkerer = new ItemWandTinkerer(LibItemIDs.idWandTinkerer).setUnlocalizedName(LibItemNames.WAND_TINKERER);

		applyObjectTags();
		nameItems();
	}

	private static void nameItems() {
		LanguageRegistry.addName(wandTinkerer, LibItemNames.WAND_TINKERER_D);
	}

	private static void applyObjectTags() {
		ObjectTags tags = new ObjectTags().add(EnumTag.MAGIC, 48).add(EnumTag.VOID, 12).add(EnumTag.CRYSTAL, 15);
		ThaumcraftApi.registerObjectTag(wandTinkerer.itemID, LibMisc.TAG_META_WILDCARD, tags);
	}

}
