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
import thaumcraft.common.blocks.BlockWardedItem;
import vazkii.tinkerer.item.ItemDarkQuartzBlock;
import vazkii.tinkerer.item.ItemDarkQuartzSlab;
import vazkii.tinkerer.item.ItemPhantomStoneBlock;
import vazkii.tinkerer.lib.LibBlockIDs;
import vazkii.tinkerer.lib.LibBlockNames;
import vazkii.tinkerer.lib.LibMisc;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public final class ModBlocks {

	public static Block glowstoneGas;
	public static Block transmutator;
	public static Block wardChest;
	public static Block darkQuartz;
	public static Block darkQuartzStairs;
	public static Block darkQuartzSlab;
	public static Block darkQuartzSlabFull;
	public static Block animationTablet;
	public static Block darkGas;
	public static Block phantomStone;

	public static void initBlocks() {
		glowstoneGas = new BlockGlowstoneGas(LibBlockIDs.idGlowstoneGas).setUnlocalizedName(LibBlockNames.GLOWSTONE_GAS);
		transmutator = new BlockTransmutator(LibBlockIDs.idTransmutator).setUnlocalizedName(LibBlockNames.TRANSMUTATOR);
		wardChest = new BlockWardChest(LibBlockIDs.idWardChest).setUnlocalizedName(LibBlockNames.WARD_CHEST);
		darkQuartz = new BlockDarkQuartz(LibBlockIDs.idDarkQuartz).setUnlocalizedName(LibBlockNames.DARK_QUARTZ);
		darkQuartzStairs = new BlockDarkQuartzStairs(LibBlockIDs.idDarkQuartzStairs).setUnlocalizedName(LibBlockNames.DARK_QUARTZ_STAIRS);
		darkQuartzSlab = new BlockDarkQuartzSlab(LibBlockIDs.idDarkQuartzSlab, false).setUnlocalizedName(LibBlockNames.DARK_QUARTZ_SLAB);
		darkQuartzSlabFull = new BlockDarkQuartzSlab(LibBlockIDs.idDarkQuartzSlabFull, true).setUnlocalizedName(LibBlockNames.DARK_QUARTZ_SLAB);
		animationTablet = new BlockAnimationTablet(LibBlockIDs.idAnimationTablet).setUnlocalizedName(LibBlockNames.ANIMATION_TABLET);
		darkGas = new BlockDarkGas(LibBlockIDs.idDarkGas).setUnlocalizedName(LibBlockNames.DARK_GAS);
		phantomStone = new BlockPhantomStone(LibBlockIDs.idPhantomStone).setUnlocalizedName(LibBlockNames.PHANTOM_STONE);

		registerBlocks();
		nameBlocks();
	}

	private static void registerBlocks() {
		GameRegistry.registerBlock(glowstoneGas, LibBlockNames.GLOWSTONE_GAS);
		GameRegistry.registerBlock(transmutator, LibBlockNames.TRANSMUTATOR);
		GameRegistry.registerBlock(wardChest, LibBlockNames.WARD_CHEST);
		GameRegistry.registerBlock(darkQuartz, ItemDarkQuartzBlock.class, LibBlockNames.DARK_QUARTZ);
		GameRegistry.registerBlock(darkQuartzStairs, LibBlockNames.DARK_QUARTZ_STAIRS);
		GameRegistry.registerBlock(darkQuartzSlab, ItemDarkQuartzSlab.class, LibBlockNames.DARK_QUARTZ_SLAB);
		GameRegistry.registerBlock(darkQuartzSlabFull, ItemDarkQuartzSlab.class, LibBlockNames.DARK_QUARTZ_SLAB_FULL);
		GameRegistry.registerBlock(animationTablet, LibBlockNames.ANIMATION_TABLET);
		GameRegistry.registerBlock(darkGas, LibBlockNames.DARK_GAS);
		GameRegistry.registerBlock(phantomStone, ItemPhantomStoneBlock.class, LibBlockNames.PHANTOM_STONE);
	}

	private static void nameBlocks() {
		LanguageRegistry.addName(transmutator, LibBlockNames.TRANSMUTATOR_D);
		LanguageRegistry.addName(wardChest, LibBlockNames.WARD_CHEST_D);
		LanguageRegistry.addName(darkQuartzStairs, LibBlockNames.DARK_QUARTZ_STAIRS_D);
		LanguageRegistry.addName(animationTablet, LibBlockNames.ANIMATION_TABLET_D);
		LanguageRegistry.addName(phantomStone, LibBlockNames.PHANTOM_STONE_D);
	}

	public static void applyObjectTags() {
		ObjectTags tags = new ObjectTags().add(EnumTag.WOOD, 16).add(EnumTag.EXCHANGE, 64).add(EnumTag.FLUX, 24).add(EnumTag.MECHANISM, 8).add(EnumTag.MAGIC, 16);
		ThaumcraftApi.registerObjectTag(transmutator.blockID, LibMisc.CRAFTING_META_WILDCARD, tags);

		tags = new ObjectTags().add(EnumTag.WOOD, 16).add(EnumTag.VOID, 18).add(EnumTag.MAGIC, 12);
		ThaumcraftApi.registerObjectTag(wardChest.blockID, LibMisc.CRAFTING_META_WILDCARD, tags);
	}

}
