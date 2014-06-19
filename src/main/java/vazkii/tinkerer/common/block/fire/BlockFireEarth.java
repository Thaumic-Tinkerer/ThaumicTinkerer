package vazkii.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.HashMap;

public class BlockFireEarth extends BlockFireBase {
	public BlockFireEarth() {
		super();
	}

	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_EARTH;
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
		result.put(Blocks.sand, Blocks.dirt);
		result.put(Blocks.gravel, Blocks.clay);
		result.put(Blocks.nether_brick, Blocks.planks);
		result.put(Blocks.nether_brick_fence, Blocks.fence);
		result.put(Blocks.nether_brick_stairs, Blocks.oak_stairs);
		result.put(Blocks.cactus, Blocks.log);
		result.put(Blocks.snow_layer, Blocks.tallgrass);
		result.put(Blocks.stone, Blocks.monster_egg);
		result.put(Blocks.mob_spawner, Blocks.iron_block);
		return result;
	}
}
