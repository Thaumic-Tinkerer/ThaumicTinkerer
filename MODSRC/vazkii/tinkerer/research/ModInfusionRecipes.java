package vazkii.tinkerer.research;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import thaumcraft.api.EnumTag;
import thaumcraft.api.ObjectTags;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.common.Config;
import vazkii.tinkerer.block.ModBlocks;
import vazkii.tinkerer.item.ModItems;
import vazkii.tinkerer.lib.LibBlockNames;
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

		tags = new ObjectTags().add(EnumTag.CLOTH, 4).add(EnumTag.MAGIC, 6).add(EnumTag.EXCHANGE, 8);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.SPELL_CLOTH_R, LibItemNames.SPELL_CLOTH_R, 55, tags, new ItemStack(ModItems.spellCloth),
				" C ", "CEC", " C ",
				'C', new ItemStack(Config.itemResource, 1, 7),
				'E', new ItemStack(Item.expBottle));

		tags = new ObjectTags().add(EnumTag.TIME, 12).add(EnumTag.MECHANISM, 20);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.STOPWATCH_R, LibItemNames.STOPWATCH_R, 85, tags, new ItemStack(ModItems.stopwatch),
				" Q ", "QCQ", " Q ",
				'Q', new ItemStack(Item.netherQuartz),
				'C', new ItemStack(Item.pocketSundial));

		tags = new ObjectTags().add(EnumTag.TRAP, 12).add(EnumTag.EXCHANGE, 14);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.WAND_DISLOCATION_R, LibItemNames.WAND_DISLOCATION_R, 90, tags, new ItemStack(ModItems.wandDislocation),
				" H", "W ",
				'H', new ItemStack(Config.itemPortableHole),
				'W', new ItemStack(Config.itemWandTrade));

		tags = new ObjectTags().add(EnumTag.FLUX, 24).add(EnumTag.EXCHANGE, 48);
		ThaumcraftApi.addInfusionCraftingRecipe(LibBlockNames.TRANSMUTATOR_R, LibBlockNames.TRANSMUTATOR_R, 320, tags, new ItemStack(ModBlocks.transmutator),
				"ITI", "WFW", "WCW",
				'W', new ItemStack(Config.blockWooden),
				'T', new ItemStack(Config.itemWandTrade),
				'I', new ItemStack(Config.itemResource, 1, 2),
				'F', new ItemStack(Config.itemResource, 1, 8),
				'C', new ItemStack(Config.blockCrucible));

		tags = new ObjectTags().add(EnumTag.EVIL, 6).add(EnumTag.TRAP, 18).add(EnumTag.KNOWLEDGE, 12);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.XP_TALISMAN_R, LibItemNames.XP_TALISMAN_R, 140, tags, new ItemStack(ModItems.xpTalisman),
				"OGO", "GBG", "OGO",
				'O', new ItemStack(Block.obsidian),
				'G', new ItemStack(Item.ingotGold),
				'B', new ItemStack(Config.itemResource, 1, 5));

		tags = new ObjectTags().add(EnumTag.FIRE, 8).add(EnumTag.ROCK, 6).add(EnumTag.METAL, 12);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.FIRE_BRACELET_R, LibItemNames.FIRE_BRACELET_R, 85, tags, new ItemStack(ModItems.fireBracelet),
				"O O", "ILI",
				'O', new ItemStack(Block.obsidian),
				'I', new ItemStack(Item.ingotIron),
				'L', new ItemStack(Item.bucketLava));

		tags = new ObjectTags().add(EnumTag.VOID, 12).add(EnumTag.MAGIC, 16);
		ThaumcraftApi.addInfusionCraftingRecipe(LibBlockNames.WARD_CHEST_R, LibBlockNames.WARD_CHEST_R, 25, tags, new ItemStack(ModBlocks.wardChest),
				"GGG", "IBI", "WCW",
				'G', new ItemStack(Config.blockCosmeticOpaque, 1, 2),
				'I', new ItemStack(Config.itemResource, 1, 2),
				'B', new ItemStack(Config.itemResource, 1, 5),
				'W', new ItemStack(Config.blockWooden),
				'C', new ItemStack(Block.chest));

		tags = new ObjectTags().add(EnumTag.MOTION, 16).add(EnumTag.ELDRITCH, 4).add(EnumTag.MECHANISM, 12);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.TELEPORTATION_SIGIL_R, LibItemNames.TELEPORTATION_SIGIL_R, 50, tags, new ItemStack(ModItems.teleportSigil),
				" O ", "OEO", " O ",
				'O', new ItemStack(Block.obsidian),
				'E', new ItemStack(Item.enderPearl));

		tags = new ObjectTags().add(EnumTag.FLIGHT, 12).add(EnumTag.WIND, 6);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.WAND_UPRISING_R, LibItemNames.WAND_UPRISING_R, 25, tags, new ItemStack(ModItems.wandUprising),
				" Q ", "QWQ", " Q ",
				'Q', new ItemStack(Config.itemResource, 1, 10),
				'W', new ItemStack(Config.itemWandLightning));

		tags = new ObjectTags().add(EnumTag.POWER, 14).add(EnumTag.FLIGHT, 6);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.SWORD_CONDOR_R, LibItemNames.SWORD_CONDOR_R, 25, tags, new ItemStack(ModItems.swordCondor),
				"I W", " S ", "  I",
				'I', new ItemStack(Config.itemResource, 1, 2),
				'S', new ItemStack(Config.itemSwordElemental),
				'W', new ItemStack(ModItems.wandUprising));

		tags = new ObjectTags().add(EnumTag.DEATH, 16).add(EnumTag.POWER, 12).add(EnumTag.MAGIC, 20);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.DEATH_RUNE_R, LibItemNames.DEATH_RUNE_R, 60, tags, new ItemStack(ModItems.deathRune),
				"TOT", "OBO", "TOT",
				'T', new ItemStack(Config.itemResource, 1, 2),
				'O', new ItemStack(Block.obsidian),
				'B', new ItemStack(Config.itemResource, 1, 5));

		tags = new ObjectTags().add(EnumTag.CONTROL, 16).add(EnumTag.MOTION, 8).add(EnumTag.MECHANISM, 16).add(EnumTag.MAGIC, 12);
		ThaumcraftApi.addInfusionCraftingRecipe(LibBlockNames.ANIMATION_TABLET_R, LibBlockNames.ANIMATION_TABLET_R, 25, tags, new ItemStack(ModBlocks.animationTablet),
				"GIG", "ICI",
				'G', new ItemStack(Item.ingotGold),
				'I', new ItemStack(Item.ingotIron),
				'C', new ItemStack(Config.itemGolemCore, 1, 0));

		tags = new ObjectTags().add(EnumTag.TOOL, 25).add(EnumTag.DARK, 4).add(EnumTag.MAGIC, 12).add(EnumTag.CLOTH, 100);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.SILK_SWORD_R, LibItemNames.SILK_SWORD_R, 100, tags, new ItemStack(ModItems.silkSword),
				"O1O", "2D3", "O4O",
				'1', new ItemStack(Config.itemSwordThaumium),
				'2', new ItemStack(Config.itemAxeThaumium),
				'3', new ItemStack(Config.itemPickThaumium),
				'4', new ItemStack(Config.itemShovelThaumium),
				'O', new ItemStack(Block.obsidian),
				'D', new ItemStack(Item.diamond));

		tags = new ObjectTags().add(EnumTag.TOOL, 25).add(EnumTag.DARK, 4).add(EnumTag.MAGIC, 12).add(EnumTag.VALUABLE, 25);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.FORTUNE_MAUL_R, LibItemNames.FORTUNE_MAUL_R, 100, tags, new ItemStack(ModItems.fortuneMaul),
				"O1O", "2D3", "O4O",
				'1', new ItemStack(Config.itemHoeThaumium),
				'2', new ItemStack(Config.itemAxeThaumium),
				'3', new ItemStack(Config.itemPickThaumium),
				'4', new ItemStack(Config.itemShovelThaumium),
				'O', new ItemStack(Block.obsidian),
				'D', new ItemStack(Item.diamond));

		tags = new ObjectTags().add(EnumTag.TOOL, 8).add(EnumTag.VISION, 6).add(EnumTag.ELDRITCH, 12);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.ENDER_MIRROR_R, LibItemNames.ENDER_MIRROR_R, 25, tags, new ItemStack(ModItems.enderMirror),
				"E C", " M ", "O E",
				'O', new ItemStack(Block.obsidian),
				'C', new ItemStack(Block.enderChest),
				'M', new ItemStack(Config.itemHandMirror),
				'E', new ItemStack(Item.enderPearl));
		
		tags = new ObjectTags().add(EnumTag.BEAST, 16).add(EnumTag.ARMOR, 16);
		ThaumcraftApi.addInfusionCraftingRecipe(LibItemNames.GOLIATH_LEGS_R, LibItemNames.GOLIATH_LEGS_R, 80, tags, new ItemStack(ModItems.goliathLegs),
				"LLL", "LRL", "L L",
				'L', new ItemStack(Item.leather),
				'R', new ItemStack(Config.itemLegsRobe));
	}
}
