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
 * File Created @ [25 Apr 2013, 11:46:35 (GMT)]
 */
package vazkii.tinkerer.block;

import net.minecraft.block.Block;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import vazkii.tinkerer.lib.LibBlockIDs;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibMisc;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class ModBlocks {

	public static Block glowstoneGas;
	public static Block transmutator;
	public static Block wardChest;

	public static void initBlocks() {
		glowstoneGas = new BlockGlowstoneGas(LibBlockIDs.idGlowstoneGas).setUnlocalizedName(LibBlockNames.GLOWSTONE_GAS);
		transmutator = new BlockTransmutator(LibBlockIDs.idTransmutator).setUnlocalizedName(LibBlockNames.TRANSMUTATOR);
		wardChest = new BlockWardChest(LibBlockIDs.idWardChest).setUnlocalizedName(LibBlockNames.WARD_CHEST);

		registerBlocks();
		nameBlocks();
		registerObjectTags();
	}

	private static void registerBlocks() {
		GameRegistry.registerBlock(glowstoneGas, LibBlockNames.GLOWSTONE_GAS);
		GameRegistry.registerBlock(transmutator, LibBlockNames.TRANSMUTATOR);
		GameRegistry.registerBlock(wardChest, LibBlockNames.WARD_CHEST);
	}

	private static void nameBlocks() {
		LanguageRegistry.addName(glowstoneGas, LibBlockNames.GLOWSTONE_GAS_D);
		LanguageRegistry.addName(transmutator, LibBlockNames.TRANSMUTATOR_D);
		LanguageRegistry.addName(wardChest, LibBlockNames.WARD_CHEST_D);
	}

	private static void registerObjectTags() {
		ObjectTags tags = new ObjectTags().add(EnumTag.WOOD, 16).add(EnumTag.EXCHANGE, 64).add(EnumTag.FLUX, 24).add(EnumTag.MECHANISM, 8).add(EnumTag.MAGIC, 16);
		ThaumcraftApi.registerObjectTag(transmutator.blockID, LibMisc.CRAFTING_META_WILDCARD, tags);
	}

}
