package vazkii.tinkerer.common.block.fire;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import vazkii.tinkerer.common.lib.LibBlockNames;
import vazkii.tinkerer.common.registry.ThaumicTinkererRecipe;
import vazkii.tinkerer.common.research.IRegisterableResearch;

import java.util.HashMap;

public class BlockFireAir extends BlockFireBase {
	@Override
	public String getBlockName() {
		return LibBlockNames.BLOCK_FIRE_AIR;
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
		result.put(Blocks.log, Blocks.sand);
		result.put(Blocks.leaves, Blocks.sandstone);
		result.put(Blocks.leaves2, Blocks.sandstone);
		result.put(Blocks.log2, Blocks.sand);
		result.put(Blocks.ice, Blocks.glass);
		result.put(Blocks.water, Blocks.cake);
		return result;
	}
}

