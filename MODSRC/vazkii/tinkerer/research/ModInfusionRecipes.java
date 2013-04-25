package vazkii.tinkerer.research;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.Config;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibItemNames;
import vazkii.tinkerer.lib.LibMisc;

public final class ModInfusionRecipes {

	public static void initInfusionRecipes() {
		ObjectTags tags = new ObjectTags().add(EnumTag.MAGIC, 16).add(EnumTag.VOID, 12);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.WAND_TINKERER_R, LibItemNames.WAND_TINKERER_R, 85, tags, new ItemStack(ModItems.wandTinkerer),
				"SSS", "SWS", "SSS",
				'S', new ItemStack(Config.itemShard, 1, 4),
				'W', new ItemStack(Config.itemWandCastingApprentice, 1, LibMisc.CRAFTING_META_WILDCARD));

		tags = new ObjectTags().add(EnumTag.WIND, 8);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.GLOWSTONE_GAS_R, LibItemNames.GLOWSTONE_GAS_R, 5, tags, new ItemStack(ModItems.glowstoneGas),
				"GPG", " N ",
				'G', new ItemStack(Block.glowStone),
				'P', new ItemStack(Config.itemEssence, 1, 0),
				'N', new ItemStack(Config.itemResource, 1, 1));
	}

}
