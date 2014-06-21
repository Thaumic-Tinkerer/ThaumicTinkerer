package vazkii.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.HashMap;

public class BlockFireIgnis extends BlockFireBase {

	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_FIRE;
	}

	@Override
	public IRegisterableResearch getResearchItem() {
		return null;
	}

	@Override
	public ThaumicTinkererRecipe getRecipeItem() {
		return null;
	}

	@Override
	public HashMap<Block, Block> getBlockTransformation() {
		HashMap<Block, Block> result = new HashMap<Block, Block>();
		result.put(Blocks.grass, Blocks.netherrack);
		result.put(Blocks.dirt, Blocks.netherrack);
		result.put(Blocks.sand, Blocks.soul_sand);
		result.put(Blocks.gravel, Blocks.soul_sand);
		result.put(Blocks.clay, Blocks.glowstone);
		result.put(Blocks.coal_ore, Blocks.quartz_ore);
		result.put(Blocks.iron_ore, Blocks.quartz_ore);
		result.put(Blocks.diamond_ore, Blocks.quartz_ore);
		result.put(Blocks.emerald_ore, Blocks.quartz_ore);
		result.put(Blocks.gold_block, Blocks.quartz_ore);
		result.put(Blocks.lapis_ore, Blocks.quartz_ore);
		result.put(Blocks.redstone_ore, Blocks.quartz_ore);
		result.put(Blocks.lit_redstone_ore, Blocks.quartz_ore);
		result.put(Blocks.lava, Blocks.water);
		result.put(Blocks.wheat, Blocks.nether_wart);
		result.put(Blocks.potatoes, Blocks.nether_wart);
		result.put(Blocks.carrots, Blocks.nether_wart);
		result.put(Blocks.red_flower, Blocks.brown_mushroom);
		result.put(Blocks.yellow_flower, Blocks.yellow_flower);
		return result;
	}
}
